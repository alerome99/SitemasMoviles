package com.example.urclean.model;

import java.util.ArrayList;

public class Grupo {

    private String numero;
    private String codigoPostal;

    public Grupo(String numero, String codigoPostal) {
        this.numero = numero;
        this.codigoPostal = codigoPostal;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodigoPostal(){
        return codigoPostal;
    }

    public void setCodigoPostal(String cp){
        codigoPostal = cp;
    }
}
