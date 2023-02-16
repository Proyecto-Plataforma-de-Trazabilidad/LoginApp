package com.example.loginapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Marcadores {
    private String lat;
    private String lon;
    private String nombre;

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

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    private String domicilio;

    public Marcadores(){

    }
    public Marcadores(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        lat = jsonObject.optString("Latitud");
        lon = jsonObject.optString("Longitud");
        domicilio=jsonObject.optString("Domicilio");
        nombre=jsonObject.optString("NombreCentro");
    }

    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Domicilio", domicilio);
            jsonObject.put("Latitud", lat);
            jsonObject.put("Longitud", lon);
            jsonObject.put("NombreCentro", nombre);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
