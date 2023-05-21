package com.example.loginapp.Reportes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.R;
import com.example.loginapp.Reportes.Estadisticos.ConteMasConcu;
import com.example.loginapp.Reportes.Estadisticos.DisMasConcu;
import com.example.loginapp.Reportes.Estadisticos.DisMenosEntre;
import com.example.loginapp.Reportes.Estadisticos.EnvMasO;
import com.example.loginapp.Reportes.Estadisticos.MunisMenosEntr;
import com.example.loginapp.Reportes.Estadisticos.ProducMasOrden;

public class IndexRepoEstadis extends AppCompatActivity {

    CardView t1,t2,t3,t4,t5,t6;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_repo_estadis);
        t1=findViewById(R.id.T1);
        t2=findViewById(R.id.T2);
        t3=findViewById(R.id.T3);
        t4=findViewById(R.id.T4);
        t5=findViewById(R.id.T5);
        t6=findViewById(R.id.T6);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoEstadis.this, EnvMasO.class);
                startActivity(i);
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoEstadis.this, DisMasConcu.class);
                startActivity(i);
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoEstadis.this, ConteMasConcu.class);
                startActivity(i);
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoEstadis.this, ProducMasOrden.class);
                startActivity(i);
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoEstadis.this, MunisMenosEntr.class);
                startActivity(i);
            }
        });
        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoEstadis.this, DisMenosEntre.class);
                startActivity(i);
            }
        });
    }
}