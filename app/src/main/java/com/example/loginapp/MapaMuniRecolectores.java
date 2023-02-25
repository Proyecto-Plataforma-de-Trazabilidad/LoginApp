package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MapaMuniRecolectores extends AppCompatActivity {
    private GoogleMap mMap;

    MaterialButton btnregresar;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    String httpURI="https://proyectoapejal.000webhostapp.com/agenda/consulMuniReco.php";

    ArrayList<marcadores2> listaPuntosE = new ArrayList<>();
    String dato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_muni_recolectores);

        //para traer el valor dela combo
        Bundle bundle= getIntent().getExtras();//se trae lo que se mando en el extra en el evento al consultar
        dato= bundle.getString("Municipio");//dato rescata el string del identificador(osea el valor del estado seleccionado)
        Toast.makeText(this,dato, Toast.LENGTH_SHORT).show();//imprime nomas para asegurarce

    }
}