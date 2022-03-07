package com.redylab.redylabsirr.adaptadores;

import android.content.UriMatcher;
import android.net.Uri;

import com.redylab.redylabsirr.entity.Users;

/**
 * Contract Class entre el provider y las aplicaciones
 */
public class ContractParaG {
    /**
     * Autoridad del Content Provider
     */

    public final static String AUTHORITY = "com.redylab.redylabsirr";

    /**
     * Representación de la tabla a consultar
     */

    public static final String TRAFO = "trafo";
    public static final String USERS = "users";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + AUTHORITY + TRAFO;
    /**
     * Tipo MIME que retorna la consulta de {@link //CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + TRAFO;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + TRAFO);

    public final static Uri CONTENT_URI2 =
            Uri.parse("content://" + AUTHORITY + "/" + USERS);



    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;
    /**
     * Código para URIs de multiples registros
     */
    public static final int ALLROWS = 1;
    public static final int ALLROWS2 = 3;
    /**
     * Código para URIS de un solo registro
     */
    public static final int SINGLE_ROW = 2;
    public static final int SINGLE_ROW2 = 4;


    // Asignación de URIs
    static {
        uriMatcher = new UriMatcher( UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TRAFO, ALLROWS);
        uriMatcher.addURI(AUTHORITY, USERS, ALLROWS2);
        uriMatcher.addURI(AUTHORITY, TRAFO + "/#", SINGLE_ROW);
        uriMatcher.addURI(AUTHORITY, USERS + "/#", SINGLE_ROW2);
    }

    // Valores para la columna ESTADO
    public static final int ESTADO_OK = 0;
    public static final int ESTADO_SYNC = 1;



}
