package com.example.loginapp.Movimientos.Distribuidor.Ordenes;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Index;
import com.example.loginapp.Movimientos.Productores.DatePickerFragment;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsultaOrdenesPeridoDistribuidorBinding;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConsultaOrdenesPeridoDistribuidor extends DrawerBaseActivity {
    ActivityConsultaOrdenesPeridoDistribuidorBinding coppb;
    EditText FI, FF;

    //para tabla
    TableLayout tbtOP,tbtdet;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulOrdenesMoviDistribuidores.php";
    MaterialButton volver;
    String fi,ff;
    String emisorname;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ordenes_perido_distribuidor);

        //aqui va lo del menu
        coppb= ActivityConsultaOrdenesPeridoDistribuidorBinding.inflate(getLayoutInflater());
        setContentView(coppb.getRoot());
        allowActivityTitle("Movimientos/Ordenes/Periodo");

        //variables sesion
        emisorname = Index.obtenerrol(ConsultaOrdenesPeridoDistribuidor.this, Index.no);
        Toast.makeText(ConsultaOrdenesPeridoDistribuidor.this, emisorname, Toast.LENGTH_SHORT).show();

        tbtOP = findViewById(R.id.tablaO);
        //limpiar tabla
        tbtOP.removeAllViews();//remueve columnas


        requestQueue= Volley.newRequestQueue(ConsultaOrdenesPeridoDistribuidor.this);
        progressDialog=new ProgressDialog(ConsultaOrdenesPeridoDistribuidor.this);
        //botones
        volver=findViewById(R.id.btnreg1);
        FI = findViewById(R.id.FI);
        FF = findViewById(R.id.FF);

        //eventos botones
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
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
    }//finoncreate

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
                cargartabla();

            }
        });
        fragment.show(getSupportFragmentManager(), "datePicker");

    }
    private void cargartabla() {
        // Toast.makeText(this, "Fecha inicial: "+fi, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, "Fecha final:  "+ff, Toast.LENGTH_SHORT).show();

        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        //limpiar tabla
        tbtOP.removeAllViews();//remueve columnas

        //peticion a servidor
        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray result=new JSONArray(response);
                    for (int i = 0; i < result.length();i++ ) {
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
                        String distriO=jsonObject.getString("Productor");
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
                                // Toast.makeText(ConsultaOrdenesPeridoProductor.this, "Pertenezco a la orden"+v.getTag(), Toast.LENGTH_SHORT).show();
                                String id=v.getTag().toString();

                                CargarDetalle(id);
                            }
                        });

                        tbtOP.addView(registro);
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
                parametros.put("opcion","consulOfecha");
                parametros.put("nombre","Ever SA DE CV");
                parametros.put("fi",fi);
                parametros.put("ff",ff);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }//fin cargar tabla

    private void CargarDetalle(String id) {
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        //Toast.makeText(this, "Perteneszco a la orden: "+id, Toast.LENGTH_SHORT).show();

        tbtdet=findViewById(R.id.tabladetO);
        tbtdet.removeAllViews();//remueve columnas

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONArray result2=new JSONArray(response);
                    for (int i = 0; i < result2.length();i++ ) {
                        JSONObject jsonObject = result2.getJSONObject(i);

                        View registroD = LayoutInflater.from(getApplicationContext()).inflate(R.layout.table_row_detordenes, null, false);

                        TextView consec= registroD.findViewById(R.id.col1);
                        TextView quimico = registroD.findViewById(R.id.col2);
                        TextView envase= registroD.findViewById(R.id.col3);
                        TextView color = registroD.findViewById(R.id.col4);
                        TextView piezas= registroD.findViewById(R.id.col5);


                        //rescata los valores
                        String cdo=jsonObject.getString("Consecutivo");
                        String qdo=jsonObject.getString("Concepto");
                        String edo=jsonObject.getString("TipoEnvase");
                        String colordo=jsonObject.getString("Color");
                        String pdo=jsonObject.getString("CantidadPiezas");

                        //asigna los valores rescatador
                        consec.setText(cdo);
                        quimico.setText(qdo);
                        envase.setText(edo);
                        color.setText(colordo);
                        piezas.setText(pdo);

                        //agrega fila
                        tbtdet.addView(registroD);
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
                parametros.put("opcion","DetOrdDistribuidor");
                parametros.put("IdOrden",id);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }//fin carga detalle

}//fin clas