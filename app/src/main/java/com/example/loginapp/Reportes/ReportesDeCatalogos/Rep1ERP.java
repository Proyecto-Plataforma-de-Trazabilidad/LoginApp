package com.example.loginapp.Reportes.ReportesDeCatalogos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.R;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rep1ERP extends AppCompatActivity implements OnChartValueSelectedListener {
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsuReportes.php";

    BarChart barras2,barras3;
    int[] valoresm;
    String[] etiquetasm;
    String[] etiquetas;
    int[] valores;
    int[] color= ColorTemplate.MATERIAL_COLORS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep1_erp);
        requestQueue= Volley.newRequestQueue(Rep1ERP.this);
        progressDialog=new ProgressDialog(Rep1ERP.this);
        barras2=findViewById(R.id.ERP_E);
        barras2.setOnChartValueSelectedListener(this);
        Consulta();
    }//fin on create
    private void Consulta() {
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray result = new JSONArray(response);

                    int ancho=result.length();

                    etiquetas=new String[ancho];
                    valores=new int[ancho];

                    for (int i = 0; i < result.length(); ) {
                        JSONObject jsonObject = result.getJSONObject(i);

                        int valor = Integer.parseInt(jsonObject.getString("TotalE"));
                        String nom = jsonObject.getString("Edo");
                        etiquetas[i]=nom;
                        valores[i]=valor;

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
                parametros.put("opcion","Rep1EERP");
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }
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
        barras2=(BarChart)getSameChart(barras2,"ERP", Color.BLACK,Color.WHITE,2000);
        barras2.setDrawGridBackground(true);

        barras2.setData(getBarData());
        barras2.invalidate();

        axisX(barras2.getXAxis());//llamando asus propiedades personalizadas
        axislefth(barras2.getAxisLeft());
        axisright(barras2.getAxisRight());
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

    //---------------otro grafico ---------------------------------
    public void onValueSelected(Entry e, Highlight h) {
        String barrac=etiquetas[(int) e.getX()];
        Toast.makeText(this, barrac, Toast.LENGTH_SHORT).show();
        ConsultaMunicipio(barrac);
    }
    private void ConsultaMunicipio(String barrac) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray result = new JSONArray(response);

                    int ancho=result.length();

                    etiquetasm=new String[ancho];
                    valoresm=new int[ancho];

                    for (int i = 0; i < result.length(); ) {
                        JSONObject jsonObject = result.getJSONObject(i);

                        int valor = Integer.parseInt(jsonObject.getString("TotalM"));
                        String estado = jsonObject.getString("Municipio");
                        etiquetasm[i]=estado;
                        valoresm[i]=valor;
                        i++;
                    }
                    graficoMunicipios(barrac);

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
                parametros.put("opcion","Rep1MERP");
                parametros.put("edo",barrac);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void graficoMunicipios(String barrac) {
        barras3 = (BarChart) findViewById(R.id.ERP_M);
        barras3=(BarChart)getSameChart3(barras3,"Municipios del Estado: "+barrac,Color.BLACK,Color.WHITE,2000);
        barras3.setDrawGridBackground(true);

        barras3.setData(getBarData3());
        barras3.invalidate();

        axisX3(barras3.getXAxis());//llamando asus propiedades personalizadas
        axislefth3(barras3.getAxisLeft());
        axisright3(barras3.getAxisRight());

    }
    private Chart getSameChart3(Chart chart,String des,int txtcolor,int back, int anima){
        chart.getDescription().setText(des);//personalizacion de la grafica
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(back);
        chart.animateY(anima);
        leyenda3(chart);
        return chart;
    }
    private void leyenda3(Chart chart){
        //personalizacion de la leyenda
        Legend l=chart.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);


        ArrayList<LegendEntry>entriesm=new ArrayList<>();
        for (int i = 0; i < etiquetasm.length; i++) {
            LegendEntry entry=new LegendEntry();
            entry.formColor=color[i];
            entry.label= etiquetasm[i];
            entriesm.add(entry);
        }//fin for
        l.setCustom(entriesm);
    }
    private void axisX3(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(etiquetasm));
        axis.setEnabled(false);//desabilita etiquetas eje x
    }
    private void axislefth3(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }
    private void axisright3(YAxis axis){
        axis.setEnabled(false);
    }
    private DataSet getData3(DataSet dataSet){
        dataSet.setColors(color);
        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private BarData getBarData3(){
        BarDataSet bds= (BarDataSet) getData(new BarDataSet(Entriesgraf3(),""));//que datos vas a poner en la grafica
        BarData bD=new BarData(bds);
        bD.setBarWidth(0.45f);
        return bD;
    }
    private ArrayList<BarEntry> Entriesgraf3(){
        ArrayList<BarEntry> entries=new ArrayList<>();//agregar valores de la barra
        for(int i=0;i<valoresm.length;){
            entries.add(new BarEntry(i,valoresm[i]));
            i++;
        }
        return entries;
    }
    public void onNothingSelected() {
    }
}