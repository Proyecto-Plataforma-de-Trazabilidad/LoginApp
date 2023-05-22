package com.example.loginapp.Movimientos.AdminAmocali.Extraviados;

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
import com.example.loginapp.Movimientos.AdminAmocali.Ordenes.ConsuOrdenes;
import com.example.loginapp.Movimientos.Productores.DatePickerFragment;
import com.example.loginapp.R;
import com.example.loginapp.SetGet_Consultas.cboEntradas;
import com.example.loginapp.databinding.ActivityConsuExtraviadosBinding;
import com.example.loginapp.databinding.ActivityConsultaExtraviadosProductorBinding;
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

public class ConsuExtraviados extends DrawerBaseActivity implements AdapterView.OnItemSelectedListener{
    ActivityConsuExtraviadosBinding activityConsuExtraviadosBinding;
    TableLayout tbtE;
    AsyncHttpClient cliente;
    Spinner cboproductor;
    String ff,fi,productor;
    EditText FI, FF;

    //conexion
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsultaMovimientos.php";
    JSONArray arreglo;
    MaterialButton volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_extraviados);

        //aqui va lo del menu
        activityConsuExtraviadosBinding= ActivityConsuExtraviadosBinding.inflate(getLayoutInflater());
        setContentView(activityConsuExtraviadosBinding.getRoot());
        allowActivityTitle("Extraviados/Productor");

        //cbo
        cliente = new AsyncHttpClient();
        cboproductor = (Spinner) findViewById(R.id.cbopro);
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

        tbtE = findViewById(R.id.tablaO);
        tbtE.removeAllViews();//remueve columnas

        requestQueue= Volley.newRequestQueue(ConsuExtraviados.this);
        progressDialog=new ProgressDialog(ConsuExtraviados.this);


        //botones
        volver=findViewById(R.id.btnvolver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
    }//fin conreate
    //cbo---------------------------------------------------
    private void llenarspinner() {
        String url = "https://campolimpiojal.com/android/CboProductoresEntregas.php";
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
    }
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
            cboproductor.setAdapter(a);
            cboproductor.setOnItemSelectedListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        productor = parent.getSelectedItem().toString();
        //Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
    }
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //fechas------------------------------------------------
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
                CargarTabla();
            }
        });
        fragment.show(getSupportFragmentManager(), "datePicker");
    }
//Servidor-----------------------------------------------
    private void CargarTabla() {
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
                parametros.put("opcion","ExP");
                parametros.put("pro",productor);
                parametros.put("fi",fi);
                parametros.put("ff",ff);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

}