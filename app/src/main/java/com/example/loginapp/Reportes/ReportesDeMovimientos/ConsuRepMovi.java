package com.example.loginapp.Reportes.ReportesDeMovimientos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;

public class ConsuRepMovi extends AppCompatActivity {
    MaterialButton TPP,TQP,TEP;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_rep_movi);

        TPP=findViewById(R.id.btn1);
        TQP=findViewById(R.id.btn2);
        TEP=findViewById(R.id.btn3);
        TPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuRepMovi.this, RepTPP.class);
                startActivity(i);
            }
        });
        TQP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuRepMovi.this, RepTQP.class);
                startActivity(i);
            }
        });
        TEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuRepMovi.this, RepTEP.class);
                startActivity(i);
            }
        });
    }
}