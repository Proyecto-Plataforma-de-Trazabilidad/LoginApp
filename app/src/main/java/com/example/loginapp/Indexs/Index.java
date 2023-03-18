package com.example.loginapp.Indexs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Index extends DrawerBaseActivity {

    ActivityIndexBinding activityIndexBinding;
    String u,emisor;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI = "https://proyectoapejal.000webhostapp.com/agenda/usuario.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_catalogos);

        //aqui va lo del menu
        activityIndexBinding=ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(activityIndexBinding.getRoot());
        allowActivityTitle("Inicio");

        //variables sesion
        emisor=MainActivity.obtenerusuario(Index.this,MainActivity.m);
        Toast.makeText(this, emisor, Toast.LENGTH_SHORT).show();

        requestQueue = Volley.newRequestQueue(Index.this);
        progressDialog = new ProgressDialog(Index.this);
        cargardatosindex();
    }


    private void cargardatosindex() {
        //NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
       // usuario=navigationView.getMenu().findItem(R.id.nav_user); //servidor

        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray result=new JSONArray(response);
                    JSONObject jsonObject = result.getJSONObject(0);
                    u=jsonObject.getString("nombre");
                    Toast.makeText(Index.this, u, Toast.LENGTH_SHORT).show();
                   // usuario.setTitle(u);
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
    }//fin cargar datos

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