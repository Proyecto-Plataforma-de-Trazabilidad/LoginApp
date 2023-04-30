package com.example.loginapp.Movimientos.Productores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.Productores.Entregas.ConsuEntreProdEmD;
import com.example.loginapp.Movimientos.Productores.Entregas.ConsuEntreProdxCAT;
import com.example.loginapp.Movimientos.Productores.Entregas.ConsuEntreProdxDist;
import com.example.loginapp.Movimientos.Productores.Entregas.ConsuEntreProdxMu;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsulEntreProdBinding;
import com.example.loginapp.databinding.ActivityConsultasOrdenesProductorBinding;

public class ConsulEntreProd extends DrawerBaseActivity {
    ActivityConsulEntreProdBinding ACEPB;
    CardView dis,cat,erp,muni;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul_entre_prod);

        //aqui va lo del menu
        ACEPB= ActivityConsulEntreProdBinding.inflate(getLayoutInflater());
        setContentView(ACEPB.getRoot());
        allowActivityTitle("Movimientos/Entregas");

        dis=findViewById(R.id.tc1);
        cat=findViewById(R.id.tc2);
        erp=findViewById(R.id.tc3);
        muni=findViewById(R.id.tc4);


        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsulEntreProd.this, ConsuEntreProdxDist.class);
                startActivity(i);
            }
        });
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsulEntreProd.this, ConsuEntreProdxCAT.class);
                startActivity(i);
            }
        });
        erp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsulEntreProd.this, ConsuEntreProdEmD.class);
                startActivity(i);
            }
        });
        muni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsulEntreProd.this, ConsuEntreProdxMu.class);
                startActivity(i);
            }
        });

    }
}