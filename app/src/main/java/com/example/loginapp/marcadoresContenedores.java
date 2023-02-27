package com.example.loginapp;

import org.json.JSONException;
import org.json.JSONObject;

public class marcadoresContenedores {
    private String lat;
    private String lon;
    private String origen;

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

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    private String concepto;

    public marcadoresContenedores(){

    }
    public marcadoresContenedores(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        lat = jsonObject.optString("Latitud");
        lon = jsonObject.optString("Longitud");
        concepto =jsonObject.optString("Concepto");
        origen =jsonObject.optString("Origen");
    }

    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Concepto", concepto);
            jsonObject.put("Latitud", lat);
            jsonObject.put("Longitud", lon);
            jsonObject.put("Origen", origen);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
