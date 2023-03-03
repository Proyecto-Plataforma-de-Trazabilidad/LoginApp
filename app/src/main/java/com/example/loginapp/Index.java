package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.loginapp.databinding.ActivityIndexBinding;
import com.google.android.material.button.MaterialButton;

public class Index extends DrawerBaseActivity {

     ActivityIndexBinding activityIndexBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_catalogos);

        activityIndexBinding=ActivityIndexBinding.inflate(getLayoutInflater());
        setContentView(activityIndexBinding.getRoot());
        allowActivityTitle("Inicio");

    }
}