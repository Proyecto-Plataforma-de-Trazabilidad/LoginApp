package com.example.loginapp.Catalogos_muni;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.loginapp.Indexs.IndexMunicipales;
import com.example.loginapp.Mapas.MapaMuniRecolectores;
import com.example.loginapp.R;
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

public class FormularioRecolec extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    AsyncHttpClient cliente;
    Spinner cbomunicipio;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "http://campolimpiojal.com/android/ConsultasMunicipio.php";

    String e;
    TableLayout tbtreco;

    MaterialButton btnregresa,btnconsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_recolec);

        cliente=new AsyncHttpClient();
        cbomunicipio=(Spinner) findViewById(R.id.cbomunicipiore);
        llenarspinner();

        requestQueue= Volley.newRequestQueue(FormularioRecolec.this);
        progressDialog=new ProgressDialog(FormularioRecolec.this);

        btnregresa= (MaterialButton) findViewById(R.id.btnregresarR);
        btnconsulta= (MaterialButton) findViewById(R.id.btnconsultarR);

        btnregresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regresa= new Intent(FormularioRecolec.this, IndexMunicipales.class);
                startActivity(regresa);
            }
        });

        btnconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conxe= new Intent(FormularioRecolec.this, MapaMuniRecolectores.class);
                 conxe.putExtra("Municipio",e);
                  startActivity(conxe);
            }
        });

    }//fin

    private void llenarspinner(){
        String url="http://campolimpiojal.com/android/cbomuniRecole.php";
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
    }//fin llenar spinner

    private void cargarspinner(String respuesta){
        ArrayList<municipios> lista= new ArrayList<municipios>();
        try{
            JSONArray jsonArreglo=new JSONArray(respuesta);
            for(int i=0; i<jsonArreglo.length(); i++){
                municipios mun=new municipios();
                mun.setMunicipio(jsonArreglo.getJSONObject(i).getString("Municipio"));
                lista.add(mun);
            }
            ArrayAdapter<CharSequence> a=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,lista);
            cbomunicipio.setAdapter(a);
            cbomunicipio.setOnItemSelectedListener(this);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        e = parent.getSelectedItem().toString();
        CargarTabla();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void CargarTabla() {

        //tabla
        tbtreco=findViewById(R.id.tablacxmre);
        tbtreco.removeAllViews();//remueve columnas


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

                        View registro= LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row,null,false);

                        TextView nombre=registro.findViewById(R.id.columnaname);
                        TextView email=registro.findViewById(R.id.coemail);

                        //nombre.append(jsonObject.getString("NombreCentro"));
                        //email.append(jsonObject.getString("Domicilio"));

                        String name=jsonObject.getString("Nombre");
                        String emails=jsonObject.getString("Domicilio");
                        nombre.setText(name);
                        email.setText(emails);

                        tbtreco.addView(registro);
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
                parametros.put("opcion","Erecolectoras");
                parametros.put("Municipio",e);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }
}