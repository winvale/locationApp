package com.redylab.redylabsirr.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.redylab.redylabsirr.adaptadores.ContractParaG.TRAFO;
import static com.redylab.redylabsirr.database.DatabaseManagerUser.NTRAFOS;
import static com.redylab.redylabsirr.database.DatabaseManagerUser.USERST;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NOMBRE = "redylxke_sirr.db";

   // private static final String ENCODING = "UTF-8";

    public static int DB_SCHEME_VERSION = 4;

    public DatabaseHelper(Context context) {

        super(context, DB_NOMBRE, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // crea tabla usuario
        db.execSQL(DatabaseManagerUser.cmd4);
        db.execSQL(DatabaseManagerUser.cmd5);
        db.execSQL(DatabaseManagerUser.cmd7);
        db.execSQL(DatabaseManagerUser.in_ntrafos_mocoa);
        db.execSQL(DatabaseManagerUser.in_ntrafos_mocoa2);
        db.execSQL(DatabaseManagerUser.in_ntrafos_mocoa3);
        db.execSQL(DatabaseManagerUser.in_ntrafos_villa);
        db.execSQL(DatabaseManagerUser.in_ntrafos_villa2);
        db.execSQL(DatabaseManagerUser.in_ntrafos_villa3);
        db.execSQL(DatabaseManagerUser.in_ntrafos_guzman);
        db.execSQL(DatabaseManagerUser.in_ntrafos_guzman2);
        db.execSQL(DatabaseManagerUser.in_ntrafos_guzman3);
        db.execSQL(DatabaseManagerUser.in_ntrafos_piamonte);
        db.execSQL(DatabaseManagerUser.in_ntrafos_piamonte2);
        db.execSQL(DatabaseManagerUser.in_ntrafos_piamonte3);
        db.execSQL(DatabaseManagerUser.in_ntrafos_orito);
        db.execSQL(DatabaseManagerUser.in_ntrafos_orito2);
        db.execSQL(DatabaseManagerUser.in_ntrafos_orito3);
        db.execSQL(DatabaseManagerUser.up_trafo);
        db.execSQL(DatabaseManagerUser.in_usu);

    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + NTRAFOS);
            db.execSQL("DROP TABLE IF EXISTS " + USERST);
            db.execSQL("DROP TABLE IF EXISTS " + TRAFO);


        onCreate(db);
    }
}

