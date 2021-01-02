package com.example.urclean.model;

import java.util.ArrayList;

public class Queja {
    private String descripcion;
    private estadoQueja estado;
    private String email;
    private String titulo;

    public Queja(String descripcion, String email, String titulo) {
        this.descripcion = descripcion;
        estado = estadoQueja.NOTIFICADA;
        this.email = email;
        this.titulo = titulo;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String t){
        titulo = t;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String desc){
        descripcion = desc;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String ema){
        email = ema;
    }

    public estadoQueja getEstado() {
        return estado;
    }

    public void setEstado(String e) {
        if(e.equals("NOTIFICADA")){
            estado = estadoQueja.NOTIFICADA;
        }
        if(e.equals("RECIBIDA")){
            estado = estadoQueja.RECIBIDA;
        }
        if(e.equals("SOLUCIONADA")){
            estado = estadoQueja.SOLUCIONADA;
        }
    }

    public void marcarVisto() {
        estado = estadoQueja.RECIBIDA;
    }

    public void marcarSolucionado() { estado = estadoQueja.SOLUCIONADA; }
}
