package com.example.urclean.model;

public class Respuesta {

    private String respuesta;
    private String razon;
    private String email;

    public Respuesta(String respuesta, String razon, String email){
        this.respuesta = respuesta;
        this.razon = razon;
        this.email = email;
    }

    public String getRespuesta(){
        return respuesta;
    }

    public void setRespuesta(String re){
        respuesta = re;
    }

    public String getRazon(){
        return razon;
    }

    public void setRazon(String ra){
        razon = ra;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String e){
        email = e;
    }
}
