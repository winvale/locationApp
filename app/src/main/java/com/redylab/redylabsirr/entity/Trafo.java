package com.redylab.redylabsirr.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Trafo implements Serializable{
    public String idTrafo;
    public String  nomb_trafo;
    public int  capacidad;
    public int  zonaur;
    public String  mpio;
    public String  propiedad_trafo;
    public String  tipo;
    public double  lati_trafo;
    public double  long_trafo;
    public double  alt_trafo;
    public String  tipo_trafo;
    public Timestamp fech_levant_trafo;
    public String  observacion_trafo;
    public int  nro_postes_trafo;
    public String  espacio_trafo_poste;
    public int  nro_poste_corresp;
    public String  nomb_nodo;
    public String  id_trafo_nodo;
    public double  lati_nodo;
    public double  long_nodo;
    public int  alti_nodo;
    public int  altura_nodo;
    public String  material_nodo;
    public String  estruct_1_nodo;
    public String  tipo_1_estruct;
    public String  estruct_2_nodo;
    public String  tipo_2_estruct;
    public String  estruct_3_nodo;
    public String  tipo_3_estruct;
    public String  estruct_4_nodo;
    public String  tipo_4_estruct;
    public int  nr_reten;
    public int  antiguedad;
    public String  propiedad_nodo;
    public int  nro_acomet;
    public String  con1;
    public String  cal_fase_con1;
    public String  cal_neutro_con1;
    public String  cal_ap_con1;
    public String  nhil_con1;
    public String  con2;
    public String  cal_fase_con2;
    public String  cal_neutro_con2;
    public String  cal_ap_con2;
    public String  nhil_con2;
    public String  con3;
    public String  cal_fase_con3;
    public String  cal_neutro_con3;
    public String  cal_ap_con3;
    public String  nhil_con3;
    public String  con4;
    public String  cal_fase_con4;
    public String  cal_neutro_con4;
    public String  cal_ap_con4;
    public String  nhil_con4;
    public Timestamp  fech_levant_nodo;
    public String  observacion_nodo;
    public int  nro_acometidas;
    public String  espacio_poste_acometida;
    public String nro_acometida_corresp;
    public String  niu;
    public String  id_nodo_usu;
    public String  direccion;
    public String  cont_ante;
    public String  cont_act;
    public String  cont_marca;
    public double  lati_usu;
    public double  long_usu;
    public double  alti_usu;
    public String  tip_ca;
    public String  tip_as;
    public String  tip_mbt;
    public String  tip_rst;
    public int  calibre;
    public String  tablero;
    public String  fas_tablero;
    public String  tip_servicio;
    public String  observacion_usu;
    public Timestamp  fech_levant_usu;
    public int usu_levant_usu;
    public int estado;
    public int pendiente_insercion;

    public Trafo( String idTrafo, String nomb_trafo, int capacidad, int zonaur, String mpio, String propiedad_trafo, String tipo, double lati_trafo, double long_trafo, double alt_trafo, String tipo_trafo, Timestamp fech_levant_trafo, String observacion_trafo, int nro_postes_trafo, String espacio_trafo_poste, int nro_poste_corresp, String nomb_nodo, String id_trafo_nodo, double lati_nodo, double long_nodo, int alti_nodo, int altura_nodo, String material_nodo, String estruct_1_nodo, String tipo_1_estruct, String estruct_2_nodo, String tipo_2_estruct, String estruct_3_nodo, String tipo_3_estruct, String estruct_4_nodo, String tipo_4_estruct, int nr_reten, int antiguedad, String propiedad_nodo, int nro_acomet, String con1, String cal_fase_con1, String cal_neutro_con1, String cal_ap_con1, String nhil_con1, String con2, String cal_fase_con2, String cal_neutro_con2, String cal_ap_con2, String nhil_con2, String con3, String cal_fase_con3, String cal_neutro_con3, String cal_ap_con3, String nhil_con3, String con4, String cal_fase_con4, String cal_neutro_con4, String cal_ap_con4, String nhil_con4, Timestamp fech_levant_nodo, String observacion_nodo, int nro_acometidas, String espacio_poste_acometida, String nro_acometida_corresp, String niu, String id_nodo_usu, String direccion, String cont_ante, String cont_act, String cont_marca, double lati_usu, double long_usu, double alti_usu, String tip_ca, String tip_as, String tip_mbt, String tip_rst, int calibre, String tablero, String fas_tablero, String tip_servicio, String observacion_usu, Timestamp fech_levant_usu, int usu_levant_usu,int estado,int pendiente_insercion ) {
        this.idTrafo = idTrafo;
        this.nomb_trafo = nomb_trafo;
        this.capacidad = capacidad;
        this.zonaur = zonaur;
        this.mpio = mpio;
        this.propiedad_trafo = propiedad_trafo;
        this.tipo = tipo;
        this.lati_trafo = lati_trafo;
        this.long_trafo = long_trafo;
        this.alt_trafo = alt_trafo;
        this.tipo_trafo = tipo_trafo;
        this.fech_levant_trafo = fech_levant_trafo;
        this.observacion_trafo = observacion_trafo;
        this.nro_postes_trafo = nro_postes_trafo;
        this.espacio_trafo_poste = espacio_trafo_poste;
        this.nro_poste_corresp = nro_poste_corresp;
        this.nomb_nodo = nomb_nodo;
        this.id_trafo_nodo = id_trafo_nodo;
        this.lati_nodo = lati_nodo;
        this.long_nodo = long_nodo;
        this.alti_nodo = alti_nodo;
        this.altura_nodo = altura_nodo;
        this.material_nodo = material_nodo;
        this.estruct_1_nodo = estruct_1_nodo;
        this.tipo_1_estruct = tipo_1_estruct;
        this.estruct_2_nodo = estruct_2_nodo;
        this.tipo_2_estruct = tipo_2_estruct;
        this.estruct_3_nodo = estruct_3_nodo;
        this.tipo_3_estruct = tipo_3_estruct;
        this.estruct_4_nodo = estruct_4_nodo;
        this.tipo_4_estruct = tipo_4_estruct;
        this.nr_reten = nr_reten;
        this.antiguedad = antiguedad;
        this.propiedad_nodo = propiedad_nodo;
        this.nro_acomet = nro_acomet;
        this.con1 = con1;
        this.cal_fase_con1 = cal_fase_con1;
        this.cal_neutro_con1 = cal_neutro_con1;
        this.cal_ap_con1 = cal_ap_con1;
        this.nhil_con1 = nhil_con1;
        this.con2 = con2;
        this.cal_fase_con2 = cal_fase_con2;
        this.cal_neutro_con2 = cal_neutro_con2;
        this.cal_ap_con2 = cal_ap_con2;
        this.nhil_con2 = nhil_con2;
        this.con3 = con3;
        this.cal_fase_con3 = cal_fase_con3;
        this.cal_neutro_con3 = cal_neutro_con3;
        this.cal_ap_con3 = cal_ap_con3;
        this.nhil_con3 = nhil_con3;
        this.con4 = con4;
        this.cal_fase_con4 = cal_fase_con4;
        this.cal_neutro_con4 = cal_neutro_con4;
        this.cal_ap_con4 = cal_ap_con4;
        this.nhil_con4 = nhil_con4;
        this.fech_levant_nodo = fech_levant_nodo;
        this.observacion_nodo = observacion_nodo;
        this.nro_acometidas = nro_acometidas;
        this.espacio_poste_acometida = espacio_poste_acometida;
        this.nro_acometida_corresp = nro_acometida_corresp;
        this.niu = niu;
        this.id_nodo_usu = id_nodo_usu;
        this.direccion = direccion;
        this.cont_ante = cont_ante;
        this.cont_act = cont_act;
        this.cont_marca = cont_marca;
        this.lati_usu = lati_usu;
        this.long_usu = long_usu;
        this.alti_usu = alti_usu;
        this.tip_ca = tip_ca;
        this.tip_as = tip_as;
        this.tip_mbt = tip_mbt;
        this.tip_rst = tip_rst;
        this.calibre = calibre;
        this.tablero = tablero;
        this.fas_tablero = fas_tablero;
        this.tip_servicio = tip_servicio;
        this.observacion_usu = observacion_usu;
        this.fech_levant_usu = fech_levant_usu;
        this.usu_levant_usu = usu_levant_usu;
        this.estado = estado;
        this.pendiente_insercion = pendiente_insercion;
    }

    public Trafo() {

    }

    public String getIdTrafo() {
        return idTrafo;
    }

    public void setIdTrafo( String idTrafo ) {
        this.idTrafo = idTrafo;
    }

    public String getNomb_trafo() {
        return nomb_trafo;
    }

    public void setNomb_trafo( String nomb_trafo ) {
        this.nomb_trafo = nomb_trafo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad( int capacidad ) {
        this.capacidad = capacidad;
    }

    public int getZonaur() {
        return zonaur;
    }

    public void setZonaur( int zonaur ) {
        this.zonaur = zonaur;
    }

    public String getMpio() {
        return mpio;
    }

    public void setMpio( String mpio ) {
        this.mpio = mpio;
    }

    public String getPropiedad_trafo() {
        return propiedad_trafo;
    }

    public void setPropiedad_trafo( String propiedad_trafo ) {
        this.propiedad_trafo = propiedad_trafo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo( String tipo ) {
        this.tipo = tipo;
    }

    public double getLati_trafo() {
        return lati_trafo;
    }

    public void setLati_trafo( double lati_trafo ) {
        this.lati_trafo = lati_trafo;
    }

    public double getLong_trafo() {
        return long_trafo;
    }

    public void setLong_trafo( double long_trafo ) {
        this.long_trafo = long_trafo;
    }

    public double getAlt_trafo() {
        return alt_trafo;
    }

    public void setAlt_trafo( double alt_trafo ) {
        this.alt_trafo = alt_trafo;
    }

    public String getTipo_trafo() {
        return tipo_trafo;
    }

    public void setTipo_trafo( String tipo_trafo ) {
        this.tipo_trafo = tipo_trafo;
    }

    public Timestamp getFech_levant_trafo() {
        return fech_levant_trafo;
    }

    public void setFech_levant_trafo( Timestamp fech_levant_trafo ) {
        this.fech_levant_trafo = fech_levant_trafo;
    }

    public String getObservacion_trafo() {
        return observacion_trafo;
    }

    public void setObservacion_trafo( String observacion_trafo ) {
        this.observacion_trafo = observacion_trafo;
    }

    public int getNro_postes_trafo() {
        return nro_postes_trafo;
    }

    public void setNro_postes_trafo( int nro_postes_trafo ) {
        this.nro_postes_trafo = nro_postes_trafo;
    }

    public String getEspacio_trafo_poste() {
        return espacio_trafo_poste;
    }

    public void setEspacio_trafo_poste( String espacio_trafo_poste ) {
        this.espacio_trafo_poste = espacio_trafo_poste;
    }

    public int getNro_poste_corresp() {
        return nro_poste_corresp;
    }

    public void setNro_poste_corresp( int nro_poste_corresp ) {
        this.nro_poste_corresp = nro_poste_corresp;
    }

    public String getNomb_nodo() {
        return nomb_nodo;
    }

    public void setNomb_nodo( String nomb_nodo ) {
        this.nomb_nodo = nomb_nodo;
    }

    public String getId_trafo_nodo() {
        return id_trafo_nodo;
    }

    public void setId_trafo_nodo( String id_trafo_nodo ) {
        this.id_trafo_nodo = id_trafo_nodo;
    }

    public double getLati_nodo() {
        return lati_nodo;
    }

    public void setLati_nodo( double lati_nodo ) {
        this.lati_nodo = lati_nodo;
    }

    public double getLong_nodo() {
        return long_nodo;
    }

    public void setLong_nodo( double long_nodo ) {
        this.long_nodo = long_nodo;
    }

    public int getAlti_nodo() {
        return alti_nodo;
    }

    public void setAlti_nodo( int alti_nodo ) {
        this.alti_nodo = alti_nodo;
    }

    public int getAltura_nodo() {
        return altura_nodo;
    }

    public void setAltura_nodo( int altura_nodo ) {
        this.altura_nodo = altura_nodo;
    }

    public String getMaterial_nodo() {
        return material_nodo;
    }

    public void setMaterial_nodo( String material_nodo ) {
        this.material_nodo = material_nodo;
    }

    public String getEstruct_1_nodo() {
        return estruct_1_nodo;
    }

    public void setEstruct_1_nodo( String estruct_1_nodo ) {
        this.estruct_1_nodo = estruct_1_nodo;
    }

    public String getTipo_1_estruct() {
        return tipo_1_estruct;
    }

    public void setTipo_1_estruct( String tipo_1_estruct ) {
        this.tipo_1_estruct = tipo_1_estruct;
    }

    public String getEstruct_2_nodo() {
        return estruct_2_nodo;
    }

    public void setEstruct_2_nodo( String estruct_2_nodo ) {
        this.estruct_2_nodo = estruct_2_nodo;
    }

    public String getTipo_2_estruct() {
        return tipo_2_estruct;
    }

    public void setTipo_2_estruct( String tipo_2_estruct ) {
        this.tipo_2_estruct = tipo_2_estruct;
    }

    public String getEstruct_3_nodo() {
        return estruct_3_nodo;
    }

    public void setEstruct_3_nodo( String estruct_3_nodo ) {
        this.estruct_3_nodo = estruct_3_nodo;
    }

    public String getTipo_3_estruct() {
        return tipo_3_estruct;
    }

    public void setTipo_3_estruct( String tipo_3_estruct ) {
        this.tipo_3_estruct = tipo_3_estruct;
    }

    public String getEstruct_4_nodo() {
        return estruct_4_nodo;
    }

    public void setEstruct_4_nodo( String estruct_4_nodo ) {
        this.estruct_4_nodo = estruct_4_nodo;
    }

    public String getTipo_4_estruct() {
        return tipo_4_estruct;
    }

    public void setTipo_4_estruct( String tipo_4_estruct ) {
        this.tipo_4_estruct = tipo_4_estruct;
    }

    public int getNr_reten() {
        return nr_reten;
    }

    public void setNr_reten( int nr_reten ) {
        this.nr_reten = nr_reten;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad( int antiguedad ) {
        this.antiguedad = antiguedad;
    }

    public String getPropiedad_nodo() {
        return propiedad_nodo;
    }

    public void setPropiedad_nodo( String propiedad_nodo ) {
        this.propiedad_nodo = propiedad_nodo;
    }

    public int getNro_acomet() {
        return nro_acomet;
    }

    public void setNro_acomet( int nro_acomet ) {
        this.nro_acomet = nro_acomet;
    }

    public String getCon1() {
        return con1;
    }

    public void setCon1( String con1 ) {
        this.con1 = con1;
    }

    public String getCal_fase_con1() {
        return cal_fase_con1;
    }

    public void setCal_fase_con1( String cal_fase_con1 ) {
        this.cal_fase_con1 = cal_fase_con1;
    }

    public String getCal_neutro_con1() {
        return cal_neutro_con1;
    }

    public void setCal_neutro_con1( String cal_neutro_con1 ) {
        this.cal_neutro_con1 = cal_neutro_con1;
    }

    public String getCal_ap_con1() {
        return cal_ap_con1;
    }

    public void setCal_ap_con1( String cal_ap_con1 ) {
        this.cal_ap_con1 = cal_ap_con1;
    }

    public String getNhil_con1() {
        return nhil_con1;
    }

    public void setNhil_con1( String nhil_con1 ) {
        this.nhil_con1 = nhil_con1;
    }

    public String getCon2() {
        return con2;
    }

    public void setCon2( String con2 ) {
        this.con2 = con2;
    }

    public String getCal_fase_con2() {
        return cal_fase_con2;
    }

    public void setCal_fase_con2( String cal_fase_con2 ) {
        this.cal_fase_con2 = cal_fase_con2;
    }

    public String getCal_neutro_con2() {
        return cal_neutro_con2;
    }

    public void setCal_neutro_con2( String cal_neutro_con2 ) {
        this.cal_neutro_con2 = cal_neutro_con2;
    }

    public String getCal_ap_con2() {
        return cal_ap_con2;
    }

    public void setCal_ap_con2( String cal_ap_con2 ) {
        this.cal_ap_con2 = cal_ap_con2;
    }

    public String getNhil_con2() {
        return nhil_con2;
    }

    public void setNhil_con2( String nhil_con2 ) {
        this.nhil_con2 = nhil_con2;
    }

    public String getCon3() {
        return con3;
    }

    public void setCon3( String con3 ) {
        this.con3 = con3;
    }

    public String getCal_fase_con3() {
        return cal_fase_con3;
    }

    public void setCal_fase_con3( String cal_fase_con3 ) {
        this.cal_fase_con3 = cal_fase_con3;
    }

    public String getCal_neutro_con3() {
        return cal_neutro_con3;
    }

    public void setCal_neutro_con3( String cal_neutro_con3 ) {
        this.cal_neutro_con3 = cal_neutro_con3;
    }

    public String getCal_ap_con3() {
        return cal_ap_con3;
    }

    public void setCal_ap_con3( String cal_ap_con3 ) {
        this.cal_ap_con3 = cal_ap_con3;
    }

    public String getNhil_con3() {
        return nhil_con3;
    }

    public void setNhil_con3( String nhil_con3 ) {
        this.nhil_con3 = nhil_con3;
    }

    public String getCon4() {
        return con4;
    }

    public void setCon4( String con4 ) {
        this.con4 = con4;
    }

    public String getCal_fase_con4() {
        return cal_fase_con4;
    }

    public void setCal_fase_con4( String cal_fase_con4 ) {
        this.cal_fase_con4 = cal_fase_con4;
    }

    public String getCal_neutro_con4() {
        return cal_neutro_con4;
    }

    public void setCal_neutro_con4( String cal_neutro_con4 ) {
        this.cal_neutro_con4 = cal_neutro_con4;
    }

    public String getCal_ap_con4() {
        return cal_ap_con4;
    }

    public void setCal_ap_con4( String cal_ap_con4 ) {
        this.cal_ap_con4 = cal_ap_con4;
    }

    public String getNhil_con4() {
        return nhil_con4;
    }

    public void setNhil_con4( String nhil_con4 ) {
        this.nhil_con4 = nhil_con4;
    }

    public Timestamp getFech_levant_nodo() {
        return fech_levant_nodo;
    }

    public void setFech_levant_nodo( Timestamp fech_levant_nodo ) {
        this.fech_levant_nodo = fech_levant_nodo;
    }

    public String getObservacion_nodo() {
        return observacion_nodo;
    }

    public void setObservacion_nodo( String observacion_nodo ) {
        this.observacion_nodo = observacion_nodo;
    }

    public int getNro_acometidas() {
        return nro_acometidas;
    }

    public void setNro_acometidas( int nro_acometidas ) {
        this.nro_acometidas = nro_acometidas;
    }

    public String getEspacio_poste_acometida() {
        return espacio_poste_acometida;
    }

    public void setEspacio_poste_acometida( String espacio_poste_acometida ) {
        this.espacio_poste_acometida = espacio_poste_acometida;
    }

    public String getNro_acometida_corresp() {
        return nro_acometida_corresp;
    }

    public void setNro_acometida_corresp( String nro_acometida_corresp ) {
        this.nro_acometida_corresp = nro_acometida_corresp;
    }

    public String getNiu() {
        return niu;
    }

    public void setNiu( String niu ) {
        this.niu = niu;
    }

    public String getId_nodo_usu() {
        return id_nodo_usu;
    }

    public void setId_nodo_usu( String id_nodo_usu ) {
        this.id_nodo_usu = id_nodo_usu;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion( String direccion ) {
        this.direccion = direccion;
    }

    public String getCont_ante() {
        return cont_ante;
    }

    public void setCont_ante( String cont_ante ) {
        this.cont_ante = cont_ante;
    }

    public String getCont_act() {
        return cont_act;
    }

    public void setCont_act( String cont_act ) {
        this.cont_act = cont_act;
    }

    public String getCont_marca() {
        return cont_marca;
    }

    public void setCont_marca( String cont_marca ) {
        this.cont_marca = cont_marca;
    }

    public double getLati_usu() {
        return lati_usu;
    }

    public void setLati_usu( double lati_usu ) {
        this.lati_usu = lati_usu;
    }

    public double getLong_usu() {
        return long_usu;
    }

    public void setLong_usu( double long_usu ) {
        this.long_usu = long_usu;
    }

    public double getAlti_usu() {
        return alti_usu;
    }

    public void setAlti_usu( double alti_usu ) {
        this.alti_usu = alti_usu;
    }

    public String getTip_ca() {
        return tip_ca;
    }

    public void setTip_ca( String tip_ca ) {
        this.tip_ca = tip_ca;
    }

    public String getTip_as() {
        return tip_as;
    }

    public void setTip_as( String tip_as ) {
        this.tip_as = tip_as;
    }

    public String getTip_mbt() {
        return tip_mbt;
    }

    public void setTip_mbt( String tip_mbt ) {
        this.tip_mbt = tip_mbt;
    }

    public String getTip_rst() {
        return tip_rst;
    }

    public void setTip_rst( String tip_rst ) {
        this.tip_rst = tip_rst;
    }

    public int getCalibre() {
        return calibre;
    }

    public void setCalibre( int calibre ) {
        this.calibre = calibre;
    }

    public String getTablero() {
        return tablero;
    }

    public void setTablero( String tablero ) {
        this.tablero = tablero;
    }

    public String getFas_tablero() {
        return fas_tablero;
    }

    public void setFas_tablero( String fas_tablero ) {
        this.fas_tablero = fas_tablero;
    }

    public String getTip_servicio() {
        return tip_servicio;
    }

    public void setTip_servicio( String tip_servicio ) {
        this.tip_servicio = tip_servicio;
    }

    public String getObservacion_usu() {
        return observacion_usu;
    }

    public void setObservacion_usu( String observacion_usu ) {
        this.observacion_usu = observacion_usu;
    }

    public Timestamp getFech_levant_usu() {
        return fech_levant_usu;
    }

    public void setFech_levant_usu( Timestamp fech_levant_usu ) {
        this.fech_levant_usu = fech_levant_usu;
    }

    public int getUsu_levant_usu() {
        return usu_levant_usu;
    }

    public void setUsu_levant_usu( int usu_levant_usu ) {
        this.usu_levant_usu = usu_levant_usu;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado( int estado ) {
        this.estado = estado;
    }

    public int getPendiente_insercion() {
        return pendiente_insercion;
    }

    public void setPendiente_insercion( int pendiente_insercion ) {
        this.pendiente_insercion = pendiente_insercion;
    }
}
