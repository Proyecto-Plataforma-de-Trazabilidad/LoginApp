package com.example.loginapp.Datos_Usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.loginapp.Indexs.Index;
import com.example.loginapp.MainActivity;
import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;

public class Perfil extends AppCompatActivity {
    MaterialButton editar,change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        change=findViewById(R.id.btnchange);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change= new Intent(Perfil.this, Changepsw.class);
                startActivity(change);
            }
        });

    }//fin on create
}//fin class