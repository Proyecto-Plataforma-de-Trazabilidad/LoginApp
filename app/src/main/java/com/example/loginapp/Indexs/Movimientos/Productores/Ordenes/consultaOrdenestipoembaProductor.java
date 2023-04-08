package com.example.loginapp.Indexs.Movimientos.Productores.Ordenes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.Indexs.Movimientos.Productores.consultas_ordenesProductor;
import com.example.loginapp.R;
import com.google.android.material.button.MaterialButton;

public class consultaOrdenestipoembaProductor extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TableLayout tbtOP,tbtdet;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String httpURI= "https://campolimpiojal.com/android/ConsulOrdenesMoviProductores.php";
    MaterialButton volver;
    Spinner cboEnvase;
    String[]Envases={"Rígidos lavables","Rígidos no lavables","Flexibles","Tapas","Cubetas","Cartón(Embalaje)","Tambos","Metal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ordenestipoemba_productor);

        tbtOP = findViewById(R.id.tablaO);
        tbtOP.removeAllViews();//remueve columnas

        requestQueue= Volley.newRequestQueue(consultaOrdenestipoembaProductor.this);
        progressDialog=new ProgressDialog(consultaOrdenestipoembaProductor.this);

        //botones
        volver=findViewById(R.id.btnreg1);

        //eventos botones
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volver=new Intent(consultaOrdenestipoembaProductor.this, consultas_ordenesProductor.class);
                startActivity(volver);
            }
        });

        cboEnvase=findViewById(R.id.cboenvase);
        ArrayAdapter<String> adapter=new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,Envases);
        cboEnvase.setAdapter(adapter);
        cboEnvase.setOnItemSelectedListener(this);


    }//fin oncreate


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String value=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "Rescate: "+value, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}//fin class