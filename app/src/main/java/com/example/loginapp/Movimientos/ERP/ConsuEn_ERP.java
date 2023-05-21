package com.example.loginapp.Movimientos.ERP;

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
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsuEnErpBinding;

public class ConsuEn_ERP extends DrawerBaseActivity {
    ActivityConsuEnErpBinding ACEEB;
    CardView G,Pro;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_en_erp);

        //aqui va lo del menu
        ACEEB= ActivityConsuEnErpBinding.inflate(getLayoutInflater());
        setContentView(ACEEB.getRoot());
        allowActivityTitle("Movimientos/Entregas");

        G=findViewById(R.id.tc2);
        Pro=findViewById(R.id.tc1);

        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuEn_ERP.this, ConsuEnGe_ERP.class);
                startActivity(i);
            }
        });
        Pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuEn_ERP.this, ConsuEnPr_ERP.class);
                startActivity(i);
            }
        });
    }
}