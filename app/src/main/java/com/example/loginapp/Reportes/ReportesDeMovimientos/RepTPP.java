package com.example.loginapp.Reportes.ReportesDeMovimientos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Movimientos.Distribuidor.Entregas.ConsulEntregaxProductor;
import com.example.loginapp.Movimientos.Productores.DatePickerFragment;
import com.example.loginapp.R;
import com.example.loginapp.SetGet_Consultas.cboEntradas;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class RepTPP extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AsyncHttpClient cliente;
    Spinner cboproductor;
    String ff,fi;
    EditText FI, FF;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI = "https://campolimpiojal.com/android/ConsuReportes.php";

    String e;
    BarChart Gbar;//grafico
    int[] color= ColorTemplate.MATERIAL_COLORS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_tpp);

        cliente = new AsyncHttpClient();
        cboproductor = (Spinner) findViewById(R.id.cbopro);
        llenarspinner();

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

        requestQueue = Volley.newRequestQueue(RepTPP.this);
        progressDialog = new ProgressDialog(RepTPP.this);

        //grafico
        Gbar=findViewById(R.id.TPP);//enlazamos el grafico


    }//fin oncrate

    //---------------spiner----------------------
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
        e = parent.getSelectedItem().toString();
        //Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //-----------------calendario---------------
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
               Consultar();
            }
        });
        fragment.show(getSupportFragmentManager(), "datePicker");
    }
    //--------consulta a servidor-------------------
    private void Consultar() {
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        //peticion a servidor
        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    JSONArray result=new JSONArray(response);

                    ArrayList<BarEntry> barentriesC= new ArrayList<>();
                    String[] etiquetas=new String[result.length()];

                    for (int i = 0; i < result.length(); ) {
                        JSONObject jsonObject = result.getJSONObject(i);

                        //rescatando los datos
                        int C= Integer.parseInt(jsonObject.getString("TotalPiezas"));
                        String n=jsonObject.getString("Nombre");

                        //agregamos datos al arreglo
                        barentriesC.add(new BarEntry(i,C));
                        etiquetas[i]=n;

                        //incremento
                        i++;
                    }
                    Creargrafico(barentriesC,etiquetas);
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
                parametros.put("opcion","RepTPP");
                parametros.put("pro",e);
                parametros.put("fi",fi);
                parametros.put("ff",ff);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    //----- cargar grafico---------------------
    private void Creargrafico(ArrayList<BarEntry> barentriesC, String[] etiquetas) {
        //asignando los datos recuperados y un color
        BarDataSet barDataSet1=new BarDataSet(barentriesC,etiquetas[0]);
        barDataSet1.setColor(color[0]);
        barDataSet1.setValueTextColor(Color.BLACK);
        barDataSet1.setValueTextSize(16f);

        BarData data=new BarData(barDataSet1);
        Gbar.setFitBars(true);
        Gbar.setData(data);
        Gbar.getDescription().setText(" ");
        Gbar.invalidate();
        Gbar.animateY(1000);

        YAxis yAxis=Gbar.getAxisRight();
        yAxis.setEnabled(false);

        YAxis yAxis2=Gbar.getAxisLeft();
        yAxis2.setAxisMinimum(0);


        XAxis xAxis=Gbar.getXAxis();
        xAxis.setEnabled(false);


        Gbar.moveViewToX(10);

        Legend l=Gbar.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setYOffset(10f);
        l.setXOffset(10f);

        Gbar.getDescription().setText("Total Pzs");
        Gbar.setExtraOffsets(10f, 10f, 10f, 10f);
    }
}