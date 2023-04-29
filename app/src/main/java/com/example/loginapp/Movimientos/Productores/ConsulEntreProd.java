package com.example.loginapp.Movimientos.Productores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Movimientos.Productores.Entregas.ConsuEntreProdEmD;
import com.example.loginapp.Movimientos.Productores.Entregas.ConsuEntreProdxCAT;
import com.example.loginapp.Movimientos.Productores.Entregas.ConsuEntreProdxDist;
import com.example.loginapp.R;

public class ConsulEntreProd extends AppCompatActivity {
    CardView dis,cat,erp;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul_entre_prod);
        dis=findViewById(R.id.tc1);
        cat=findViewById(R.id.tc2);
        erp=findViewById(R.id.tc3);


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

    }
}