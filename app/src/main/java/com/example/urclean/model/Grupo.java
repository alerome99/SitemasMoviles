package com.example.urclean.model;

import java.util.ArrayList;

public class Grupo {
    private String numero;

    private ArrayList<userClient> lista;

    public Grupo(String numero) {
        this.numero = numero;
        lista = new ArrayList<>();
    }

    public ArrayList<userClient> getBarrenderos(){
        return lista;
    }

    public void addBarrendero(userClient c){
        lista.add(c);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
