package com.example.loginapp.Movimientos.Distribuidor.Ordenes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Index;
import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class consulGeneralDist extends AppCompatActivity {
    TableLayout tbtCOP,tbtdet;
    String emisorname;
    TextView nom;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulOrdenesMoviDistribuidores.php";
    JSONArray arreglo;
    MaterialButton volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul_general_distri);
        tbtCOP = findViewById(R.id.tablaO);
        tbtCOP.removeAllViews();//remueve columnas

        //variables sesion
        emisorname = Index.obtenerrol(consulGeneralDist.this, Index.no);
        Toast.makeText(consulGeneralDist.this, emisorname, Toast.LENGTH_SHORT).show();

        nom=findViewById(R.id.distribuidor);
        nom.setText(Html.fromHtml("<b>Distribuidor: </b>"+emisorname));

        requestQueue= Volley.newRequestQueue(consulGeneralDist.this);
        progressDialog=new ProgressDialog(consulGeneralDist.this);



        cargaOrdenes();

        //Boton para volver + Evento
        volver=findViewById(R.id.btnreg1);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

    }

    private void cargaOrdenes() {
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

                        View registro = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_ordenes, null, false);

                        TextView id = registro.findViewById(R.id.col1);
                        TextView distri = registro.findViewById(R.id.col2);
                        TextView fact = registro.findViewById(R.id.col3);
                        TextView rece = registro.findViewById(R.id.col4);

                        //agregar bton
                        ImageButton boton=registro.findViewById(R.id.btndetalle);

                        //rescata los valores
                        String idO=jsonObject.getString("IdOrden");
                        String distriO=jsonObject.getString("Distribuidor");
                        String factO=jsonObject.getString("NumFactura");
                        String receO=jsonObject.getString("NumReceta");

                        //asigna los valores rescatador
                        id.setText(idO);
                        distri.setText(distriO);
                        fact.setText(factO);
                        rece.setText(receO);

                        //un valor id valido a boton segun cada fila que se genere
                        boton.setId(View.generateViewId());
                        boton.setTag(idO);//asigna su identificador
                        boton.setImageDrawable(getDrawable(R.drawable.detalle));

                        boton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            //Carga detalles
                            public void onClick(View v) {
                                Toast.makeText(consulGeneralDist.this, "Pertenezco a la orden"+v.getTag(), Toast.LENGTH_SHORT).show();
                                String id=v.getTag().toString();

                                CargarDetalle(id);
                            }
                        });

                        arreglo=result;//para generar el csv

                        tbtCOP.addView(registro);
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
                parametros.put("opcion","OrdenDistribuidor");
                parametros.put("nombre",emisorname);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);


    }//fin cargar ordenes

    private void CargarDetalle(String id){
        tbtdet=findViewById(R.id.tabladetO);
        tbtdet.removeAllViews();//remueve columnas

        Toast.makeText(this, "ID:"+id, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result2=new JSONArray(response);
                    for (int i = 0; i < 5;i++ ) {
                        JSONObject jsonObject = result2.getJSONObject(i);

                        View registroD = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_detordenes, null, false);

                        TextView consec= registroD.findViewById(R.id.col1);
                        TextView quimico = registroD.findViewById(R.id.col2);
                        TextView envase= registroD.findViewById(R.id.col3);
                        TextView color = registroD.findViewById(R.id.col4);
                        TextView piezas= registroD.findViewById(R.id.col5);


                        //rescata los valores
                        String cdo=jsonObject.getString("Consecutivo");
                        String qdo=jsonObject.getString("Concepto");
                        String edo=jsonObject.getString("TipoEnvase");
                        String colordo=jsonObject.getString("Color");
                        String pdo=jsonObject.getString("CantidadPiezas");

                        //asigna los valores rescatador
                        consec.setText(cdo);
                        quimico.setText(qdo);
                        envase.setText(edo);
                        color.setText(colordo);
                        piezas.setText(pdo);

                        //agrega fila
                        tbtdet.addView(registroD);
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
                parametros.put("opcion","DetOrdDistribuidor");
                parametros.put("IdOrden",id);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }//fin cargar detalle

}