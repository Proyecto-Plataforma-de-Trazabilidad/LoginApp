package com.example.loginapp.SetGet_Consultas;

public class origen {

    private String Origen;
    public origen(){}

    public origen(String Origen){
        this.Origen =Origen;
    }

    public String getOrigen() {return Origen;}

    public void setOrigen(String origen) {
        Origen = origen;}

    public String toString(){
        return Origen;
    }
}