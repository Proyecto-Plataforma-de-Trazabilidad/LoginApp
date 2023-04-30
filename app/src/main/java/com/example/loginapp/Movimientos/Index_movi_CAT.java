package com.example.loginapp.Movimientos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.CAT.ConsuEn_CAT;
import com.example.loginapp.Movimientos.ERP.ConsuEn_ERP;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexMoviCatBinding;
import com.example.loginapp.databinding.ActivityIndexmovimientoMunicipiosBinding;

public class Index_movi_CAT extends DrawerBaseActivity {
    ActivityIndexMoviCatBinding IMCB;
    Intent i;
    CardView Entregas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_movi_cat);

        //aqui va lo del menu
        IMCB= ActivityIndexMoviCatBinding.inflate(getLayoutInflater());
        setContentView(IMCB.getRoot());
        allowActivityTitle("Movimientos");

        Entregas=findViewById(R.id.t1);
        Entregas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(Index_movi_CAT.this, ConsuEn_CAT.class);
                startActivity(i);
            }
        });//fin onclick
    }
}