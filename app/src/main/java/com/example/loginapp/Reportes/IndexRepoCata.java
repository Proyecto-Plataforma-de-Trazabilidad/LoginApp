package com.example.loginapp.Reportes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.R;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1Cat;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1Dis;
import com.example.loginapp.databinding.ActivityIndexBinding;
import com.example.loginapp.databinding.ActivityIndexRepoCataBinding;

public class IndexRepoCata extends DrawerBaseActivity {
    ActivityIndexRepoCataBinding aircb;
    CardView CAT,DIS;
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
    }
}