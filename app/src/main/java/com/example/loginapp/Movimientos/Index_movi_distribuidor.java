package com.example.loginapp.Movimientos;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;


import com.example.loginapp.Movimientos.Distribuidor.ConsultasOrdenesDistri;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexMoviDistribuidorBinding;

public class Index_movi_distribuidor extends DrawerBaseActivity {
    ActivityIndexMoviDistribuidorBinding activityIndexMoviDistribuidorBinding;
    CardView Ordenes,Entregas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_movi_distribuidor);

        //aqui va lo del menu
        activityIndexMoviDistribuidorBinding= ActivityIndexMoviDistribuidorBinding.inflate(getLayoutInflater());
        setContentView(activityIndexMoviDistribuidorBinding.getRoot());
        allowActivityTitle("Movimientos");

        Ordenes=findViewById(R.id.t1);
        Entregas=findViewById(R.id.t2);
        Ordenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent or=new Intent(Index_movi_distribuidor.this, ConsultasOrdenesDistri.class);
                startActivity(or);
            }
        });
        Entregas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent en=new Intent(Index_movi_distribuidor.this, IndexEntregas.class);
               // startActivity(en);
            }
        });
    }
}