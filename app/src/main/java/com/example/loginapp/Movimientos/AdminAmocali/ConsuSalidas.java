package com.example.loginapp.Movimientos.AdminAmocali;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.AdminAmocali.Entregas.EntreDisAdmi;
import com.example.loginapp.Movimientos.AdminAmocali.Entregas.EntreErpAdmi;
import com.example.loginapp.Movimientos.AdminAmocali.Entregas.EntreMuniAdmi;
import com.example.loginapp.Movimientos.AdminAmocali.Salidas.SaliDisAdmi;
import com.example.loginapp.Movimientos.AdminAmocali.Salidas.SaliERPAdmi;
import com.example.loginapp.Movimientos.AdminAmocali.Salidas.SaliMuniAdmi;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsuSalidasBinding;
import com.example.loginapp.databinding.ActivityConsulEntregasBinding;

public class ConsuSalidas extends DrawerBaseActivity {
    ActivityConsuSalidasBinding activityConsuSalidasBinding;
    CardView PRO,DIS,MU,ERP;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_salidas);

        //aqui va lo del menu
        activityConsuSalidasBinding= ActivityConsuSalidasBinding.inflate(getLayoutInflater());
        setContentView(activityConsuSalidasBinding.getRoot());
        allowActivityTitle("Movimientos/Salidas");


        DIS=findViewById(R.id.tc1);
        MU=findViewById(R.id.tc4);
        ERP=findViewById(R.id.tc3);

        DIS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuSalidas.this, SaliDisAdmi.class);
                startActivity(i);
            }
        });
        MU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuSalidas.this, SaliMuniAdmi.class);
                startActivity(i);
            }
        });
        ERP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuSalidas.this, SaliERPAdmi.class);
                startActivity(i);
            }
        });
    }
}