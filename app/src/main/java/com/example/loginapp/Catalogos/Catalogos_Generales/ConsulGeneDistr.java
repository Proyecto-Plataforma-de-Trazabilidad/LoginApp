package com.example.loginapp.Catalogos.Catalogos_Generales;

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
import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Mapas.MapaDistribuidores;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsulGeneConteBinding;
import com.example.loginapp.databinding.ActivityConsulGeneDistrBinding;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConsulGeneDistr extends DrawerBaseActivity {
    ActivityConsulGeneDistrBinding activityConsulGeneDistrBinding;
    MaterialButton btnregresa,btnconsulta;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI="https://campolimpiojal.com/android/ConsultasGenerales.php";
    TableLayout tbtdis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul_gene_distr);

        //aqui va lo del menu
        activityConsulGeneDistrBinding= ActivityConsulGeneDistrBinding.inflate(getLayoutInflater());
        setContentView(activityConsulGeneDistrBinding.getRoot());
        allowActivityTitle("Catálogos/General/Distribuidores");

        requestQueue= Volley.newRequestQueue(ConsulGeneDistr.this);
        progressDialog=new ProgressDialog(ConsulGeneDistr.this);


        btnregresa= (MaterialButton) findViewById(R.id.btnreg1);
        btnconsulta= (MaterialButton) findViewById(R.id.btnconsu1);
        btnregresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

        btnconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conxe= new Intent(ConsulGeneDistr.this, MapaDistribuidores.class);
                startActivity(conxe);

            }
        });
        CargarTabla();
    }//fin

    private void CargarTabla() {
        //tabla
        tbtdis=findViewById(R.id.tablaGD);
        tbtdis.removeAllViews();//remueve columnas

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

                        String name=jsonObject.getString("Nombre");
                        String dom=jsonObject.getString("Domicilio");
                        String lati=jsonObject.getString("Latitud");
                        String longi=jsonObject.getString("Longitud");



                        nombre.setText(name);
                        domicilio.setText(dom);
                        lat.setText(lati);
                        lon.setText(longi);

                        tbtdis.addView(registro);
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
                btnconsulta.setEnabled(false);
                progressDialog.dismiss();
                //Mostrar el error de Volley exacto a través de la librería
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String, String> parametros=new HashMap<>();
                parametros.put("opcion","distribuidores");
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }
}//finclass