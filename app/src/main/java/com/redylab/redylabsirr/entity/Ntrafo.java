package com.redylab.redylabsirr.entity;

import java.io.Serializable;

public class Ntrafo implements Serializable {

    private int id_ntrafos;
    private String id_nmb_ntrafo;
    private String id_nposte;
    private String id_acometida;
    private String mpio;
    private int est_trafo;
    private int est_poste;
    private int est_acometida;

    public Ntrafo( int id_ntrafos, String id_nmb_ntrafo, String id_nposte, String id_acometida, String mpio, int est_trafo, int est_poste, int est_acometida ) {
        this.id_ntrafos = id_ntrafos;
        this.id_nmb_ntrafo = id_nmb_ntrafo;
        this.id_nposte = id_nposte;
        this.id_acometida = id_acometida;
        this.mpio = mpio;
        this.est_trafo = est_trafo;
        this.est_poste = est_poste;
        this.est_acometida = est_acometida;
    }

    public Ntrafo(){

    }

    public int getId_ntrafos() {
        return id_ntrafos;
    }

    public void setId_ntrafos( int id_ntrafos ) {
        this.id_ntrafos = id_ntrafos;
    }

    public String getId_nmb_ntrafo() {
        return id_nmb_ntrafo;
    }

    public void setId_nmb_ntrafo( String id_nmb_ntrafo ) {
        this.id_nmb_ntrafo = id_nmb_ntrafo;
    }

    public String getId_nposte() {
        return id_nposte;
    }

    public void setId_nposte( String id_nposte ) {
        this.id_nposte = id_nposte;
    }

    public String getId_acometida() {
        return id_acometida;
    }

    public void setId_acometida( String id_acometida ) {
        this.id_acometida = id_acometida;
    }

    public String getMpio() {
        return mpio;
    }

    public void setMpio( String mpio ) {
        this.mpio = mpio;
    }

    public int getEst_trafo() {
        return est_trafo;
    }

    public void setEst_trafo( int est_trafo ) {
        this.est_trafo = est_trafo;
    }

    public int getEst_poste() {
        return est_poste;
    }

    public void setEst_poste( int est_poste ) {
        this.est_poste = est_poste;
    }

    public int getEst_acometida() {
        return est_acometida;
    }

    public void setEst_acometida( int est_acometida ) {
        this.est_acometida = est_acometida;
    }
}