package com.example.loginapp.SetGet_Consultas;

public class cboEntradas {
    private String Nombre;

    public cboEntradas() {}

    public cboEntradas(String Nombre) {this.Nombre = Nombre;}
    public String getNombre() {return Nombre;}

    public void setNombre(String nombre) {Nombre = nombre;}

    public String toString(){
        return Nombre;
    }

}
