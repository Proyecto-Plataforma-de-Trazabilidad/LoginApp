package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class Index extends AppCompatActivity {

    MaterialButton btnmapa,btnconsul,btnconxm,btnconxe;
    MaterialButton btncontene,btnconteneM,btndistri,btndistriM,btndest,btndestM,btnReco,btnRecoM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        btnmapa= (MaterialButton) findViewById(R.id.btnmapa);
        btnconsul= (MaterialButton) findViewById(R.id.btnconscat);
        btnconxm= (MaterialButton) findViewById(R.id.btnconscatxm);
        btnconxe= (MaterialButton) findViewById(R.id.btnconscatxe);

        btncontene = (MaterialButton) findViewById(R.id.btnconsC);
        btnconteneM= (MaterialButton) findViewById(R.id.btnconsCM);

        btndistri= (MaterialButton) findViewById(R.id.btnconsDis);
        btndistriM= (MaterialButton) findViewById(R.id.btnconsDisM);

        btndest= (MaterialButton) findViewById(R.id.btnconsDes);
        btndestM= (MaterialButton) findViewById(R.id.btnconsDesM);

        btnReco= (MaterialButton) findViewById(R.id.btnconsPriv);
        btnRecoM= (MaterialButton) findViewById(R.id.btnconsPrivM);

        btnmapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapa= new Intent(Index.this,Mapa.class);
                startActivity(mapa);
            }
        });//fin evento boton btnmapa

        btnconsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulG = new Intent(Index.this, ConsultaGeneral.class);
                startActivity(consulG);
            }
        });//fin evento btnconsul

        btnconxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulxM = new Intent(Index.this, SpinnerMunicipio.class);
                startActivity(consulxM);
            }
        });//fin evento btnconxm

        btnconxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulxe= new Intent(Index.this,SpinnerEstado.class);
                startActivity(consulxe);
            }
        });//fin evento btnconxe

        btncontene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//fin evento boton contenedores
        btnconteneM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//fin evento boton contenedores muni


        btndest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//fin evento boton dest
        btndestM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//fin evento boton dest muni

        btndistri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent distri= new Intent(Index.this,ConsulGeneDistr.class);
                startActivity(distri);
            }
        });//fin evento boton dris
        btndistriM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//fin evento boton dris muni

        btnReco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//fin evento boton reco
        btnRecoM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//fin evento boton reco

    }//oncreate
}//main