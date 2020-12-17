package com.example.urclean.model;

import java.io.Serializable;

public class Tarea implements Serializable {


    private String name;
    private String descripcion;
    private String estado;
    private String responsable;
    private String calle;

    public Tarea(String name, String descripcion, String estado, String responsable,String calle) {
        this.name = name;
        this.descripcion = descripcion;
        this.estado = estado;
        this.responsable = responsable;
       this.calle=calle;
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



}
