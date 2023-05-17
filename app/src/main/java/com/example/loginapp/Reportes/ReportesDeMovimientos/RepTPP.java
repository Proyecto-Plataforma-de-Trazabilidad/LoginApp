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
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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

public class RepTPP extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AsyncHttpClient cliente;
    Spinner cboproductor;
    String ff,fi;
    EditText FI, FF;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI = "https://campolimpiojal.com/android/ConsuReportes.php";

    String productor;
    BarChart Gbar;//grafico
    int[] color= ColorTemplate.MATERIAL_COLORS;

    String[] etiquetas;
    int[] valores;
    MaterialButton btnconsulta;

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

        btnconsulta=findViewById(R.id.consul);

        btnconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Consultar(productor,fi,ff);
            }
        });
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
        productor = parent.getSelectedItem().toString();
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
            }
        });
        fragment.show(getSupportFragmentManager(), "datePicker");
    }
    //--------consulta a servidor-------------------
    private void Consultar(String productor,String fi,String ff) {
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        //peticion a servidor
        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    JSONArray result=new JSONArray(response);

                    int ancho=result.length();
                    etiquetas=new String[ancho];
                    valores=new int[ancho];

                    for (int i = 0; i < result.length(); ) {
                        JSONObject jsonObject = result.getJSONObject(i);

                        //rescatando los datos
                        String c=jsonObject.getString("TotalPiezas");
                        String n=jsonObject.getString("Nombre");

                        if(n=="null" && c=="null"){
                            //agregamos datos al arreglo
                            valores[i]=0;
                            etiquetas[i]=productor;
                        }
                        else{
                            int C= Integer.parseInt(jsonObject.getString("TotalPiezas"));
                            //agregamos datos al arreglo
                            valores[i]=C;
                            etiquetas[i]=n;
                        }

                        //incremento
                        i++;
                    }
                    creargrafico();
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
                parametros.put("pro",productor);
                parametros.put("fi",fi);
                parametros.put("ff",ff);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    //----- cargar grafico---------------------
    private Chart getSameChart(Chart chart, String des, int txtcolor, int back, int anima){
        chart.getDescription().setText(des);//personalizacion de la grafica
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(back);
        chart.animateY(anima);
        leyenda(chart);
        return chart;
    }
    private void leyenda(Chart chart){
        //personalizacion de la leyenda
        Legend l=chart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);


        ArrayList<LegendEntry> entries=new ArrayList<>();
        for (int i = 0; i <etiquetas.length; i++) {
            LegendEntry entry=new LegendEntry();
            entry.formColor=color[i];
            entry.label= etiquetas[i];
            entries.add(entry);
        }//fin for
        l.setCustom(entries);
    }
    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(etiquetas));
        axis.setEnabled(false);//desabilita etiquetas eje x

    }
    private void axislefth(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }
    private void axisright(YAxis axis){
        axis.setEnabled(false);
    }
    private void creargrafico(){
        Gbar=(BarChart)getSameChart(Gbar,"Total Ordenados", Color.BLACK,Color.WHITE,2000);
        Gbar.setDrawGridBackground(true);
        Gbar.setData(getBarData());
        Gbar.invalidate();

        axisX(Gbar.getXAxis());//llamando asus propiedades personalizadas
        axislefth(Gbar.getAxisLeft());
        axisright(Gbar.getAxisRight());
    }
    private DataSet getData(DataSet dataSet){
        dataSet.setColors(color);
        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private BarData getBarData(){
        BarDataSet bds= (BarDataSet) getData(new BarDataSet(Entriesgraf(),""));//que datos vas a poner en la grafica
        BarData bD=new BarData(bds);
        bD.setBarWidth(0.45f);
        return bD;
    }
    private ArrayList<BarEntry> Entriesgraf(){
        ArrayList<BarEntry> entries=new ArrayList<>();//agregar valores de la barra
        for(int i=0;i<valores.length;){
            entries.add(new BarEntry(i,valores[i]));
            i++;
        }
        return entries;
    }
}