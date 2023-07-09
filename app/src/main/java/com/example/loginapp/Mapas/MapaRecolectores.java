package com.example.loginapp.Mapas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginapp.SetGet_Consultas.Marcadores;
import com.example.loginapp.R;
import com.example.loginapp.SetGet_Consultas.marcadores2;
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

public class MapaRecolectores extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    MaterialButton btnvolver;

    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    String httpURI="https://campolimpiojal.com/android/ConsultasGenerales.php";

    ArrayList<marcadores2> listaPuntos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_recolectores);

        requestQueue= Volley.newRequestQueue(MapaRecolectores.this);
        //Indicar dónde se ejecutará progressdialog
        progressDialog=new ProgressDialog(MapaRecolectores.this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void TraerMarcadores(){
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, httpURI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONArray result=new JSONArray(response);
                            listaPuntos = new ArrayList<>();
                            //JSONObject obj = new JSONObject(response);

                            if (result != null) {
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject jsonObject = result.getJSONObject(i);
                                    marcadores2 marcadores = new marcadores2(jsonObject);
                                    listaPuntos.add(marcadores);
                                }
                                CargarPuntosAMapa();
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
                parametros.put("opcion","Erecolectoras");
                return parametros;
            }
        };
        //requestQueue ejecute la cadena
        requestQueue.add(stringRequest);
    }

    private void CargarPuntosAMapa(){
        mMap.clear();
        if (listaPuntos.size() > 0) {
            for (int i = 0; i < listaPuntos.size(); i++) {

                LatLng marker = new LatLng((Double.parseDouble(listaPuntos.get(i).getLat())), (Double.parseDouble(listaPuntos.get(i).getLon())));

                //redimencionamos el logo
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marcador);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100,100, false);


                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).position(marker).title(listaPuntos.get(i).getNombre()).snippet(listaPuntos.get(i).getDomicilio()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

            }
        }
        else {
            Toast.makeText(this, "Lo sentimos, no tenemos agentes en la Unidad Seleccionada", Toast.LENGTH_SHORT).show();
        }
        mMap.setOnMarkerClickListener(this);
        mMap.setMinZoomPreference(8.0f);
        mMap.setMaxZoomPreference(16.0f);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);

    }

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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(19.525772, -103.36180);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        TraerMarcadores();
    }
}