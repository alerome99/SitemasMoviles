package com.example.urclean.model;

public class NotificacionCiudadano {
    private String tipo;
    private String descripcion;
    private String email;
    private String estado;
    private String titulo;
    private String direccion;
    private String grupo;
    private String leida;

    //Queja
    public NotificacionCiudadano(String descripcion, String email, String estado, String titulo) {
        tipo = "Queja";
        leida = "false";
        this.descripcion=descripcion;
        this.email=email;
        this.estado=estado;
        this.titulo=titulo;
    }

    //Desperfecto
    public NotificacionCiudadano(String descripcion, String email, String estado, String titulo, String direccion){
        tipo = "Desperfecto";
        leida = "false";
        this.descripcion=descripcion;
        this.email=email;
        this.estado=estado;
        this.titulo=titulo;
        this.direccion=direccion;
    }

    //Incidencia
    public NotificacionCiudadano(String descripcion,String email, String estado, String titulo, String direccion, String grupo){
        tipo = "Incidencia";
        leida = "false";
        this.descripcion=descripcion;
        this.email=email;
        this.estado=estado;
        this.titulo=titulo;
        this.direccion=direccion;
        this.grupo=grupo;
    }

    public String getTipo(){return tipo;}
    public String getLeida (){return leida;}
    public String getDescripcion(){return descripcion;}
    public String getEmail(){return email;}
    public String getEstado(){return estado;}
    public String getTitulo(){return titulo;}
    public String getDireccion(){return direccion;}
    public String getGrupo(){return grupo;}

    public void setTipo(String tipo){this.tipo=tipo;}
    public void setLeida (String leida){this.leida=leida;}
    public void setDescripcion(String descripcion){this.descripcion=descripcion;}
    public void setEmail(String email){this.email=email;}
    public void setEstado(String estado){this.estado=estado;}
    public void setTitulo(String titulo){this.titulo=titulo;}
    public void setDireccion(String direccion){this.direccion=direccion;}
    public void setGrupo(String grupo){this.grupo=grupo;}


}
