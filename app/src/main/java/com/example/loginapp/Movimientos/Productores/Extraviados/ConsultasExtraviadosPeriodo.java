package com.example.loginapp.Movimientos.Productores.Extraviados;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Index;
import com.example.loginapp.Movimientos.Productores.DatePickerFragment;
import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ConsultasExtraviadosPeriodo extends AppCompatActivity {
    String emisorname,ff,fi;
    EditText FI, FF;

    //para tabla
    JSONArray arreglo;
    TableLayout tbtEP;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulExtraviadosMovimiProductores.php";
    MaterialButton volver;
    Button CSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_extraviados_periodo);

        //variables sesion
        emisorname = Index.obtenerrol(ConsultasExtraviadosPeriodo.this, Index.no);
        Toast.makeText(ConsultasExtraviadosPeriodo.this, emisorname, Toast.LENGTH_SHORT).show();

        tbtEP = findViewById(R.id.tablaO);
        //limpiar tabla
        tbtEP.removeAllViews();//remueve columnas


        requestQueue= Volley.newRequestQueue(ConsultasExtraviadosPeriodo.this);
        progressDialog=new ProgressDialog(ConsultasExtraviadosPeriodo.this);

        //botones
        volver=findViewById(R.id.btnreg1);
        FI = findViewById(R.id.FI);
        FF = findViewById(R.id.FF);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
        FI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrircalendario();
            }
        });
        FF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrircalendario2();
            }
        });
    }
    public void abrircalendario() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + "/" + (month + 1) + "/" + day;
                FI.setText(selectedDate);//imprime en el cuadro
                fi=selectedDate;
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void abrircalendario2(){
        DatePickerFragment fragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String Date = year + "/" + (month+1) + "/" + day;
                FF.setText(Date);//imprime en el cuadro
                ff=Date;
                cargartabla();

            }
        });
        fragment.show(getSupportFragmentManager(), "datePicker");

    }

    private void cargartabla() {
        CSV=findViewById(R.id.csv);
        // Toast.makeText(this, "Fecha inicial: "+fi, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "Fecha final:  "+ff, Toast.LENGTH_SHORT).show();

        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        //limpiar tabla
        tbtEP.removeAllViews();//remueve columnas

        //peticion a servidor
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
                        tbtEP.addView(registro);
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
                parametros.put("opcion","Pproductor");
                parametros.put("nombre",emisorname);
                parametros.put("fi",fi);
                parametros.put("ff",ff);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
        //agregar el metodo csv
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
                FileOutputStream out = openFileOutput("ExtraviadosPerio"+emisorname+".csv", MODE_PRIVATE);
                out.write((data.toString()).getBytes("ISO-8859-1"));
                Toast.makeText(this, "Guardado en "+getFilesDir()+"/"+"ExtraviadosPerio"+emisorname+".csv", Toast.LENGTH_SHORT).show();
                out.close();


                //exporting
                Context context = getApplicationContext();
                File filelocation = new File(getFilesDir(), "ExtraviadosPerio"+emisorname+".csv");
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