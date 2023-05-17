package com.example.loginapp.Reportes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.R;
import com.example.loginapp.Reportes.ReportesDeCatalogos.Rep1Dis;
import com.example.loginapp.Reportes.ReportesDeMovimientos.ConsuRepMovi;
import com.example.loginapp.databinding.ActivityIndexRepoMoviBinding;

public class IndexRepoMovi extends DrawerBaseActivity {
    ActivityIndexRepoMoviBinding AIMB;
    CardView DIS,PRO,ERP,MUN;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_repo_movi);

        PRO=findViewById(R.id.T1);
        DIS=findViewById(R.id.T2);
        ERP=findViewById(R.id.T3);
        MUN=findViewById(R.id.T4);

        DIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoMovi.this, ConsuRepMovi.class);
                startActivity(i);
            }
        });
        PRO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(IndexRepoMovi.this, ConsuRepMovi.class);
                startActivity(i);
            }
        });
    }
}