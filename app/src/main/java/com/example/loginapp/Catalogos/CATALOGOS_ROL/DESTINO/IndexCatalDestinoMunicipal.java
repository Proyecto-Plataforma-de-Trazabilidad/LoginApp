package com.example.loginapp.Catalogos.CATALOGOS_ROL.DESTINO;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Catalogos.Catalogos_muni.FormularioMuniCAT;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexCatalDestinoMunicipalBinding;

public class IndexCatalDestinoMunicipal extends DrawerBaseActivity {
    ActivityIndexCatalDestinoMunicipalBinding cdmb;
    CardView CATM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_catal_destino_municipal);

        //aqui va lo del menu
        cdmb= ActivityIndexCatalDestinoMunicipalBinding.inflate(getLayoutInflater());
        setContentView(cdmb.getRoot());
        allowActivityTitle("Catálogos/Municipales");

        CATM=findViewById(R.id.tarjeta4G);//muni
        CATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulxM = new Intent(IndexCatalDestinoMunicipal.this, FormularioMuniCAT.class);
                startActivity(consulxM);
            }
        });

    }
}