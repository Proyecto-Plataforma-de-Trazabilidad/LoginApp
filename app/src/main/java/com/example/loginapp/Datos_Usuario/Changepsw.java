package com.example.loginapp.Datos_Usuario;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Indexs.Index;
import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Changepsw extends AppCompatActivity {
    TextInputEditText ncontra,confirma;
    MaterialButton btnguarda,btnv;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI = "https://proyectoapejal.000webhostapp.com/agenda/forgotPSW.php";//servidor en 000webhost
    String n,c, emisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepsw);

        //variable sesion de main
        emisor= MainActivity.obtenerusuario(Changepsw.this,MainActivity.m);
        //Toast.makeText(this, emisor, Toast.LENGTH_SHORT).show();

        requestQueue = Volley.newRequestQueue(Changepsw.this);
        progressDialog = new ProgressDialog(Changepsw.this);


        ncontra=findViewById(R.id.ncontra);
        confirma=findViewById(R.id.confirma);

        btnguarda=findViewById(R.id.btnguardar);
        btnv=findViewById(R.id.btnv);

        btnguarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valida();
            }
        });
        btnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent vo=new Intent(Changepsw.this,Perfil.class);
                startActivity(vo);
            }
        });
    }
    public void valida(){
        n=ncontra.getText().toString();
        c=confirma.getText().toString();

        if(n.isEmpty()&& c.isEmpty()){
            Toast.makeText(Changepsw.this, "Debes llenar los campos", Toast.LENGTH_SHORT).show();
        }
        else{
            if(n.length()<8 && c.length()<8){
                Toast.makeText(this, "Minimo de 8 caracteres", Toast.LENGTH_SHORT).show();
            }
            else{
                if(n.equals(c)){
                    AlertDialog.Builder builder=new AlertDialog.Builder(Changepsw.this);
                    builder.setMessage("¿Estas seguro de cambiar la contraseña?");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            conectaservidor();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }//fin equals
                else{
                    Toast.makeText(Changepsw.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }//fin 2 else
            }
        }//fin 1 else

    }

    public void conectaservidor(){
        progressDialog.setMessage("Procesando...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    progressDialog.dismiss();
                    String r="CAMBIADO";
                if(response.equals(r)){
                    Toast.makeText(getApplicationContext(),"Cambio Correctamente",Toast.LENGTH_LONG).show();
                    ncontra.setText("");
                    confirma.setText("");
                }
                else{
                    Toast.makeText(getApplicationContext(),"Algo fallo",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Changepsw.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String, String> parametros=new HashMap<>();
                //Parámetros que se esperan en el webservice
                parametros.put("opcion","cambia");
                parametros.put("ncontra",n);
                parametros.put("email",emisor);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}