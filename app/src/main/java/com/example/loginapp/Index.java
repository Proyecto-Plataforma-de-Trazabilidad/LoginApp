package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;

public class Index extends AppCompatActivity {

    ImageView btnDistribuidores,btnEmpresas,btnContenedores,btnCat,btnERP;
    ImageView btnDistribuidoresMnc,btnEmpresasMnc,btnContenedoresMnc,btnCatMnc,btnERPMnc;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_catalogos);

        btnDistribuidores = findViewById(R.id.DISTRIBUIDORES);
        btnDistribuidoresMnc = findViewById(R.id.DM);
        btnEmpresas = findViewById(R.id.DesG);
        btnEmpresasMnc = findViewById(R.id.DesM);
        btnContenedores = findViewById(R.id.ConteneG);
        btnContenedoresMnc = findViewById(R.id.ConteneM);
        btnCat = findViewById(R.id.CATGrl);
        btnCatMnc = findViewById(R.id.CATM);
        btnERP = findViewById(R.id.RECOL);
        btnERPMnc = findViewById(R.id.RECOLM);

        btnDistribuidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent distG= new Intent(Index.this,ConsulGeneDistr.class);
                startActivity(distG);
            }
        });// Distribuidores General
        btnDistribuidoresMnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent distE= new Intent(Index.this,FormularioDistri.class);
                startActivity(distE);
            }
        });// Distribuidores Municipal
        btnEmpresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empresaG= new Intent(Index.this,ConsulGeneEmpresas.class);
                startActivity(empresaG);
            }
        });// Empresas General
        btnEmpresasMnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empresaM= new Intent(Index.this,FormularioEmpre.class);
                startActivity(empresaM);
            }
        });// Empresas Municipal
        btnContenedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conteneg= new Intent(Index.this,ConsulGeneConte.class);
                startActivity(conteneg);
            }
        });// Contenedores General
        btnContenedoresMnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conteO= new Intent(Index.this,FormularioConte.class);
                startActivity(conteO);
            }
        });// Contenedores Municipal
        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CatG= new Intent(Index.this,ConsultaGeneral.class);
                startActivity(CatG);
            }
        });// CAT General
        btnCatMnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CatM= new Intent(Index.this,FormularioMuniCAT.class);
                startActivity(CatM);
            }
        });// CAT Municipal
        btnERP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa= new Intent(Index.this,ConsulGeneRecolectora.class);
                startActivity(mapa);
            }
        });// ERP general
        btnERPMnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa= new Intent(Index.this,FormularioRecolec.class);
                startActivity(mapa);
            }
        });// ERP Municipal

    }
}