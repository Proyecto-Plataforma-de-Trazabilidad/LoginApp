package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class Index extends AppCompatActivity {

    MaterialButton btnmapa,btnconsul;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        btnmapa= (MaterialButton) findViewById(R.id.btnmapa);
        btnconsul= (MaterialButton) findViewById(R.id.btnconscat);

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

    }//oncreate
}//main