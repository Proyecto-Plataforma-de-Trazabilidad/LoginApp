package com.example.loginapp.SetGet_Consultas;

public class TipoQuimico {


    private String TipoQuimico;

    public TipoQuimico(){}

    public TipoQuimico(String TipoQuimico){
        this.TipoQuimico=TipoQuimico;
    }

    public String getTipoQuimico() {
        return TipoQuimico;
    }

    public void setTipoQuimico(String tipoQuimico) {
        TipoQuimico = tipoQuimico;
    }

    public String toString(){
        return TipoQuimico;
    }
}
