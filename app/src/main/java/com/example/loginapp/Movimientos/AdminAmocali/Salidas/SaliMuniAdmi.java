package com.example.loginapp.Movimientos.AdminAmocali.Salidas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.loginapp.Movimientos.Productores.DatePickerFragment;
import com.example.loginapp.R;
import com.example.loginapp.SetGet_Consultas.cboEntradas;
import com.example.loginapp.databinding.ActivitySaliDisAdmiBinding;
import com.example.loginapp.databinding.ActivitySaliMuniAdmiBinding;
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

public class SaliMuniAdmi extends DrawerBaseActivity implements AdapterView.OnItemSelectedListener {
    ActivitySaliMuniAdmiBinding activitySaliMuniAdmiBinding;
    AsyncHttpClient cliente;
    Spinner cbousuario;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsultaMovimientos.php";
    String usuario;
    TableLayout tbtE,tbtDetE;
    MaterialButton btnregresa;
    String ff,fi;
    EditText FI, FF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sali_muni_admi);
        //aqui va lo del menu
        activitySaliMuniAdmiBinding= ActivitySaliMuniAdmiBinding.inflate(getLayoutInflater());
        setContentView(activitySaliMuniAdmiBinding.getRoot());
        allowActivityTitle("Salidas/Municipios");

        tbtE=findViewById(R.id.tablaEntregas);
        tbtDetE=findViewById(R.id.tabladetEn);

        cliente=new AsyncHttpClient();
        cbousuario =(Spinner) findViewById(R.id.cbo);
        llenarspinner();

        //fechas
        FI = findViewById(R.id.FI);
        FF = findViewById(R.id.FF);
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

        requestQueue= Volley.newRequestQueue(SaliMuniAdmi.this);
        progressDialog=new ProgressDialog(SaliMuniAdmi.this);

        btnregresa= (MaterialButton) findViewById(R.id.btnreg1);
        btnregresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
    }//fin on create
    //---------------------cbo--------------------------
    private void llenarspinner() {
        String url = "https://campolimpiojal.com/android/cboEntregasMunicipios.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    cargarspinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }//fin llenar spinner
    private void cargarspinner(String respuesta) {
        ArrayList<cboEntradas> lista = new ArrayList<cboEntradas>();
        try {
            JSONArray jsonArreglo = new JSONArray(respuesta);
            for (int i = 0; i < jsonArreglo.length(); i++) {
                cboEntradas ori = new cboEntradas();
                ori.setNombre(jsonArreglo.getJSONObject(i).getString("Nombre"));
                lista.add(ori);
            }
            ArrayAdapter<CharSequence> a = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lista);
            cbousuario.setAdapter(a);
            cbousuario.setOnItemSelectedListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        usuario= parent.getSelectedItem().toString();
    }
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //-----------------fechas -------------------------------------
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
    //---------------------servidor--------------------------------------
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
                parametros.put("opcion","SaDME");
                parametros.put("re",usuario);
                parametros.put("fi",fi);
                parametros.put("ff",ff);
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

                        View registroD = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_detsalidas, null, false);

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