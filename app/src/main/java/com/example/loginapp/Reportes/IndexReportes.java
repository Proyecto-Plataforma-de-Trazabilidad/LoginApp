package com.example.loginapp.Reportes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.R;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1Cat;

public class IndexReportes extends AppCompatActivity {
    CardView CAT;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_reportes);

        CAT=findViewById(R.id.T1);
        CAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexReportes.this, Rep1Cat.class);
                startActivity(i);
            }
        });

    }
}