package com.example.loginapp.Indexs.Movimientos.Productores.Extraviados;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Indexs.Index;
import com.example.loginapp.Indexs.Movimientos.Productores.ConsultasExtraviadosProductor;
import com.example.loginapp.Indexs.Movimientos.Productores.Ordenes.ConsulGeneralDelProductor;
import com.example.loginapp.Indexs.Movimientos.Productores.consultas_ordenesProductor;
import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ConsultaExtraviadosProductor extends AppCompatActivity {
    TableLayout tbtE;
    String emisorname;
    TextView nom;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulExtraviadosMovimiProductores.php";
    JSONArray arreglo;
    MaterialButton volver;
    Button CSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_extraviados_productor);

        tbtE = findViewById(R.id.tablaO);
        tbtE.removeAllViews();//remueve columnas

        //variables sesion
        emisorname = Index.obtenerrol(ConsultaExtraviadosProductor.this, Index.no);
        Toast.makeText(ConsultaExtraviadosProductor.this, emisorname, Toast.LENGTH_SHORT).show();

        nom=findViewById(R.id.productor);
        nom.setText(Html.fromHtml("<b>Productor: </b>"+emisorname));

        requestQueue= Volley.newRequestQueue(ConsultaExtraviadosProductor.this);
        progressDialog=new ProgressDialog(ConsultaExtraviadosProductor.this);

        CargarTabla();

        //botones
        volver=findViewById(R.id.btnreg1);


        //eventos botones
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
    }

    private void CargarTabla() {
        CSV=findViewById(R.id.csv);
        //------------
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray result=new JSONArray(response);
                    for (int i = 0; i < result.length();i++ ) {
                        JSONObject jsonObject = result.getJSONObject(i);

                        View registro = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_extraviados, null, false);

                        TextView E = registro.findViewById(R.id.col1);
                        TextView P = registro.findViewById(R.id.col2);
                        TextView A = registro.findViewById(R.id.col3);
                        TextView F = registro.findViewById(R.id.col4);


                        //rescata los valores
                        String En=jsonObject.getString("TipoEnvaseVacio");
                        String pz=jsonObject.getString("CantidadPiezas");
                        String acla=jsonObject.getString("Aclaracion");
                        String fecha=jsonObject.getString("fecha");

                        //asigna los valores rescatador
                        E.setText(En);
                        P.setText(pz);
                        A.setText(acla);
                        F.setText(fecha);

                        arreglo=result;//para generar el csv
                        tbtE.addView(registro);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //Mostrar el error de Volley exacto a través de la librería
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String, String> parametros=new HashMap<>();
                parametros.put("opcion","EProductor");
                parametros.put("nombre",emisorname);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
        CSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Export(arreglo);
            }
        });

    }//fin cargar tabla

    public void Export(JSONArray a){
        StringBuilder data=new StringBuilder();
        data.append("Tipo Envase,Cantidad Piezas,Aclaración,Fecha");

        try{
            for (int i=0; i<a.length();i++){
                JSONObject jsonObject = a.getJSONObject(i);
                String En=jsonObject.getString("TipoEnvaseVacio");
                String pz=jsonObject.getString("CantidadPiezas");
                String acla=jsonObject.getString("Aclaracion");
                String fecha=jsonObject.getString("fecha");

                //agrega datos
                data.append("\n "+En+","+pz+","+acla+","+fecha);
            }
            try{
                //saving the file into device
                FileOutputStream out = openFileOutput("ExtraviadosGeneral.csv", MODE_PRIVATE);
                out.write((data.toString()).getBytes("ISO-8859-1"));
                Toast.makeText(this, "Guardado en "+getFilesDir()+"/ExtraviadosGeneral.csv", Toast.LENGTH_SHORT).show();
                out.close();


                //exporting
                Context context = getApplicationContext();
                File filelocation = new File(getFilesDir(), "ExtraviadosGeneral.csv");
                Uri path = FileProvider.getUriForFile(context, "com.example.tabla.fileprovider", filelocation);


                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                fileIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.setClipData(ClipData.newRawUri("", path));
                fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                startActivity(Intent.createChooser(fileIntent, "Send mail"));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}