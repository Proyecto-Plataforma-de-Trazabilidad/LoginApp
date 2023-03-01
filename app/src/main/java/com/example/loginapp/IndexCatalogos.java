package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IndexCatalogos extends AppCompatActivity {
    Intent opcion;
    CardView CATM,CATG,DES,DESM,DIS,DISM,CONT,CONTM,REC,RECM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_catalogos);

        //tarjetas
        CATG=findViewById(R.id.tarjeta4G);//general
        CATM=findViewById(R.id.tarjeta4M);//municipio

        DES=findViewById(R.id.tarjeta2G);
        DESM=findViewById(R.id.tarjeta2M);

        DIS=findViewById(R.id.tarjeta1G);
        DISM=findViewById(R.id.tarjeta1M);

        CONT=findViewById(R.id.tarjeta3G);
        CONTM=findViewById(R.id.tarjeta3M);

        REC=findViewById(R.id.tarjeta5G);
        RECM=findViewById(R.id.tarjeta5M);

        //eventos tarjetas cat
        CATG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulG = new Intent(IndexCatalogos.this, ConsultaGeneral.class);
                startActivity(consulG);
            }
        });
        CATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulxM = new Intent(IndexCatalogos.this, FormularioMuniCAT.class);
                startActivity(consulxM);
            }
        });

        //evento destino
        DES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empresagen= new Intent(IndexCatalogos.this,ConsulGeneEmpresas.class);
                startActivity(empresagen);
            }
        });
        DESM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empresamun= new Intent(IndexCatalogos.this,FormularioEmpre.class);
                startActivity(empresamun);
            }
        });


        //distribuidores
        DIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent distri= new Intent(IndexCatalogos.this,ConsulGeneDistr.class);
                startActivity(distri);
            }
        });
        DISM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Fdis= new Intent(IndexCatalogos.this,FormularioDistri.class);
                startActivity(Fdis);
            }
        });

        //contenedores
        CONT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conteGen= new Intent(IndexCatalogos.this,ConsulGeneConte.class);
                startActivity(conteGen);
            }
        });
        CONTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conteOriGen= new Intent(IndexCatalogos.this,FormularioConte.class);
                startActivity(conteOriGen);
            }
        });

        //recolectores
        REC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reco= new Intent(IndexCatalogos.this,ConsulGeneRecolectora.class);
                startActivity(reco);
            }
        });
        RECM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Freco= new Intent(IndexCatalogos.this,FormularioRecolec.class);
                startActivity(Freco);
            }
        });

    }
}