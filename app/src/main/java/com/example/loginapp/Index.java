package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.loginapp.databinding.ActivityIndexBinding;


public class Index extends DrawerBaseActivity {

    //es del menu para enlazarlo
    ActivityIndexBinding activityIndexBinding;

    //tarjetas
    CardView Generales,Municipales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_catalogos);

        //aqui va lo del menu
        activityIndexBinding=ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(activityIndexBinding.getRoot());
        allowActivityTitle("Inicio");


        /*regresa el index de las imaguenes de aqui

        Generales = findViewById(R.id.tarjetaGeneral);
        Municipales = findViewById(R.id.tarjetaMunicipal);

        Generales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent distG= new Intent(Index.this,IndexGenerales.class);
                startActivity(distG);
            }
        });// Distribuidores General
        Municipales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent distE= new Intent(Index.this,IndexMunicipales.class);
                startActivity(distE);
            }
        });// Distribuidores Municipal */
    }
}