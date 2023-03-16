package com.example.loginapp.SetGet_Consultas;

import org.json.JSONException;
import org.json.JSONObject;

public class MarcadoresEmpresas {
    private String lat;
    private String lon;
    private String nombre;
    private String domic;


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomic() {
        return domic;
    }

    public void setDomic(String domic) {
        this.domic = domic;
    }

    public MarcadoresEmpresas(){}
    public MarcadoresEmpresas(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        lat = jsonObject.optString("Latitud");
        lon = jsonObject.optString("Longitud");
        domic=jsonObject.optString("Domicilio");
        nombre=jsonObject.optString("Razonsocial");
    }

    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Domicilio", domic);
            jsonObject.put("Latitud", lat);
            jsonObject.put("Longitud", lon);
            jsonObject.put("Razonsocial", nombre);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


}
