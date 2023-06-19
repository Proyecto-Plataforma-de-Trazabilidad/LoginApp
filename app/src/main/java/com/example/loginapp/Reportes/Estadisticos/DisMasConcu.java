package com.example.loginapp.Reportes.Estadisticos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityDisMasConcuBinding;
import com.example.loginapp.databinding.ActivityEnvMasOBinding;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DisMasConcu extends DrawerBaseActivity {
    ActivityDisMasConcuBinding activityDisMasConcuBinding;
    PieChart pieE;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsuReportes.php";

    String[] etiquetas;
    int[] valores;
    int[] color= ColorTemplate.MATERIAL_COLORS;

    Button pdf,csv;
    JSONArray arreglo;
    LinearLayout layout;
    Bitmap bitmap;
    TextView fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_mas_concu);

        //aqui va lo del menu
        activityDisMasConcuBinding= ActivityDisMasConcuBinding.inflate(getLayoutInflater());
        setContentView(activityDisMasConcuBinding.getRoot());
        allowActivityTitle("Reportes/Distribuidores");

        requestQueue= Volley.newRequestQueue(DisMasConcu.this);
        progressDialog=new ProgressDialog(DisMasConcu.this);


        fecha=findViewById(R.id.fechasys);

        SimpleDateFormat dateFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        }
        Date date = new Date();
        String fech = dateFormat.format(date);

        fecha.setText(Html.fromHtml("<b>Fecha:</b> "+fech));

        pieE=findViewById(R.id.pieD);
        Consulta();

        layout=findViewById(R.id.layout_pdf);
        pdf=findViewById(R.id.pdf);
        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap=LoadBitmap(layout,layout.getWidth(),layout.getHeight());
                crearPDF();
            }
        });
    }//fin on create
    //---------------servidor-------------------------------------------------------
    private void Consulta(){
        //boton genera csv
        csv=findViewById(R.id.csv);
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

                        String valor = jsonObject.getString("total");
                        String etique = jsonObject.getString("Nombre");

                        if(valor=="null" || etique=="null"){
                            //agregamos datos al arreglo
                            valores[i]=0;
                            etiquetas[i]="Distribuidores";
                        }
                        else{
                            int C= Integer.parseInt(jsonObject.getString("total"));
                            //agregamos datos al arreglo
                            valores[i]=C;
                            etiquetas[i]=etique;
                        }

                        i++;
                    }
                    creargrafico();
                    arreglo=result;//para generar el csv

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                pdf.setEnabled(false);
                csv.setEnabled(false);
                //Mostrar el error de Volley exacto a través de la librería
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String, String> parametros=new HashMap<>();
                parametros.put("opcion","2");
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
        csv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Export(arreglo);
            }
        });
    }

    //----------------grafico--------------------------------------------------------
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
    private ArrayList<PieEntry> Entriesgraf(){
        ArrayList<PieEntry> entries=new ArrayList<>();//agregar valores de la barra
        for(int i=0;i<valores.length;){
            entries.add(new PieEntry(valores[i]));
            i++;
        }
        return entries;
    }

    private void creargrafico(){
        pieE=(PieChart)getSameChart(pieE,"Distribuidores", Color.BLACK,Color.WHITE,2000);
        pieE.setHoleRadius(10);
        pieE.setTransparentCircleRadius(12);
        pieE.setData(getPieData());
        pieE.setDrawHoleEnabled(false);
        pieE.invalidate();
        pieE.setExtraOffsets(10f, 10f, 10f, 10f);
    }
    private DataSet getData(DataSet dataSet){
        dataSet.setColors(color);
        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private PieData getPieData(){
        PieDataSet bds= (PieDataSet) getData(new PieDataSet(Entriesgraf(),""));//que datos vas a poner en la grafica
        bds.setSliceSpace(2);
        bds.setValueFormatter(new PercentFormatter());
        return new PieData(bds);
    }
    //------------pdf----------------------------------------------------------------
    private Bitmap LoadBitmap(LinearLayout layout, int width, int height) {
        Bitmap bitmap1=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_4444);
        Canvas canvas=new Canvas(bitmap1);
        layout.draw(canvas);
        return bitmap1;
    }

    private void crearPDF() {
        WindowManager windowManager=(WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float width=displayMetrics.widthPixels;
        float height=displayMetrics.heightPixels;
        int convertwith=(int)width,converhei=(int)height;

        PdfDocument document=new PdfDocument();
        PdfDocument.PageInfo pageInfo=new PdfDocument.PageInfo.Builder(convertwith,converhei,1).create();
        PdfDocument.Page page=document.startPage(pageInfo);

        Canvas canvas=page.getCanvas();
        Paint paint=new Paint();
        canvas.drawPaint(paint);
        bitmap=Bitmap.createScaledBitmap(bitmap,convertwith,converhei,true);
        canvas.drawBitmap(bitmap,0,0,null);

        document.finishPage(page);


        try{
            FileOutputStream out = openFileOutput("ReporteDistriMasConcurridos.pdf", MODE_PRIVATE);
            document.writeTo(out);
            document.close();

            //exporting
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "ReporteDistriMasConcurridos.pdf");
            Uri path = FileProvider.getUriForFile(context, "com.example.tabla.fileprovider", filelocation);

            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("application/pdf");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.setClipData(ClipData.newRawUri("", path));
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));

            Toast.makeText(this, "Archivo Descargado", Toast.LENGTH_SHORT).show();

        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Algo falló, intente de nuevo", Toast.LENGTH_SHORT).show();
        }
    }
    //------------csv----------------------------------------------------------------
    public void Export(JSONArray a){
        StringBuilder data=new StringBuilder();
        data.append("Nombre,total");

        try{
            for (int i=0; i<a.length();i++){
                JSONObject jsonObject = a.getJSONObject(i);
                String te=jsonObject.getString("Nombre");
                String tot=jsonObject.getString("total");

                //agrega datos
                data.append("\n "+te+","+tot);
            }
            try{
                //saving the file into device
                FileOutputStream out = openFileOutput("DistribuidoresMasConcurridos.csv", MODE_PRIVATE);
                out.write((data.toString()).getBytes("ISO-8859-1"));
                Toast.makeText(this, "Guardado en "+getFilesDir()+"/DistribuidoresMasConcurridos.csv", Toast.LENGTH_SHORT).show();
                out.close();


                //exporting
                Context context = getApplicationContext();
                File filelocation = new File(getFilesDir(), "DistribuidoresMasConcurridos.csv");
                Uri path = FileProvider.getUriForFile(context, "com.example.tabla.fileprovider", filelocation);


                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                fileIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.setClipData(ClipData.newRawUri("", path));
                fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                startActivity(Intent.createChooser(fileIntent, "Send mail"));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}