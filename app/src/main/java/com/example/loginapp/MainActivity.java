package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.app.ProgressDialog;

//bibliotecas de voley para validacion
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    EditText username, passname;
    MaterialButton loginbtn;
    String u, c;

    //Animación de carga del webservice para ver que hace la aplicación
    ProgressDialog progressDialog;
    //Cuando mandamos petición, se debe enviar string con muchos elementos para ejecutar volley
    RequestQueue requestQueue;
    String httpURI = "https://proyectoapejal.000webhostapp.com/agenda/usuario.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.username);
        passname = (EditText) findViewById(R.id.passname);

        loginbtn = (MaterialButton) findViewById(R.id.buttonacceder);

        //Inicializar o enlazar a requestQueue y el progressDialog
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        //Indicar dónde se ejecutará progressdialog
        progressDialog = new ProgressDialog(MainActivity.this);


        //cuando se de click
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });


    }
    //---------------------------------------Metodo de validacion-----------------------------------------
    private void Login() {
        u = username.getText().toString();
        c = passname.getText().toString();

        if (u.isEmpty() || c.isEmpty()) {
            //vacio
            Toast.makeText(getApplicationContext(), "Debes introducir los datos en los dos campos", Toast.LENGTH_LONG).show();
        }
        else {
            //Mostramos el progressDialog
            progressDialog.setMessage("Procesando...");
            progressDialog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
                @Override
                public void onResponse(String serverResponse) {
                    //Quitar el progressDialog porque ya recibimos la respuesta y ocultamos el progressDialog
                    progressDialog.dismiss();

                    try{
                        //Crear objeto Json el cual va a obtener los valores que tenga de parte del webservice en ejecución
                        JSONObject obj = new JSONObject(serverResponse);

                        //Interpretar los valores que retorne por medio de obj
                        Boolean error=obj.getBoolean("Error");
                        //Obtener el mensaje
                        String mensaje=obj.getString("mensaje");

                        if (error)//Datos incorrectos
                            Toast.makeText(getApplicationContext(), mensaje,Toast.LENGTH_LONG).show();
                        else{
                            Toast.makeText(getApplicationContext(), "Acceso correcto",Toast.LENGTH_LONG).show();
                                    //Presentar otra activity....
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Si es otro error raro, detenemos el progess
                    progressDialog.dismiss();
                    //Mostrar el error de Volley exacto a través de la librería
                    Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
                }
            })//fin string
            {//inicio antes de map
                protected Map<String,String> getParams(){
                    Map<String, String> parametros=new HashMap<>();
                    //Parámetros que se esperan en el webservice
                    parametros.put("email",u);
                    parametros.put("password",c);
                    parametros.put("opcion","login");
                    return parametros;
                }
            };//fin map
            //requestQueue ejecute la cadena
            requestQueue.add(stringRequest);
        }//else
    }//private
}//main