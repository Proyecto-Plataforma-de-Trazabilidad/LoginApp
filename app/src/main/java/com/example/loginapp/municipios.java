package com.example.loginapp;

public class municipios {

    private String Municipio;
    public municipios(){}

    public municipios(String Municipio){
        this.Municipio=Municipio;
    }

    public String getMunicipio() {return Municipio;}

    public void setMunicipio(String municipio) {Municipio = municipio;}

    public String toString(){
        return Municipio;
    }
}