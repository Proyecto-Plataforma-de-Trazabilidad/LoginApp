package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class Index extends AppCompatActivity {

    CardView Generales,Municipales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_catalogos);

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
        });// Distribuidores Municipal

    }
}