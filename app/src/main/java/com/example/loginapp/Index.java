package com.example.loginapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatDelegate;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.databinding.ActivityIndexBinding;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Index extends DrawerBaseActivity {

    ActivityIndexBinding activityIndexBinding;
    String u,emisor,idrol;
    public static final String r="usuariorol";
    public static final String no="nombreusuario";
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI = "https://campolimpiojal.com/android/usuario.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_catalogos2);

        //aqui va lo del menu
        activityIndexBinding=ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(activityIndexBinding.getRoot());
        allowActivityTitle("Inicio");

        //variables sesion
        emisor=MainActivity.obtenerusuario(Index.this,MainActivity.m);

        requestQueue = Volley.newRequestQueue(Index.this);
        progressDialog = new ProgressDialog(Index.this);

        obtenerrol(Index.this,r);
        obtenernombre(Index.this,no);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        datosIndex();

    }

    private void datosIndex() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try{
                    JSONArray result=new JSONArray(response);
                    JSONObject jsonObject = result.getJSONObject(0);
                    u=jsonObject.getString("Nombre");
                    idrol=jsonObject.getString("IdtipoUsuario");

                    Toast.makeText(Index.this, u, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Index.this, idrol, Toast.LENGTH_SHORT).show();

                    guardarrol(Index.this,idrol,r);
                    guardarnombre(Index.this,u,no);

                }
                catch (JSONException e) {e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            //parametros
            protected Map<String,String> getParams(){
                Map<String, String> parametros=new HashMap<>();
                //Parámetros que se esperan en el webservice
                parametros.put("email",emisor);
                parametros.put("opcion","datos");
                return parametros;
            }
        };
        requestQueue.add(stringRequest);


    }


    //rol guardado en variables de sesion
    public static void guardarrol(Context c, String us, String key){
        SharedPreferences preferences=c.getSharedPreferences(MainActivity.keyu,MODE_PRIVATE);
        preferences.edit().putString(key,us).apply();
    }
    public static String obtenerrol(Context c,String key){
        SharedPreferences preferences=c.getSharedPreferences(MainActivity.keyu,MODE_PRIVATE);
        return preferences.getString(key,"");
    }

    public static void guardarnombre(Context c,String nom, String key){
        SharedPreferences preferences=c.getSharedPreferences(MainActivity.keyu,MODE_PRIVATE);
        preferences.edit().putString(key,nom).apply();
    }
    public static String obtenernombre(Context c,String key){
        SharedPreferences preferences=c.getSharedPreferences(MainActivity.keyu,MODE_PRIVATE);
        return preferences.getString(key,"");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent main = new Intent(Index.this, MainActivity.class);
            startActivity(main);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}