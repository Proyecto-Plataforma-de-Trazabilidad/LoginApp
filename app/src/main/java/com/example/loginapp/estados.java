package com.example.loginapp;

public class estados {

    private String HorarioDiasLaborales;

    public estados(){
    }

    public estados(String HorarioDiasLaborales){
        this.HorarioDiasLaborales=HorarioDiasLaborales;
    }

    public String getHorarioDiasLaborales() {
        return HorarioDiasLaborales;
    }

    public void setHorarioDiasLaborales(String HorarioDiasLaborales){
        this.HorarioDiasLaborales=HorarioDiasLaborales;
    }

    public String toString(){
        return HorarioDiasLaborales;
    }
}
