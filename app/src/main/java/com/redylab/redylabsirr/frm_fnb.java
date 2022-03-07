package com.redylab.redylabsirr;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.Utilidades.Constantes;
import com.redylab.redylabsirr.Utilidades.Utilidades;
import com.redylab.redylabsirr.adaptadores.ContractParaG;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.sync.SyncAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class frm_fnb extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    private static final String TAG = "frm_fnd";
    RGps gps;
    EditText cmp_conexion1,cmp_conexion2,cmp_conexion3,cmp_conexion4;
    Spinner cmp_calibrefase1,cmp_calibreneutro1,cmp_calibreap1,cmp_calibrefase2,cmp_calibreneutro2,cmp_calibreap2,cmp_calibrefase3,cmp_calibreneutro3,cmp_calibreap3,cmp_calibrefase4,cmp_calibreneutro4,cmp_calibreap4;
    Button botonnodo;
    TextView tvcodigo,tvcodigo2,tv_operario;
    String selectionnodo1,vcodigo,vcodigo2,cp_altura,cp_material,cp_estr1,cp_tip1,cp_estr2,cp_tip2,cp_estr3,cp_tip3,cp_estr4,cp_tip4,cp_retenida,cp_antiguedad,cp_propiedadnodo;
    String cmp_latitud_nodo,cmp_longitu_nodo,cmp_altitud_nodo;
    String f_ch1pR="",f_ch1pT="",f_ch1pS="",f_ch2pR="",f_ch2pS="",f_ch2pT="",resul_chp1="",f_ch3pR="",f_ch3pT="",f_ch3pS="",f_ch4pR="",f_ch4pS="",f_ch4pT="",resul_chp2="",resul_chp3="",resul_chp4="";
    CheckBox cmp_ch_fsrp1,cmp_ch_fstp1,cmp_ch_fssp1,cmp_ch_fsrp2,cmp_ch_fstp2,cmp_ch_fssp2,cmp_ch_fsrp3,cmp_ch_fstp3,cmp_ch_fssp3,cmp_ch_fsrp4,cmp_ch_fstp4,cmp_ch_fssp4;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.frm_fnd );
        //
        //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        } else {
            locationStart();
        }
        //
        //
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            vcodigo = extras.getString( "codigotrafo" );
            vcodigo2 = extras.getString( "codigoposte" );
            cp_altura = extras.getString( "alturaposte" );
            cp_material = extras.getString( "materialposte" );
            cp_estr1 = extras.getString( "estructura1" );
            cp_tip1 = extras.getString( "tipo1" );
            cp_estr2 = extras.getString( "estructura2" );
            cp_tip2 = extras.getString( "tipo2" );
            cp_estr3 = extras.getString( "estructura3" );
            cp_tip3 = extras.getString( "tipo3" );
            cp_estr4 = extras.getString( "estructura4" );
            cp_tip4 = extras.getString( "tipo4" );
            cp_retenida = extras.getString( "retenidas" );
            cp_antiguedad = extras.getString( "antiguedad" );
            cp_propiedadnodo = extras.getString( "propiedad_nodo" );
        }
        // capturar el codigo del transformador  y poste
        tvcodigo = findViewById( R.id.codigoTrafo );//
        tvcodigo2 = findViewById( R.id.codPost );
        tvcodigo.setText( vcodigo );
        tvcodigo2.setText( vcodigo2 );
        selectionnodo1="NINGUNA";
        //InputMethodManager imm = (InputMethodManager)getSystemService(frm_nodos.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        //
        //
        //
        operarios();
        //
        //
        //conexion1
        cmp_conexion1 = findViewById( R.id.conexion1 );
        //
        //
        //calibrefase1
        cmp_calibrefase1 = findViewById( R.id.calibrefase1 );
        //
        //
        //
        //calibreneutro1
        cmp_calibreneutro1 = findViewById( R.id.calibreneutro1 );
        //
        //
        //
        //calibreap1
        cmp_calibreap1 = findViewById( R.id.calibrealpu1 );
        //
        //
        //
        //fases
        //chexbox
        cmp_ch_fsrp1 =(CheckBox) findViewById( R.id.ch_faserp1 );
        cmp_ch_fstp1 =(CheckBox) findViewById( R.id.ch_fasetp1 );
        cmp_ch_fssp1 =(CheckBox) findViewById( R.id.ch_fasesp1 );
        //
        //
        //conexion2
        cmp_conexion2 = findViewById( R.id.conexion2 );
        //
        //
        //calibrefase2
        cmp_calibrefase2 = findViewById( R.id.calibrefase2 );
        //
        //
        //calibreneutro2
        cmp_calibreneutro2 = findViewById( R.id.calibreneutro2 );
        //
        //
        //calibreap2
        cmp_calibreap2 = findViewById( R.id.calibrealpu2 );
        //
        //
        //fases2
        //chexbox
        cmp_ch_fsrp2 =(CheckBox) findViewById( R.id.ch_faserp2 );
        cmp_ch_fstp2 =(CheckBox) findViewById( R.id.ch_fasetp2 );
        cmp_ch_fssp2 =(CheckBox) findViewById( R.id.ch_fasesp2 );
        //
        //
        //
        //conexion3
        cmp_conexion3 = findViewById( R.id.conexion3 );
        //
        //
        // calibrefase3
        cmp_calibrefase3 = findViewById( R.id.calibrefase3 );
        //
        //
        //calibreneutro3
        cmp_calibreneutro3 = findViewById( R.id.calibreneutro3 );
        //
        //
        //calibreap3
        cmp_calibreap3 = findViewById( R.id.calibrealpu3 );
        //
        //
        //fases3
        cmp_ch_fsrp3 =(CheckBox) findViewById( R.id.ch_faserp3 );
        cmp_ch_fstp3 =(CheckBox) findViewById( R.id.ch_fasetp3 );
        cmp_ch_fssp3 =(CheckBox) findViewById( R.id.ch_fasesp3 );
        //
        //
        //conexion4
        cmp_conexion4 = findViewById( R.id.conexion4 );
        //
        //
        //calibrefase4
        cmp_calibrefase4 = findViewById( R.id.calibrefase4 );
        //
        //
        //calibreneutro4
        cmp_calibreneutro4 = findViewById( R.id.calibreneutro4 );
        //
        //
        //calibreap4
        cmp_calibreap4 = findViewById( R.id.calibrealpu4 );
        //
        //
        //fases4
        cmp_ch_fsrp4 =(CheckBox) findViewById( R.id.ch_faserp4 );
        cmp_ch_fstp4 =(CheckBox) findViewById( R.id.ch_fasetp4 );
        cmp_ch_fssp4 =(CheckBox) findViewById( R.id.ch_fasesp4 );
        //
        //
        //observaciones nodos
        String[] array ={"NINGUNA",
                "RETENIDA AVERIADA",
                "NO TIENE TERMINAL DE PUESTA A TIERRA",
                "POSTE AVERIADO",
                "AISLADORES AVERIADOS",
                "REDES EMPALMADAS"};
        MultiSelectionSpinner cmp_obs_nodos = (MultiSelectionSpinner) findViewById(R.id.obsnodo);
        cmp_obs_nodos.setItems(array);
        //cmp_observa.setSelection(new int[]{2, 6});
        cmp_obs_nodos.setListener(this);
        //
        //
       //boton registro
        botonnodo = findViewById( R.id.btnGuardarnodo );
        botonnodo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                if (cmp_conexion1.getText().toString().trim().isEmpty()) {
                    Toast.makeText( getBaseContext(), "Error! Completar la Conexión Con el Poste", Toast.LENGTH_LONG ).show();
                } else if (cmp_calibrefase1.getSelectedItem().toString().trim().equals( "SELECC" )) {
                    Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Fase de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                } else if (cmp_calibreneutro1.getSelectedItem().toString().trim().equals( "SELECC" )) {
                    Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Neutro de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                } else if (cmp_calibreap1.getSelectedItem().toString().trim().equals( "SELECC" )) {
                    Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de AlumbradoPublico de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                } else if(cmp_ch_fsrp1.isChecked() || cmp_ch_fstp1.isChecked() || cmp_ch_fssp1.isChecked() ) {
                    if (cmp_conexion2.getText().toString().trim().isEmpty() && cmp_calibrefase2.getSelectedItem().toString().trim().equals( "SELECC" ) && (cmp_calibreneutro2.getSelectedItem().toString().trim().equals( "SELECC" ) && cmp_calibreap2.getSelectedItem().toString().trim().equals( "SELECC" ) && (!cmp_ch_fsrp2.isChecked() && !cmp_ch_fstp2.isChecked() && !cmp_ch_fssp2.isChecked()))) {

                        if (cmp_conexion3.getText().toString().trim().isEmpty() && cmp_calibrefase3.getSelectedItem().toString().trim().equals( "SELECC" ) && (cmp_calibreneutro3.getSelectedItem().toString().trim().equals( "SELECC" ) && cmp_calibreap3.getSelectedItem().toString().trim().equals( "SELECC" ) && (!cmp_ch_fsrp3.isChecked() && !cmp_ch_fstp3.isChecked() && !cmp_ch_fssp3.isChecked()))) {

                            if (cmp_conexion4.getText().toString().trim().isEmpty() && cmp_calibrefase4.getSelectedItem().toString().trim().equals( "SELECC" ) && (cmp_calibreneutro4.getSelectedItem().toString().trim().equals( "SELECC" ) && cmp_calibreap4.getSelectedItem().toString().trim().equals( "SELECC" ) && (!cmp_ch_fsrp4.isChecked() && !cmp_ch_fstp4.isChecked() && !cmp_ch_fssp4.isChecked()))) {

                                //Toast.makeText( getBaseContext(), "Guardar4v", Toast.LENGTH_LONG ).show();
                                registarPosteb();

                            } else if (cmp_calibrefase4.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Fase de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                            } else if (cmp_calibreneutro4.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Neutro de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                            } else if (cmp_calibreap4.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de AlumbradoPublico de la Conexión del Poste4", Toast.LENGTH_LONG ).show();
                            } else if (((cmp_ch_fsrp4.isChecked()) || (cmp_ch_fstp4.isChecked()) || (cmp_ch_fssp4.isChecked()))) {

                                if (cmp_conexion4.getText().toString().trim().isEmpty()) {
                                    Toast.makeText( getBaseContext(), "Error! Completar la Conexión Con el Poste", Toast.LENGTH_LONG ).show();
                                } else {
                                    //Toast.makeText( getBaseContext(), "Guardar4", Toast.LENGTH_LONG ).show();
                                    registarPosteb();
                                }
                            }else{
                                Toast.makeText( getBaseContext(), "Error! Seleccione la Secuencia de la Conexión del Poste4", Toast.LENGTH_LONG ).show();
                            }

                        } else if (cmp_calibrefase3.getSelectedItem().toString().trim().equals( "SELECC" )) {
                            Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Fase de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                        } else if (cmp_calibreneutro3.getSelectedItem().toString().trim().equals( "SELECC" )) {
                            Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Neutro de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                        } else if (cmp_calibreap3.getSelectedItem().toString().trim().equals( "SELECC" )) {
                            Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de AlumbradoPublico de la Conexión del Poste3", Toast.LENGTH_LONG ).show();
                        } else if (cmp_ch_fsrp3.isChecked() || cmp_ch_fstp3.isChecked() || cmp_ch_fssp3.isChecked()) {
                            if (cmp_conexion3.getText().toString().trim().isEmpty()) {
                                Toast.makeText( getBaseContext(), "Error! Completar la Conexión Con el Poste", Toast.LENGTH_LONG ).show();
                            } else {
                                //Toast.makeText( getBaseContext(), "Guardar3", Toast.LENGTH_LONG ).show();
                                registarPosteb();
                            }
                        } else{
                            Toast.makeText( getBaseContext(), "Error! Seleccione la Secuencia de la Conexión del Poste3", Toast.LENGTH_LONG ).show();
                        }

                    } else if (cmp_calibrefase2.getSelectedItem().toString().trim().equals( "SELECC" )) {
                        Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Fase de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                    } else if (cmp_calibreneutro2.getSelectedItem().toString().trim().equals( "SELECC" )) {
                        Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Neutro de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                    } else if (cmp_calibreap2.getSelectedItem().toString().trim().equals( "SELECC" )) {
                        Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de AlumbradoPublico de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                    } else if (cmp_ch_fsrp2.isChecked() || cmp_ch_fstp2.isChecked() || cmp_ch_fssp2.isChecked()) {
                        if (cmp_conexion2.getText().toString().trim().isEmpty()) {
                            Toast.makeText( getBaseContext(), "Error! Completar la Conexión Con el Poste", Toast.LENGTH_LONG ).show();
                        } else {
                            if (cmp_conexion3.getText().toString().trim().isEmpty() && cmp_calibrefase3.getSelectedItem().toString().trim().equals( "SELECC" ) && (cmp_calibreneutro3.getSelectedItem().toString().trim().equals( "SELECC" ) && cmp_calibreap3.getSelectedItem().toString().trim().equals( "SELECC" ) && (!cmp_ch_fsrp3.isChecked() && !cmp_ch_fstp3.isChecked() && !cmp_ch_fssp3.isChecked()))) {
                                if (cmp_conexion4.getText().toString().trim().isEmpty() && cmp_calibrefase4.getSelectedItem().toString().trim().equals( "SELECC" ) && (cmp_calibreneutro4.getSelectedItem().toString().trim().equals( "SELECC" ) && cmp_calibreap4.getSelectedItem().toString().trim().equals( "SELECC" ) && (!cmp_ch_fsrp4.isChecked() && !cmp_ch_fstp4.isChecked() && !cmp_ch_fssp4.isChecked()))) {
                                    //Toast.makeText( getBaseContext(), "Guardar4v", Toast.LENGTH_LONG ).show();
                                    registarPosteb();

                                } else if (cmp_calibrefase4.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                    Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Fase de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                                } else if (cmp_calibreneutro4.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                    Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Neutro de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                                } else if (cmp_calibreap4.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                    Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de AlumbradoPublico de la Conexión del Poste4", Toast.LENGTH_LONG ).show();
                                } else if (((cmp_ch_fsrp4.isChecked()) || (cmp_ch_fstp4.isChecked()) || (cmp_ch_fssp4.isChecked()))) {

                                    if (cmp_conexion4.getText().toString().trim().isEmpty()) {
                                        Toast.makeText( getBaseContext(), "Error! Completar la Conexión Con el Poste", Toast.LENGTH_LONG ).show();
                                    } else {
                                        //Toast.makeText( getBaseContext(), "Guardar4", Toast.LENGTH_LONG ).show();
                                        registarPosteb();
                                    }
                                }else{
                                    Toast.makeText( getBaseContext(), "Error! Seleccione la Secuencia de la Conexión del Poste4", Toast.LENGTH_LONG ).show();
                                }

                            } else if (cmp_calibrefase3.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Fase de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                            } else if (cmp_calibreneutro3.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Neutro de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                            } else if (cmp_calibreap3.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de AlumbradoPublico de la Conexión del Poste3", Toast.LENGTH_LONG ).show();
                            } else if (cmp_ch_fsrp3.isChecked() || cmp_ch_fstp3.isChecked() || cmp_ch_fssp3.isChecked()) {
                                if (cmp_conexion3.getText().toString().trim().isEmpty()) {
                                    Toast.makeText( getBaseContext(), "Error! Completar la Conexión Con el Poste", Toast.LENGTH_LONG ).show();
                                } else {
                                    if (cmp_conexion4.getText().toString().trim().isEmpty() && cmp_calibrefase4.getSelectedItem().toString().trim().equals( "SELECC" ) && (cmp_calibreneutro4.getSelectedItem().toString().trim().equals( "SELECC" ) && cmp_calibreap4.getSelectedItem().toString().trim().equals( "SELECC" ) && (!cmp_ch_fsrp4.isChecked() && !cmp_ch_fstp4.isChecked() && !cmp_ch_fssp4.isChecked()))) {
                                        //Toast.makeText( getBaseContext(), "Guardar4v", Toast.LENGTH_LONG ).show();
                                        registarPosteb();

                                    } else if (cmp_calibrefase4.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                        Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Fase de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                                    } else if (cmp_calibreneutro4.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                        Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de Neutro de la Conexión del Poste", Toast.LENGTH_LONG ).show();
                                    } else if (cmp_calibreap4.getSelectedItem().toString().trim().equals( "SELECC" )) {
                                        Toast.makeText( getBaseContext(), "Error! Seleccione la Calibre de AlumbradoPublico de la Conexión del Poste4", Toast.LENGTH_LONG ).show();
                                    } else if (((cmp_ch_fsrp4.isChecked()) || (cmp_ch_fstp4.isChecked()) || (cmp_ch_fssp4.isChecked()))) {

                                        if (cmp_conexion4.getText().toString().trim().isEmpty()) {
                                            Toast.makeText( getBaseContext(), "Error! Completar la Conexión Con el Poste", Toast.LENGTH_LONG ).show();
                                        } else {
                                            //Toast.makeText( getBaseContext(), "Guardar4", Toast.LENGTH_LONG ).show();
                                            registarPosteb();
                                        }
                                    }else{
                                        Toast.makeText( getBaseContext(), "Error! Seleccione la Secuencia de la Conexión del Poste4", Toast.LENGTH_LONG ).show();
                                    }
                                }
                            } else{
                                Toast.makeText( getBaseContext(), "Error! Seleccione la Secuencia de la Conexión del Poste3", Toast.LENGTH_LONG ).show();
                            }
                        }
                    } else{
                        Toast.makeText( getBaseContext(), "Error! Seleccione la Secuencia de la Conexión del Poste2", Toast.LENGTH_LONG ).show();
                    }

                }else{
                    Toast.makeText(getBaseContext(),"Error! Seleccione la Secuencia de la Conexión del Poste1 ",Toast.LENGTH_SHORT).show();
                }
            }
        } );
        //
        //
        //
    }
    //
    //
    //
    private void operarios(){
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
        //Trafo traf=null;
        Cursor cursoroperario = databaseManager.getDb().rawQuery( "select distinct(usu_levant_usu) from trafo where length(usu_levant_usu) >1 and (id_trafo_nodo='"+vcodigo+"' OR nomb_trafo='"+vcodigo+"')", null);
        while(cursoroperario.moveToNext()) {
            String resul_operario = cursoroperario.getString( 0 );
            tv_operario = findViewById( R.id.operario );
            tv_operario.setText( resul_operario );
        }
        cursoroperario.close();
        databaseManager.getDb().close();
    }

    //colocar en todas las actividades boton de atras
    @Override
    public boolean onSupportNavigateUp() {
        // onBackPressed();
        finish();
        return false;
    }
    //
    //
    // ESTA FUNCION SE AGREGO PARA HACER MAS FACIL LAS COORDENADAS //
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        frm_fnb.Localizacion Local = new frm_fnb.Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    // mensaje2.setText(DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void selectedIndices( List <Integer> indices ) {

    }

    @Override
    public void selectedStrings( List <String> strings ) {
        String valobser;
        valobser = strings.toString().replaceAll( ",","-" );
        selectionnodo1 = valobser;
    }

    /* Aqui empieza la Clase Localizacion */
    class Localizacion implements LocationListener {
        mainActivity frm_fnb;
        public mainActivity getMainActivity() {
            return frm_fnb;
        }

        public void setMainActivity(mainActivity mainActivity) {
            this.frm_fnb = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();
            loc.getAltitude();
            loc.setAccuracy( 1 );
            loc.getAccuracy();

            String Textxx = "Lat = "+ loc.getLatitude() + "n Long = " + loc.getLongitude()+"n Alt = "+loc.getAltitude()+"n Pre = "+loc.getAccuracy();
            Log.i( TAG, "seleccion5 : " + Textxx);
            // String Text = "Lat = "+ loc.getLatitude() + "n Long = " + loc.getLongitude()+"n Alt = "+loc.getAltitude()+"n Pre = "+loc.getAccuracy();
            cmp_latitud_nodo = ""+loc.getLatitude();
            cmp_longitu_nodo = ""+loc.getLongitude();
            cmp_altitud_nodo = ""+loc.getAltitude();

            // mensaje1.setText(Text);
            //this.InsertActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            // mensaje1.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            // mensaje1.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
        public void setMainActivity( frm_fnb frm_fnb ) {
        }
    }
    //
    private void registarPosteb() {
        String observacion_poste ="";
        if (selectionnodo1 != null) {
            //Log.i( TAG, "seleccion5 : " + selectionnodo1);
            int sx = selectionnodo1.indexOf( "[" );
            if (sx < 0) {
                observacion_poste = selectionnodo1.trim();
            } else {
                observacion_poste = selectionnodo1.replace( "]", "" ).replace( "[", "" );
            }
        } else {
            //selection5 = "NINGUNA";
            observacion_poste ="NINGUNA";
        }
        if(cmp_conexion2.getText().toString().trim().isEmpty()){
            cmp_conexion2.setText( "0" );
        }
        if(cmp_conexion3.getText().toString().trim().isEmpty()){
            cmp_conexion3.setText( "0" );
        }
        if(cmp_conexion4.getText().toString().trim().isEmpty()) {
            cmp_conexion4.setText( "0" );
        }
        String nomb_trafoText = "0";
        String capacidadText = "0";
        String zonaurText = "0";
        String mpioText = "0";
        String propiedad_trafoText = "0";
        String tipoText = "0";
        String lati_trafoText = String.valueOf("0");
        String long_trafoText = String.valueOf("0");
        String alt_trafoText = String.valueOf("0");
        String tipo_trafoText = "0";
        String observacion_trafoText = "0";
        String nro_postes_trafoText ="0";
        String espacio_n1Text = "N";
        String nro_poste_correspText = "1";
        String nomb_nodoText = tvcodigo2.getText().toString();
        String id_trafo_nodoText = tvcodigo.getText().toString();
        String lati_nodoText = cmp_latitud_nodo;
        String long_nodoText = cmp_longitu_nodo;
        String alti_nodoText = cmp_altitud_nodo;
        String altura_nodoText = cp_altura;
        String material_nodoText = cp_material;
        String estruct_1_nodoText = cp_estr1;
        String tipo_1_estructText = cp_tip1;
        String estruct_2_nodoText = cp_estr2;
        String tipo_2_estructText = cp_tip2;
        String estruct_3_nodoText = cp_estr3;
        String tipo_3_estructText = cp_tip3;
        String estruct_4_nodoText = cp_estr4;
        String tipo_4_estructText = cp_tip4;
        String nr_retenText = cp_retenida;
        String antiguedadText = cp_antiguedad;
        String propiedadText = cp_propiedadnodo;
        String nro_acometText = "0";
        String con1Text = cmp_conexion1.getText().toString();
        String cal_fase_con1Text = cmp_calibrefase1.getSelectedItem().toString().trim();
        String cal_neutro_con1Text = cmp_calibreneutro1.getSelectedItem().toString().trim();
        String cal_ap_con1Text = cmp_calibreap1.getSelectedItem().toString().trim();
        String nhil_con1Text = resul_chp1.trim();
        String con2Text = cmp_conexion2.getText().toString();
        String cal_fase_con2Text = cmp_calibrefase2.getSelectedItem().toString().trim();
        String cal_neutro_con2Text = cmp_calibreneutro2.getSelectedItem().toString().trim();
        String cal_ap_con2Text = cmp_calibreap2.getSelectedItem().toString().trim();
        String nhil_con2Text = resul_chp2.trim();
        String con3Text = cmp_conexion3.getText().toString();
        String cal_fase_con3Text = cmp_calibrefase3.getSelectedItem().toString().trim();
        String cal_neutro_con3Text = cmp_calibreneutro3.getSelectedItem().toString().trim();
        String cal_ap_con3Text = cmp_calibreap3.getSelectedItem().toString().trim();
        String nhil_con3Text = resul_chp3.trim();
        String con4Text = cmp_conexion4.getText().toString();
        String cal_fase_con4Text = cmp_calibrefase4.getSelectedItem().toString().trim();
        String cal_neutro_con4Text = cmp_calibreneutro4.getSelectedItem().toString().trim();
        String cal_ap_con4Text = cmp_calibreap4.getSelectedItem().toString().trim();
        String nhil_con4Text = resul_chp4.trim();
        String observacion_nodoText = observacion_poste;
        String nro_acometidasText = "0";
        String espacio_n2Text = "0";
        String acometida_correspText = "0";
        String niuText = "0";
        String cont_actText = "0";
        String id_nodo_acometidaText = "0";
        String direccionText = "0";
        String cont_anteText = "0";
        String cont_marcaText = "0";
        String lati_usuText = String.valueOf("0");
        String long_usuText = String.valueOf("0");
        String alti_usuText = String.valueOf("0");
        String tip_caText = "0";
        String tip_asText = "0";
        String tip_mbtText = "0";
        String tip_rstText = "0";
        String calibreText = "0";
        String tableroText = "0";
        String fas_tableroText = "0";
        String tip_servicioText = "0";
        String observacion_usuText = "0";
        String usu_levant_usuText = tv_operario.getText().toString();

        ContentValues valuesno=new ContentValues();
        Log.i( TAG, "RETENIDA F: " + cp_retenida);

        //ingreso de datos del trafo
        valuesno.put( DatabaseManagerUser.Columnas.NOMB_TRAFO, nomb_trafoText);
        valuesno.put( DatabaseManagerUser.Columnas.CAPACIDAD, capacidadText);
        valuesno.put( DatabaseManagerUser.Columnas.ZONAUR, zonaurText);
        valuesno.put( DatabaseManagerUser.Columnas.MPIO, mpioText);
        valuesno.put( DatabaseManagerUser.Columnas.PROPIEDAD_TRAFO, propiedad_trafoText);
        valuesno.put( DatabaseManagerUser.Columnas.TIPO, tipoText);
        valuesno.put( DatabaseManagerUser.Columnas.LATI_TRAFO, lati_trafoText);
        valuesno.put( DatabaseManagerUser.Columnas.LONG_TRAFO, long_trafoText);
        valuesno.put( DatabaseManagerUser.Columnas.ALT_TRAFO, alt_trafoText);
        valuesno.put( DatabaseManagerUser.Columnas.TIPO_TRAFO, tipo_trafoText);
        valuesno.put( DatabaseManagerUser.Columnas.OBSERVACION_TRAFO, observacion_trafoText);
        valuesno.put( DatabaseManagerUser.Columnas.NRO_POSTES_TRAFO, nro_postes_trafoText);
        valuesno.put( DatabaseManagerUser.Columnas.ESPACIO_N1, espacio_n1Text);
        valuesno.put( DatabaseManagerUser.Columnas.NRO_POSTE_CORRESP, nro_poste_correspText);
        valuesno.put( DatabaseManagerUser.Columnas.NOMB_NODO, nomb_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.ID_TRAFO_NODO, id_trafo_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.LATI_NODO, lati_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.LONG_NODO, long_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.ALTI_NODO, alti_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.ALTURA_NODO, altura_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.MATERIAL_NODO, material_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.ESTRUCT_1_NODO, estruct_1_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.TIPO_1_ESTRUCT, tipo_1_estructText);
        valuesno.put( DatabaseManagerUser.Columnas.ESTRUCT_2_NODO, estruct_2_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.TIPO_2_ESTRUCT, tipo_2_estructText);
        valuesno.put( DatabaseManagerUser.Columnas.ESTRUCT_3_NODO, estruct_3_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.TIPO_3_ESTRUCT, tipo_3_estructText);
        valuesno.put( DatabaseManagerUser.Columnas.ESTRUCT_4_NODO, estruct_4_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.TIPO_4_ESTRUCT, tipo_4_estructText);
        valuesno.put( DatabaseManagerUser.Columnas.NR_RETEN, nr_retenText);
        valuesno.put( DatabaseManagerUser.Columnas.ANTIGUEDAD, antiguedadText);
        valuesno.put( DatabaseManagerUser.Columnas.PROPIEDAD, propiedadText);
        valuesno.put( DatabaseManagerUser.Columnas.NRO_ACOMET, nro_acometText);
        valuesno.put( DatabaseManagerUser.Columnas.CON1, con1Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_FASE_CON1, cal_fase_con1Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_NEUTRO_CON1, cal_neutro_con1Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_AP_CON1, cal_ap_con1Text);
        valuesno.put( DatabaseManagerUser.Columnas.NHIL_CON1, nhil_con1Text);
        valuesno.put( DatabaseManagerUser.Columnas.CON2, con2Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_FASE_CON2, cal_fase_con2Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_NEUTRO_CON2, cal_neutro_con2Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_AP_CON2, cal_ap_con2Text);
        valuesno.put( DatabaseManagerUser.Columnas.NHIL_CON2, nhil_con2Text);
        valuesno.put( DatabaseManagerUser.Columnas.CON3, con3Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_FASE_CON3, cal_fase_con3Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_NEUTRO_CON3, cal_neutro_con3Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_AP_CON3, cal_ap_con3Text);
        valuesno.put( DatabaseManagerUser.Columnas.NHIL_CON3, nhil_con3Text);
        valuesno.put( DatabaseManagerUser.Columnas.CON4, con4Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_FASE_CON4, cal_fase_con4Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_NEUTRO_CON4, cal_neutro_con4Text);
        valuesno.put( DatabaseManagerUser.Columnas.CAL_AP_CON4, cal_ap_con4Text);
        valuesno.put( DatabaseManagerUser.Columnas.NHIL_CON4, nhil_con4Text);
        valuesno.put( DatabaseManagerUser.Columnas.OBSERVACION_NODO, observacion_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.NRO_ACOMETIDAS, nro_acometidasText);
        valuesno.put( DatabaseManagerUser.Columnas.ESPACIO_N2, espacio_n2Text);
        valuesno.put( DatabaseManagerUser.Columnas.ACOMETIDA_CORRESP, acometida_correspText);
        valuesno.put( DatabaseManagerUser.Columnas.NIU, niuText);
        valuesno.put( DatabaseManagerUser.Columnas.CONT_ACT, cont_actText);
        valuesno.put( DatabaseManagerUser.Columnas.ID_NODO_ACOMETIDA, id_nodo_acometidaText);
        valuesno.put( DatabaseManagerUser.Columnas.DIRECCION, direccionText);
        valuesno.put( DatabaseManagerUser.Columnas.CONT_ANTE, cont_anteText);
        valuesno.put( DatabaseManagerUser.Columnas.CONT_MARCA, cont_marcaText);
        valuesno.put( DatabaseManagerUser.Columnas.LATI_USU, lati_usuText);
        valuesno.put( DatabaseManagerUser.Columnas.LONG_USU, long_usuText);
        valuesno.put( DatabaseManagerUser.Columnas.ALTI_USU, alti_usuText);
        valuesno.put( DatabaseManagerUser.Columnas.TIP_CA, tip_caText);
        valuesno.put( DatabaseManagerUser.Columnas.TIP_AS, tip_asText);
        valuesno.put( DatabaseManagerUser.Columnas.TIP_MBT, tip_mbtText);
        valuesno.put( DatabaseManagerUser.Columnas.TIP_RST, tip_rstText);
        valuesno.put( DatabaseManagerUser.Columnas.CALIBRE, calibreText);
        valuesno.put( DatabaseManagerUser.Columnas.TABLERO, tableroText);
        valuesno.put( DatabaseManagerUser.Columnas.FAS_TABLERO, fas_tableroText);
        valuesno.put( DatabaseManagerUser.Columnas.TIP_SERVICIO, tip_servicioText);
        valuesno.put( DatabaseManagerUser.Columnas.OBSERVACION_USU, observacion_usuText);
        valuesno.put( DatabaseManagerUser.Columnas.USU_LEVANT_USU, usu_levant_usuText);
        valuesno.put( Constantes.ESTADO, 0 );
        valuesno.put( DatabaseManagerUser.Columnas.PENDIENTE_INSERCION, 1 );

        getContentResolver().insert( ContractParaG.CONTENT_URI, valuesno);
        SyncAdapter.sincronizarAhora(this, true);

        if (Utilidades.materialDesign()) {
            finishAfterTransition();
        }else {finish();}

    }
    //
    public void chbox2( View view ) {
        //chexbox

        if (cmp_ch_fsrp1.isChecked()) {
            f_ch1pR = "R";
        } else {
            f_ch1pR = "";
        }

        if (cmp_ch_fstp1.isChecked()) {
            f_ch1pT = "T";
        } else {
            f_ch1pT = "";
        }

        if (cmp_ch_fssp1.isChecked()) {
            f_ch1pS = "S";
        } else {
            f_ch1pS = "";
        }
        //
        resul_chp1 = f_ch1pR + f_ch1pT + f_ch1pS;
        //
    }
    public void chbox3( View view ) {
        if(cmp_ch_fsrp2.isChecked()){
            f_ch2pR="R";
        }else{
            f_ch2pR="";
        }
        if(cmp_ch_fssp2.isChecked()){
            f_ch2pS="S";
        }else{
            f_ch2pS="";
        }
        if(cmp_ch_fstp2.isChecked()){
            f_ch2pT="T";
        }else{
            f_ch2pT="";
        }
        //
        resul_chp2=f_ch2pR+f_ch2pT+f_ch2pS;
        //
    }
    //
    public void chbox4( View view ) {
        if(cmp_ch_fsrp3.isChecked()){
            f_ch3pR="R";
        }else{
            f_ch3pR="";
        }
        if(cmp_ch_fssp3.isChecked()){
            f_ch3pS="S";
        }else{
            f_ch3pS="";
        }
        if(cmp_ch_fstp3.isChecked()){
            f_ch3pT="T";
        }else{
            f_ch3pT="";
        }
        //
        resul_chp3=f_ch3pR+f_ch3pT+f_ch3pS;
        //
    }
    //
    public void chbox5( View view ) {
        if(cmp_ch_fsrp4.isChecked()){
            f_ch4pR="R";
        }else{
            f_ch4pR="";
        }
        if(cmp_ch_fssp4.isChecked()){
            f_ch4pS="S";
        }else{
            f_ch4pS="";
        }
        if(cmp_ch_fstp4.isChecked()){
            f_ch4pT="T";
        }else{
            f_ch4pT="";
        }
        //
        resul_chp4=f_ch4pR+f_ch4pT+f_ch4pS;
        //
    }
    //

}
