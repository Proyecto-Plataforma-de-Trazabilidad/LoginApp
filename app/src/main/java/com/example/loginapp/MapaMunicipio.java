package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapaMunicipio extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    MaterialButton btnregresar;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    String httpURI="https://proyectoapejal.000webhostapp.com/agenda/marcadoresxMunicipio.php";

    ArrayList<Marcadores> listaPuntosE = new ArrayList<>();
    String dato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_municipio);

        //para traer el valor dela combo
        Bundle bundle= getIntent().getExtras();//se trae lo que se mando en el extra en el evento al consultar
        dato= bundle.getString("Municipio");//dato rescata el string del identificador(osea el valor del estado seleccionado)
        Toast.makeText(this,dato, Toast.LENGTH_SHORT).show();//imprime nomas para asegurarce

        //inciializar el boton para volver
        btnregresar= (MaterialButton) findViewById(R.id.btnregresar3);

        //para los valores del mapa y llamada al servidor
        requestQueue= Volley.newRequestQueue(MapaMunicipio.this);
        //Indicar dónde se ejecutará progressdialog
        progressDialog=new ProgressDialog(MapaMunicipio.this);

        //evento del boton
        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regresa= new Intent(MapaMunicipio.this,SpinnerMunicipio.class);
                startActivity(regresa);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    public void onMapReady(@NonNull GoogleMap googleMap) { //evento inicial del mapa
        mMap = googleMap;

        // Add a marker in Sydney and move the camera, valor defaul inicial
        LatLng sydney = new LatLng(19.525772, -103.36180);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        TraerMarcadores();
    }

    private void TraerMarcadores(){//aqui consulta los datos en la BD
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONArray result=new JSONArray(response);
                            listaPuntosE = new ArrayList<>();
                            //JSONObject obj = new JSONObject(response);

                            if (result != null) {
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject jsonObject = result.getJSONObject(i);
                                    Marcadores marcadores = new Marcadores(jsonObject);
                                    listaPuntosE.add(marcadores);
                                }
                                CargarPuntosAMapa();//se los manda para que los ponga en el mapa
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                //Mostrar el error de Volley exacto a través de la librería
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String,String> getParams(){
                Map<String, String> parametros=new HashMap<>();
                //Parámetros que se esperan en el webservice
                parametros.put("Municipio",dato);//dato es el valor que recogio el valor del combo
                return parametros;
            }
        };
        //requestQueue ejecute la cadena
        requestQueue.add(stringRequest);
    }//fin traer marcadores


    private void CargarPuntosAMapa(){//aqui repinta los marcadores segun los datos recibidos en el mapa
        mMap.clear();//limpia el valor default

        if (listaPuntosE.size() > 0) {//carga cada punto
            for (int i = 0; i < listaPuntosE.size(); i++) {

                LatLng marker = new LatLng((Double.parseDouble(listaPuntosE.get(i).getLat())), (Double.parseDouble(listaPuntosE.get(i).getLon())));
                mMap.addMarker(new MarkerOptions().position(marker).title(listaPuntosE.get(i).getNombre()).snippet(listaPuntosE.get(i).getDomicilio()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

            }
        }
        else {
            Toast.makeText(this, "Lo sentimos, no tenemos agentes en la Unidad Seleccionada", Toast.LENGTH_SHORT).show();
        }
        //estilos del mapa y zoom para verlos mas de cerca
        mMap.setOnMarkerClickListener(this);
        mMap.setMinZoomPreference(12.0f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);//tipo de mapa
        mMap.getUiSettings().setZoomControlsEnabled(true);//habilitar botones para zoom

    }//fin cargar puntos

    public boolean onMarkerClick(Marker marker) {//evento dela tarjetita del marcador que muestra la info
        try {
            String clickCount = (String) marker.getTag();
            //  return true;

        } catch (Exception ex) {
            Marcadores info = new Marcadores();
            info = (Marcadores) marker.getTag();

        }
        return false;
    }//fin onmarkerclick
}//fin clase