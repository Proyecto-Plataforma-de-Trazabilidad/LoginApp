package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    EditText username,passname;
    MaterialButton loginbtn;
    String u,c;

    //Animación de carga del webservice para ver que hace la aplicación
   // ProgressDialog progressDialog;
    //Cuando mandamos petición, se debe enviar string con muchos elementos para ejecutar volley
    //RequestQueue requestQueue;
    //String httpURI="https://moveint.com/agenda/usuario.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       username=(EditText) findViewById(R.id.username);
       passname=(EditText) findViewById(R.id.passname);

       loginbtn=(MaterialButton) findViewById(R.id.buttonacceder);

        //Inicializar o enlazar a requestQueue y el progressDialog
        //requestQueue= Volley.newRequestQueue(MainActivity.this);
        //Indicar dónde se ejecutará progressdialog
        //progressDialog=new ProgressDialog(MainActivity.this);
        //cuando se de click
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });


    }
    private void Login(){
        u=username.getText().toString();
        c=passname.getText().toString();
        if(u.isEmpty() || c.isEmpty()){
            //vacio
            Toast.makeText(getApplicationContext(), "Debes introducir los datos en los dos campos", Toast.LENGTH_LONG).show();
        }
        else{
            //
            Toast.makeText(getApplicationContext(), "correcto o incorrecto", Toast.LENGTH_LONG).show();
        }
    }
}