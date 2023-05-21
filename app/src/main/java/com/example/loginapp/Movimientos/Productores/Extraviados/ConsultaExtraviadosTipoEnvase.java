package com.example.loginapp.Movimientos.Productores.Extraviados;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Index;
import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsultaExtraviadosTipoEnvaseBinding;
import com.example.loginapp.databinding.ActivityConsultasExtraviadosPeriodoBinding;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ConsultaExtraviadosTipoEnvase extends DrawerBaseActivity implements AdapterView.OnItemSelectedListener {
    TableLayout tbtETE;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulExtraviadosMovimiProductores.php";
    MaterialButton volver;
    Spinner cboEnvase;
    JSONArray arreglo;
    String[]Envases={"Rígidos lavables","Rígidos no lavables","Flexibles","Tapas","Cubetas","Cartón(Embalaje)","Tambos","Metal"};
    String emisor, emisorname;
    Button CSV;
    ActivityConsultaExtraviadosTipoEnvaseBinding activityConsultaExtraviadosTipoEnvaseBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_extraviados_tipo_envase);

        //aqui va lo del menu
        activityConsultaExtraviadosTipoEnvaseBinding= ActivityConsultaExtraviadosTipoEnvaseBinding.inflate(getLayoutInflater());
        setContentView(activityConsultaExtraviadosTipoEnvaseBinding.getRoot());
        allowActivityTitle("Extraviados/Tipo Envase");


        requestQueue= Volley.newRequestQueue(ConsultaExtraviadosTipoEnvase.this);
        progressDialog=new ProgressDialog(ConsultaExtraviadosTipoEnvase.this);

        ///variables sesion correo
        emisor= MainActivity.obtenerusuario(ConsultaExtraviadosTipoEnvase.this,MainActivity.m);

        //variables sesion nombre
        emisorname = Index.obtenerrol(ConsultaExtraviadosTipoEnvase.this, Index.no);

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

        cboEnvase=findViewById(R.id.cboenvase);
        ArrayAdapter<String> adapter=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,Envases);
        cboEnvase.setAdapter(adapter);
        cboEnvase.setOnItemSelectedListener(this);

        tbtETE = findViewById(R.id.tablaO);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String value=parent.getItemAtPosition(position).toString();
        //Toast.makeText(this, "Rescate: "+value, Toast.LENGTH_SHORT).show();
        tbtETE.removeAllViews();//remueve columnas
        CargarTabla(value);
    }
    private void CargarTabla(String value) {
        CSV=findViewById(R.id.csv);
        //Toast.makeText(this, "Recibi: "+value, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        tbtETE.addView(registro);
                    }
                    CSV.setEnabled(true);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                CSV.setEnabled(false);
                //Mostrar el error de Volley exacto a través de la librería
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String, String> parametros=new HashMap<>();
                parametros.put("opcion","TEProductor");
                parametros.put("correo",emisor);
                parametros.put("envase",value);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
        //generar csv
        CSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arreglo!=null){
                    Export(arreglo);
                }
                else{
                    Toast.makeText(ConsultaExtraviadosTipoEnvase.this, "La tabla aun no tiene datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
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
                FileOutputStream out = openFileOutput("ExtraviadosTEnv"+emisorname+".csv", MODE_PRIVATE);
                out.write((data.toString()).getBytes("ISO-8859-1"));
                Toast.makeText(this, "Guardado en "+getFilesDir()+"/"+"ExtraviadosTEnv"+emisorname+".csv", Toast.LENGTH_SHORT).show();
                out.close();


                //exporting
                Context context = getApplicationContext();
                File filelocation = new File(getFilesDir(), "ExtraviadosTEnv"+emisorname+".csv");
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}