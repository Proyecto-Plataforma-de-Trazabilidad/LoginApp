package com.example.loginapp.Movimientos.ERP.Salidas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.loginapp.Index;
import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConsuGS_ERP extends AppCompatActivity {

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulSalidas_Gen.php";

    TableLayout tbtE,tbtDetE;
    MaterialButton btnregresa;
    String emisor,emisorname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_gs_erp);

        //variables sesion correo
        emisor= MainActivity.obtenerusuario(ConsuGS_ERP.this,MainActivity.m);
        //variables sesion nombre
        emisorname = Index.obtenerrol(ConsuGS_ERP.this, Index.no);

        TextView nom=findViewById(R.id.cat);
        nom.setText(Html.fromHtml("<b>Municipio: </b>"+emisorname));//nombre del usuario

        tbtE=findViewById(R.id.tablaEntregas);
        tbtDetE=findViewById(R.id.tabladetEn);

        requestQueue= Volley.newRequestQueue(ConsuGS_ERP.this);
        progressDialog=new ProgressDialog(ConsuGS_ERP.this);

        cargartabla();

        btnregresa= (MaterialButton) findViewById(R.id.btnreg1);
        btnregresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

    }

    private void cargartabla() {

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

                        View registro = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_salidas_general, null, false);

                        TextView ids = registro.findViewById(R.id.col1);
                        TextView idc = registro.findViewById(R.id.col2);
                        TextView res = registro.findViewById(R.id.col3);
                        TextView cantidad = registro.findViewById(R.id.col4);
                        TextView fecha = registro.findViewById(R.id.col5);

                        //agregar bton
                        ImageButton boton=registro.findViewById(R.id.btndetalle);

                        //rescata los valores
                        String idSalida=jsonObject.getString("IdSalida");
                        String idConte=jsonObject.getString("IdContenedor");
                        String respon=jsonObject.getString("Responsable");
                        String can=jsonObject.getString("Cantidad");
                        String fech=jsonObject.getString("fecha");


                        //asigna los valores rescatador
                        ids.setText(idSalida);
                        idc.setText(idConte);
                        res.setText(respon);
                        cantidad.setText(can);
                        fecha.setText(fech);


                        //un valor id valido a boton segun cada fila que se genere
                        boton.setId(View.generateViewId());
                        boton.setTag(idSalida);//asigna su identificador
                        boton.setImageDrawable(getDrawable(R.drawable.detalle));

                        boton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id=v.getTag().toString();
                                CargarDetalle(idConte);
                            }
                        });

                        tbtE.addView(registro);

                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }//fin onresponse
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //Mostrar el error de Volley exacto a través de la librería
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }//fin onerrorResponse
        }){
            protected Map<String,String> getParams(){
                Map<String, String> parametros=new HashMap<>();
                parametros.put("opcion","ConsulSalidasGen");
                parametros.put("correo",emisor);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CargarDetalle(String id) {
        tbtDetE.removeAllViews();//remueve columnas

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result2=new JSONArray(response);
                    for (int i = 0; i < result2.length();i++ ) {
                        JSONObject jsonObject = result2.getJSONObject(i);

                        View registroD = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_detallentregas, null, false);

                        TextView tc= registroD.findViewById(R.id.col1);
                        TextView ori = registroD.findViewById(R.id.col2);
                        TextView capa= registroD.findViewById(R.id.col3);
                        TextView desc = registroD.findViewById(R.id.col4);
                        TextView cs= registroD.findViewById(R.id.col5);


                        //rescata los valores
                        String cde=jsonObject.getString("Concepto");
                        String ede=jsonObject.getString("Origen");
                        String pde=jsonObject.getString("Capacidad");
                        String pes=jsonObject.getString("Descripcion");
                        String obser=jsonObject.getString("CapacidadStatus");

                        //asigna los valores rescatador
                        tc.setText(cde);
                        ori.setText(ede);
                        capa.setText(pde);
                        desc.setText(pes);
                        cs.setText(obser);

                        //agrega fila
                        tbtDetE.addView(registroD);
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
                parametros.put("opcion","DetCont");
                parametros.put("id",id);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }
}