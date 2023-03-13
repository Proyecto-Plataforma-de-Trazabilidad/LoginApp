package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.loginapp.databinding.ActivityIndexBinding;


public class Index extends DrawerBaseActivity {

    //es del menu para enlazarlo
    ActivityIndexBinding activityIndexBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_catalogos);

        //aqui va lo del menu
        activityIndexBinding=ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(activityIndexBinding.getRoot());
        allowActivityTitle("Inicio");

    }
}