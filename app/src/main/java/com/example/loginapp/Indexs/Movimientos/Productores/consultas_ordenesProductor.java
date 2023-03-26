package com.example.loginapp.Indexs.Movimientos.Productores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsultasOrdenesProductorBinding;
import com.example.loginapp.databinding.ActivityIndexMoviDistribuidorBinding;

public class consultas_ordenesProductor extends DrawerBaseActivity {
    ActivityConsultasOrdenesProductorBinding consultasOrdenesProductorBinding;
    CardView G,P,TQ,TE;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_ordenes_productor);

        //aqui va lo del menu
        consultasOrdenesProductorBinding= ActivityConsultasOrdenesProductorBinding.inflate(getLayoutInflater());
        setContentView(consultasOrdenesProductorBinding.getRoot());
        allowActivityTitle("Movimientos/Ordenes");

        G=findViewById(R.id.tc1);
        P=findViewById(R.id.tc2);
        TQ=findViewById(R.id.tc3);
        TE=findViewById(R.id.tc4);
        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(consultas_ordenesProductor.this,ConsulGeneralDelProductor.class);
                startActivity(i);
            }
        });
        P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(consultas_ordenesProductor.this,ConsultaOrdenesPeridoProductor.class);
                startActivity(i);
            }
        });
        TQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(consultas_ordenesProductor.this,ConsultaOrdenesTipoQuimicoProductor.class);
                startActivity(i);
            }
        });
        TE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(consultas_ordenesProductor.this,consultaOrdenestipoembaProductor.class);
                startActivity(i);
            }
        });
    }
}