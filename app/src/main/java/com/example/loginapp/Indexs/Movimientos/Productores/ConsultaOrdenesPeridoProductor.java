package com.example.loginapp.Indexs.Movimientos.Productores;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsultaOrdenesPeridoProductorBinding;
import com.example.loginapp.databinding.ActivityIndexMoviDistribuidorBinding;

import java.nio.BufferUnderflowException;
import java.time.Month;
import java.util.Calendar;

public class ConsultaOrdenesPeridoProductor extends DrawerBaseActivity {
    ActivityConsultaOrdenesPeridoProductorBinding coppb;
    EditText FI, FF;
    int dia, mes, año;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ordenes_perido_productor);

        //aqui va lo del menu
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
                // +1 because January is zero
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                FI.setText(selectedDate);//imprime en el cuadro
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void abrircalendario2(){
        DatePickerFragment fragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String Date = day + " / " + (month+1) + " / " + year;
                FF.setText(Date);//imprime en el cuadro
            }
        });
        fragment.show(getSupportFragmentManager(), "datePicker");
    }

}