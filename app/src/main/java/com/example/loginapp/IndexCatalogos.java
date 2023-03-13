package com.example.loginapp;

import androidx.cardview.widget.CardView;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.example.loginapp.databinding.ActivityIndexCatalogos2Binding;

public class IndexCatalogos extends DrawerBaseActivity {

    ActivityIndexCatalogos2Binding indexCatalogos;
    CardView Generales,Municipales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_catalogos2);

        Generales = findViewById(R.id.tarjetaGeneral);
        Municipales = findViewById(R.id.tarjetaMunicipal);


        Generales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent distG= new Intent(IndexCatalogos.this,IndexGenerales.class);
                startActivity(distG);
            }
        });
        Municipales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent distE= new Intent(IndexCatalogos.this,IndexMunicipales.class);
                startActivity(distE);
            }
        });

        indexCatalogos=ActivityIndexCatalogos2Binding.inflate(getLayoutInflater());
        setContentView(indexCatalogos.getRoot());
        allowActivityTitle("Catalogos");
    }

}