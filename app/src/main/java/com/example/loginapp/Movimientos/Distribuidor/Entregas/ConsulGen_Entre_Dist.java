package com.example.loginapp.Movimientos.Distribuidor.Entregas;

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
import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Index;
import com.example.loginapp.MainActivity;
import com.example.loginapp.Movimientos.Productores.Entregas.ConsuEntreProdEmD;
import com.example.loginapp.Movimientos.Productores.Ordenes.ConsulGeneralDelProductor;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsuEnDistBinding;
import com.example.loginapp.databinding.ActivityIndexBinding;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class ConsulGen_Entre_Dist extends DrawerBaseActivity {
    ActivityConsuEnDistBinding activityConsuEnDistBinding;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulEntregas_Dis_Muni_Cat_Erp.php";

    TableLayout tbtE,tbtDetE;
    MaterialButton btnregresa;
    String emisor,emisorname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_en_dist);

        //aqui va lo del menu
        activityConsuEnDistBinding= ActivityConsuEnDistBinding.inflate(getLayoutInflater());
        setContentView(activityConsuEnDistBinding.getRoot());
        allowActivityTitle("Entregas/General");

        //variables sesion correo
        emisor= MainActivity.obtenerusuario(ConsulGen_Entre_Dist.this,MainActivity.m);
        //variables sesion nombre
        emisorname = Index.obtenerrol(ConsulGen_Entre_Dist.this, Index.no);

        TextView nom=findViewById(R.id.cat);
        nom.setText(Html.fromHtml("<b>Distribuidor: </b>"+emisorname));//nombre del usuario

        tbtE=findViewById(R.id.tablaEntregas);
        tbtDetE=findViewById(R.id.tabladetEn);

        requestQueue= Volley.newRequestQueue(ConsulGen_Entre_Dist.this);
        progressDialog=new ProgressDialog(ConsulGen_Entre_Dist.this);

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

                        View registro = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_entregas_general, null, false);

                        TextView id = registro.findViewById(R.id.col1);
                        TextView prod = registro.findViewById(R.id.col2);
                        TextView res = registro.findViewById(R.id.col3);
                        TextView fecha = registro.findViewById(R.id.col4);

                        //agregar bton
                        ImageButton boton=registro.findViewById(R.id.btndetalle);

                        //rescata los valores
                        String idE=jsonObject.getString("IdEntrega");
                        String pE=jsonObject.getString("Nombre");
                        String resp=jsonObject.getString("ResponsableEntrega");
                        String fech=jsonObject.getString("fecha");


                        //asigna los valores rescatador
                        id.setText(idE);
                        prod.setText(pE);
                        res.setText(resp);
                        fecha.setText(fech);


                        //un valor id valido a boton segun cada fila que se genere
                        boton.setId(View.generateViewId());
                        boton.setTag(idE);//asigna su identificador
                        boton.setImageDrawable(getDrawable(R.drawable.detalle));

                        boton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Toast.makeText(ConsuEnGe_CAT.this, "pertenesco a "+v.getTag(), Toast.LENGTH_SHORT).show();
                                String id=v.getTag().toString();
                                CargarDetalle(id);
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
                parametros.put("opcion","ConsulEntradaG");
                parametros.put("correo",emisor);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }//fin cargartabla

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

                        TextView consec= registroD.findViewById(R.id.col1);
                        TextView envase = registroD.findViewById(R.id.col2);
                        TextView pz= registroD.findViewById(R.id.col3);
                        TextView peso = registroD.findViewById(R.id.col4);
                        TextView obs= registroD.findViewById(R.id.col5);


                        //rescata los valores
                        String cde=jsonObject.getString("Consecutivo");
                        String ede=jsonObject.getString("TipoEnvaseVacio");
                        String pde=jsonObject.getString("CantidadPiezas");
                        String pes=jsonObject.getString("Peso");
                        String obser=jsonObject.getString("Observaciones");

                        //asigna los valores rescatador
                        consec.setText(cde);
                        envase.setText(ede);
                        pz.setText(pde);
                        peso.setText(pes);
                        obs.setText(obser);

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
                parametros.put("opcion","DetEntrada");
                parametros.put("id",id);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }
}