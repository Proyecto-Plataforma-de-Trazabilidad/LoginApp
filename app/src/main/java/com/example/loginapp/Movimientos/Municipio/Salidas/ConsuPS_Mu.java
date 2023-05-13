package com.example.loginapp.Movimientos.Municipio.Salidas;
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
import com.example.loginapp.MainActivity;
import com.example.loginapp.Movimientos.Productores.DatePickerFragment;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsuPsMuBinding;
import com.example.loginapp.databinding.ActivityConsultaOrdenesPeridoDistribuidorBinding;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConsuPS_Mu extends DrawerBaseActivity {
    ActivityConsuPsMuBinding CSMB;
    EditText FI, FF;

    //para tabla
    TableLayout tbtOP,tbtdet;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulSalidas_Gen.php";
    MaterialButton volver;
    String fi,ff;
    String emisor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_ps_mu);

        //aqui va lo del menu
        CSMB= ActivityConsuPsMuBinding.inflate(getLayoutInflater());
        setContentView(CSMB.getRoot());
        allowActivityTitle("Movimientos");

        //variables sesion
        emisor= MainActivity.obtenerusuario(ConsuPS_Mu.this,MainActivity.m);
        Toast.makeText(ConsuPS_Mu.this, emisor, Toast.LENGTH_SHORT).show();

        tbtOP = findViewById(R.id.tablaO);
        //limpiar tabla
        tbtOP.removeAllViews();//remueve columnas


        requestQueue= Volley.newRequestQueue(ConsuPS_Mu.this);
        progressDialog=new ProgressDialog(ConsuPS_Mu.this);
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
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        //Limpia la tabla
        tbtOP.removeAllViews();

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

                        TextView ids = registro.findViewById(R.id.col1);
                        TextView idc = registro.findViewById(R.id.col2);
                        TextView res = registro.findViewById(R.id.col3);
                        TextView cantidad = registro.findViewById(R.id.col4);

                        //agregar bton
                        ImageButton boton=registro.findViewById(R.id.btndetalle);

                        //rescata los valores
                        String idSalida=jsonObject.getString("IdSalida");
                        String idConte=jsonObject.getString("IdContenedor");
                        String respon=jsonObject.getString("Responsable");
                        String can=jsonObject.getString("Cantidad");
                        String fech=jsonObject.getString("fecha");


                        //asigna los valores rescatador
                        ids.setText(idSalida);
                        idc.setText(idConte);
                        res.setText(respon);
                        cantidad.setText(can);

                        //un valor id valido a  segun cada fila que se genere
                        boton.setId(View.generateViewId());
                        boton.setTag(idSalida);//asigna su identificador
                        boton.setImageDrawable(getDrawable(R.drawable.detalle));

                        boton.setOnClickListener(new View.OnClickListener() {
                            @Override //que pasa si se da click en el boton, aqui debe mandar  a llamar a otro metodo que carge el detalle
                            public void onClick(View v) {
                                String id=v.getTag().toString();
                                CargarDetalle(idConte);
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
                parametros.put("opcion","consulSalidasfecha");
                parametros.put("correo",emisor);
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

                        TextView tc= registroD.findViewById(R.id.col1);
                        TextView ori = registroD.findViewById(R.id.col2);
                        TextView capa= registroD.findViewById(R.id.col3);
                        TextView desc = registroD.findViewById(R.id.col4);
                        TextView cs= registroD.findViewById(R.id.col5);


                        //rescata los valores
                        String cde=jsonObject.getString("Concepto");
                        String ede=jsonObject.getString("Origen");
                        String pde=jsonObject.getString("Capacidad");
                        String pes=jsonObject.getString("Descripcion");
                        String obser=jsonObject.getString("CapacidadStatus");

                        //asigna los valores rescatador
                        tc.setText(cde);
                        ori.setText(ede);
                        capa.setText(pde);
                        desc.setText(pes);
                        cs.setText(obser);

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
                parametros.put("opcion","DetCont");
                parametros.put("id",id);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }//fin carga detalle

}//fin clas