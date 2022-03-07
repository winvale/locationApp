package com.redylab.redylabsirr.adaptadores;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.redylab.redylabsirr.database.DatabaseHelper;
import com.redylab.redylabsirr.database.DatabaseManagerUser;

import static android.content.ContentValues.TAG;

/**
 * Content Provider personalizado
 */
public class Providerbd extends ContentProvider {
    /**
     * Nombre de la base de datos
     */
    //private static final String DATABASE_NAME = "redylxke_crunch_expenses.db";
    /**
     * Versión actual de la base de datos
     */
    //private static final int DATABASE_VERSION = 1;
    /**
     * Instancia global del Content Resolver
     */
    private ContentResolver resolver;
    /**
     * Instancia del administrador de BD
     */
    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        databaseHelper = new DatabaseHelper(getContext());

        resolver = getContext().getContentResolver();

        return true;
    }

    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        // Obtener base de datos
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Comparar Uri
        int match = ContractParaG.uriMatcher.match(uri);

        Cursor c;

        switch (match) {
            case ContractParaG.ALLROWS:
                // Consultando todos los registros
                c = db.query(ContractParaG.TRAFO, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                c.setNotificationUri(
                        resolver,
                        ContractParaG.CONTENT_URI);
                break;
            case ContractParaG.SINGLE_ROW:
                // Consultando un solo registro basado en el Id del Uri
                long idTrafo = ContentUris.parseId(uri);
                c = db.query(ContractParaG.TRAFO, projection,
                        DatabaseManagerUser.Columnas._ID + " = " + idTrafo,
                        selectionArgs, null, null, sortOrder);
                c.setNotificationUri(
                        resolver,
                        ContractParaG.CONTENT_URI);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        return c;

    }

    @Override
    public String getType( Uri uri) {
        switch (ContractParaG.uriMatcher.match(uri)) {
            case ContractParaG.ALLROWS:
                return ContractParaG.MULTIPLE_MIME;
            case ContractParaG.SINGLE_ROW:
                return ContractParaG.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de Registro desconocido: " + uri);
        }
    }

    @Override
    public Uri insert( Uri uri, ContentValues values) {
        // Validar la uri
        if (ContractParaG.uriMatcher.match(uri) != ContractParaG.ALLROWS) {
            throw new IllegalArgumentException("URI desconocida : " + uri);
        }
        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }

        // Inserción de nueva fila
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(ContractParaG.TRAFO, null, contentValues);
        if (rowId > 0) {
            Uri uri_gasto = ContentUris.withAppendedId(
                    ContractParaG.CONTENT_URI, rowId);
            resolver.notifyChange(uri_gasto, null, false);
            return uri_gasto;
        }
        throw new SQLException("Falla al insertar fila en : " + uri);
    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int match = ContractParaG.uriMatcher.match(uri);
        Log.i( TAG,"codigo"+match );
        int affected;

        switch (match) {
            case ContractParaG.ALLROWS:
                affected = db.delete( ContractParaG.TRAFO,
                        selection,
                        selectionArgs );
                break;
            case ContractParaG.SINGLE_ROW:
                long idTrafo = ContentUris.parseId( uri );
                affected = db.delete( ContractParaG.TRAFO,
                        DatabaseManagerUser.Columnas.ID_REMOTA + "=" + idTrafo
                                + (!TextUtils.isEmpty( selection ) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs );
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange( uri, null, false );
                break;
            case ContractParaG.SINGLE_ROW2:
                long idUsers = ContentUris.parseId( uri );
                affected = db.delete( ContractParaG.USERS,
                        DatabaseManagerUser.Columnas.ID_USUARIO + "=" + idUsers
                                + (!TextUtils.isEmpty( selection ) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs );
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange( uri, null, false );
                break;
            case ContractParaG.ALLROWS2:
                affected = db.delete( ContractParaG.USERS,
                        selection,
                        selectionArgs );
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange( uri, null, false );
                break;
            default:
                throw new IllegalArgumentException( "Elemento registro desconocido: " +
                        uri );
        }return affected;

    }
    
    
    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (ContractParaG.uriMatcher.match(uri)) {
            case ContractParaG.ALLROWS:
                affected = db.update(ContractParaG.TRAFO, values,
                        selection, selectionArgs);
                break;
            case ContractParaG.SINGLE_ROW:
                String idTrafo = uri.getPathSegments().get(1);
                affected = db.update(ContractParaG.TRAFO, values,
                        DatabaseManagerUser.Columnas.ID_REMOTA + "=" + idTrafo
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        resolver.notifyChange(uri, null, false);
        return affected;
    }

}

