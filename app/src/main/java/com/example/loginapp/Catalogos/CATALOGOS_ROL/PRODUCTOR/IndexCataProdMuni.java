package com.example.loginapp.Catalogos.CATALOGOS_ROL.PRODUCTOR;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Catalogos.Catalogos_muni.FormularioDistri;
import com.example.loginapp.Catalogos.Catalogos_muni.FormularioMuniCAT;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexCataProdMuniBinding;

public class IndexCataProdMuni extends DrawerBaseActivity {
    ActivityIndexCataProdMuniBinding icpmb;
    CardView CATG,DIS,JORNA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_cata_prod_muni);

        //aqui va lo del menu
        icpmb = ActivityIndexCataProdMuniBinding.inflate(getLayoutInflater());
        setContentView(icpmb.getRoot());
        allowActivityTitle("Ubicaciones/Municipio");

        //tarjetas
        CATG = findViewById(R.id.tarjeta2G);//general
        DIS = findViewById(R.id.tarjeta1G);
        JORNA = findViewById(R.id.tarjeta3G);

        CATG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulG = new Intent(IndexCataProdMuni.this, FormularioMuniCAT.class);
                startActivity(consulG);
            }
        });
        DIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empresagen= new Intent(IndexCataProdMuni.this, FormularioDistri.class);
                startActivity(empresagen);
            }
        });
        JORNA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}