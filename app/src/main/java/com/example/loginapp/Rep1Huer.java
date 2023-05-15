package com.example.loginapp;

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
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1VDis;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rep1Huer extends AppCompatActivity {
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsuReportes.php";

    BarChart barras2;
    String[] etiquetas;
    int[] valores;
    int[] color= ColorTemplate.MATERIAL_COLORS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep1_huer);

        requestQueue= Volley.newRequestQueue(Rep1Huer.this);
        progressDialog=new ProgressDialog(Rep1Huer.this);


        barras2=findViewById(R.id.HuerP);
        Consulta();
    }

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

                        int valor = Integer.parseInt(jsonObject.getString("TotalH"));
                        String nom = jsonObject.getString("Nombre");
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
                parametros.put("opcion","Rep1H");
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
        barras2=(BarChart)getSameChart(barras2,"Huertos", Color.BLACK,Color.WHITE,2000);
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

}