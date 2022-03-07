package com.redylab.redylabsirr.entity;

public class Users {
    private String id_user;
    private String cedula;
    private String nombre;
    private String pass;
    private String estado;
    private String tipo_usu;

    public Users() {
        this.id_user = id_user;
        this.cedula = cedula;
        this.nombre = nombre;
        this.pass = pass;
        this.estado = estado;
        this.tipo_usu = tipo_usu;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user( String id_user ) {
        this.id_user = id_user;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula( String cedula ) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre( String nombre ) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass( String pass ) {
        this.pass = pass;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado( String estado ) {
        this.estado = estado;
    }

    public String getTipo_usu() {
        return tipo_usu;
    }

    public void setTipo_usu( String tipo_usu ) {
        this.tipo_usu = tipo_usu;
    }
}