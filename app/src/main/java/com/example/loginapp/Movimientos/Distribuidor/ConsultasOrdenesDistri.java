package com.example.loginapp.Movimientos.Distribuidor;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.Distribuidor.Ordenes.consulGeneralDist;
import com.example.loginapp.Movimientos.Distribuidor.Ordenes.consultaOrdenEnvaseDistribuidor;
import com.example.loginapp.Movimientos.Distribuidor.Ordenes.ConsultaOrdenesQuimicoDist;
import com.example.loginapp.Movimientos.Distribuidor.Ordenes.ConsultaOrdenesPeridoDistribuidor;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsultasOrdenesDistriBinding;

public class ConsultasOrdenesDistri extends DrawerBaseActivity{
        ActivityConsultasOrdenesDistriBinding consultasOrdenesDistriBinding;
        CardView G,P,TQ,TE;
        Intent i;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_consultas_ordenes_distri);

            //aqui va lo del menu
            consultasOrdenesDistriBinding= ActivityConsultasOrdenesDistriBinding.inflate(getLayoutInflater());
            setContentView(consultasOrdenesDistriBinding.getRoot());
            allowActivityTitle("Movimientos/Ordenes");

            G=findViewById(R.id.tc1);
            P=findViewById(R.id.tc2);
            TQ=findViewById(R.id.tc3);
            TE=findViewById(R.id.tc4);
            G.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=new Intent(ConsultasOrdenesDistri.this, consulGeneralDist.class);
                    startActivity(i);
                }
            });
            P.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=new Intent(ConsultasOrdenesDistri.this, ConsultaOrdenesPeridoDistribuidor.class);
                    startActivity(i);
                }
            });
            TQ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=new Intent(ConsultasOrdenesDistri.this, ConsultaOrdenesQuimicoDist.class);
                    startActivity(i);
                }
            });
            TE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i=new Intent(ConsultasOrdenesDistri.this, consultaOrdenEnvaseDistribuidor.class);
                    startActivity(i);
                }
            });
        }
    }