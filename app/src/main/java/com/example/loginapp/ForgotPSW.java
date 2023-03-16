package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

public class ForgotPSW extends AppCompatActivity {
    MaterialButton btnrecu;
    EditText correo;
    String mail;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI = "https://proyectoapejal.000webhostapp.com/agenda/forgotPSW.php";//servidor en 000webhost


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_psw);

        btnrecu= (MaterialButton) findViewById(R.id.btnrecupera);
        correo=findViewById(R.id.mailrecupera);

        //Inicializar o enlazar a requestQueue y el progressDialog
        requestQueue = Volley.newRequestQueue(ForgotPSW.this);
        //Indicar dónde se ejecutará progressdialog
        progressDialog = new ProgressDialog(ForgotPSW.this);

        Restablecer();
    }

    private void Restablecer() {
        btnrecu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail=correo.getText().toString();

                if (mail.isEmpty()){
                    //vacio
                    Toast.makeText(getApplicationContext(), "Debes introducir los datos.", Toast.LENGTH_LONG).show();
                }
                else {
                    progressDialog.setMessage("Procesando...");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, httpURI, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            String r=response.toString();
                            if(response.equals(r)){
                                Toast.makeText(getApplicationContext(),"Correo enviado correctamente, verifica tu correo",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Falló",Toast.LENGTH_LONG).show();
                            }

                        }//fin onresponse
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
                        }//fin onerror
                    }){
                        protected Map<String,String> getParams(){
                            Map<String, String> parametros=new HashMap<>();
                            //Parámetros que se esperan en el webservice
                            parametros.put("email",mail);
                            parametros.put("opcion","recupera");
                            return parametros;
                        }
                    };
                    //requestQueue ejecute la cadena
                    requestQueue.add(stringRequest);
                }//fin else

            }
        });
    }
}//fin