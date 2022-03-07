package com.redylab.redylabsirr.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.redylab.redylabsirr.R;
import com.redylab.redylabsirr.Utilidades.Constantes;
import com.redylab.redylabsirr.Utilidades.Utilidades;
import com.redylab.redylabsirr.Utilidades.VolleySingleton;
import com.redylab.redylabsirr.adaptadores.ContractParaG;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Trafo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Maneja la transferencia de datos entre el servidor y el cliente
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getSimpleName();
    public static String repuesta_act ="0";

    ContentResolver resolver;
    private Gson gson = new Gson();

    /**
     * Proyección para las consultas
     */
    public static final String[] PROJECTION = new String[]{
            DatabaseManagerUser.Columnas._ID,
            DatabaseManagerUser.Columnas.ID_REMOTA,
            DatabaseManagerUser.Columnas.NOMB_TRAFO,
            DatabaseManagerUser.Columnas.CAPACIDAD,
            DatabaseManagerUser.Columnas.ZONAUR,
            DatabaseManagerUser.Columnas.MPIO,
            DatabaseManagerUser.Columnas.PROPIEDAD_TRAFO,
            DatabaseManagerUser.Columnas.TIPO,
            DatabaseManagerUser.Columnas.LATI_TRAFO,
            DatabaseManagerUser.Columnas.LONG_TRAFO,
            DatabaseManagerUser.Columnas.ALT_TRAFO,
            DatabaseManagerUser.Columnas.TIPO_TRAFO,
            DatabaseManagerUser.Columnas.FECH_LEVANT_TRAFO,
            DatabaseManagerUser.Columnas.OBSERVACION_TRAFO,
            DatabaseManagerUser.Columnas.NRO_POSTES_TRAFO,
            DatabaseManagerUser.Columnas.ESPACIO_N1,
            DatabaseManagerUser.Columnas.NRO_POSTE_CORRESP,
            DatabaseManagerUser.Columnas.NOMB_NODO,
            DatabaseManagerUser.Columnas.ID_TRAFO_NODO,
            DatabaseManagerUser.Columnas.LATI_NODO,
            DatabaseManagerUser.Columnas.LONG_NODO,
            DatabaseManagerUser.Columnas.ALTI_NODO,
            DatabaseManagerUser.Columnas.ALTURA_NODO,
            DatabaseManagerUser.Columnas.MATERIAL_NODO,
            DatabaseManagerUser.Columnas.ESTRUCT_1_NODO,
            DatabaseManagerUser.Columnas.TIPO_1_ESTRUCT,
            DatabaseManagerUser.Columnas.ESTRUCT_2_NODO,
            DatabaseManagerUser.Columnas.TIPO_2_ESTRUCT,
            DatabaseManagerUser.Columnas.ESTRUCT_3_NODO,
            DatabaseManagerUser.Columnas.TIPO_3_ESTRUCT,
            DatabaseManagerUser.Columnas.ESTRUCT_4_NODO,
            DatabaseManagerUser.Columnas.TIPO_4_ESTRUCT,
            DatabaseManagerUser.Columnas.NR_RETEN,
            DatabaseManagerUser.Columnas.ANTIGUEDAD,
            DatabaseManagerUser.Columnas.PROPIEDAD,
            DatabaseManagerUser.Columnas.NRO_ACOMET,
            DatabaseManagerUser.Columnas.CON1,
            DatabaseManagerUser.Columnas.CAL_FASE_CON1,
            DatabaseManagerUser.Columnas.CAL_NEUTRO_CON1,
            DatabaseManagerUser.Columnas.CAL_AP_CON1,
            DatabaseManagerUser.Columnas.NHIL_CON1,
            DatabaseManagerUser.Columnas.CON2,
            DatabaseManagerUser.Columnas.CAL_FASE_CON2,
            DatabaseManagerUser.Columnas.CAL_NEUTRO_CON2,
            DatabaseManagerUser.Columnas.CAL_AP_CON2,
            DatabaseManagerUser.Columnas.NHIL_CON2,
            DatabaseManagerUser.Columnas.CON3,
            DatabaseManagerUser.Columnas.CAL_FASE_CON3,
            DatabaseManagerUser.Columnas.CAL_NEUTRO_CON3,
            DatabaseManagerUser.Columnas.CAL_AP_CON3,
            DatabaseManagerUser.Columnas.NHIL_CON3,
            DatabaseManagerUser.Columnas.CON4,
            DatabaseManagerUser.Columnas.CAL_FASE_CON4,
            DatabaseManagerUser.Columnas.CAL_NEUTRO_CON4,
            DatabaseManagerUser.Columnas.CAL_AP_CON4,
            DatabaseManagerUser.Columnas.NHIL_CON4,
            DatabaseManagerUser.Columnas.FECH_LEVANT_NODO,
            DatabaseManagerUser.Columnas.OBSERVACION_NODO,
            DatabaseManagerUser.Columnas.NRO_ACOMETIDAS,
            DatabaseManagerUser.Columnas.ESPACIO_N2,
            DatabaseManagerUser.Columnas.ACOMETIDA_CORRESP,
            DatabaseManagerUser.Columnas.NIU,
            DatabaseManagerUser.Columnas.CONT_ACT,
            DatabaseManagerUser.Columnas.ID_NODO_ACOMETIDA,
            DatabaseManagerUser.Columnas.DIRECCION,
            DatabaseManagerUser.Columnas.CONT_ANTE,
            DatabaseManagerUser.Columnas.CONT_MARCA,
            DatabaseManagerUser.Columnas.LATI_USU,
            DatabaseManagerUser.Columnas.LONG_USU,
            DatabaseManagerUser.Columnas.ALTI_USU,
            DatabaseManagerUser.Columnas.TIP_CA,
            DatabaseManagerUser.Columnas.TIP_AS,
            DatabaseManagerUser.Columnas.TIP_MBT,
            DatabaseManagerUser.Columnas.TIP_RST,
            DatabaseManagerUser.Columnas.CALIBRE,
            DatabaseManagerUser.Columnas.TABLERO,
            DatabaseManagerUser.Columnas.FAS_TABLERO,
            DatabaseManagerUser.Columnas.TIP_SERVICIO,
            DatabaseManagerUser.Columnas.OBSERVACION_USU,
            DatabaseManagerUser.Columnas.FECH_LEVANT_USU,
            DatabaseManagerUser.Columnas.USU_LEVANT_USU
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID = 0;
    public static final int COLUMNA_ID_REMOTA = 1;
    public static final int COLUMNA_NOMB_TRAFO = 2;
    public static final int COLUMNA_CAPACIDAD = 3;
    public static final int COLUMNA_ZONAUR = 4;
    public static final int COLUMNA_MPIO = 5;
    public static final int COLUMNA_PROPIEDAD_TRAFO = 6;
    public static final int COLUMNA_TIPO = 7;
    public static final int COLUMNA_LATI_TRAFO = 8;
    public static final int COLUMNA_LONG_TRAFO = 9;
    public static final int COLUMNA_ALT_TRAFO = 10;
    public static final int COLUMNA_TIPO_TRAFO = 11;
    public static final int COLUMNA_FECH_LEVANT_TRAFO = 12;
    public static final int COLUMNA_OBSERVACION_TRAFO = 13;
    public static final int COLUMNA_NRO_POSTES_TRAFO = 14;
    public static final int COLUMNA_ESPACIO_N1 = 15;
    public static final int COLUMNA_NRO_POSTE_CORRESP = 16;
    public static final int COLUMNA_NOMB_NODO = 17;
    public static final int COLUMNA_ID_TRAFO_NODO = 18;
    public static final int COLUMNA_LATI_NODO = 19;
    public static final int COLUMNA_LONG_NODO = 20;
    public static final int COLUMNA_ALTI_NODO = 21;
    public static final int COLUMNA_ALTURA_NODO = 22;
    public static final int COLUMNA_MATERIAL_NODO = 23;
    public static final int COLUMNA_ESTRUCT_1_NODO = 24;
    public static final int COLUMNA_TIPO_1_ESTRUCT = 25;
    public static final int COLUMNA_ESTRUCT_2_NODO = 26;
    public static final int COLUMNA_TIPO_2_ESTRUCT = 27;
    public static final int COLUMNA_ESTRUCT_3_NODO = 28;
    public static final int COLUMNA_TIPO_3_ESTRUCT = 29;
    public static final int COLUMNA_ESTRUCT_4_NODO = 30;
    public static final int COLUMNA_TIPO_4_ESTRUCT = 31;
    public static final int COLUMNA_NR_RETEN = 32;
    public static final int COLUMNA_ANTIGUEDAD = 33;
    public static final int COLUMNA_PROPIEDAD = 34;
    public static final int COLUMNA_NRO_ACOMET = 35;
    public static final int COLUMNA_CON1 = 36;
    public static final int COLUMNA_CAL_FASE_CON1 = 37;
    public static final int COLUMNA_CAL_NEUTRO_CON1 = 38;
    public static final int COLUMNA_CAL_AP_CON1 = 39;
    public static final int COLUMNA_NHIL_CON1 = 40;
    public static final int COLUMNA_CON2 = 41;
    public static final int COLUMNA_CAL_FASE_CON2 = 42;
    public static final int COLUMNA_CAL_NEUTRO_CON2 = 43;
    public static final int COLUMNA_CAL_AP_CON2 = 44;
    public static final int COLUMNA_NHIL_CON2 = 45;
    public static final int COLUMNA_CON3 = 46;
    public static final int COLUMNA_CAL_FASE_CON3 = 47;
    public static final int COLUMNA_CAL_NEUTRO_CON3 = 48;
    public static final int COLUMNA_CAL_AP_CON3 = 49;
    public static final int COLUMNA_NHIL_CON3 = 50;
    public static final int COLUMNA_CON4 = 51;
    public static final int COLUMNA_CAL_FASE_CON4 = 52;
    public static final int COLUMNA_CAL_NEUTRO_CON4 = 53;
    public static final int COLUMNA_CAL_AP_CON4 = 54;
    public static final int COLUMNA_NHIL_CON4 = 55;
    public static final int COLUMNA_FECH_LEVANT_NODO = 56;
    public static final int COLUMNA_OBSERVACION_NODO = 57;
    public static final int COLUMNA_NRO_ACOMETIDAS = 58;
    public static final int COLUMNA_ESPACIO_N2 = 59;
    public static final int COLUMNA_ACOMETIDA_CORRESP = 60;
    public static final int COLUMNA_NIU = 61;
    public static final int COLUMNA_CONT_ACT = 62;
    public static final int COLUMNA_ID_NODO_ACOMETIDA = 63;
    public static final int COLUMNA_DIRECCION = 64;
    public static final int COLUMNA_CONT_ANTE = 65;
    public static final int COLUMNA_CONT_MARCA = 66;
    public static final int COLUMNA_LATI_USU = 67;
    public static final int COLUMNA_LONG_USU = 68;
    public static final int COLUMNA_ALTI_USU = 69;
    public static final int COLUMNA_TIP_CA = 70;
    public static final int COLUMNA_TIP_AS = 71;
    public static final int COLUMNA_TIP_MBT = 72;
    public static final int COLUMNA_TIP_RST = 73;
    public static final int COLUMNA_CALIBRE = 74;
    public static final int COLUMNA_TABLERO = 75;
    public static final int COLUMNA_FAS_TABLERO = 76;
    public static final int COLUMNA_TIP_SERVICIO = 77;
    public static final int COLUMNA_OBSERVACION_USU = 78;
    public static final int COLUMNA_FECH_LEVANT_USU = 79;
    public static final int COLUMNA_USU_LEVANT_USU = 80;


    public SyncAdapter( Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        resolver = context.getContentResolver();
    }

    /**
     * Constructor para mantener compatibilidad en versiones inferiores a 3.0
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        resolver = context.getContentResolver();
    }

    public static void inicializarSyncAdapter(Context context) {
        obtenerCuentaASincronizar(context);
    }
    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                               final SyncResult syncResult) {

        Log.i(TAG, "onPerformSync()...");

        boolean soloSubida = extras.getBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, false);

        if (!soloSubida) {
            //realizarSincronizacionLocal(syncResult);
        } else {
            realizarSincronizacionRemota();
        }
    }

    private void realizarSincronizacionLocal(final SyncResult syncResult) {
        Log.i(TAG, "Actualizando Aplicativo Local.");

        VolleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        Constantes.GET_URL,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGet(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, error.networkResponse.toString());
                            }
                        }
                )
        );
    }

    /**
     * Procesa la respuesta del servidor al pedir que se retornen todos los gastos.
     *
     * //@param response   Respuesta en formato Json
     * //@param syncResult Registro de resultados de sincronización
     */
    private void procesarRespuestaGet( JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);

            switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocales(response, syncResult);
                    Log.i(TAG, "parametros reponse-synresul: "+ response+ "-"+syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    Log.i(TAG, mensaje);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void realizarSincronizacionRemota() {
        Log.i(TAG, "Actualizando el servidor...");

        iniciarActualizacion();

        Cursor c = obtenerRegistrosSucios();

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros sucios.");

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(COLUMNA_ID );
                Log.i(TAG, "ojo ver respuesta "+ Constantes.INSERT_URL);
                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERT_URL,
                                Utilidades.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.i(TAG, "ojo ver respuesta ingreso"+ response.toString().substring( 11,12 ));
                                        repuesta_act = (response.toString().substring(11,12));
                                        procesarRespuestaInsert(response, idLocal);
                                    }

                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.getMessage());
                                    }
                                }

                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" + getParamsEncoding();
                            }
                        }
                );
            }

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }
        c.close();
    }

    /**
     * Obtiene el registro que se acaba de marcar como "pendiente por sincronizar" y
     * con "estado de sincronización"
     *
     * @return Cursor con el registro.
     */
    private Cursor obtenerRegistrosSucios() {
        Uri uri = ContractParaG.CONTENT_URI;
        String selection = DatabaseManagerUser.Columnas.PENDIENTE_INSERCION + "=? AND "
                + Constantes.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaG.ESTADO_SYNC + ""};

        return resolver.query(uri, PROJECTION, selection, selectionArgs, null);
    }

    /**
     * Cambia a estado "de sincronización" el registro que se acaba de insertar localmente
     */
    private void iniciarActualizacion() {
        Uri uri = ContractParaG.CONTENT_URI;
        String selection = DatabaseManagerUser.Columnas.PENDIENTE_INSERCION + "=? AND "
                + Constantes.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaG.ESTADO_OK + ""};

        ContentValues v = new ContentValues();
        v.put(Constantes.ESTADO, ContractParaG.ESTADO_SYNC);

        int results = resolver.update(uri, v, selection, selectionArgs);
        Log.i(TAG, "Registros puestos en cola de inserción:" + results);
    }

    /**
     * Limpia el registro que se sincronizó y le asigna la nueva id remota proveida
     * por el servidor
     *
     * @param idRemota id remota
     */
    private void finalizarActualizacion( String idRemota, int idLocal) {
        Uri uri = ContractParaG.CONTENT_URI;
        String selection = DatabaseManagerUser.Columnas._ID + "=?";//ojo
        String[] selectionArgs = new String[]{String.valueOf(idLocal)};

        ContentValues v = new ContentValues();
        v.put(DatabaseManagerUser.Columnas.PENDIENTE_INSERCION, "0");
        v.put(Constantes.ESTADO, ContractParaG.ESTADO_OK);
        v.put(DatabaseManagerUser.Columnas.ID_REMOTA, idRemota);

        resolver.update(uri, v, selection, selectionArgs);
    }

    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en formato Json
     */
    public void procesarRespuestaInsert( JSONObject response, int idLocal) {
        Log.i(TAG, "ojo ver respuesta a procesar dos "+ response);
        Log.i(TAG, "idLocal "+ idLocal);

        try {
            // Obtener estado
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);
            // Obtener identificador del nuevo registro creado en el servidor
            String idRemota = response.getString(Constantes.ID_TRAFO);

            switch (estado) {
                case Constantes.SUCCESS:
                    Log.i(TAG, mensaje);
                    finalizarActualizacion(idRemota, idLocal);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     * Actualiza los registros locales a través de una comparación con los datos
     * del servidor
     *
     * @param response   Respuesta en formato Json obtenida del servidor
     * @param syncResult Registros de la sincronización
     */
   private void actualizarDatosLocales( JSONObject response, SyncResult syncResult) {

        JSONArray trafos = null;
        Log.i(TAG, "ojo ver respuesta a procesar datos locales "+ response);
        Log.i(TAG, "idLocal "+ syncResult);
        try {
            // Obtener array
            trafos = response.getJSONArray(Constantes.TRAFOS);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Trafo[] res = gson.fromJson(trafos != null ? trafos.toString() : null, Trafo[].class);
        List<Trafo> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Tabla hash para recibir las entradas entrantes
        HashMap <String, Trafo> expenseMap = new HashMap <String, Trafo>();
        for (Trafo e : data) {
            expenseMap.put(e.idTrafo, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractParaG.CONTENT_URI;
        String select = DatabaseManagerUser.Columnas.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, PROJECTION, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        String nomb_trafo;
        int capacidad;
        int zonaur;
        String mpio;
        String propiedad_trafo;
        String tipo;
        double lati_trafo;
        double long_trafo;
        double alt_trafo;
        String tipo_trafo;
        String fech_levant_trafo;
        String observacion_trafo;
        int nro_postes_trafo;
        String espacio_trafo_poste;
        int nro_poste_corresp;
        String nomb_nodo;
        String id_trafo_nodo;
        double lati_nodo;
        double long_nodo;
        double alti_nodo;
        int altura_nodo;
        String material_nodo;
        String estruct_1_nodo;
        String tipo_1_estruct;
        String estruct_2_nodo;
        String tipo_2_estruct;
        String estruct_3_nodo;
        String tipo_3_estruct;
        String estruct_4_nodo;
        String tipo_4_estruct;
        int nr_reten;
        int antiguedad;
        String propiedad_nodo;
        int nro_acomet;
        String con1;
        String cal_fase_con1;
        String cal_neutro_con1;
        String cal_ap_con1;
        String nhil_con1;
        String con2;
        String cal_fase_con2;
        String cal_neutro_con2;
        String cal_ap_con2;
        String nhil_con2;
        String con3;
        String cal_fase_con3;
        String cal_neutro_con3;
        String cal_ap_con3;
        String nhil_con3;
        String con4;
        String cal_fase_con4;
        String cal_neutro_con4;
        String cal_ap_con4;
        String nhil_con4;
        String fech_levant_nodo;
        String observacion_nodo;
        int nro_acometidas;
        String espacio_poste_acometida;
        int nro_acometida_corresp;
        String niu;
        String id_nodo_usu;
        String direccion;
        String cont_ante;
        int cont_act;
        String cont_marca;
        double lati_usu;
        double long_usu;
        double alti_usu;
        String tip_ca;
        String tip_as;
        String tip_mbt;
        String tip_rst;
        int calibre;
        String tablero;
        String fas_tablero;
        String tip_servicio;
        String observacion_usu;
        String fech_levant_usu;
        int usu_levant_usu;
        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(COLUMNA_ID_REMOTA);
            nomb_trafo = c.getString(COLUMNA_NOMB_TRAFO);
            capacidad = c.getInt(COLUMNA_CAPACIDAD);
            zonaur = c.getInt(COLUMNA_ZONAUR);
            mpio = c.getString(COLUMNA_MPIO);
            propiedad_trafo = c.getString(COLUMNA_PROPIEDAD_TRAFO);
            tipo = c.getString(COLUMNA_TIPO);
            lati_trafo = c.getDouble(COLUMNA_LATI_TRAFO);
            long_trafo = c.getDouble(COLUMNA_LONG_TRAFO);
            alt_trafo = c.getDouble(COLUMNA_ALT_TRAFO);
            tipo_trafo = c.getString(COLUMNA_TIPO_TRAFO);
            observacion_trafo = c.getString(COLUMNA_OBSERVACION_TRAFO);
            fech_levant_trafo = c.getString(COLUMNA_FECH_LEVANT_TRAFO);
            nro_postes_trafo = c.getInt(COLUMNA_NRO_POSTES_TRAFO);
            espacio_trafo_poste = c.getString(COLUMNA_ESPACIO_N1);
            nro_poste_corresp = c.getInt(COLUMNA_NRO_POSTE_CORRESP);
            nomb_nodo = c.getString(COLUMNA_NOMB_NODO);
            id_trafo_nodo = c.getString(COLUMNA_ID_TRAFO_NODO);
            lati_nodo = c.getDouble(COLUMNA_LATI_NODO);
            long_nodo = c.getDouble(COLUMNA_LONG_NODO);
            alti_nodo = c.getInt(COLUMNA_ALTI_NODO);
            altura_nodo = c.getInt(COLUMNA_ALTURA_NODO);
            material_nodo = c.getString(COLUMNA_MATERIAL_NODO);
            estruct_1_nodo = c.getString(COLUMNA_ESTRUCT_1_NODO);
            tipo_1_estruct = c.getString(COLUMNA_TIPO_1_ESTRUCT);
            estruct_2_nodo = c.getString(COLUMNA_ESTRUCT_2_NODO);
            tipo_2_estruct = c.getString(COLUMNA_TIPO_2_ESTRUCT);
            estruct_3_nodo = c.getString(COLUMNA_ESTRUCT_3_NODO);
            tipo_3_estruct = c.getString(COLUMNA_TIPO_3_ESTRUCT);
            estruct_4_nodo = c.getString(COLUMNA_ESTRUCT_4_NODO);
            tipo_4_estruct = c.getString(COLUMNA_TIPO_4_ESTRUCT);
            nr_reten = c.getInt(COLUMNA_NR_RETEN);
            antiguedad = c.getInt(COLUMNA_ANTIGUEDAD);
            propiedad_nodo = c.getString(COLUMNA_PROPIEDAD);
            nro_acomet = c.getInt(COLUMNA_NRO_ACOMET);
            con1 = c.getString(COLUMNA_CON1);
            cal_fase_con1 = c.getString(COLUMNA_CAL_FASE_CON1);
            cal_neutro_con1 = c.getString(COLUMNA_CAL_NEUTRO_CON1);
            cal_ap_con1 = c.getString(COLUMNA_CAL_AP_CON1);
            nhil_con1 = c.getString(COLUMNA_NHIL_CON1);
            con2 = c.getString(COLUMNA_CON2);
            cal_fase_con2 = c.getString(COLUMNA_CAL_FASE_CON2);
            cal_neutro_con2 = c.getString(COLUMNA_CAL_NEUTRO_CON2);
            cal_ap_con2 = c.getString(COLUMNA_CAL_AP_CON2);
            nhil_con2 = c.getString(COLUMNA_NHIL_CON2);
            con3 = c.getString(COLUMNA_CON3);
            cal_fase_con3 = c.getString(COLUMNA_CAL_FASE_CON3);
            cal_neutro_con3 = c.getString(COLUMNA_CAL_NEUTRO_CON3);
            cal_ap_con3 = c.getString(COLUMNA_CAL_AP_CON3);
            nhil_con3 = c.getString(COLUMNA_NHIL_CON3);
            con4 = c.getString(COLUMNA_CON4);
            cal_fase_con4 = c.getString(COLUMNA_CAL_FASE_CON4);
            cal_neutro_con4 = c.getString(COLUMNA_CAL_NEUTRO_CON4);
            cal_ap_con4 = c.getString(COLUMNA_CAL_AP_CON4);
            nhil_con4 = c.getString(COLUMNA_NHIL_CON4);
            fech_levant_nodo = c.getString(COLUMNA_FECH_LEVANT_NODO);
            observacion_nodo = c.getString(COLUMNA_OBSERVACION_NODO);
            nro_acometidas = c.getInt(COLUMNA_NRO_ACOMETIDAS);
            espacio_poste_acometida = c.getString(COLUMNA_ESPACIO_N2);
            nro_acometida_corresp = c.getInt(COLUMNA_ACOMETIDA_CORRESP);
            niu = c.getString(COLUMNA_NIU);
            id_nodo_usu = c.getString(COLUMNA_CONT_ACT);
            direccion = c.getString(COLUMNA_ID_NODO_ACOMETIDA);
            cont_ante = c.getString(COLUMNA_DIRECCION);
            cont_act = c.getInt(COLUMNA_CONT_ANTE);
            cont_marca = c.getString(COLUMNA_CONT_MARCA);
            lati_usu = c.getDouble(COLUMNA_LATI_USU);
            long_usu = c.getDouble(COLUMNA_LONG_USU);
            alti_usu = c.getDouble(COLUMNA_ALTI_USU);
            tip_ca = c.getString(COLUMNA_TIP_CA);
            tip_as = c.getString(COLUMNA_TIP_AS);
            tip_mbt = c.getString(COLUMNA_TIP_MBT);
            tip_rst = c.getString(COLUMNA_TIP_RST);
            calibre = c.getInt(COLUMNA_CALIBRE);
            tablero = c.getString(COLUMNA_TABLERO);
            fas_tablero = c.getString(COLUMNA_FAS_TABLERO);
            tip_servicio = c.getString(COLUMNA_TIP_SERVICIO);
            observacion_usu = c.getString(COLUMNA_OBSERVACION_USU);
            fech_levant_usu = c.getString(COLUMNA_FECH_LEVANT_USU);
            usu_levant_usu = c.getInt(COLUMNA_USU_LEVANT_USU);

            Trafo match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractParaG.CONTENT_URI.buildUpon()
                        .appendPath( id ).build();

                // Comprobar si el trafo necesita ser actualizado

                boolean b = match.nomb_trafo != null && !match.nomb_trafo.equals(nomb_trafo);
                boolean b1 = match.capacidad != capacidad;
                boolean b2 = match.zonaur != zonaur;
                boolean b3 = match.mpio != null && !match.mpio.equals(mpio);
                boolean b4 = match.propiedad_trafo != null && !match.propiedad_trafo.equals(propiedad_trafo);
                boolean b5 = match.tipo != null && !match.tipo.equals(tipo);
                boolean b6 = match.lati_trafo != lati_trafo;
                boolean b7 = match.long_trafo != long_trafo;
                boolean b8 = match.alt_trafo != alt_trafo;
                boolean b9 = match.tipo_trafo != null && !match.tipo_trafo.equals(tipo_trafo);
                boolean b10 = match.fech_levant_trafo != null && !match.fech_levant_trafo.equals(fech_levant_trafo);
                boolean b11 = match.observacion_trafo != null && !match.observacion_trafo.equals(observacion_trafo);
                boolean b12 = match.nro_postes_trafo != nro_postes_trafo;
                boolean b13 = match.espacio_trafo_poste != null && !match.espacio_trafo_poste.equals(espacio_trafo_poste);
                boolean b14 = match.nro_poste_corresp != nro_poste_corresp;
                boolean b15 = match.nomb_nodo != null && !match.nomb_nodo.equals(nomb_nodo);
                boolean b16 = match.id_trafo_nodo != null && !match.id_trafo_nodo.equals(id_trafo_nodo);
                boolean b17 = match.lati_nodo != lati_nodo;
                boolean b18 = match.long_nodo != long_nodo;
                boolean b19 = match.alti_nodo != alti_nodo;
                boolean b20 = match.altura_nodo != altura_nodo;
                boolean b21 = match.material_nodo != null && !match.material_nodo.equals(material_nodo);
                boolean b22 = match.estruct_1_nodo != null && !match.estruct_1_nodo.equals( estruct_1_nodo );
                boolean b23 = match.tipo_1_estruct != null && !match.tipo_1_estruct.equals(tipo_1_estruct);
                boolean b24 = match.estruct_2_nodo != null && !match.estruct_2_nodo.equals( estruct_2_nodo );;
                boolean b25 = match.tipo_2_estruct != null && !match.tipo_2_estruct.equals(tipo_2_estruct);
                boolean b26 = match.estruct_3_nodo != null && !match.estruct_3_nodo.equals( estruct_3_nodo );;
                boolean b27 = match.tipo_3_estruct != null && !match.tipo_3_estruct.equals(tipo_3_estruct);
                boolean b28 = match.estruct_4_nodo != null && !match.estruct_4_nodo.equals( estruct_4_nodo );
                boolean b29 = match.tipo_4_estruct != null && !match.tipo_4_estruct.equals(tipo_4_estruct);
                boolean b30 = match.nr_reten != nr_reten;
                boolean b31 = match.antiguedad != antiguedad;
                boolean b32 = match.propiedad_nodo != null && !match.propiedad_nodo.equals(propiedad_nodo);
                boolean b33 = match.nro_acomet != nro_acomet;
                boolean b34 = match.con1 != null && !match.con1.equals(con1);
                boolean b35 = match.cal_fase_con1 != null  && !match.cal_fase_con1.equals(cal_fase_con1);
                boolean b36 = match.cal_neutro_con1 != null  && !match.cal_neutro_con1.equals(cal_neutro_con1);
                boolean b37 = match.cal_ap_con1 != null  && !match.cal_ap_con1.equals(cal_ap_con1);
                boolean b38 = match.nhil_con1 != null && !match.nhil_con1.equals(nhil_con1);
                boolean b39 = match.con2 != null && !match.con2.equals(con2);
                boolean b40 = match.cal_fase_con2 != null  && !match.cal_fase_con2.equals(cal_fase_con2);
                boolean b41 = match.cal_neutro_con2 != null  && !match.cal_neutro_con2.equals(cal_neutro_con2);
                boolean b42 = match.cal_ap_con2 != null  && !match.cal_ap_con2.equals(cal_ap_con2);
                boolean b43 = match.nhil_con2 != null && !match.nhil_con2.equals(nhil_con2);
                boolean b44 = match.con3 != null && !match.con3.equals(con3);
                boolean b45 = match.cal_fase_con3 != null  && !match.cal_fase_con3.equals(cal_fase_con3);
                boolean b46 = match.cal_neutro_con3 != null  && !match.cal_neutro_con3.equals(cal_neutro_con3);
                boolean b47 = match.cal_ap_con3 != null  && !match.cal_ap_con3.equals(cal_ap_con3);
                boolean b48 = match.nhil_con3 != null && !match.nhil_con3.equals(nhil_con3);
                boolean b49 = match.con4 != null && !match.con4.equals(con4);
                boolean b50 = match.cal_fase_con4 != null  && !match.cal_fase_con4.equals(cal_fase_con4);
                boolean b51 = match.cal_neutro_con4 != null  && !match.cal_neutro_con4.equals(cal_neutro_con4);
                boolean b52 = match.cal_ap_con4 != null  && !match.cal_ap_con4.equals(cal_ap_con4);
                boolean b53 = match.nhil_con4 != null && !match.nhil_con4.equals(nhil_con4);
                boolean b54 = match.fech_levant_nodo != null && !match.tipo_trafo.equals(fech_levant_nodo);
                boolean b55 = match.observacion_nodo != null && !match.observacion_nodo.equals(observacion_nodo);
                boolean b56 = match.nro_acometidas != nro_acometidas;
                boolean b57 = match.espacio_poste_acometida != null && !match.espacio_poste_acometida.equals(espacio_poste_acometida);
                boolean b58 = match.nro_acometida_corresp != null && !match.nro_acometida_corresp.equals(nro_acometida_corresp);
                boolean b59 = match.niu != niu;
                boolean b60 = match.id_nodo_usu != null && !match.id_nodo_usu.equals(id_nodo_usu);
                boolean b61 = match.direccion != direccion;
                boolean b62 = match.cont_ante != null && !match.cont_ante.equals(cont_ante);
                boolean b63 = match.cont_act != null && !match.cont_act.equals(cont_act);
                boolean b64 = match.cont_marca != null && !match.cont_marca.equals(cont_marca);
                boolean b65 = match.lati_usu != lati_usu;
                boolean b66 = match.long_usu != long_usu;
                boolean b67 = match.alti_usu != alti_usu;
                boolean b68 = match.tip_ca != null && !match.tip_ca.equals(tip_ca);
                boolean b69 = match.tip_as != null && !match.tip_as.equals(tip_as);
                boolean b70 = match.tip_mbt != null && !match.tip_mbt.equals(tip_mbt);
                boolean b71 = match.tip_rst != null && !match.tip_rst.equals(tip_rst);
                boolean b72 = match.calibre != calibre;
                boolean b73 = match.tablero != null && !match.tablero.equals(tablero);
                boolean b74 = match.fas_tablero != null && !match.fas_tablero.equals(fas_tablero);
                boolean b75 = match.tip_servicio != null && !match.tip_servicio.equals(tip_servicio);
                boolean b76 = match.observacion_usu != null && !match.observacion_usu.equals(observacion_usu);
                boolean b77 = match.fech_levant_usu != null && !match.tipo_trafo.equals(fech_levant_usu);
                boolean b78 = match.usu_levant_usu != usu_levant_usu;

                if (b || b1 || b2 || b3 || b4 || b5 || b6 || b7 || b8 || b9 || b10 || b11 || b12 || b13 || b14 || b15 || b16 || b17 || b18 || b19 || b20 || b21 || b22 || b23 || b24 || b25 || b26 || b27 || b28 || b29 || b30 || b31 || b32 || b33 || b34 || b35 || b36 || b37 || b38 || b39 || b40 || b41 || b42 || b43 || b44 || b45 || b46 || b47 || b48 || b49 || b50 || b51 || b52 || b53 || b54 || b55 || b56 || b57 || b58 || b59 || b60 || b61 || b62  || b63 || b64 || b65 || b66 || b67 || b68 || b69 || b70 || b71 || b72 || b73 || b74 || b75 || b76 || b77 || b78){

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add( ContentProviderOperation.newUpdate(existingUri)
                            .withValue(DatabaseManagerUser.Columnas.NOMB_TRAFO , match.nomb_trafo)
                            .withValue(DatabaseManagerUser.Columnas.CAPACIDAD , match.capacidad)
                            .withValue(DatabaseManagerUser.Columnas.ZONAUR , match.zonaur)
                            .withValue(DatabaseManagerUser.Columnas.MPIO , match.mpio)
                            .withValue(DatabaseManagerUser.Columnas.PROPIEDAD_TRAFO , match.propiedad_trafo)
                            .withValue(DatabaseManagerUser.Columnas.TIPO , match.tipo)
                            .withValue(DatabaseManagerUser.Columnas.LATI_TRAFO , match.lati_trafo)
                            .withValue(DatabaseManagerUser.Columnas.LONG_TRAFO , match.long_trafo)
                            .withValue(DatabaseManagerUser.Columnas.ALT_TRAFO , match.alt_trafo)
                            .withValue(DatabaseManagerUser.Columnas.TIPO_TRAFO , match.tipo_trafo)
                            .withValue(DatabaseManagerUser.Columnas.OBSERVACION_TRAFO , match.observacion_trafo)
                            .withValue(DatabaseManagerUser.Columnas.FECH_LEVANT_TRAFO , match.fech_levant_trafo)
                            .withValue(DatabaseManagerUser.Columnas.NRO_POSTES_TRAFO , match.nro_postes_trafo)
                            .withValue(DatabaseManagerUser.Columnas.ESPACIO_N1 , match.espacio_trafo_poste)
                            .withValue(DatabaseManagerUser.Columnas.NRO_POSTE_CORRESP , match.nro_poste_corresp)
                            .withValue(DatabaseManagerUser.Columnas.NOMB_NODO , match.nomb_nodo)
                            .withValue(DatabaseManagerUser.Columnas.ID_TRAFO_NODO , match.id_trafo_nodo)
                            .withValue(DatabaseManagerUser.Columnas.LATI_NODO , match.lati_nodo)
                            .withValue(DatabaseManagerUser.Columnas.LONG_NODO , match.long_nodo)
                            .withValue(DatabaseManagerUser.Columnas.ALTI_NODO , match.alti_nodo)
                            .withValue(DatabaseManagerUser.Columnas.ALTURA_NODO , match.altura_nodo)
                            .withValue(DatabaseManagerUser.Columnas.MATERIAL_NODO , match.material_nodo)
                            .withValue(DatabaseManagerUser.Columnas.ESTRUCT_1_NODO , match.estruct_1_nodo)
                            .withValue(DatabaseManagerUser.Columnas.TIPO_1_ESTRUCT , match.tipo_1_estruct)
                            .withValue(DatabaseManagerUser.Columnas.ESTRUCT_2_NODO , match.estruct_2_nodo)
                            .withValue(DatabaseManagerUser.Columnas.TIPO_2_ESTRUCT , match.tipo_2_estruct)
                            .withValue(DatabaseManagerUser.Columnas.ESTRUCT_3_NODO , match.estruct_3_nodo)
                            .withValue(DatabaseManagerUser.Columnas.TIPO_3_ESTRUCT , match.tipo_3_estruct)
                            .withValue(DatabaseManagerUser.Columnas.ESTRUCT_4_NODO , match.estruct_4_nodo)
                            .withValue(DatabaseManagerUser.Columnas.TIPO_4_ESTRUCT , match.tipo_4_estruct)
                            .withValue(DatabaseManagerUser.Columnas.NR_RETEN , match.nr_reten)
                            .withValue(DatabaseManagerUser.Columnas.ANTIGUEDAD , match.antiguedad)
                            .withValue(DatabaseManagerUser.Columnas.PROPIEDAD , match.propiedad_nodo)
                            .withValue(DatabaseManagerUser.Columnas.NRO_ACOMET , match.nro_acomet)
                            .withValue(DatabaseManagerUser.Columnas.CON1 , match.con1)
                            .withValue(DatabaseManagerUser.Columnas.CAL_FASE_CON1 , match.cal_fase_con1)
                            .withValue(DatabaseManagerUser.Columnas.CAL_NEUTRO_CON1 , match.cal_neutro_con1)
                            .withValue(DatabaseManagerUser.Columnas.CAL_AP_CON1 , match.cal_ap_con1)
                            .withValue(DatabaseManagerUser.Columnas.NHIL_CON1 , match.nhil_con1)
                            .withValue(DatabaseManagerUser.Columnas.CON2 , match.con2)
                            .withValue(DatabaseManagerUser.Columnas.CAL_FASE_CON2 , match.cal_fase_con2)
                            .withValue(DatabaseManagerUser.Columnas.CAL_NEUTRO_CON2 , match.cal_neutro_con2)
                            .withValue(DatabaseManagerUser.Columnas.CAL_AP_CON2 , match.cal_ap_con2)
                            .withValue(DatabaseManagerUser.Columnas.NHIL_CON2 , match.nhil_con2)
                            .withValue(DatabaseManagerUser.Columnas.CON3 , match.con3)
                            .withValue(DatabaseManagerUser.Columnas.CAL_FASE_CON3 , match.cal_fase_con3)
                            .withValue(DatabaseManagerUser.Columnas.CAL_NEUTRO_CON3 , match.cal_neutro_con3)
                            .withValue(DatabaseManagerUser.Columnas.CAL_AP_CON3 , match.cal_ap_con3)
                            .withValue(DatabaseManagerUser.Columnas.NHIL_CON3 , match.nhil_con3)
                            .withValue(DatabaseManagerUser.Columnas.CON4 , match.con4)
                            .withValue(DatabaseManagerUser.Columnas.CAL_FASE_CON4 , match.cal_fase_con4)
                            .withValue(DatabaseManagerUser.Columnas.CAL_NEUTRO_CON4 , match.cal_neutro_con4)
                            .withValue(DatabaseManagerUser.Columnas.CAL_AP_CON4 , match.cal_ap_con4)
                            .withValue(DatabaseManagerUser.Columnas.NHIL_CON4 , match.nhil_con4)
                            .withValue(DatabaseManagerUser.Columnas.FECH_LEVANT_NODO , match.fech_levant_nodo)
                            .withValue(DatabaseManagerUser.Columnas.OBSERVACION_NODO , match.observacion_nodo)
                            .withValue(DatabaseManagerUser.Columnas.NRO_ACOMETIDAS , match.nro_acometidas)
                            .withValue(DatabaseManagerUser.Columnas.ESPACIO_N2 , match.espacio_poste_acometida)
                            .withValue(DatabaseManagerUser.Columnas.ACOMETIDA_CORRESP , match.nro_acometida_corresp)
                            .withValue(DatabaseManagerUser.Columnas.NIU, match.niu)
                            .withValue(DatabaseManagerUser.Columnas.ID_NODO_ACOMETIDA, match.direccion)
                            .withValue(DatabaseManagerUser.Columnas.DIRECCION, match.cont_ante)
                            .withValue(DatabaseManagerUser.Columnas.CONT_ANTE, match.cont_act)
                            .withValue(DatabaseManagerUser.Columnas.CONT_ACT, match.id_nodo_usu)
                            .withValue(DatabaseManagerUser.Columnas.CONT_MARCA, match.cont_marca)
                            .withValue(DatabaseManagerUser.Columnas.LATI_USU , match.lati_usu)
                            .withValue(DatabaseManagerUser.Columnas.LONG_USU , match.long_usu)
                            .withValue(DatabaseManagerUser.Columnas.ALTI_USU , match.alti_usu)
                            .withValue(DatabaseManagerUser.Columnas.TIP_CA, match.tip_ca)
                            .withValue(DatabaseManagerUser.Columnas.TIP_AS, match.tip_as)
                            .withValue(DatabaseManagerUser.Columnas.TIP_MBT, match.tip_mbt)
                            .withValue(DatabaseManagerUser.Columnas.TIP_RST, match.tip_rst)
                            .withValue(DatabaseManagerUser.Columnas.CALIBRE, match.calibre)
                            .withValue(DatabaseManagerUser.Columnas.TABLERO, match.tablero)
                            .withValue(DatabaseManagerUser.Columnas.FAS_TABLERO, match.fas_tablero)
                            .withValue(DatabaseManagerUser.Columnas.TIP_SERVICIO, match.tip_servicio)
                            .withValue(DatabaseManagerUser.Columnas.OBSERVACION_USU, match.observacion_usu)
                            .withValue(DatabaseManagerUser.Columnas.FECH_LEVANT_USU, match.fech_levant_usu)
                            .withValue(DatabaseManagerUser.Columnas.USU_LEVANT_USU, match.usu_levant_usu)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractParaG.CONTENT_URI.buildUpon()
                        .appendPath(id).build();//ojo
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add( ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Trafo e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de: " + e.idTrafo);
            ops.add( ContentProviderOperation.newInsert(ContractParaG.CONTENT_URI)
                    .withValue(DatabaseManagerUser.Columnas.ID_REMOTA, e.idTrafo)
                    .withValue(DatabaseManagerUser.Columnas.NOMB_TRAFO ,e.nomb_trafo)
                    .withValue(DatabaseManagerUser.Columnas.CAPACIDAD ,e.capacidad)
                    .withValue(DatabaseManagerUser.Columnas.ZONAUR ,e.zonaur)
                    .withValue(DatabaseManagerUser.Columnas.MPIO ,e.mpio)
                    .withValue(DatabaseManagerUser.Columnas.PROPIEDAD_TRAFO ,e.propiedad_trafo)
                    .withValue(DatabaseManagerUser.Columnas.TIPO ,e.tipo)
                    .withValue(DatabaseManagerUser.Columnas.LATI_TRAFO ,e.lati_trafo)
                    .withValue(DatabaseManagerUser.Columnas.LONG_TRAFO ,e.long_trafo)
                    .withValue(DatabaseManagerUser.Columnas.ALT_TRAFO ,e.alt_trafo)
                    .withValue(DatabaseManagerUser.Columnas.TIPO_TRAFO ,e.tipo_trafo)
                    .withValue(DatabaseManagerUser.Columnas.FECH_LEVANT_TRAFO ,e.fech_levant_trafo)
                    .withValue(DatabaseManagerUser.Columnas.OBSERVACION_TRAFO ,e.observacion_trafo)
                    .withValue(DatabaseManagerUser.Columnas.NRO_POSTES_TRAFO ,e.nro_postes_trafo)
                    .withValue(DatabaseManagerUser.Columnas.ESPACIO_N1 ,e.espacio_trafo_poste)
                    .withValue(DatabaseManagerUser.Columnas.NRO_POSTE_CORRESP ,e.nro_poste_corresp)
                    .withValue(DatabaseManagerUser.Columnas.NOMB_NODO ,e.nomb_nodo)
                    .withValue(DatabaseManagerUser.Columnas.ID_TRAFO_NODO ,e.id_trafo_nodo)
                    .withValue(DatabaseManagerUser.Columnas.LATI_NODO ,e.lati_nodo)
                    .withValue(DatabaseManagerUser.Columnas.LONG_NODO ,e.long_nodo)
                    .withValue(DatabaseManagerUser.Columnas.ALTI_NODO ,e.alti_nodo)
                    .withValue(DatabaseManagerUser.Columnas.ALTURA_NODO ,e.altura_nodo)
                    .withValue(DatabaseManagerUser.Columnas.MATERIAL_NODO ,e.material_nodo)
                    .withValue(DatabaseManagerUser.Columnas.ESTRUCT_1_NODO ,e.estruct_1_nodo)
                    .withValue(DatabaseManagerUser.Columnas.TIPO_1_ESTRUCT ,e.tipo_1_estruct)
                    .withValue(DatabaseManagerUser.Columnas.ESTRUCT_2_NODO ,e.estruct_2_nodo)
                    .withValue(DatabaseManagerUser.Columnas.TIPO_2_ESTRUCT ,e.tipo_2_estruct)
                    .withValue(DatabaseManagerUser.Columnas.ESTRUCT_3_NODO ,e.estruct_3_nodo)
                    .withValue(DatabaseManagerUser.Columnas.TIPO_3_ESTRUCT ,e.tipo_3_estruct)
                    .withValue(DatabaseManagerUser.Columnas.ESTRUCT_4_NODO ,e.estruct_4_nodo)
                    .withValue(DatabaseManagerUser.Columnas.TIPO_4_ESTRUCT ,e.tipo_4_estruct)
                    .withValue(DatabaseManagerUser.Columnas.NR_RETEN ,e.nr_reten)
                    .withValue(DatabaseManagerUser.Columnas.ANTIGUEDAD ,e.antiguedad)
                    .withValue(DatabaseManagerUser.Columnas.PROPIEDAD ,e.propiedad_nodo)
                    .withValue(DatabaseManagerUser.Columnas.NRO_ACOMET ,e.nro_acomet)
                    .withValue(DatabaseManagerUser.Columnas.CON1 ,e.con1)
                    .withValue(DatabaseManagerUser.Columnas.CAL_FASE_CON1 ,e.cal_fase_con1)
                    .withValue(DatabaseManagerUser.Columnas.CAL_NEUTRO_CON1 ,e.cal_neutro_con1)
                    .withValue(DatabaseManagerUser.Columnas.CAL_AP_CON1 ,e.cal_ap_con1)
                    .withValue(DatabaseManagerUser.Columnas.NHIL_CON1 ,e.nhil_con1)
                    .withValue(DatabaseManagerUser.Columnas.CON2 ,e.con2)
                    .withValue(DatabaseManagerUser.Columnas.CAL_FASE_CON2 ,e.cal_fase_con2)
                    .withValue(DatabaseManagerUser.Columnas.CAL_NEUTRO_CON2 ,e.cal_neutro_con2)
                    .withValue(DatabaseManagerUser.Columnas.CAL_AP_CON2 ,e.cal_ap_con2)
                    .withValue(DatabaseManagerUser.Columnas.NHIL_CON2 ,e.nhil_con2)
                    .withValue(DatabaseManagerUser.Columnas.CON3 ,e.con3)
                    .withValue(DatabaseManagerUser.Columnas.CAL_FASE_CON3 ,e.cal_fase_con3)
                    .withValue(DatabaseManagerUser.Columnas.CAL_NEUTRO_CON3 ,e.cal_neutro_con3)
                    .withValue(DatabaseManagerUser.Columnas.CAL_AP_CON3 ,e.cal_ap_con3)
                    .withValue(DatabaseManagerUser.Columnas.NHIL_CON3 ,e.nhil_con3)
                    .withValue(DatabaseManagerUser.Columnas.CON4 ,e.con4)
                    .withValue(DatabaseManagerUser.Columnas.CAL_FASE_CON4 ,e.cal_fase_con4)
                    .withValue(DatabaseManagerUser.Columnas.CAL_NEUTRO_CON4 ,e.cal_neutro_con4)
                    .withValue(DatabaseManagerUser.Columnas.CAL_AP_CON4 ,e.cal_ap_con4)
                    .withValue(DatabaseManagerUser.Columnas.NHIL_CON4 ,e.nhil_con4)
                    .withValue(DatabaseManagerUser.Columnas.FECH_LEVANT_NODO ,e.fech_levant_nodo)
                    .withValue(DatabaseManagerUser.Columnas.OBSERVACION_NODO ,e.observacion_nodo)
                    .withValue(DatabaseManagerUser.Columnas.NRO_ACOMETIDAS ,e.nro_acometidas)
                    .withValue(DatabaseManagerUser.Columnas.ESPACIO_N2 ,e.espacio_poste_acometida)
                    .withValue(DatabaseManagerUser.Columnas.ACOMETIDA_CORRESP ,e.nro_acometida_corresp)
                    .withValue(DatabaseManagerUser.Columnas.NIU,e.niu)
                    .withValue(DatabaseManagerUser.Columnas.ID_NODO_ACOMETIDA,e.direccion)
                    .withValue(DatabaseManagerUser.Columnas.DIRECCION,e.cont_ante)
                    .withValue(DatabaseManagerUser.Columnas.CONT_ANTE,e.cont_act)
                    .withValue(DatabaseManagerUser.Columnas.CONT_ACT,e.id_nodo_usu)
                    .withValue(DatabaseManagerUser.Columnas.CONT_MARCA,e.cont_marca)
                    .withValue(DatabaseManagerUser.Columnas.LATI_USU ,e.lati_usu)
                    .withValue(DatabaseManagerUser.Columnas.LONG_USU ,e.long_usu)
                    .withValue(DatabaseManagerUser.Columnas.ALTI_USU ,e.alti_usu)
                    .withValue(DatabaseManagerUser.Columnas.TIP_CA,e.tip_ca)
                    .withValue(DatabaseManagerUser.Columnas.TIP_AS,e.tip_as)
                    .withValue(DatabaseManagerUser.Columnas.TIP_MBT,e.tip_mbt)
                    .withValue(DatabaseManagerUser.Columnas.TIP_RST,e.tip_rst)
                    .withValue(DatabaseManagerUser.Columnas.CALIBRE,e.calibre)
                    .withValue(DatabaseManagerUser.Columnas.TABLERO,e.tablero)
                    .withValue(DatabaseManagerUser.Columnas.FAS_TABLERO,e.fas_tablero)
                    .withValue(DatabaseManagerUser.Columnas.TIP_SERVICIO,e.tip_servicio)
                    .withValue(DatabaseManagerUser.Columnas.OBSERVACION_USU,e.observacion_usu)
                    .withValue(DatabaseManagerUser.Columnas.FECH_LEVANT_USU,e.fech_levant_usu)
                    .withValue(DatabaseManagerUser.Columnas.USU_LEVANT_USU,e.usu_levant_usu)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                resolver.applyBatch(ContractParaG.AUTHORITY, ops);
            } catch (RemoteException | OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractParaG.CONTENT_URI,
                    null,
                    false);
            Log.i(TAG, "Sincronización finalizada.");

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }

    }

    /**
     * Inicia manualmente la sincronización
     *
     * @param context    Contexto para crear la petición de sincronización
     * @param onlyUpload Usa true para sincronizar el servidor o false para sincronizar el cliente
     */
    public static void sincronizarAhora( Context context, boolean onlyUpload) {
        Log.i(TAG, "Realizando petición de sincronización manual.");
        Bundle bundle = new Bundle();
        bundle.putBoolean( ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean( ContentResolver.SYNC_EXTRAS_MANUAL, true);
        if (onlyUpload)
            bundle.putBoolean( ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        ContentResolver.requestSync(obtenerCuentaASincronizar(context),
                context.getString(R.string.provider_authority), bundle);

    }

    /**
     * Crea u obtiene una cuenta existente
     *
     * @param context Contexto para acceder al administrador de cuentas
     * @return cuenta auxiliar.
     */
    public static Account obtenerCuentaASincronizar( Context context) {
        // Obtener instancia del administrador de cuentas
        AccountManager accountManager =
                (AccountManager) context.getSystemService( Context.ACCOUNT_SERVICE);

        // Crear cuenta por defecto
        Account newAccount = new Account(
                context.getString(R.string.app_name), Constantes.ACCOUNT_TYPE);

        // Comprobar existencia de la cuenta
        if (null == accountManager.getPassword(newAccount)) {

            // Añadir la cuenta al account manager sin password y sin datos de usuario
            if (!accountManager.addAccountExplicitly(newAccount, "", null))
                return null;
        }
        Log.i(TAG, "Cuenta de usuario obtenida.");
        return newAccount;
    }

}