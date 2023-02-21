package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MapaEstado extends AppCompatActivity {

    MaterialButton btnregresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_estado);

        btnregresar= (MaterialButton) findViewById(R.id.btnregresar2);

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regresa= new Intent(MapaEstado.this,SpinnerEstado.class);
                startActivity(regresa);
            }
        });


    }
}