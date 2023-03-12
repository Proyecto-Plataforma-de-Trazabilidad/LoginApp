package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.example.loginapp.databinding.ActivityIndexCatalogosBinding;


public class IndexGenerales extends DrawerBaseActivity {
    //aqui el menu


    //
    Intent opcion;
    CardView CATG,DES,DIS,CONT,REC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogos_general);



        //tarjetas
        CATG=findViewById(R.id.tarjeta4G);//general


        DES=findViewById(R.id.tarjeta2G);


        DIS=findViewById(R.id.tarjeta1G);


        CONT=findViewById(R.id.tarjeta3G);


        REC=findViewById(R.id.tarjeta5G);


        CATG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulG = new Intent(IndexGenerales.this, ConsultaGeneral.class);
                startActivity(consulG);
            }
        });

        DES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empresagen= new Intent(IndexGenerales.this,ConsulGeneEmpresas.class);
                startActivity(empresagen);
            }
        });

        DIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent distri= new Intent(IndexGenerales.this,ConsulGeneDistr.class);
                startActivity(distri);
            }
        });

        //contenedores
        CONT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conteGen= new Intent(IndexGenerales.this,ConsulGeneConte.class);
                startActivity(conteGen);
            }
        });


        //recolectores
        REC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reco= new Intent(IndexGenerales.this,ConsulGeneRecolectora.class);
                startActivity(reco);
            }
        });


    }
}