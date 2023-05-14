package com.example.loginapp.Movimientos;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;

import com.example.loginapp.Movimientos.Municipio.ConsuS_Mu;
import com.example.loginapp.Movimientos.Municipio.ConsulEntreMuni;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexmovimientoMunicipiosBinding;

public class indexmovimientoMunicipios extends DrawerBaseActivity {
    ActivityIndexmovimientoMunicipiosBinding immb;

    CardView Entregas,Salidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indexmovimiento_municipios);

        //aqui va lo del menu
        immb= ActivityIndexmovimientoMunicipiosBinding.inflate(getLayoutInflater());
        setContentView(immb.getRoot());
        allowActivityTitle("Movimientos");

        Entregas=findViewById(R.id.t1);
        Salidas=findViewById(R.id.t2);
        Entregas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent en=new Intent(indexmovimientoMunicipios.this, ConsulEntreMuni.class);
                startActivity(en);
            }
        });//fin onclick
        Salidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent en=new Intent(indexmovimientoMunicipios.this, ConsuS_Mu.class);
                startActivity(en);
            }
        });
    }//finoncreate
}