package com.example.loginapp.Datos_Usuario;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityPerfilBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Perfil extends DrawerBaseActivity {
    TextView nombre, correo;
    String name,emisor;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI = "https://campolimpiojal.com/android/usuario.php";

    //menu
    ActivityPerfilBinding activityPerfilBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //menu
        activityPerfilBinding=ActivityPerfilBinding.inflate(getLayoutInflater());
        setContentView(activityPerfilBinding.getRoot());
        allowActivityTitle("Usuario/Perfil");

        //variable sesion de main
        emisor=MainActivity.obtenerusuario(Perfil.this,MainActivity.m);
       // Toast.makeText(this, emisor, Toast.LENGTH_SHORT).show();

        requestQueue = Volley.newRequestQueue(Perfil.this);
        progressDialog = new ProgressDialog(Perfil.this);


        cargardatosperfil();//datos del usuario


    }//fin on create

  private void cargardatosperfil() {
        nombre=findViewById(R.id.txtnombre);
        correo=findViewById(R.id.txtcorreo);

        //servidor
        progressDialog.setMessage("Procesando...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    JSONArray result=new JSONArray(response);
                    JSONObject jsonObject = result.getJSONObject(0);
                    name=jsonObject.getString("Nombre");

                   // Toast.makeText(Perfil.this, name, Toast.LENGTH_SHORT).show();

                     nombre.setText(name);
                    correo.setText(emisor);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        { //parametros
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

}//fin class