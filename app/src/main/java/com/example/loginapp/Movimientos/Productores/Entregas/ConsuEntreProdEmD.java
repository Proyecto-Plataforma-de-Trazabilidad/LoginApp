package com.example.loginapp.Movimientos.Productores.Entregas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
import com.example.loginapp.MainActivity;
import com.example.loginapp.Movimientos.Productores.Ordenes.ConsultaOrdenesPeridoProductor;
import com.example.loginapp.R;
import com.example.loginapp.SetGet_Consultas.cboEntradas;
import com.example.loginapp.databinding.ActivityConsuEntreProdEmDBinding;
import com.example.loginapp.databinding.ActivityConsuEntreProdxCatBinding;
import com.google.android.material.button.MaterialButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class ConsuEntreProdEmD extends DrawerBaseActivity implements AdapterView.OnItemSelectedListener{
    AsyncHttpClient cliente;
    Spinner cbousuario;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulEntregasProductor.php";

    String e;
    TableLayout tbtE,tbtDetE;

    MaterialButton btnregresa;
    String emisor;
    ActivityConsuEntreProdEmDBinding activityConsuEntreProdEmDBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_entre_prod_em_d);

        //aqui va lo del menu
        activityConsuEntreProdEmDBinding=ActivityConsuEntreProdEmDBinding.inflate(getLayoutInflater());
        setContentView(activityConsuEntreProdEmDBinding.getRoot());
        allowActivityTitle("Entregas/Empresa");

        ///variables sesion correo
        emisor= MainActivity.obtenerusuario(ConsuEntreProdEmD.this,MainActivity.m);

        tbtE=findViewById(R.id.tablaEntregas);
        tbtDetE=findViewById(R.id.tabladetEn);

        cliente=new AsyncHttpClient();
        cbousuario =(Spinner) findViewById(R.id.cboERP);
        llenarspinner();

        requestQueue= Volley.newRequestQueue(ConsuEntreProdEmD.this);
        progressDialog=new ProgressDialog(ConsuEntreProdEmD.this);

        btnregresa= (MaterialButton) findViewById(R.id.btnreg1);
        btnregresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

    }

    private void llenarspinner() {
        String url="https://campolimpiojal.com/android/cboEntregasERP.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarspinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void cargarspinner(String respuesta) {
        ArrayList<cboEntradas> lista= new ArrayList<cboEntradas>();
        try{
            JSONArray jsonArreglo=new JSONArray(respuesta);
            for(int i=0; i<jsonArreglo.length(); i++){
                cboEntradas ori=new cboEntradas();
                ori.setNombre(jsonArreglo.getJSONObject(i).getString("Nombre"));
                lista.add(ori);
            }
            ArrayAdapter<CharSequence> a=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,lista);
            cbousuario.setAdapter(a);
            cbousuario.setOnItemSelectedListener(this);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        e = parent.getSelectedItem().toString();
        tbtE.removeAllViews();//remueve columnas
        tbtDetE.removeAllViews();
        CargarTabla(e);
    }

    private void CargarTabla(String e) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result=new JSONArray(response);
                    for (int i = 0; i < result.length();i++ ) {
                        JSONObject jsonObject = result.getJSONObject(i);

                        View registro = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_entregas, null, false);

                        TextView id = registro.findViewById(R.id.col1);
                        TextView res = registro.findViewById(R.id.col2);
                        TextView fecha = registro.findViewById(R.id.col3);


                        //agregar bton
                        ImageButton boton=registro.findViewById(R.id.btndetalle);

                        //rescata los valores
                        String idE=jsonObject.getString("IdEntrega");
                        String resp=jsonObject.getString("ResponsableEntrega");
                        String fech=jsonObject.getString("fecha");


                        //asigna los valores rescatador
                        id.setText(idE);
                        res.setText(resp);
                        fecha.setText(fech);


                        //un valor id valido a boton segun cada fila que se genere
                        boton.setId(View.generateViewId());
                        boton.setTag(idE);//asigna su identificador
                        boton.setImageDrawable(getDrawable(R.drawable.detalle));

                        boton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ConsuEntreProdEmD.this, "pertenesco a "+v.getTag(), Toast.LENGTH_SHORT).show();
                                String id=v.getTag().toString();
                                CargarDetalle(id,e);
                            }
                        });

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
                parametros.put("opcion","ConsulEntrada");
                parametros.put("correo",emisor);
                parametros.put("usu",e);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CargarDetalle(String id, String e) {
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}