package com.example.urclean.model;

public class CodigoPostal {

    private String zona;
    private String codigo;

    public CodigoPostal(String zona, String codigo){
        this.codigo = codigo;
        this.zona = zona;
    }

    public String getCodigo(){
        return codigo;
    }

    public void setCodigo(String c){
        codigo = c;
    }

    public String getZona(){
        return zona;
    }

    public void setZona(String z){
        zona = z;
    }
}
