package com.example.loginapp.Indexs.Movimientos.Productores.Ordenes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Indexs.Movimientos.Productores.consultas_ordenesProductor;
import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;

public class consultaOrdenestipoembaProductor extends AppCompatActivity {
    TableLayout tbtOP,tbtdet;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulOrdenesMoviProductores.php";
    MaterialButton volver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ordenestipoemba_productor);

        tbtOP = findViewById(R.id.tablaO);
        tbtOP.removeAllViews();//remueve columnas

        requestQueue= Volley.newRequestQueue(consultaOrdenestipoembaProductor.this);
        progressDialog=new ProgressDialog(consultaOrdenestipoembaProductor.this);

        //botones
        volver=findViewById(R.id.btnreg1);

        //eventos botones
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver=new Intent(consultaOrdenestipoembaProductor.this, consultas_ordenesProductor.class);
                startActivity(volver);
            }
        });

    }//fin oncreate
}//fin class