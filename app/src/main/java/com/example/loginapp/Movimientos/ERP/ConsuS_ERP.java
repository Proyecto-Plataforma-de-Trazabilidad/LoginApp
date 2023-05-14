package com.example.loginapp.Movimientos.ERP;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.ERP.Salidas.ConsuGS_ERP;
import com.example.loginapp.Movimientos.ERP.Salidas.ConsuPS_ERP;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsuSerpBinding;

public class ConsuS_ERP extends DrawerBaseActivity {
    ActivityConsuSerpBinding ACSB;
    CardView G,Pe;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_sd);

        //Menu
       /* ACSB= ActivityConsuSerpBinding.inflate(getLayoutInflater());
        setContentView(ACSB.getRoot());
        allowActivityTitle("Movimientos");*/

        G=findViewById(R.id.tc1);
        Pe=findViewById(R.id.tc2);

        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuS_ERP.this, ConsuGS_ERP.class);
                startActivity(i);
            }
        });
        Pe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuS_ERP.this, ConsuPS_ERP.class);
                startActivity(i);
            }
        });
    }
}