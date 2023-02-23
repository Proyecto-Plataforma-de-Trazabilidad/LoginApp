package com.example.loginapp;

public class municipios {
    private String Municipio;
    public municipios(){}

    public municipios(String Municipio){
        this.Municipio=Municipio;
    }

    public String getHorarioDiasLaborales() {
        return Municipio;
    }

    public void setHorarioDiasLaborales(String Municipio){
        this.Municipio=Municipio;
    }

    public String toString(){
        return Municipio;
    }
}