package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConsultaGeneral extends AppCompatActivity {

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI="https://proyectoapejal.000webhostapp.com/agenda/marcadoresjson.php";

    MaterialButton btnregresa,btnconsulta;
    TableLayout tbtCG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_general);

        requestQueue= Volley.newRequestQueue(ConsultaGeneral.this);
        progressDialog=new ProgressDialog(ConsultaGeneral.this);

        btnregresa= (MaterialButton) findViewById(R.id.btnreg);
        btnconsulta= (MaterialButton) findViewById(R.id.btnconsu);

        btnregresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regresa= new Intent(ConsultaGeneral.this,Index.class);
                startActivity(regresa);
            }
        });

        btnconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conxe= new Intent(ConsultaGeneral.this,Mapa.class);
                startActivity(conxe);
            }
        });
        CargarTabla();
    }//fin

    private void CargarTabla() {

        //tabla
        tbtCG=findViewById(R.id.tablaG);
        tbtCG.removeAllViews();//remueve columnas


        //------------
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    JSONArray result=new JSONArray(response);

                    for (int i = 0; i < result.length(); ) {

                        JSONObject jsonObject = result.getJSONObject(i);

                        View registro= LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_general,null,false);

                        TextView nombre=registro.findViewById(R.id.col1);
                        TextView domicilio=registro.findViewById(R.id.col2);
                        TextView lat=registro.findViewById(R.id.col3);
                        TextView lon=registro.findViewById(R.id.col4);

                        String name=jsonObject.getString("NombreCentro");
                        String dom=jsonObject.getString("Domicilio");
                        String lati=jsonObject.getString("Latitud");
                        String longi=jsonObject.getString("Longitud");



                        nombre.setText(name);
                        domicilio.setText(dom);
                        lat.setText(lati);
                        lon.setText(longi);

                        tbtCG.addView(registro);
                        i++;

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
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

}//fin class