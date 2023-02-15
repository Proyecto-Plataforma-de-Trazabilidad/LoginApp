package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.loopj.android.http.*;

import org.json.JSONArray;

import java.util.ArrayList;

import android.os.Bundle;

import cz.msebera.android.httpclient.Header;

public class SpinnerEstado extends AppCompatActivity {

    AsyncHttpClient cliente;
    Spinner cboestados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_estado);

        cliente=new AsyncHttpClient();
        cboestados=(Spinner) findViewById(R.id.cboestado);
        llenarspinner();

    }

    private void llenarspinner(){
        String url="https://proyectoapejal.000webhostapp.com/agenda/cboestados.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    cargarspinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void cargarspinner(String respuesta){
        ArrayList<estados> lista= new ArrayList<estados>();
        try{
            JSONArray jsonArreglo=new JSONArray(respuesta);
            for(int i=0; i<jsonArreglo.length(); i++){
                estados es=new estados();
                es.setHorarioDiasLaborales(jsonArreglo.getJSONObject(i).getString("HorarioDiasLaborales"));
                lista.add(es);
            }
            ArrayAdapter <estados> a=new ArrayAdapter<estados>(this, android.R.layout.simple_dropdown_item_1line,lista);
            cboestados.setAdapter(a);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}