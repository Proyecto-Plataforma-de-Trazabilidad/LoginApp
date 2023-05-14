package com.example.loginapp.Movimientos.Distribuidor;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.Distribuidor.Salidas.ConsuGSD;
import com.example.loginapp.Movimientos.Distribuidor.Salidas.ConsuPSD;
import com.example.loginapp.Movimientos.Municipio.Salidas.ConsuGS_Mu;
import com.example.loginapp.Movimientos.Municipio.Salidas.ConsuPS_Mu;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsuSdBinding;

public class ConsuSD extends DrawerBaseActivity {
    ActivityConsuSdBinding ACSB;
    CardView G,Pe;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_sd);


        ACSB= ActivityConsuSdBinding.inflate(getLayoutInflater());
        setContentView(ACSB.getRoot());
        allowActivityTitle("Movimientos");

        G=findViewById(R.id.tc1);
        Pe=findViewById(R.id.tc2);

        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuSD.this, ConsuGSD.class);
                startActivity(i);
            }
        });
        Pe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuSD.this, ConsuPSD.class);
                startActivity(i);
            }
        });
    }
}