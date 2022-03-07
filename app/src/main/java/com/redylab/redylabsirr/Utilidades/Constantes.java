package com.redylab.redylabsirr.Utilidades;

/**
 * Constantes
 */
public class Constantes {

    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta característica.
     */
    private static final String PUERTO_HOST = "";

    /**
     * Dirección IP de genymotion o AVD
     */
    private static final String IP = "https://www.energiaptyo.com.co";
    //private static final String IP = "https://redylab.com";
    /**
     * URLs del Web Service
     */
    public static final String GET_URL = IP + PUERTO_HOST + "/SIRR/web/obtener_trafos.php";
    public static final String INSERT_URL = IP + PUERTO_HOST + "/SIRR/web/insertar_trafo.php";
    /**
     * Campos de las respuestas Json
     */
    // variables
    /**
     * Campos de las respuestas Json
     */
    public static final String ID_TRAFO = "idTrafo";
    public static final String ESTADO = "estado";
    public static final String TRAFOS = "trafos";
    public static final String MENSAJE = "mensaje";

    /**
     * Códigos del campo {@link}ESTADO
     */
    public static final String SUCCESS = "1";
    public static final String FAILED = "2";


    /**
     * Tipo de cuenta para la sincronización
     */
    public static final String ACCOUNT_TYPE = "com.redylab.redylabsirr.account";



}
