package com.example.loginapp.Reportes;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.IndexRepoEstadis;
import com.example.loginapp.R;
import com.example.loginapp.Reportes.ReportesDeMovimientos.ConsuRepMovi;
import com.example.loginapp.databinding.ActivityIndexReportesBinding;

public class IndexReportes extends DrawerBaseActivity {
    ActivityIndexReportesBinding airb;
    CardView CATA,MOVI,ESTA;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_reportes);

        //aqui va lo del menu
        airb= ActivityIndexReportesBinding.inflate(getLayoutInflater());
        setContentView(airb.getRoot());
        allowActivityTitle("Reportes");

        CATA=findViewById(R.id.T1);
        MOVI=findViewById(R.id.T2);
        ESTA=findViewById(R.id.T3);
        CATA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexReportes.this, IndexRepoCata.class);
                startActivity(i);
            }
        });
        MOVI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexReportes.this, ConsuRepMovi.class);
                startActivity(i);
            }
        });
        ESTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               i=new Intent(IndexReportes.this, IndexRepoEstadis.class);
               startActivity(i);
            }
        });

    }
}