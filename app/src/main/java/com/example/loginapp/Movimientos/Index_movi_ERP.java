package com.example.loginapp.Movimientos;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.ERP.ConsuEn_ERP;
import com.example.loginapp.Movimientos.ERP.ConsuS_ERP;
import com.example.loginapp.Movimientos.Municipio.ConsuS_Mu;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexMoviErpBinding;

public class Index_movi_ERP extends DrawerBaseActivity {
    ActivityIndexMoviErpBinding IMEB;
    CardView Entregas,salidas;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_movi_erp);

        //aqui va lo del menu
        IMEB= ActivityIndexMoviErpBinding.inflate(getLayoutInflater());
        setContentView(IMEB.getRoot());
        allowActivityTitle("Movimientos");

        Entregas=findViewById(R.id.t1);
        salidas=findViewById(R.id.t2);
        Entregas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(Index_movi_ERP.this, ConsuEn_ERP.class);
                startActivity(i);
            }
        });//fin onclick
        salidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(Index_movi_ERP.this, ConsuS_ERP.class);
                startActivity(i);

            }
        });//fin onclick
    }
}