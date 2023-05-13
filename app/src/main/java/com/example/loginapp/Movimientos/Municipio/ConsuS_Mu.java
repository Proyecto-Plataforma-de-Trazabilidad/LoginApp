package com.example.loginapp.Movimientos.Municipio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.Distribuidor.ConsultasOrdenesDistri;
import com.example.loginapp.Movimientos.ERP.Entregas.ConsuEnGe_ERP;
import com.example.loginapp.Movimientos.ERP.Entregas.ConsuEnPr_ERP;
import com.example.loginapp.Movimientos.Index_movi_distribuidor;
import com.example.loginapp.Movimientos.Municipio.Salidas.ConsuGS_Mu;
import com.example.loginapp.Movimientos.Municipio.Salidas.ConsuPS_Mu;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsuEnCatBinding;
import com.example.loginapp.databinding.ActivityConsuEnErpBinding;
import com.example.loginapp.databinding.ActivityConsuGsMuBinding;

public class ConsuS_Mu extends DrawerBaseActivity {
ActivityConsuGsMuBinding ACGMU;
    CardView G,Pe;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_smu);

        /*
        ACGMU= ActivityConsuGsMuBinding.inflate(getLayoutInflater());
        setContentView(ACGMU.getRoot());
        allowActivityTitle("Movimientos");*/

        G=findViewById(R.id.tc1);
        Pe=findViewById(R.id.tc2);

        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuS_Mu.this, ConsuGS_Mu.class);
                startActivity(i);
            }
        });
        Pe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuS_Mu.this, ConsuPS_Mu.class);
                startActivity(i);
            }
        });
    }
}