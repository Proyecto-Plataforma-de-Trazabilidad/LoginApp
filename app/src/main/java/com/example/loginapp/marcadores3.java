package com.example.loginapp;


import org.json.JSONObject;

public class marcadores3 {
    private String lat;
    private String lon;
    private String nombreCentro;

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

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    private String domicilio;

    public marcadores3() {

    }

    public marcadores3(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        lat = jsonObject.optString("Latitud");
        lon = jsonObject.optString("Longitud");
        domicilio = jsonObject.optString("Domicilio");
        nombreCentro = jsonObject.optString("NombreCentro");
    }
}
