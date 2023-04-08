package com.example.loginapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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


import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Indexs.Index;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    EditText username, passname;
    MaterialButton loginbtn;
    String u, c;

    TextView forgot;

    //Animación de carga del webservice para ver que hace la aplicación
    ProgressDialog progressDialog;
    //Cuando mandamos petición, se debe enviar string con muchos elementos para ejecutar volley
    RequestQueue requestQueue;
    String httpURI = "https://campolimpiojal.com/android/usuario.php";//servidor en 000webhost

    public static final String m="usuariologin";
    public static final String keyu="keyusuario";
    String emisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emisor=obtenerusuario(MainActivity.this,m);
        //Toast.makeText(this, u, Toast.LENGTH_SHORT).show();

        username = (EditText) findViewById(R.id.username);
        passname = (EditText) findViewById(R.id.passname);
        forgot= (TextView) findViewById(R.id.forgot);

        loginbtn = (MaterialButton) findViewById(R.id.buttonacceder);

        //Inicializar o enlazar a requestQueue y el progressDialog
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        //Indicar dónde se ejecutará progressdialog
        progressDialog = new ProgressDialog(MainActivity.this);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        //cuando se de click
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(Html.fromHtml("¡Opcion disponible solo desde web!\n" +
                        "Por favor dirigete al sitio oficial en "+" <u><i>https://campolimpiojal.com/</i></u>"+" ,para restablecerla."));
                builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
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
                            guardarusuario(MainActivity.this,u,m);
                            Toast.makeText(getApplicationContext(), "Acceso correcto",Toast.LENGTH_LONG).show();
                            //Presentar otra activity....
                            Intent index= new Intent(MainActivity.this, Index.class);
                            startActivity(index);
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

    //variables sesion
    public static void guardarusuario(Context c, String us, String key){
        SharedPreferences preferences=c.getSharedPreferences(keyu,MODE_PRIVATE);
        preferences.edit().putString(key,us).apply();
    }
    public static String obtenerusuario(Context c,String key){
        SharedPreferences preferences=c.getSharedPreferences(keyu,MODE_PRIVATE);
        return preferences.getString(key,"");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            this.finishAffinity();
        }
        return super.onKeyDown(keyCode, event);
    }
}//main