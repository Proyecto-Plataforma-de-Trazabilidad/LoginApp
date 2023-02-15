package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class Index extends AppCompatActivity {

    MaterialButton btnmapa,btnconsul,btnconxm,btnconxe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        btnmapa= (MaterialButton) findViewById(R.id.btnmapa);
        btnconsul= (MaterialButton) findViewById(R.id.btnconscat);
        btnconxm= (MaterialButton) findViewById(R.id.btnconscatxm);
        btnconxe= (MaterialButton) findViewById(R.id.btnconscatxe);

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
            }
        });//fin evento btnconsul

        btnconxm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });//fin evento btnconxm

        btnconxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulxe= new Intent(Index.this,SpinnerEstado.class);
                startActivity(consulxe);
            }
        });//fin evento btnconxe


    }//oncreate
}//main