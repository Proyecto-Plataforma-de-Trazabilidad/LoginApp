package com.example.loginapp.Catalogos;

import androidx.cardview.widget.CardView;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Catalogos.CATALOGOS_ROL.AMOCALI.IndexCataAMOCALIgeneral;
import com.example.loginapp.Catalogos.CATALOGOS_ROL.AMOCALI.IndexCataAMOCALImunici;
import com.example.loginapp.Catalogos.CATALOGOS_ROL.DESTINO.IndexCatalDestinoGene;
import com.example.loginapp.Catalogos.CATALOGOS_ROL.DESTINO.IndexCatalDestinoMunicipal;
import com.example.loginapp.Index;
import com.example.loginapp.Catalogos.CATALOGOS_ROL.PRODUCTOR.IndexCataProGene;
import com.example.loginapp.Catalogos.CATALOGOS_ROL.PRODUCTOR.IndexCataProdMuni;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexCatalogos2Binding;

public class IndexCatalogos extends DrawerBaseActivity {

   ActivityIndexCatalogos2Binding indexCatalogos;
    CardView Generales,Municipales;
    Intent i;
    String emisorRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_catalogos2);


        //aqui va lo del menu
        indexCatalogos= ActivityIndexCatalogos2Binding.inflate(getLayoutInflater());
        setContentView(indexCatalogos.getRoot());
        allowActivityTitle("Catálogos");

        Generales = findViewById(R.id.tarjetaGeneral);
        Municipales = findViewById(R.id.tarjetaMunicipal);

        //variables sesion
        emisorRol= Index.obtenerrol(IndexCatalogos.this,Index.r);
        //Toast.makeText(IndexCatalogos.this, emisorRol, Toast.LENGTH_SHORT).show();

        Generales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validar roles
                switch (emisorRol){
                    case "1"://administrador
                        i= new Intent(IndexCatalogos.this, IndexGenerales.class);
                        startActivity(i);
                        break;
                    case "2"://productor
                        i= new Intent(IndexCatalogos.this, IndexCataProGene.class);
                        startActivity(i);
                        break;
                    case "3"://distribuidor
                        i= new Intent(IndexCatalogos.this, IndexGenerales.class);
                        startActivity(i);
                        break;
                    case "4"://municipios
                        i= new Intent(IndexCatalogos.this, IndexGenerales.class);
                        startActivity(i);
                        break;
                    case "5"://empre recolec priva
                        i= new Intent(IndexCatalogos.this, IndexGenerales.class);
                        startActivity(i);
                        break;
                    case "6"://empre destino
                        i= new Intent(IndexCatalogos.this, IndexCatalDestinoGene.class);
                        startActivity(i);
                        break;
                    case "7"://AMOCALI
                        i= new Intent(IndexCatalogos.this, IndexCataAMOCALIgeneral.class);
                        startActivity(i);
                        break;
                    case "8"://ASICA
                        i= new Intent(IndexCatalogos.this, IndexGenerales.class);
                        startActivity(i);
                        break;
                    case "9"://CESAVEJAL
                        i= new Intent(IndexCatalogos.this, IndexGenerales.class);
                        startActivity(i);
                        break;
                    case "10"://APEAJAL
                        i= new Intent(IndexCatalogos.this, IndexGenerales.class);
                        startActivity(i);
                        break;
                    case "11"://CAT
                        i= new Intent(IndexCatalogos.this, IndexGenerales.class);
                        startActivity(i);
                        break;
                }//fin switch
            }//fin onclick
        });//fin setlistener
        Municipales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validar roles
                switch (emisorRol){
                    case "1"://administrador
                        i= new Intent(IndexCatalogos.this, IndexMunicipales.class);
                        startActivity(i);
                        break;
                    case "2"://productor
                        i= new Intent(IndexCatalogos.this, IndexCataProdMuni.class);
                        startActivity(i);
                        break;
                    case "3"://distribuidor
                        i= new Intent(IndexCatalogos.this, IndexMunicipales.class);
                        startActivity(i);
                        break;
                    case "4"://municipios
                        i= new Intent(IndexCatalogos.this, IndexMunicipales.class);
                        startActivity(i);
                        break;
                    case "5"://empre recolec priva
                        i= new Intent(IndexCatalogos.this, IndexMunicipales.class);
                        startActivity(i);
                        break;
                    case "6"://empre destino
                        i= new Intent(IndexCatalogos.this, IndexCatalDestinoMunicipal.class);
                        startActivity(i);
                        break;
                    case "7"://AMOCALI
                        i= new Intent(IndexCatalogos.this, IndexCataAMOCALImunici.class);
                        startActivity(i);
                        break;
                    case "8"://ASICA
                        i= new Intent(IndexCatalogos.this, IndexMunicipales.class);
                        startActivity(i);
                        break;
                    case "9"://CESAVEJAL
                        i= new Intent(IndexCatalogos.this, IndexMunicipales.class);
                        startActivity(i);
                        break;
                    case "10"://APEAJAL
                        i= new Intent(IndexCatalogos.this, IndexMunicipales.class);
                        startActivity(i);
                        break;
                    case "11"://CAT
                        i= new Intent(IndexCatalogos.this, IndexMunicipales.class);
                        startActivity(i);
                        break;
                }//fin switch
            }//fin onclick
        });//fin setlistener
    }
}