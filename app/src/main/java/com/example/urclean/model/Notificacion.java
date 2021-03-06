package com.example.urclean.model;

import java.util.ArrayList;

public class Notificacion {

    private String descripcion;
    private String grupo;
    private String email;
    private String usuario;
    private String grupoAnt;
    private estadoNotificacion estado;

    public Notificacion(String grupo, String grupoAnt, String descripcion, String email /*String usuario*/) {
        this.grupo = grupo;
        this.descripcion = descripcion;
        this.email = email;
        this.grupoAnt = grupoAnt;
        estado = estadoNotificacion.ENPROCESO;
        //this.usuario = usuario;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String e){
        email = e;
    }

    public String getGrupo(){
        return grupo;
    }

    public void setGrupo(String g){
        grupo = g;
    }

    public String getGrupoAnterior(){
        return grupoAnt;
    }

    public void setGrupoAnterior(String gA){
        grupoAnt = gA;
    }

    public void setDescripcion(String d){
        descripcion = d;
    }

    public void setUsuario(String u){
        usuario = u;
    }

    public String getUsuario(){
        return usuario;
    }

    public estadoNotificacion getEstado(){
        return estado;
    }

    public void setEstado(String e) {
        if(e.equals("ENPROCESO")){
            estado = estadoNotificacion.ENPROCESO;
        }
        if(e.equals("CONCEDIDO")){
            estado = estadoNotificacion.CONCEDIDO;
        }
        if(e.equals("RECHAZADO")){
            estado = estadoNotificacion.RECHAZADO;
        }
    }
}
