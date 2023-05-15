package com.example.loginapp.Reportes;


import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.R;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1ERP;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1Huer;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1Cat;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1Dis;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1Pro;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1VDis;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1VERP;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep2Pro;
import com.example.loginapp.databinding.ActivityIndexRepoCataBinding;

public class IndexRepoCata extends DrawerBaseActivity {
    ActivityIndexRepoCataBinding aircb;
    CardView CAT,DIS,VDIS,PRO,PPRO,HUE,ERP,VERP;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_repo_cata);

        //aqui va lo del menu
        aircb= ActivityIndexRepoCataBinding.inflate(getLayoutInflater());
        setContentView(aircb.getRoot());
        allowActivityTitle("Reportes/Catálogos");

        CAT=findViewById(R.id.T1);
        DIS=findViewById(R.id.T2);
        VDIS=findViewById(R.id.T3);
        PRO=findViewById(R.id.T4);
        PPRO=findViewById(R.id.T4_1);
        HUE=findViewById(R.id.T5);
        ERP=findViewById(R.id.T6);
        VERP=findViewById(R.id.T7);
        CAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoCata.this, Rep1Cat.class);
                startActivity(i);
            }
        });
        DIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoCata.this, Rep1Dis.class);
                startActivity(i);
            }
        });
        VDIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoCata.this, Rep1VDis.class);
                startActivity(i);
            }
        });
        PRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoCata.this, Rep1Pro.class);
                startActivity(i);
            }
        });
        PPRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoCata.this, Rep2Pro.class);
                startActivity(i);
            }
        });
        HUE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoCata.this, Rep1Huer.class);
                startActivity(i);
            }
        });
        ERP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoCata.this, Rep1ERP.class);
                startActivity(i);
            }
        });
        VERP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoCata.this, Rep1VERP.class);
                startActivity(i);
            }
        });

    }
}