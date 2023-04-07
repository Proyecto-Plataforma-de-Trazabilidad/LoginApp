package com.example.loginapp.Indexs.CATALOGOS_ROL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Catalogos_Generales.ConsultaGeneral;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexCataAmocaligeneralBinding;
import com.example.loginapp.databinding.ActivityIndexCatalDestinoGeneBinding;

public class IndexCatalDestinoGene extends DrawerBaseActivity {
    ActivityIndexCatalDestinoGeneBinding cdgb;
    CardView CATG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_catal_destino_gene);

        //aqui va lo del menu
        cdgb = ActivityIndexCatalDestinoGeneBinding.inflate(getLayoutInflater());
        setContentView(cdgb.getRoot());
        allowActivityTitle("Catálogos/Generales");

        //tarjetas
        CATG = findViewById(R.id.tarjeta4G);//general
        CATG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulG = new Intent(IndexCatalDestinoGene.this, ConsultaGeneral.class);
                startActivity(consulG);
            }
        });

    }
}