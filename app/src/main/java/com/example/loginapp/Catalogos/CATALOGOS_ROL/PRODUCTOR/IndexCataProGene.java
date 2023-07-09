package com.example.loginapp.Catalogos.CATALOGOS_ROL.PRODUCTOR;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Catalogos.Catalogos_Generales.ConsulGeneDistr;
import com.example.loginapp.Catalogos.Catalogos_Generales.ConsultaGeneral;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexCataProGeneBinding;

public class IndexCataProGene extends DrawerBaseActivity {
    ActivityIndexCataProGeneBinding icpg;
    CardView CATG,DIS,JORNA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_cata_pro_gene);

        //aqui va lo del menu
        icpg = ActivityIndexCataProGeneBinding.inflate(getLayoutInflater());
        setContentView(icpg.getRoot());
        allowActivityTitle("Ubicaciones/Generales");

        //tarjetas
        CATG = findViewById(R.id.tarjeta2G);//general
        DIS = findViewById(R.id.tarjeta1G);
        JORNA = findViewById(R.id.tarjeta3G);

        CATG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulG = new Intent(IndexCataProGene.this, ConsultaGeneral.class);
                startActivity(consulG);
            }
        });
        DIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empresagen= new Intent(IndexCataProGene.this, ConsulGeneDistr.class);
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