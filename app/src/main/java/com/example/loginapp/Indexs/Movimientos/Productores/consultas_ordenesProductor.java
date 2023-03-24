package com.example.loginapp.Indexs.Movimientos.Productores;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsultasOrdenesProductorBinding;
import com.example.loginapp.databinding.ActivityIndexMoviDistribuidorBinding;

public class consultas_ordenesProductor extends DrawerBaseActivity {
    ActivityConsultasOrdenesProductorBinding consultasOrdenesProductorBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_ordenes_productor);

        //aqui va lo del menu
        consultasOrdenesProductorBinding= ActivityConsultasOrdenesProductorBinding.inflate(getLayoutInflater());
        setContentView(consultasOrdenesProductorBinding.getRoot());
        allowActivityTitle("Movimientos/Ordenes");
    }
}