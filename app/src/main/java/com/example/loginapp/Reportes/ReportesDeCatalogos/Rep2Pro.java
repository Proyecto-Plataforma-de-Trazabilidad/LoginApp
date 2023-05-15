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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rep2Pro extends AppCompatActivity {
    BarChart Gbar;//grafico

    //conexion a servidor
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsuReportes.php";
    int[] color= ColorTemplate.MATERIAL_COLORS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep2_pro);

        //dialogo y respuesta servidor
        requestQueue= Volley.newRequestQueue(Rep2Pro.this);
        progressDialog=new ProgressDialog(Rep2Pro.this);

        Gbar=findViewById(R.id.group_bar);//enlazamos el grafico

        DatosEntries();//llenar arreglos con datos de servidor



    }//fin on create

    private void DatosEntries() {
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    JSONArray result=new JSONArray(response);

                    ArrayList<BarEntry> barentriesP= new ArrayList<>();
                    ArrayList<BarEntry> barentriesO= new ArrayList<>();
                    ArrayList<BarEntry> barentriesE= new ArrayList<>();
                    ArrayList<BarEntry> barentriesS= new ArrayList<>();

                    String[] etiquetas=new String[result.length()];

                    for (int i = 0; i < result.length(); ) {
                        JSONObject jsonObject = result.getJSONObject(i);
                        //rescatando los datos
                        int p= Integer.parseInt(jsonObject.getString("Puntos"));
                        int o= Integer.parseInt(jsonObject.getString("Orden"));
                        int e= Integer.parseInt(jsonObject.getString("Entregas"));
                        int s= Integer.parseInt(jsonObject.getString("SaldoPiezas"));
                        String n= jsonObject.getString("Nombre");

                        //agregamos datos al arreglo
                        barentriesP.add(new BarEntry(i,p));
                        barentriesO.add(new BarEntry(i,o));
                        barentriesE.add(new BarEntry(i,e));
                        barentriesS.add(new BarEntry(i,s));
                        etiquetas[i]=n;

                        //incremento
                        i++;
                    }
                    Creargrafico(barentriesP,barentriesO,barentriesE,barentriesS,etiquetas);
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
                parametros.put("opcion","Rep2P");
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }//fin de datos entries

    private void Creargrafico(ArrayList<BarEntry> barentriesP, ArrayList<BarEntry> barentriesO, ArrayList<BarEntry> barentriesE, ArrayList<BarEntry> barentriesS, String[] etiquetas) {
        //asignando los datos recuperados y un color
        BarDataSet barDataSet1=new BarDataSet(barentriesP,"Puntos");
        barDataSet1.setColor(color[1]);

        BarDataSet barDataSet2=new BarDataSet(barentriesO,"Pzs Ordenadas");
        barDataSet2.setColor(color[2]);

        BarDataSet barDataSet3=new BarDataSet(barentriesE,"Pzs Entregadas");
        barDataSet3.setColor(color[3]);

        BarDataSet barDataSet4=new BarDataSet(barentriesS,"Saldo Pzs");
        barDataSet4.setColor(Color.GRAY);

        //Agregando los datos al grafico
        BarData data=new BarData(barDataSet1,barDataSet2,barDataSet3,barDataSet4);
        Gbar.setData(data);

        //configurando eje X
        XAxis xAxis=Gbar.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(etiquetas));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        Gbar.setDragEnabled(true);
        float barspace=0.05f;
        float groupspace=0.16f;
        data.setBarWidth(0.16f);

        Gbar.getXAxis().setAxisMinimum(0);
        Gbar.getXAxis().setAxisMaximum(0+Gbar.getBarData().getGroupWidth(groupspace,barspace)*(barentriesP.size()));

        Gbar.groupBars(0,groupspace,barspace);
        Gbar.invalidate();
        Gbar.animateY(1000);

        Gbar.moveViewToX(10);

        Legend l=Gbar.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setYOffset(10f);
        l.setXOffset(10f);

        Gbar.getDescription().setText("Puntos,Ordenes,Entregas y saldo por productor");
        Gbar.setExtraOffsets(10f, 10f, 10f, 10f);
    }//fin crear grafico

}