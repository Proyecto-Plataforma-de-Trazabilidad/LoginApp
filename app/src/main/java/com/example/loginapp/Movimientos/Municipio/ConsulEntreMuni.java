package com.example.loginapp.Movimientos.Municipio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Movimientos.Distribuidor.ConsuEntD;
import com.example.loginapp.Movimientos.Distribuidor.Entregas.ConsulEntregaxProductor;
import com.example.loginapp.Movimientos.Distribuidor.Entregas.ConsulGen_Entre_Dist;
import com.example.loginapp.Movimientos.Municipio.Entregas.ConsulEntrega_Muni_Produc;
import com.example.loginapp.Movimientos.Municipio.Entregas.ConsulGen_Entre_Muni;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsuEntDBinding;
import com.example.loginapp.databinding.ActivityConsulEntreMuniBinding;

public class ConsulEntreMuni extends DrawerBaseActivity {

    ActivityConsulEntreMuniBinding activityConsulEntreMuniBinding;
    CardView Productor, EntregasGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consul_entre_muni);

        //aqui va lo del menu
        activityConsulEntreMuniBinding= ActivityConsulEntreMuniBinding.inflate(getLayoutInflater());
        setContentView(activityConsulEntreMuniBinding.getRoot());
        allowActivityTitle("Movimientos");


        EntregasGeneral=findViewById(R.id.tc1);
        Productor=findViewById(R.id.tc2);

        EntregasGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent or=new Intent(ConsulEntreMuni.this, ConsulGen_Entre_Muni.class);
                startActivity(or);
            }
        });
        Productor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent en=new Intent(ConsulEntreMuni.this, ConsulEntrega_Muni_Produc.class);
                startActivity(en);
            }
        });
    }
}