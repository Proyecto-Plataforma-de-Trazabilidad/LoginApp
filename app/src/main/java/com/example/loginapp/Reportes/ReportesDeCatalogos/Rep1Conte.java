package com.example.loginapp.Reportes.ReportesDeCatalogos;

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
import com.example.loginapp.Catalogos.Catalogos_muni.FormularioConte;
import com.example.loginapp.R;
import com.example.loginapp.SetGet_Consultas.TipoContenedor;
import com.example.loginapp.SetGet_Consultas.origen;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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

public class Rep1Conte extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    AsyncHttpClient cliente;//comboTC

    BarChart Gbar;//grafico

    //conexion a servidor
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsuReportes.php";

    int[] color= ColorTemplate.MATERIAL_COLORS;
    Spinner cboTc,cboOri;
    String[]Or={"Amocalli","Dist.","CAT","Recicladora","Municipio","Empresa"};
    MaterialButton btnconsulta;
    String e,o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep1_conte);

        requestQueue= Volley.newRequestQueue(Rep1Conte.this);
        progressDialog=new ProgressDialog(Rep1Conte.this);

        //grafico
        Gbar=findViewById(R.id.group_barc);//enlazamos el grafico

        //boton
        btnconsulta=findViewById(R.id.consul);
        btnconsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Rep1Conte.this, "rescate:"+o+"y"+e, Toast.LENGTH_SHORT).show();
                Consultar(o,e);
            }
        });

        //cbo ori
        cboOri=findViewById(R.id.cboOri);
        ArrayAdapter<String> adapter=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,Or);
        cboOri.setAdapter(adapter);
        cboOri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                o = parent.getSelectedItem().toString();
                //Toast.makeText(Rep1Conte.this,"origen:"+o, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //llenar el cboTC
        cliente=new AsyncHttpClient();
        cboTc =(Spinner) findViewById(R.id.cboTC);
        llenarspinner();
    }

    private void llenarspinner() {
        String url="https://campolimpiojal.com/android/cboTipoContenedor.php";
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

    }

    private void cargarspinner(String respuesta) {
        ArrayList<TipoContenedor> lista= new ArrayList<TipoContenedor>();
        try{
            JSONArray jsonArreglo=new JSONArray(respuesta);
            for(int i=0; i<jsonArreglo.length(); i++){
                TipoContenedor tc=new TipoContenedor();
                tc.setTipoContenedor(jsonArreglo.getJSONObject(i).getString("Concepto"));
                lista.add(tc);
            }
            ArrayAdapter<CharSequence> a=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,lista);
            cboTc.setAdapter(a);
            cboTc.setOnItemSelectedListener(this);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        e = parent.getSelectedItem().toString();
       // Toast.makeText(this,"tc:"+e, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //--------------------------------------------------------------
    private void Consultar(String o, String e) {
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    JSONArray result=new JSONArray(response);

                    ArrayList<BarEntry> barentriesC= new ArrayList<>();
                    ArrayList<BarEntry> barentriesCS= new ArrayList<>();
                    ArrayList<BarEntry> barentriesR= new ArrayList<>();

                    String[] etiquetas=new String[result.length()];

                    for (int i = 0; i < result.length(); ) {
                        JSONObject jsonObject = result.getJSONObject(i);
                        //rescatando los datos
                        int C= Integer.parseInt(jsonObject.getString("Capacidad"));
                        int CS= Integer.parseInt(jsonObject.getString("CapacidadStatus"));
                        int R= Integer.parseInt(jsonObject.getString("EspacioD"));

                        String n=jsonObject.getString("IdContenedor");
                        String cont="Contenedor "+n;

                        //agregamos datos al arreglo
                        barentriesC.add(new BarEntry(i,C));
                        barentriesCS.add(new BarEntry(i,CS));
                        barentriesR.add(new BarEntry(i,R));

                        etiquetas[i]=cont;

                        //incremento
                        i++;
                    }
                    Creargrafico(barentriesC,barentriesCS,barentriesR,etiquetas);
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
                parametros.put("opcion","Rep1Co");
                parametros.put("ori",o);
                parametros.put("tc",e);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Creargrafico(ArrayList<BarEntry> barentriesC, ArrayList<BarEntry> barentriesCS, ArrayList<BarEntry> barentriesR, String[] etiquetas) {
        //asignando los datos recuperados y un color
        BarDataSet barDataSet1=new BarDataSet(barentriesC,"Capacidad");
        barDataSet1.setColor(color[1]);

        BarDataSet barDataSet2=new BarDataSet(barentriesCS,"Status");
        barDataSet2.setColor(color[2]);

        BarDataSet barDataSet3=new BarDataSet(barentriesR,"Espacio Restante");
        barDataSet3.setColor(color[3]);


        //Agregando los datos al grafico
        BarData data=new BarData(barDataSet1,barDataSet2,barDataSet3);
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
        Gbar.getXAxis().setAxisMaximum(0+Gbar.getBarData().getGroupWidth(groupspace,barspace)*(etiquetas.length));

        Gbar.groupBars(0,groupspace,barspace);
        Gbar.invalidate();
        Gbar.animateY(1000);

        Gbar.moveViewToX(10);

        Legend l=Gbar.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setYOffset(10f);
        l.setXOffset(10f);

        Gbar.getDescription().setText("Capacidad,Status y Espacio Restante");
        Gbar.setExtraOffsets(10f, 10f, 10f, 10f);
    }
}