package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.databinding.ActivityIndexEntregasBinding;
import com.example.loginapp.databinding.ActivityIndexMovimiProductorBinding;

public class IndexEntregas extends DrawerBaseActivity {
    ActivityIndexEntregasBinding ieb;
    CardView DIS,CAT,DESTINO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_entregas);

        //aqui va lo del menu
        ieb=ActivityIndexEntregasBinding.inflate(getLayoutInflater());
        setContentView(ieb.getRoot());
        allowActivityTitle("Movimientos/Entregas");
    }
}