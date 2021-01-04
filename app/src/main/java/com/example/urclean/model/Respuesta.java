package com.example.urclean.model;

public class Respuesta {

    private String respuesta;
    private String razon;
    private String email;
    private String emailBarrendero;
    private estadoRespuesta estado;

    public Respuesta(String respuesta, String razon, String email, String emailBarrendero){
        this.respuesta = respuesta;
        this.razon = razon;
        this.email = email;
        this.emailBarrendero = emailBarrendero;
        estado = estadoRespuesta.NOVISTO;
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

    public String getEmailBarrendero(){
        return emailBarrendero;
    }

    public void setEmailBarrendero(String eB){
        emailBarrendero = eB;
    }

    public estadoRespuesta getEstado() {
        return estado;
    }

    public void setEstado(String e) {
        if(e.equals("NOVISTO")){
            estado = estadoRespuesta.NOVISTO;
        }
        if(e.equals("VISTO")){
            estado = estadoRespuesta.VISTO;
        }
    }
}
