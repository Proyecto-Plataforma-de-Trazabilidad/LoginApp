package com.example.loginapp.Indexs.Movimientos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Indexs.Movimientos.Distribuidor.consultas_ordenes_distribuidor;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexMoviDistribuidorBinding;

public class Index_movi_distribuidor extends DrawerBaseActivity {
    ActivityIndexMoviDistribuidorBinding activityIndexMoviDistribuidorBinding;
    CardView Ordenes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_movi_distribuidor);

        //aqui va lo del menu
        activityIndexMoviDistribuidorBinding= ActivityIndexMoviDistribuidorBinding.inflate(getLayoutInflater());
        setContentView(activityIndexMoviDistribuidorBinding.getRoot());
        allowActivityTitle("Movimientos");

        Ordenes=findViewById(R.id.t1);
        Ordenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent or=new Intent(Index_movi_distribuidor.this, consultas_ordenes_distribuidor.class);
                startActivity(or);
            }
        });
    }
}