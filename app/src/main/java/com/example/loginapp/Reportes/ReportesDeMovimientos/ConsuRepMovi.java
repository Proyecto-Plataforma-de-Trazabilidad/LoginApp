package com.example.loginapp.Reportes.ReportesDeMovimientos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.R;
import com.example.loginapp.Reportes.IndexReportes;
import com.google.android.material.button.MaterialButton;

public class ConsuRepMovi extends AppCompatActivity {
    MaterialButton TPP;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_rep_movi);

        TPP=findViewById(R.id.btn1);
        TPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuRepMovi.this, RepTPP.class);
                startActivity(i);
            }
        });
    }
}