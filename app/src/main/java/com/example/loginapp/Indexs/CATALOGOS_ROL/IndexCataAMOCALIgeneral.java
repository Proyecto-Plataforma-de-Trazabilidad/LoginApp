package com.example.loginapp.Indexs.CATALOGOS_ROL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Catalogos_Generales.ConsulGeneConte;
import com.example.loginapp.Catalogos_Generales.ConsulGeneEmpresas;
import com.example.loginapp.Catalogos_Generales.ConsultaGeneral;
import com.example.loginapp.Indexs.IndexGenerales;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityCatalogosGeneralBinding;
import com.example.loginapp.databinding.ActivityIndexCataAmocaligeneralBinding;

public class IndexCataAMOCALIgeneral extends DrawerBaseActivity {
    ActivityIndexCataAmocaligeneralBinding activityIndexCataAmocaligeneralBinding;
    CardView CATG,DES,CONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_cata_amocaligeneral);

        //aqui va lo del menu
        activityIndexCataAmocaligeneralBinding = ActivityIndexCataAmocaligeneralBinding.inflate(getLayoutInflater());
        setContentView(activityIndexCataAmocaligeneralBinding.getRoot());
        allowActivityTitle("Catálogos/Generales");

        //tarjetas
        CATG = findViewById(R.id.tarjeta4G);//general
        DES = findViewById(R.id.tarjeta2G);
        CONT = findViewById(R.id.tarjeta3G);

        CATG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent consulG = new Intent(IndexCataAMOCALIgeneral.this, ConsultaGeneral.class);
                startActivity(consulG);
            }
        });
        DES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent empresagen= new Intent(IndexCataAMOCALIgeneral.this, ConsulGeneEmpresas.class);
                startActivity(empresagen);
            }
        });
        CONT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conteGen= new Intent(IndexCataAMOCALIgeneral.this, ConsulGeneConte.class);
                startActivity(conteGen);
            }
        });

    }
}