package com.example.loginapp.Indexs.Movimientos.Productores.Ordenes;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Base_Menu.DrawerBaseActivity;
import com.example.loginapp.Indexs.Movimientos.Productores.DatePickerFragment;
import com.example.loginapp.Indexs.Movimientos.Productores.consultas_ordenesProductor;
import com.example.loginapp.R;
import com.example.loginapp.databinding.ActivityConsultaOrdenesPeridoProductorBinding;
import com.example.loginapp.databinding.ActivityIndexMoviDistribuidorBinding;
import com.google.android.material.button.MaterialButton;

import java.nio.BufferUnderflowException;
import java.time.Month;
import java.util.Calendar;

public class ConsultaOrdenesPeridoProductor extends DrawerBaseActivity {
    ActivityConsultaOrdenesPeridoProductorBinding coppb;
    EditText FI, FF;
    int dia, mes, año;

    //para tabla
    TableLayout tbtOP,tbtdet;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulOrdenesMoviProductores.php";
    MaterialButton volver;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ordenes_perido_productor);

        //aqui va lo del menu
        coppb= ActivityConsultaOrdenesPeridoProductorBinding.inflate(getLayoutInflater());
        setContentView(coppb.getRoot());
        allowActivityTitle("Movimientos/Ordenes/Periodo");

        tbtOP = findViewById(R.id.tablaO);
        tbtOP.removeAllViews();//remueve columnas

        requestQueue= Volley.newRequestQueue(ConsultaOrdenesPeridoProductor.this);
        progressDialog=new ProgressDialog(ConsultaOrdenesPeridoProductor.this);
        //botones
        volver=findViewById(R.id.btnreg1);
        FI = findViewById(R.id.FI);
        FF = findViewById(R.id.FF);

        //eventos botones
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver=new Intent(ConsultaOrdenesPeridoProductor.this, consultas_ordenesProductor.class);
                startActivity(volver);
            }
        });
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
    }//finoncreate

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

}//fin clas