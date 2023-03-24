package com.example.loginapp.Indexs.Movimientos.Distribuidor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsultasOrdenesDistribuidorBinding;
import com.example.loginapp.databinding.ActivityIndexMoviDistribuidorBinding;

public class consultas_ordenes_distribuidor extends DrawerBaseActivity {
    ActivityConsultasOrdenesDistribuidorBinding activityConsultasOrdenesDistribuidorBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_ordenes_distribuidor);

        //aqui va lo del menu
        activityConsultasOrdenesDistribuidorBinding=ActivityConsultasOrdenesDistribuidorBinding.inflate(getLayoutInflater());
        setContentView(activityConsultasOrdenesDistribuidorBinding.getRoot());
        allowActivityTitle("Movimientos/Ordenes");
    }
}