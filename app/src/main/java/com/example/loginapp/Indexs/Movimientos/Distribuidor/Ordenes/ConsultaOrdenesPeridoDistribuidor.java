package com.example.loginapp.Indexs.Movimientos.Distribuidor.Ordenes;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Indexs.Movimientos.Productores.DatePickerFragment;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsultaOrdenesPeridoProductorBinding;

public class ConsultaOrdenesPeridoDistribuidor extends DrawerBaseActivity {
    ActivityConsultaOrdenesPeridoProductorBinding coppb;
    EditText FI, FF;
    int dia, mes, año;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ordenes_perido_distribuidor);

        //Menu
        coppb= ActivityConsultaOrdenesPeridoProductorBinding.inflate(getLayoutInflater());
        setContentView(coppb.getRoot());
        allowActivityTitle("Movimientos/Ordenes/Periodo");

        FI = findViewById(R.id.FI);
        FF = findViewById(R.id.FF);
        FI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrircalendario();
            }
        });
        FF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrircalendario2();
            }
        });
    }

    public void abrircalendario() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 porque enero es 0
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                FI.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void abrircalendario2(){
        DatePickerFragment fragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 porque enero es 0
                final String Date = day + " / " + (month+1) + " / " + year;
                FF.setText(Date);
            }
        });
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

}