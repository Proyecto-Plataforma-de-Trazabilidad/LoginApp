package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MapaEstado extends AppCompatActivity {

    private GoogleMap mMap;

    MaterialButton btnregresar;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    String httpURI="https://proyectoapejal.000webhostapp.com/agenda/marcadoresjson.php";

    ArrayList<Marcadores> listaPuntos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_estado);

        Bundle bundle= getIntent().getExtras();//se trae lo que se mando en el extra en el evento al consultar
        String dato= bundle.getString("Estado");//dato rescata el string del identificador(osea el valor del estado seleccionado)
        Toast.makeText(this,dato, Toast.LENGTH_SHORT).show();//imprime nomas para asegurarce

        btnregresar= (MaterialButton) findViewById(R.id.btnregresar2);

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regresa= new Intent(MapaEstado.this,SpinnerEstado.class);
                startActivity(regresa);
            }
        });


    }
}