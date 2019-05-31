package com.example.finaleco;

public class Puntajes {
    private String claseid,usuario,puntos,correo;
    public Puntajes(){

    }
    public Puntajes( String usuario, String puntos) {

        this.usuario = usuario;
        this.puntos = puntos;

    }

    public String getClaseid() {
        return claseid;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPuntos() {
        return puntos;
    }

    public String getCorreo() {
        return correo;
    }
}
