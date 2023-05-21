package com.example.loginapp.Movimientos;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.AdminAmocali.Extraviados.ConsuExtraviados;
import com.example.loginapp.Movimientos.AdminAmocali.Ordenes.ConsuOrdenes;
import com.example.loginapp.Movimientos.AdminAmocali.ConsuSalidas;
import com.example.loginapp.Movimientos.AdminAmocali.ConsulEntregas;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexmovientosBinding;

public class indexmovientos extends DrawerBaseActivity {
    ActivityIndexmovientosBinding activityIndexmovientosBinding;
    CardView Ordenes,Entregas,Extraviados,Salidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indexmovientos);

        //aqui va lo del menu
        activityIndexmovientosBinding= ActivityIndexmovientosBinding.inflate(getLayoutInflater());
        setContentView(activityIndexmovientosBinding.getRoot());
        allowActivityTitle("Movimientos");

        Ordenes=findViewById(R.id.t1);
        Entregas=findViewById(R.id.t2);
        Extraviados=findViewById(R.id.t3);
        Salidas=findViewById(R.id.t4);

        Ordenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent or=new Intent(indexmovientos.this, ConsuOrdenes.class);
                startActivity(or);
            }
        });
        Entregas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent en=new Intent(indexmovientos.this, ConsulEntregas.class);
                startActivity(en);
            }
        });
        Extraviados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ex=new Intent(indexmovientos.this, ConsuExtraviados.class);
                startActivity(ex);
            }
        });
        Salidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sa=new Intent(indexmovientos.this, ConsuSalidas.class);
                startActivity(sa);
            }
        });

    }
}