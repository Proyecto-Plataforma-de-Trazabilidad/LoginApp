package com.example.loginapp.Movimientos.CAT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.CAT.Entregas.ConsuEnGe_CAT;
import com.example.loginapp.Movimientos.CAT.Entregas.ConsuEnPr_CAT;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsuEnCatBinding;
import com.example.loginapp.databinding.ActivityConsulEntreProdBinding;

public class ConsuEn_CAT extends DrawerBaseActivity {
    ActivityConsuEnCatBinding ACECB;
    CardView G,Pro;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consu_en_cat);

        //aqui va lo del menu
        ACECB= ActivityConsuEnCatBinding.inflate(getLayoutInflater());
        setContentView(ACECB.getRoot());
        allowActivityTitle("Movimientos/Entregas");

        G=findViewById(R.id.tc2);
        Pro=findViewById(R.id.tc1);

        G.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuEn_CAT.this, ConsuEnGe_CAT.class);
                startActivity(i);
            }
        });
        Pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i=new Intent(ConsuEn_CAT.this, ConsuEnPr_CAT.class);
                startActivity(i);
            }
        });
    }
}