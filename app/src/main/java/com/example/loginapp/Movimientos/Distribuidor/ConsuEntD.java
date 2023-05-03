package com.example.loginapp.Movimientos.Distribuidor;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.Distribuidor.Entregas.ConsulEntregaxProductor;
import com.example.loginapp.Movimientos.Distribuidor.Entregas.ConsulGen_Entre_Dist;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsuEntDBinding;

public class ConsuEntD extends DrawerBaseActivity {

    ActivityConsuEntDBinding activityConsuEntDBinding;

    CardView Productor, EntregasGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_ent_d);

        //aqui va lo del menu
        activityConsuEntDBinding= ActivityConsuEntDBinding.inflate(getLayoutInflater());
        setContentView(activityConsuEntDBinding.getRoot());
        allowActivityTitle("Movimientos");


        EntregasGeneral=findViewById(R.id.tc1);
        Productor=findViewById(R.id.tc2);

        EntregasGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent or=new Intent(ConsuEntD.this, ConsulGen_Entre_Dist.class);
                startActivity(or);
            }
        });
        Productor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent en=new Intent(ConsuEntD.this, ConsulEntregaxProductor.class);
                startActivity(en);
            }
        });
    }
}