package com.example.loginapp.Catalogos.CATALOGOS_ROL.AMOCALI;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Catalogos.Catalogos_muni.FormularioConte;
import com.example.loginapp.Catalogos.Catalogos_muni.FormularioEmpre;
import com.example.loginapp.Catalogos.Catalogos_muni.FormularioMuniCAT;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexCataAmocalimuniciBinding;

public class IndexCataAMOCALImunici extends DrawerBaseActivity {
    ActivityIndexCataAmocalimuniciBinding amomunibinding;
    CardView CATM,DESM,CONTM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_cata_amocalimunici);

        //aqui va lo del menu
        amomunibinding= ActivityIndexCataAmocalimuniciBinding.inflate(getLayoutInflater());
        setContentView(amomunibinding.getRoot());
        allowActivityTitle("Catálogos/Municipales");

        CATM=findViewById(R.id.tarjeta4G);//general
        DESM=findViewById(R.id.tarjeta2G);
        CONTM=findViewById(R.id.tarjeta3G);

        CATM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulxM = new Intent(IndexCataAMOCALImunici.this, FormularioMuniCAT.class);
                startActivity(consulxM);
            }
        });

        DESM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empresamun= new Intent(IndexCataAMOCALImunici.this, FormularioEmpre.class);
                startActivity(empresamun);
            }
        });

        CONTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conteOriGen= new Intent(IndexCataAMOCALImunici.this, FormularioConte.class);
                startActivity(conteOriGen);
            }
        });
    }
}