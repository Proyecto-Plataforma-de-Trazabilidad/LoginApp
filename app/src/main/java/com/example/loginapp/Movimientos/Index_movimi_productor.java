package com.example.loginapp.Movimientos;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.Productores.ConsulEntreProd;
import com.example.loginapp.Movimientos.Productores.ConsultasExtraviadosProductor;
import com.example.loginapp.Movimientos.Productores.consultas_ordenesProductor;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexMovimiProductorBinding;

public class Index_movimi_productor extends DrawerBaseActivity {
    ActivityIndexMovimiProductorBinding activityIndexMovimiProductorBinding;
    CardView Ordenes,Entregas,Extraviados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_movimi_productor);

        //aqui va lo del menu
        activityIndexMovimiProductorBinding= ActivityIndexMovimiProductorBinding.inflate(getLayoutInflater());
        setContentView(activityIndexMovimiProductorBinding.getRoot());
        allowActivityTitle("Movimientos");


        Ordenes=findViewById(R.id.t1);
        Entregas=findViewById(R.id.t2);
        Extraviados=findViewById(R.id.t3);

        Ordenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent or=new Intent(Index_movimi_productor.this, consultas_ordenesProductor.class);
                startActivity(or);
            }
        });
        Entregas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent en=new Intent(Index_movimi_productor.this, ConsulEntreProd.class);
                startActivity(en);
            }
        });
        Extraviados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ex=new Intent(Index_movimi_productor.this, ConsultasExtraviadosProductor.class);
                startActivity(ex);
            }
        });
    }
}