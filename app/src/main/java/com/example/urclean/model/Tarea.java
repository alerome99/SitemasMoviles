package com.example.urclean.model;

import java.io.Serializable;

public class Tarea implements Serializable {


    private String name;
    private String descripcion;
    private String estado;
    private String responsable;
    private String calle;
    private String grupo;
    private String id;
    private String email;
    private String lat;
    private String lng;


/*public Tarea(String name, String descripcion, String estado, String responsable,String calle,String id) {
        this.name = name;
        this.descripcion = descripcion;
        this.estado = estado;
        this.responsable = responsable;
        this.calle=calle;
        this.id=id;
    }*/

    public Tarea(String name, String descripcion, String estado, String responsable,String calle,String id,String grupo, String email) {
        this.name = name;
        this.descripcion = descripcion;
        this.estado = estado;
        this.responsable = responsable;
        this.calle=calle;
        this.id=id;
        this.grupo=grupo;
        this.email=email;
    }

    public Tarea(String name, String descripcion, String estado, String responsable,String calle,String id,String grupo,
                 String email,String lat, String lng) {
        this.name = name;
        this.descripcion = descripcion;
        this.estado = estado;
        this.responsable = responsable;
        this.calle=calle;
        this.id=id;
        this.grupo=grupo;
        this.email=email;
        this.lat=lat;
        this.lng=lng;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail(){return email;}

    public void setEmail(String email){this.email=email;}

    public String getGrupo(){return grupo;}

    public void setGrupo(String grupo){this.grupo=grupo;}

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

}
