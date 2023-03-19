package com.example.loginapp.Indexs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Catalogos_muni.FormularioConte;
import com.example.loginapp.Catalogos_muni.FormularioDistri;
import com.example.loginapp.Catalogos_muni.FormularioEmpre;
import com.example.loginapp.Catalogos_muni.FormularioMuniCAT;
import com.example.loginapp.Catalogos_muni.FormularioRecolec;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityCatalogosMunicipalBinding;


public class IndexMunicipales extends DrawerBaseActivity {
    ActivityCatalogosMunicipalBinding activityCatalogosMunicipalBinding;

    Intent opcion;
    CardView CATM,DESM,DISM,CONTM,RECM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogos_municipal);


        //aqui va lo del menu
        activityCatalogosMunicipalBinding= ActivityCatalogosMunicipalBinding.inflate(getLayoutInflater());
        setContentView(activityCatalogosMunicipalBinding.getRoot());
        allowActivityTitle("Catálogos/Municipales");

        CATM=findViewById(R.id.tarjeta4G);//general


        DESM=findViewById(R.id.tarjeta2G);


        DISM=findViewById(R.id.tarjeta1G);


        CONTM=findViewById(R.id.tarjeta3G);


        RECM=findViewById(R.id.tarjeta5G);


        CATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulxM = new Intent(IndexMunicipales.this, FormularioMuniCAT.class);
                startActivity(consulxM);
            }
        });


        DESM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empresamun= new Intent(IndexMunicipales.this, FormularioEmpre.class);
                startActivity(empresamun);
            }
        });

        DISM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Fdis= new Intent(IndexMunicipales.this, FormularioDistri.class);
                startActivity(Fdis);
            }
        });

        //contenedores

        CONTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conteOriGen= new Intent(IndexMunicipales.this, FormularioConte.class);
                startActivity(conteOriGen);
            }
        });

        RECM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Freco= new Intent(IndexMunicipales.this, FormularioRecolec.class);
                startActivity(Freco);
            }
        });

    }
}