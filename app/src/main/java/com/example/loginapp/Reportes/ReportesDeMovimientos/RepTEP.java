package com.example.loginapp.Reportes.ReportesDeMovimientos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.R;
import com.example.loginapp.SetGet_Consultas.cboEntradas;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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

public class RepTEP extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AsyncHttpClient cliente;
    Spinner cboproductor,cbote;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI = "https://campolimpiojal.com/android/ConsuReportes.php";

    String productor,envase;
    BarChart Gbar;//grafico
    int[] color= ColorTemplate.MATERIAL_COLORS;
    String[]Envases={"Rígidos lavable","Rígidos no lavables","Flexible","Tapas","Cubetas","Cartón(Embalaje)","Tambos","Metal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_tep);

        //cboproductor
        cliente = new AsyncHttpClient();
        cboproductor = (Spinner) findViewById(R.id.cbopro);
        llenarspinner();

        //cbo envase
        cbote=findViewById(R.id.cboTe);
        ArrayAdapter<String> adapter=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,Envases);
        cbote.setAdapter(adapter);
        cbote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                envase=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //servidor
        requestQueue = Volley.newRequestQueue(RepTEP.this);
        progressDialog = new ProgressDialog(RepTEP.this);

        //grafico
        Gbar=findViewById(R.id.TEP);//enlazamos el grafico

    }
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
        productor = parent.getSelectedItem().toString();
        Consultar();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //----------------Consultar a servidor ------------------------------------
    private void Consultar() {
        Toast.makeText(this, productor+envase, Toast.LENGTH_SHORT).show();
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
                parametros.put("opcion","RepTEP");
                parametros.put("pro",productor);
                parametros.put("te",envase);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);

    }//fin consulta
    //----------------grafico--------------------------
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