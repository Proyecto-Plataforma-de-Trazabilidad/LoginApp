package com.example.loginapp.Mapas;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.SetGet_Consultas.Marcadores;
import com.example.loginapp.R;
import com.example.loginapp.SetGet_Consultas.marcadores3;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

public class MapaMuniCAT extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;

    MaterialButton btnregresar;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    String httpURI="https://campolimpiojal.com/android/ConsultasMunicipio.php";

    ArrayList<marcadores3> listaPuntosE = new ArrayList<>();
    String dato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_muni_cat);

        //para traer el valor dela combo
        Bundle bundle= getIntent().getExtras();//se trae lo que se mando en el extra en el evento al consultar
        dato= bundle.getString("Municipio");//dato rescata el string del identificador(osea el valor del estado seleccionado)
        //Toast.makeText(this,dato, Toast.LENGTH_SHORT).show();//imprime nomas para asegurarce



        //para los valores del mapa y llamada al servidor
        requestQueue= Volley.newRequestQueue(MapaMuniCAT.this);
        //Indicar dónde se ejecutará progressdialog
        progressDialog=new ProgressDialog(MapaMuniCAT.this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
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
                                    marcadores3 marcadores = new marcadores3(jsonObject);
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
                parametros.put("opcion","cat");
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



                mMap.addMarker(new MarkerOptions().position(marker).title(listaPuntosE.get(i).getNombreCentro()).snippet(listaPuntosE.get(i).getDomicilio()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

            }
        }
        else {
            Toast.makeText(this, "Lo sentimos, no tenemos agentes en la Unidad Seleccionada", Toast.LENGTH_SHORT).show();
        }
        //estilos del mapa y zoom para verlos mas de cerca
        mMap.setOnMarkerClickListener(this);
        mMap.setMinZoomPreference(8.0f);
        mMap.setMaxZoomPreference(16.0f);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);//tipo de mapa
        mMap.getUiSettings().setZoomControlsEnabled(true);//habilitar botones para zoom

    }//fin cargar puntos

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        try {
            String clickCount = (String) marker.getTag();
            //  return true;

        } catch (Exception ex) {
            Marcadores info = new Marcadores();
            info = (Marcadores) marker.getTag();

        }
        return false;
    }
}