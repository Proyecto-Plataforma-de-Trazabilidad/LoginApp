package com.example.loginapp.Indexs.Movimientos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Indexs.Movimientos.Distribuidor.ConsultasOrdenesDistri;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityIndexMoviDistribuidorBinding;
import com.example.loginapp.databinding.ActivityIndexmovimientoMunicipiosBinding;

public class indexmovimientoMunicipios extends DrawerBaseActivity {
    ActivityIndexmovimientoMunicipiosBinding immb;

    CardView Entregas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indexmovimiento_municipios);

        //aqui va lo del menu
        immb= ActivityIndexmovimientoMunicipiosBinding.inflate(getLayoutInflater());
        setContentView(immb.getRoot());
        allowActivityTitle("Movimientos");

        Entregas=findViewById(R.id.t2);
        Entregas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });//fin onclick
    }//finoncreate
}