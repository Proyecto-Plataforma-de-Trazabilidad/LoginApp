package com.example.loginapp.Movimientos.AdminAmocali;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.AdminAmocali.Entregas.EntreDisAdmi;
import com.example.loginapp.Movimientos.AdminAmocali.Entregas.EntreErpAdmi;
import com.example.loginapp.Movimientos.AdminAmocali.Entregas.EntreMuniAdmi;
import com.example.loginapp.Movimientos.AdminAmocali.Entregas.EntreProAdmi;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsulEntregasBinding;
import com.example.loginapp.databinding.ActivityIndexmovientosBinding;

public class ConsulEntregas extends DrawerBaseActivity {
    ActivityConsulEntregasBinding activityConsulEntregasBinding;
    CardView PRO,DIS,MU,ERP;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul_entregas);

        //aqui va lo del menu
        activityConsulEntregasBinding= ActivityConsulEntregasBinding.inflate(getLayoutInflater());
        setContentView(activityConsulEntregasBinding.getRoot());
        allowActivityTitle("Movimientos/Entregas");

        PRO=findViewById(R.id.tc0);
        DIS=findViewById(R.id.tc1);
        MU=findViewById(R.id.tc4);
        ERP=findViewById(R.id.tc3);

        PRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            i=new Intent(ConsulEntregas.this, EntreProAdmi.class);
            startActivity(i);
            }
        });
        DIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsulEntregas.this, EntreDisAdmi.class);
                startActivity(i);
            }
        });
        MU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsulEntregas.this, EntreMuniAdmi.class);
                startActivity(i);
            }
        });
        ERP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsulEntregas.this, EntreErpAdmi.class);
                startActivity(i);
            }
        });

    }
}