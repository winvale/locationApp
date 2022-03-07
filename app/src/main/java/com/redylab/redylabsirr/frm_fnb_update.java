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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class frm_fnb_update extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    private static final String TAG = "frm_fnd_update";
    RGps gps;
    EditText cmp_conexion1,cmp_conexion2,cmp_conexion3,cmp_conexion4;
    Spinner cmp_calibrefase1,cmp_calibreneutro1,cmp_calibreap1,cmp_calibrefase2,cmp_calibreneutro2,cmp_calibreap2,cmp_calibrefase3,cmp_calibreneutro3,cmp_calibreap3,cmp_calibrefase4,cmp_calibreneutro4,cmp_calibreap4,cmp_hilo1,cmp_hilo2,cmp_hilo3,cmp_hilo4;
    Button botonnodo;
    TextView tvcodigo,tvcodigo2,tv_operario;
    String selectionnodo1,vcodigo,vcodigo2,cp_altura,cp_material,cp_observanodo,cp_estr1,cp_tip1,cp_estr2,cp_tip2,cp_estr3,cp_tip3,cp_estr4,cp_tip4,cp_retenida,cp_antiguedad,cp_propiedadnodo;
    String cp_vconn1,cp_vclfs1,cp_vclne1,cp_vclap1,cp_vf1,cp_vconn2,cp_vclfs2,cp_vclne2,cp_vclap2,cp_vf2,cp_vconn3, cp_vclfs3,cp_vclne3 ,cp_vclap3,cp_vf3,cp_vconn4, cp_vclfs4, cp_vclne4,cp_vclap4,cp_vf4,IDENT;
    String f_ch1pR="",f_ch1pT="",f_ch1pS="",f_ch2pR="",f_ch2pS="",f_ch2pT="",resul_chp1="",f_ch3pR="",f_ch3pT="",f_ch3pS="",f_ch4pR="",f_ch4pS="",f_ch4pT="",resul_chp2="",resul_chp3="",resul_chp4="";
    CheckBox cmp_ch_fsrp1,cmp_ch_fstp1,cmp_ch_fssp1,cmp_ch_fsrp2,cmp_ch_fstp2,cmp_ch_fssp2,cmp_ch_fsrp3,cmp_ch_fstp3,cmp_ch_fssp3,cmp_ch_fsrp4,cmp_ch_fstp4,cmp_ch_fssp4;
    double cmp_latitud_nodo,cmp_longitu_nodo,cmp_altitud_nodo;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.frm_fnd_update );

        //
        //

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            vcodigo = extras.getString( "codigotrafo" );
            vcodigo2 = extras.getString( "codigoposte" );
            cp_altura = extras.getString( "alturaposte" );
            cp_material = extras.getString( "materialposte" );
            cp_observanodo = extras.getString("vobsno");
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
            cp_vconn1 = extras.getString("vconn1");
            cp_vclfs1 = extras.getString("vclfs1");
            cp_vclne1 = extras.getString("vclne1");
            cp_vclap1 = extras.getString("vclap1");
            cp_vf1 = extras.getString("vnhil1");
            cp_vconn2 = extras.getString("vconn2");
            cp_vclfs2 = extras.getString("vclfs2");
            cp_vclne2 = extras.getString("vclne2");
            cp_vclap2 = extras.getString("vclap2");
            cp_vf2 = extras.getString("vnhil2");
            cp_vconn3 = extras.getString("vconn3");
            cp_vclfs3 = extras.getString("vclfs3");
            cp_vclne3 = extras.getString("vclne3");
            cp_vclap3 = extras.getString("vclap3");
            cp_vf3 = extras.getString("vnhil3");
            cp_vconn4 = extras.getString("vconn4");
            cp_vclfs4 = extras.getString("vclfs4");
            cp_vclne4 = extras.getString("vclne4");
            cp_vclap4 = extras.getString("vclap4");
            cp_vf4 = extras.getString("vnhil4");
            IDENT = extras.getString("IDENT");

        }
        Log.i("parametros","obervacion_nodo_final:"+cp_observanodo);

        // capturar el codigo del transformador  y poste
        tvcodigo = findViewById( R.id.codigoTrafo );//
        tvcodigo2 = findViewById( R.id.codPost );
        tvcodigo.setText( vcodigo );
        tvcodigo2.setText( vcodigo2 );
        selectionnodo1=cp_observanodo;
        //InputMethodManager imm = (InputMethodManager)getSystemService(frm_nodos.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        //
        //
        operarios();
        //
        //
        //conexion1
        cmp_conexion1 = findViewById( R.id.conexion1 );
        cmp_conexion1.setText(cp_vconn1);
                //.setText(Integer.valueOf( cp_vconn1));
        //
        //
        //calibrefase1
        cmp_calibrefase1 = findViewById( R.id.calibrefase1 );
        cmp_calibrefase1.setSelection(obtenerPosicionItem(cmp_calibrefase1,cp_vclfs1));
        //
        //
        //
        //calibreneutro1
        cmp_calibreneutro1 = findViewById( R.id.calibreneutro1 );
        cmp_calibreneutro1.setSelection(obtenerPosicionItem(cmp_calibreneutro1,cp_vclne1));
        //
        //
        //
        //calibreap1
        cmp_calibreap1 = findViewById( R.id.calibrealpu1 );
        cmp_calibreap1.setSelection(obtenerPosicionItem(cmp_calibreap1,cp_vclap1));
        //
        //
        //
        //fases1
        cmp_ch_fsrp1 =(CheckBox) findViewById( R.id.ch_faserp1 );
        cmp_ch_fstp1 =(CheckBox) findViewById( R.id.ch_fasetp1 );
        cmp_ch_fssp1 =(CheckBox) findViewById( R.id.ch_fasesp1 );
        //cmp_hilo1.setSelection(obtenerPosicionItem(cmp_hilo1,cp_vnhil1));
        //
        //
        //conexion2
        cmp_conexion2 = findViewById( R.id.conexion2 );
        cmp_conexion2.setText(cp_vconn2);
        //
        //
        //calibrefase2
        cmp_calibrefase2 = findViewById( R.id.calibrefase2 );
        cmp_calibrefase2.setSelection(obtenerPosicionItem(cmp_calibrefase2,cp_vclfs2));
        //
        //
        //calibreneutro2
        cmp_calibreneutro2 = findViewById( R.id.calibreneutro2 );
        cmp_calibreneutro2.setSelection(obtenerPosicionItem(cmp_calibreneutro2,cp_vclne2));
        //
        //
        //calibreap2
        cmp_calibreap2 = findViewById( R.id.calibrealpu2 );
        cmp_calibreap2.setSelection(obtenerPosicionItem(cmp_calibreap2,cp_vclap2));
        //
        //
        //fases2
        cmp_ch_fsrp2 =(CheckBox) findViewById( R.id.ch_faserp2 );
        cmp_ch_fstp2 =(CheckBox) findViewById( R.id.ch_fasetp2 );
        cmp_ch_fssp2 =(CheckBox) findViewById( R.id.ch_fasesp2 );
        //cmp_hilo2.setSelection(obtenerPosicionItem(cmp_hilo2,cp_vnhil2));
        //
        //
        //
        //conexion3
        cmp_conexion3 = findViewById( R.id.conexion3 );
        cmp_conexion3.setText(cp_vconn3);
        //
        //
        // calibrefase3
        cmp_calibrefase3 = findViewById( R.id.calibrefase3 );
        cmp_calibrefase3.setSelection(obtenerPosicionItem(cmp_calibrefase3,cp_vclfs3));
        //
        //
        //calibreneutro3
        cmp_calibreneutro3 = findViewById( R.id.calibreneutro3 );
        cmp_calibreneutro3.setSelection(obtenerPosicionItem(cmp_calibreneutro3,cp_vclne3));
        //
        //
        //calibreap3
        cmp_calibreap3 = findViewById( R.id.calibrealpu3 );
        cmp_calibreap3.setSelection(obtenerPosicionItem(cmp_calibreap3,cp_vclap3));
        //
        //
        //fases3
        cmp_ch_fsrp3 =(CheckBox) findViewById( R.id.ch_faserp3 );
        cmp_ch_fstp3 =(CheckBox) findViewById( R.id.ch_fasetp3 );
        cmp_ch_fssp3 =(CheckBox) findViewById( R.id.ch_fasesp3 );
        //cmp_hilo3.setSelection(obtenerPosicionItem(cmp_hilo3,cp_vnhil3));
        //
        //
        //conexion4
        cmp_conexion4 = findViewById( R.id.conexion4 );
        cmp_conexion4.setText(cp_vconn4);
        //
        //
        //calibrefase4
        cmp_calibrefase4 = findViewById( R.id.calibrefase4 );
        cmp_calibrefase4.setSelection(obtenerPosicionItem(cmp_calibrefase4,cp_vclfs4));
        //
        //
        //calibreneutro4
        cmp_calibreneutro4 = findViewById( R.id.calibreneutro4 );
        cmp_calibreneutro4.setSelection(obtenerPosicionItem(cmp_calibreneutro4,cp_vclne4));
        //
        //
        //calibreap4
        cmp_calibreap4 = findViewById( R.id.calibrealpu4 );
        cmp_calibreap4.setSelection(obtenerPosicionItem(cmp_calibreap4,cp_vclap4));
        //
        //
        //fases4
        cmp_ch_fsrp4 =(CheckBox) findViewById( R.id.ch_faserp4 );
        cmp_ch_fstp4 =(CheckBox) findViewById( R.id.ch_fasetp4 );
        cmp_ch_fssp4 =(CheckBox) findViewById( R.id.ch_fasesp4 );
        //cmp_hilo4.setSelection(obtenerPosicionItem(cmp_hilo4,cp_vnhil4));
        //
        //
        //observaciones nodos
        String[] arrayn ={"NINGUNA",
                "RETENIDA AVERIADA",
                "NO TIENE TERMINAL DE PUESTA A TIERRA",
                "POSTE AVERIADO",
                "AISLADORES AVERIADOS",
                "REDES EMPALMADAS"};
        MultiSelectionSpinner cmp_obs_nodos = (MultiSelectionSpinner) findViewById(R.id.obsnodo);
        cmp_obs_nodos.setItems(arrayn);
        //cmp_observa.setSelection(new int[]{2, 6});
        cmp_obs_nodos.setListener(this);
        String vob ="";
        String vobb ="";
        List<String> list = new ArrayList<>();
        //
        if (cp_observanodo.contains("-")) {
            // Hacer split.
            int cant=cp_observanodo.split("-").length;
            String[] parts = cp_observanodo.split("-");
            for ( int i = 0; i < cant; i++){
                vobb=String.valueOf( parts[i].toString().trim());
                for(int j = 0; j < 6; j++) {
                    vob = String.valueOf( arrayn[j].toString() );
                    if(vob.equals( vobb )) {
                        list.add(vob);

                    }
                }
            }
            //Log.i( "parametro cmp_observa: ", String.valueOf((list.toString().replace( "[","" ).replace( "]" ,""))) );
            cmp_obs_nodos.setSelection(list);

        } else {
            vobb = cp_observanodo;
            for (int j = 0; j < 6; j++) {
                vob = String.valueOf( arrayn[j].toString() );
                if (vob.equals( vobb )) {
                    list.add( vob );
                }
                cmp_obs_nodos.setSelection( list );
            }
        }
        //
        //
        //
        String opa1="",opa2="",opa3="",opa12="",opa22="",opa32="",opa13="",opa23="",opa33="",opa14="",opa24="",opa34="";
        //
        int vrtiprst1 = cp_vf1.length() ;
        int vrtiprst2 = cp_vf2.length() ;
        int vrtiprst3 = cp_vf3.length() ;
        int vrtiprst4 = cp_vf4.length() ;

        // condicion de chequeado para fases de lineas de baja o media tensión
        // 1 fase
        if( vrtiprst1 == 3){
            cmp_ch_fsrp1.setChecked( true );
            cmp_ch_fstp1.setChecked( true );
            cmp_ch_fssp1.setChecked( true );
            resul_chp1="RST";
        }else if(vrtiprst1 == 2) {
            String opc1p = cp_vf1.substring( 0,1 );
            String opc2p = cp_vf1.substring( 1,2 );

            if (opc1p.equals("R")){
                cmp_ch_fsrp1.setChecked( true );
                opa1="R";
            }else if(opc1p.equals("T")){
                cmp_ch_fstp1.setChecked( true );
                opa1="T";
            } else if(opc1p.equals("S")){
                cmp_ch_fssp1.setChecked( true );
                opa1="S";
            }
            if (opc2p.equals("R")){
                cmp_ch_fsrp1.setChecked( true );
                opa2="R";
            }else if(opc2p.equals("T")){
                cmp_ch_fstp1.setChecked( true );
                opa2="T";
            } else if(opc2p.equals("S")){
                cmp_ch_fssp1.setChecked( true );
                opa2="S";
            }
            resul_chp1=opa1+opa2;
        }else if(vrtiprst1 == 1){
            String opc3p = cp_vf1;
            if (opc3p.equals("R")){
                cmp_ch_fsrp1.setChecked( true );
                opa3="R";
            }else if(opc3p.equals("T")){
                cmp_ch_fstp1.setChecked( true );
                opa3="T";
            } else if(opc3p.equals("S")){
                cmp_ch_fssp1.setChecked( true );
                opa3="S";
            }
            resul_chp1=opa3;
        }
        //
        //
        // 2 fase
        if( vrtiprst2 == 3){
            cmp_ch_fsrp2.setChecked( true );
            cmp_ch_fstp2.setChecked( true );
            cmp_ch_fssp2.setChecked( true );
            resul_chp2="RST";
            Log.i( TAG, "revision_2pi3 : " + resul_chp2);
        }else if(vrtiprst2 == 2) {
            String opc1p2 = cp_vf2.substring( 0,1 );
            String opc2p2 = cp_vf2.substring( 1,2 );

            if (opc1p2.equals("R")){
                cmp_ch_fsrp2.setChecked( true );
                opa12="R";
            }else if(opc1p2.equals("T")){
                cmp_ch_fstp2.setChecked( true );
                opa12="T";
            } else if(opc1p2.equals("S")){
                cmp_ch_fssp2.setChecked( true );
                opa12="S";
            }
            if (opc2p2.equals("R")){
                cmp_ch_fsrp2.setChecked( true );
                opa22="R";
            }else if(opc2p2.equals("T")){
                cmp_ch_fstp2.setChecked( true );
                opa22="T";
            } else if(opc2p2.equals("S")){
                cmp_ch_fssp2.setChecked( true );
                opa22="S";
            }
            resul_chp2=opa12+opa22;
            Log.i( TAG, "revision_2pi2 : " + resul_chp2);
        }else if(vrtiprst2 == 1){
            String opc3p2 = cp_vf2;
            if (opc3p2.equals("R")){
                cmp_ch_fsrp2.setChecked( true );
                opa32="R";
            }else if(opc3p2.equals("T")){
                cmp_ch_fstp2.setChecked( true );
                opa32="T";
            } else if(opc3p2.equals("S")){
                cmp_ch_fssp2.setChecked( true );
                opa32="S";
            }
            resul_chp2=opa32;

            Log.i( TAG, "revision_2pi1 : " + resul_chp2);
        }
        //
        //
        // 3 fase
        if( vrtiprst3 == 3){
            cmp_ch_fsrp3.setChecked( true );
            cmp_ch_fstp3.setChecked( true );
            cmp_ch_fssp3.setChecked( true );
            resul_chp3="RST";
        }else if(vrtiprst3 == 2) {
            String opc1p3 = cp_vf3.substring( 0,1 );
            String opc2p3 = cp_vf3.substring( 1,2 );

            if (opc1p3.equals("R")){
                cmp_ch_fsrp3.setChecked( true );
                opa13="R";
            }else if(opc1p3.equals("T")){
                cmp_ch_fstp3.setChecked( true );
                opa13="T";
            } else if(opc1p3.equals("S")){
                cmp_ch_fssp3.setChecked( true );
                opa13="S";
            }
            if (opc2p3.equals("R")){
                cmp_ch_fsrp3.setChecked( true );
                opa23="R";
            }else if(opc2p3.equals("T")){
                cmp_ch_fstp3.setChecked( true );
                opa23="T";
            } else if(opc2p3.equals("S")){
                cmp_ch_fssp3.setChecked( true );
                opa23="S";
            }
            resul_chp3=opa13+opa23;
        }else if(vrtiprst3 == 1){
            String opc3p = cp_vf3;
            if (opc3p.equals("R")){
                cmp_ch_fsrp3.setChecked( true );
                opa33="R";
            }else if(opc3p.equals("T")){
                cmp_ch_fstp3.setChecked( true );
                opa33="T";
            } else if(opc3p.equals("S")){
                cmp_ch_fssp3.setChecked( true );
                opa33="S";
            }
            resul_chp3=opa33;
        }
        //
        //
        // 4 fase
        if( vrtiprst4 == 3){
            cmp_ch_fsrp4.setChecked( true );
            cmp_ch_fstp4.setChecked( true );
            cmp_ch_fssp4.setChecked( true );
            resul_chp4="RST";
        }else if(vrtiprst4 == 2) {
            String opc1p4 = cp_vf4.substring( 0,1 );
            String opc2p4 = cp_vf4.substring( 1,2 );

            if (opc1p4.equals("R")){
                cmp_ch_fsrp4.setChecked( true );
                opa14="R";
            }else if(opc1p4.equals("T")){
                cmp_ch_fstp4.setChecked( true );
                opa14="T";
            } else if(opc1p4.equals("S")){
                cmp_ch_fssp4.setChecked( true );
                opa14="S";
            }
            if (opc2p4.equals("R")){
                cmp_ch_fsrp4.setChecked( true );
                opa24="R";
            }else if(opc2p4.equals("T")){
                cmp_ch_fstp4.setChecked( true );
                opa24="T";
            } else if(opc2p4.equals("S")){
                cmp_ch_fssp4.setChecked( true );
                opa24="S";
            }
            resul_chp4=opa14+opa24;
        }else if(vrtiprst4 == 1){
            String opc4p = cp_vf4;
            if (opc4p.equals("R")){
                cmp_ch_fsrp4.setChecked( true );
                opa34="R";
            }else if(opc4p.equals("T")){
                cmp_ch_fstp4.setChecked( true );
                opa34="T";
            } else if(opc4p.equals("S")){
                cmp_ch_fssp4.setChecked( true );
                opa34="S";
            }
            resul_chp4=opa34;
        }
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
                                actualizarPosteb();

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
                                    actualizarPosteb();
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
                                actualizarPosteb();
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
                                    actualizarPosteb();

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
                                        actualizarPosteb();
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
                                        actualizarPosteb();

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
                                            actualizarPosteb();
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
    } // TERMINA EL ONCREATE
    //
    //
    //
    private void operarios(){
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
        //Trafo traf=null;
        Cursor cursoroperario = databaseManager.getDb().rawQuery( "select distinct(usu_levant_usu) from trafo where length(usu_levant_usu) >1 and nomb_trafo='"+vcodigo+"' OR id_trafo_nodo='"+vcodigo+"'", null);
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


    @Override
    public void selectedIndices( List <Integer> indices ) {

    }

    @Override
    public void selectedStrings( List <String> strings ) {
        String valobsern;
        valobsern = strings.toString().replaceAll( ",","-" ).replace( "[","" ).replace( "]","" );
        selectionnodo1 = valobsern;
    }

    //
    //=========================================================================================//
    //=================================MÉTODO QUE REALIZA LA TAREA=============================//
    //=========================================================================================//

    //Método para obtener la posición de un ítem del spinner
    public static int obtenerPosicionItem(Spinner spinner, String datobuscado) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String observacion`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(datobuscado)) {
                //spinner.setSelection( i );
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }
    //=========================================================================================//

    //
    private void actualizarPosteb() {
        Log.i( TAG, "revision_2p : " + resul_chp2);

        String observacion_poste ="";
        if (selectionnodo1 != null) {
           // Log.i( TAG, "seleccion5 : " + selectionnodo1);
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
        // Log.i( TAG, "seleccion5 : " + observacion_trafo);

        if(cmp_conexion2.getText().toString().trim().isEmpty()){
            cmp_conexion2.setText( "0" );
        }
        if(cmp_conexion3.getText().toString().trim().isEmpty()){
            cmp_conexion3.setText( "0" );
        }
        if(cmp_conexion4.getText().toString().trim().isEmpty()) {
            cmp_conexion4.setText( "0" );
        }

        String espacio_n1Text = "N";
        String nro_poste_correspText = "1";
        String nomb_nodoText = tvcodigo2.getText().toString();
        String id_trafo_nodoText = tvcodigo.getText().toString();
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
        String nro_acometText = "1";
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
        String usu_levant_usuText = IDENT;

        ContentValues valuesno=new ContentValues();

        //ingreso de datos del nodos

        valuesno.put( DatabaseManagerUser.Columnas.ESPACIO_N1, espacio_n1Text);
        valuesno.put( DatabaseManagerUser.Columnas.NRO_POSTE_CORRESP, nro_poste_correspText);
        valuesno.put( DatabaseManagerUser.Columnas.NOMB_NODO, nomb_nodoText);
        valuesno.put( DatabaseManagerUser.Columnas.ID_TRAFO_NODO, id_trafo_nodoText);
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
        valuesno.put( DatabaseManagerUser.Columnas.USU_LEVANT_USU, usu_levant_usuText);
        valuesno.put( Constantes.ESTADO, 0 );
        valuesno.put( DatabaseManagerUser.Columnas.PENDIENTE_INSERCION, 1 );

        getContentResolver().update( ContractParaG.CONTENT_URI, valuesno,DatabaseManagerUser.Columnas.NOMB_NODO + "=?" ,new String[]{tvcodigo2.getText().toString()} );
        SyncAdapter.sincronizarAhora(this, true);

        if (Utilidades.materialDesign()) {
            finishAfterTransition();
            Intent intent = new Intent( frm_fnb_update.this, mainActivity.class );
            Bundle miBundle = new Bundle();
            miBundle.putString( "IDENT",(IDENT) );
            intent.putExtras( miBundle );
            intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
            finish();
        }else {
            Intent intent = new Intent( frm_fnb_update.this, mainActivity.class );
            Bundle miBundle = new Bundle();
            miBundle.putString( "IDENT", ( IDENT ) );
            intent.putExtras( miBundle );
            intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
            finish();
        }

    }
    //
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
