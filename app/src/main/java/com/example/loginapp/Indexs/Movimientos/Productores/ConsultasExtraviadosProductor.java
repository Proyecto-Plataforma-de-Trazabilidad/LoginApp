package com.example.loginapp.Indexs.Movimientos.Productores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Indexs.Movimientos.Productores.Extraviados.ConsultaExtraviadosProductor;
import com.example.loginapp.Indexs.Movimientos.Productores.Extraviados.ConsultaExtraviadosTipoEnvase;
import com.example.loginapp.Indexs.Movimientos.Productores.Extraviados.ConsultasExtraviadosPeriodo;
import com.example.loginapp.Indexs.Movimientos.Productores.Ordenes.ConsulGeneralDelProductor;
import com.example.loginapp.Indexs.Movimientos.Productores.Ordenes.ConsultaOrdenesPeridoProductor;
import com.example.loginapp.Indexs.Movimientos.Productores.Ordenes.consultaOrdenestipoembaProductor;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsultasExtraviadosProductorBinding;
import com.example.loginapp.databinding.ActivityIndexMovimiProductorBinding;

public class ConsultasExtraviadosProductor extends DrawerBaseActivity {
    ActivityConsultasExtraviadosProductorBinding cepb;
    CardView G,P,TE;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_extraviados_productor);

        //aqui va lo del menu
        cepb= ActivityConsultasExtraviadosProductorBinding.inflate(getLayoutInflater());
        setContentView(cepb.getRoot());
        allowActivityTitle("Movimientos/Extraviados");

        G=findViewById(R.id.tc1);
        P=findViewById(R.id.tc2);
        TE=findViewById(R.id.tc4);

        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsultasExtraviadosProductor.this, ConsultaExtraviadosProductor.class);
                startActivity(i);
            }
        });
        P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsultasExtraviadosProductor.this, ConsultasExtraviadosPeriodo.class);
                startActivity(i);
            }
        });
        TE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsultasExtraviadosProductor.this, ConsultaExtraviadosTipoEnvase.class);
                startActivity(i);
            }
        });
    }
}