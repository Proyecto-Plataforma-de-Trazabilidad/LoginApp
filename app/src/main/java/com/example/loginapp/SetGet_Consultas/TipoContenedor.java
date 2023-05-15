package com.example.loginapp.SetGet_Consultas;

public class TipoContenedor{
    private String TipoContenedor;

    public TipoContenedor(){}

    public TipoContenedor(String TipoContenedor){
        this.TipoContenedor =TipoContenedor;
    }
    public String getTipoContenedor() {
        return TipoContenedor;
    }

    public void setTipoContenedor(String tipoContenedor) {
        TipoContenedor = tipoContenedor;
    }

    public String toString(){
        return TipoContenedor;
    }
}
