package com.example.loginapp.Indexs.Movimientos.Productores.Ordenes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.loginapp.Indexs.Movimientos.Productores.consultas_ordenesProductor;
import com.example.loginapp.R;
import com.example.loginapp.SetGet_Consultas.TipoQuimico;
import com.example.loginapp.SetGet_Consultas.municipios;
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

public class ConsultaOrdenesTipoQuimicoProductor extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //para tabla
    TableLayout tbtOP,tbtdet;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulOrdenesMoviProductores.php";
    MaterialButton volver;
    Spinner cboTipoQuimico;
    AsyncHttpClient cliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ordenes_tipo_quimico_productor);

        cliente=new AsyncHttpClient();


        requestQueue= Volley.newRequestQueue(ConsultaOrdenesTipoQuimicoProductor.this);
        progressDialog=new ProgressDialog(ConsultaOrdenesTipoQuimicoProductor.this);
        //botones
        volver=findViewById(R.id.btnreg1);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver=new Intent(ConsultaOrdenesTipoQuimicoProductor.this, consultas_ordenesProductor.class);
                startActivity(volver);
            }
        });

        cboTipoQuimico=(Spinner) findViewById(R.id.cboTipoQuimico);
        LlenarSpiner();

        tbtOP = findViewById(R.id.tablaO);
        tbtdet=findViewById(R.id.tabladetO);

    }//fin oncreate

    private void LlenarSpiner() {
        String url="https://campolimpiojal.com/android/cboTipoQuimico.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                   CargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void CargarSpinner(String respuesta) {
        ArrayList<TipoQuimico> lista= new ArrayList<TipoQuimico>();
        try{
            JSONArray jsonArreglo=new JSONArray(respuesta);
            for(int i=0; i<jsonArreglo.length(); i++){
                TipoQuimico tqo=new TipoQuimico();
                tqo.setTipoQuimico(jsonArreglo.getJSONObject(i).getString("Concepto"));
                lista.add(tqo);
            }
            ArrayAdapter<CharSequence> a=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,lista);
            cboTipoQuimico.setAdapter(a);
            cboTipoQuimico.setOnItemSelectedListener(this);

        }
        catch(Exception e){
            e.printStackTrace();
        }


    }//fin cargar spiner


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tq = parent.getSelectedItem().toString();

        tbtOP.removeAllViews();//remueve columnas
        tbtdet.removeAllViews();//remueve columnas

        CargarTabla(tq);
    }

    private void CargarTabla(String tq) {
        Toast.makeText(this, "Recibi"+tq, Toast.LENGTH_SHORT).show();



        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray result=new JSONArray(response);
                    for (int i = 0; i < 5;i++ ) {
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
                            public void onClick(View v) {
                                Toast.makeText(ConsultaOrdenesTipoQuimicoProductor.this, "pertenesco a "+v.getTag(), Toast.LENGTH_SHORT).show();
                                CargarDetalle(tq);
                            }
                        });


                        tbtOP.addView(registro);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            protected Map<String,String> getParams(){
                Map<String, String> parametros=new HashMap<>();
                parametros.put("opcion","consulTQorden");
                parametros.put("tq",tq);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }//fin cargar tabla

    private void CargarDetalle(String quimi) {

        tbtdet.removeAllViews();//remueve columnas

        Toast.makeText(this, "Hola rescate el quimico"+quimi, Toast.LENGTH_SHORT).show();
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
                parametros.put("opcion","consulDetTQorden");
                parametros.put("quimi",quimi);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}//fin class