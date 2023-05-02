package com.example.loginapp.Movimientos.Distribuidor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.Distribuidor.Entregas.ConsulEntregaxProductor;
import com.example.loginapp.R;

public class ConsuEntD extends AppCompatActivity {


    CardView Productor, EntregasGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_ent_d);


        EntregasGeneral=findViewById(R.id.tc1);
        Productor=findViewById(R.id.tc2);

        EntregasGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent or=new Intent(ConsuEntD.this, ConsultasOrdenesDistri.class);
                startActivity(or);
            }
        });
        Productor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent en=new Intent(ConsuEntD.this, ConsulEntregaxProductor.class);
                startActivity(en);
            }
        });
    }
}