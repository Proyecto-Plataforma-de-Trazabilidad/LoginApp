package com.example.loginapp.Indexs.Movimientos.Productores.Ordenes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Catalogos_muni.FormularioDistri;
import com.example.loginapp.Indexs.Index;
import com.example.loginapp.Indexs.Movimientos.Index_movimi_productor;
import com.example.loginapp.Indexs.Movimientos.Productores.consultas_ordenesProductor;
import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConsulGeneralDelProductor extends AppCompatActivity {
    TableLayout tbtCOP;
    String emisorname;
    TextView nom;
    //conexion
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulOrdenesMoviProductores.php";
    JSONArray arreglo;
    MaterialButton volver,CSV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul_general_del_productor);

        tbtCOP = findViewById(R.id.tablaO);
        tbtCOP.removeAllViews();//remueve columnas

        //variables sesion
        emisorname = Index.obtenerrol(ConsulGeneralDelProductor.this, Index.no);
        Toast.makeText(ConsulGeneralDelProductor.this, emisorname, Toast.LENGTH_SHORT).show();

        nom=findViewById(R.id.productor);
        nom.setText(Html.fromHtml("<b>Productor: </b>"+emisorname));

        requestQueue= Volley.newRequestQueue(ConsulGeneralDelProductor.this);
        progressDialog=new ProgressDialog(ConsulGeneralDelProductor.this);

        //------------
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        cargaOrdenes();

        //botones
        volver=findViewById(R.id.btnreg1);
        CSV=findViewById(R.id.csv);
        //eventos botones
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver=new Intent(ConsulGeneralDelProductor.this, consultas_ordenesProductor.class);
                startActivity(volver);
            }
        });

    }

    private void cargaOrdenes() {

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray result=new JSONArray(response);
                    for (int i = 0; i < 5;i++ ) {
                        JSONObject jsonObject = result.getJSONObject(i);

                        View registro = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_ordenes, null, false);

                        TextView id = registro.findViewById(R.id.col1);
                        TextView distri = registro.findViewById(R.id.col2);
                        TextView fact = registro.findViewById(R.id.col3);
                        TextView rece = registro.findViewById(R.id.col4);

                        //agregar bton
                        ImageButton boton=registro.findViewById(R.id.btndetalle);

                        //rescata los valores
                        String idO=jsonObject.getString("IdOrden");
                        String distriO=jsonObject.getString("Distribuidor");
                        String factO=jsonObject.getString("NumFactura");
                        String receO=jsonObject.getString("NumReceta");

                        //asigna los valores rescatador
                        id.setText(idO);
                        distri.setText(distriO);
                        fact.setText(factO);
                        rece.setText(receO);

                        //un valor id valido a boton segun cada fila que se genere
                        boton.setId(View.generateViewId());
                        boton.setTag(idO);//asigna su identificador
                        boton.setImageDrawable(getDrawable(R.drawable.detalle));

                        boton.setOnClickListener(new View.OnClickListener() {
                            @Override //que pasa si se da click en el boton, aqui debe mandar  a llamar a otro metodo que carge el detalle
                            public void onClick(View v) {
                                Toast.makeText(ConsulGeneralDelProductor.this, "Pertenezco a la orden"+v.getTag(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        arreglo=result;//para generar el csv

                        tbtCOP.addView(registro);
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
                parametros.put("opcion","OrdenProductor");
                parametros.put("nombre","Naylea");
                return parametros;
            }
        };
        requestQueue.add(stringRequest);


    }//fin cargar ordenes


}