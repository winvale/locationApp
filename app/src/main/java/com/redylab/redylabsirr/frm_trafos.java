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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.Utilidades.Utilidades;
import com.redylab.redylabsirr.adaptadores.ContractParaG;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Ntrafo;
import com.redylab.redylabsirr.MultiSelectionSpinner;
import com.redylab.redylabsirr.sync.SyncAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class frm_trafos extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    private static final String TAG = "frm_trafos";
    Spinner comboTrafos;
    ArrayList<String> listaTrafos;
    ArrayList<Ntrafo> trafosList;

    Integer position1,position2,position3,position4,position5,position6,nombre;
    String selection1,selection2,selection3,selection4,selection5,selection6,cmp_ubica,cmp_propiet,operario;
    RadioGroup rg1;
    EditText cmp_capacid,cmp_marcatf;
    RGps gps;

    Button botonRegistro;
    TextView datoperario, txtLo,textLa,textAl;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.frm_trafos );
        //
        txtLo =  this.findViewById(R.id.txtLontr);
        textLa =  this.findViewById(R.id.textLatitr);
        textAl =  this.findViewById(R.id.textAltitr);
        //
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            nombre = extras.getInt( "nombreOperario" );

        }
        //
        //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        } else {
            locationStart();
        }
        //
        //
        final Spinner spinnerMpio = (Spinner) this.findViewById(R.id.spMpio);

        //MUNICIPIOS PRUEBA -TRAFOS
        spinnerMpio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                /*Obtienes el item actualmente seleccionado*/
                 position1 = pos;
                 selection1 = adapterView.getItemAtPosition(position1).toString();

                if(position1 == 1){
                    consultarListaTrafos("MOCOA");

                    final Spinner comboTrafos = (Spinner) findViewById(R.id.cdtrafo);
                    ArrayAdapter<String>adaptadorTr = new ArrayAdapter <String>(getBaseContext(), R.layout.txtspinner, listaTrafos );
                    comboTrafos.setAdapter(adaptadorTr);
                }else if(position1 == 2){
                    final Spinner comboTrafos = (Spinner) findViewById(R.id.cdtrafo);
                    consultarListaTrafos("VILLAGARZON");
                    ArrayAdapter<String>adaptadorTr = new ArrayAdapter<String>( getBaseContext(), R.layout.txtspinner,listaTrafos );
                    comboTrafos.setAdapter(adaptadorTr);
                }else if(position1 == 3){
                    consultarListaTrafos("GUZMAN");
                    final Spinner comboTrafos = (Spinner) findViewById(R.id.cdtrafo);
                    ArrayAdapter<String>adaptadorTr = new ArrayAdapter<String>( getBaseContext(), R.layout.txtspinner,listaTrafos );
                    comboTrafos.setAdapter(adaptadorTr);
                }else if(position1 == 4){
                    consultarListaTrafos("PIAMONTE");
                    final Spinner comboTrafos = (Spinner) findViewById(R.id.cdtrafo);
                    ArrayAdapter<String>adaptadorTr = new ArrayAdapter<String>( getBaseContext(), R.layout.txtspinner,listaTrafos );
                    comboTrafos.setAdapter(adaptadorTr);
                }else if(position1 == 5){
                    consultarListaTrafos("ORITO");
                    final Spinner comboTrafos = (Spinner) findViewById(R.id.cdtrafo);
                    ArrayAdapter<String>adaptadorTr = new ArrayAdapter<String>( getBaseContext(), R.layout.txtspinner,listaTrafos );
                    comboTrafos.setAdapter(adaptadorTr);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //llamamos para spinner trafo
        //SpinnerDialog spinnerDialog;
        final Spinner comboTrafos = (Spinner) this.findViewById(R.id.cdtrafo);

        //"Seleccione ó Busque el TRANSFORMADOR"
        comboTrafos.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView <?> adapterView, View view, int pos2, long id ) {
                position2 = pos2;
                selection2 = adapterView.getItemAtPosition(position2).toString();
            }
            @Override
            public void onNothingSelected( AdapterView <?> parent ) { }
        } );

        //SpinnerDialog = new SpinnerDialog(this.findViewById(R.id.cdtrafo),AdapterView.OnItemSelectedListener(),"Seleccione ó Busque el TRANSFORMADOR","Cerrar Button Text");
        //spinnerDialog = new SpinnerDialog(this,items,"Seleccione ó busque el TRANSFORMADOR","Close Button Text");// With No Animation
        // = new SpinnerDialog(MainActivity.this,items,"Select or Search City",R.style.DialogAnimations_SmileWindow,"Close Button Text");// With 	Animation


        //
        //
        //ubicacion urbana o rural
        rg1 =(RadioGroup) this.findViewById(R.id.rgroup1);//ubicacion
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton1 = rg1.findViewById(checkedId);
                if(checkedId == R.id.rb1){
                    cmp_ubica= "3";

                }else if (checkedId == R.id.rb2){
                    cmp_ubica= "4";

                }
            }
        });
        //
        //
        // clase trafo
        final Spinner claseTrafo = (Spinner) this.findViewById(R.id.clasetrafo);
        claseTrafo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView <?> adapterView, View view, int pos3, long id ) {
                position3 = pos3;
                selection3 = adapterView.getItemAtPosition(position3).toString();
            }
            @Override
            public void onNothingSelected( AdapterView <?> parent ) { }
        } );
        //
        //
        // tipo trafo
        final Spinner tipoTrafo = (Spinner) this.findViewById(R.id.tipoTrafo);
        tipoTrafo.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView <?> adapterView, View view, int pos4, long id ) {
                position4 = pos4;
                selection4 = adapterView.getItemAtPosition(position4).toString();
            }
            @Override
            public void onNothingSelected( AdapterView <?> parent ) { }
        } );
        //

        //observaciones trafo
        String[] array ={"NINGUNA",
        "BAJANTE PARA CAMBIO",
        "ESTADO DPS IRREGULAR",
        "FILTRACION EN DPS",
        "SIN PUESTA A TIERRA",
        "FILTRACION DE ACEITE EN TRANSFORMADOR",
        "AISLADORES AVERIADOS",
        "TRAFO NO EQUIPOTENCIADO",
        "BORNES SULFATADOS",
        "LEVANTAMIENTO SOLO DE REDES MEDIA",
        "LEVANTAMIENTO SOLO DE REDES BAJA"};
        MultiSelectionSpinner cmp_observa = (MultiSelectionSpinner) findViewById(R.id.obstrafo);
        cmp_observa.setItems(array);
        //cmp_observa.setSelection(new int[]{2, 6});
        cmp_observa.setListener(this);
        //
        // capacidad trafo
            cmp_capacid = (EditText) this.findViewById( R.id.capaTrafo );
        //
        //
        // propietario trafo
        final Spinner rg2 = (Spinner) this.findViewById(R.id.rgroup2);
        rg2.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView <?> adapterView, View view, int pos5, long id ) {
                position6 = pos5;
                selection6 = adapterView.getItemAtPosition(position6).toString();
            }
            @Override
            public void onNothingSelected( AdapterView <?> parent ) { }
        } );


        //
        //
        //boton atras
        ImageButton img = (ImageButton) findViewById(R.id.btnatras2);
        img.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        });
        //
        //
        //  boton registrar
        botonRegistro = (Button) this.findViewById( R.id.btnGuardar );
        botonRegistro.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerMpio.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione un Municipio",Toast.LENGTH_LONG).show();
                }
                else if(comboTrafos.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext() ,"Error! Seleccione el Codigo del Transformador",Toast.LENGTH_LONG).show();
                }
                else if(rg1.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getBaseContext(),"Error! Escoja la Zona de Ubicación ",Toast.LENGTH_LONG).show();
                }
                if(claseTrafo.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione la Clase de Transformador",Toast.LENGTH_LONG).show();
                }
                else if(tipoTrafo.getSelectedItem().toString().trim().equals("SELECCIONE")) {
                    Toast.makeText( getBaseContext(), "Error! Seleccione Tipo de Transformador", Toast.LENGTH_LONG ).show();
                }
                else if(cmp_capacid.getText().toString().trim().isEmpty()){
                    Toast.makeText(getBaseContext(),"Error! Completar la Capacidad del Transformador",Toast.LENGTH_LONG).show();
                }
                else if(rg2.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText( getBaseContext(), "Error! Seleccione la Propiedad ", Toast.LENGTH_LONG ).show();
                }
                else {
                    registarTrafo();
                }
            }
        });


    }
    //
    //
    // ESTA FUNCION SE AGREGO PARA HACER MAS FACIL LAS COORDENADAS //
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        frm_trafos.Localizacion Local = new frm_trafos.Localizacion();
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



    /* Aqui empieza la Clase Localizacion */
    class Localizacion implements LocationListener {
        mainActivity frm_trafos;
        public mainActivity getMainActivity() {
            return frm_trafos;
        }

        public void setMainActivity(mainActivity mainActivity) {
            this.frm_trafos = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();
            loc.getAltitude();
            loc.setAccuracy(1);
            loc.getAccuracy();
            String cmp_latitud,cmp_longitu,cmp_altitud;
            String Textxx = "Lat = "+ loc.getLatitude() + "n Long = " + loc.getLongitude()+"n Alt = "+loc.getAltitude()+"n Pre = "+loc.getAccuracy();
            Log.i( TAG, "seleccion5 : " + Textxx);
            cmp_latitud = ""+loc.getLatitude();
            cmp_longitu = ""+loc.getLongitude();
            cmp_altitud = ""+loc.getAltitude();
            txtLo.setText(cmp_longitu );
            textLa.setText(cmp_latitud );
            textAl.setText(cmp_altitud );


            // mensaje1.setText(Text);
            //this.frm_trafos.setLocation(loc);
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
        public void setMainActivity( frm_trafos frm_trafos ) {
        }
    }
    @Override
    public void selectedIndices( List <Integer> indices ) {

    }

    @Override
    public void selectedStrings( List <String> strings ) {
        String valobser;
        valobser = strings.toString().replaceAll( ",","-" );
        selection5 = valobser;
    }

    private void registarTrafo() {

       final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
       // condicion para eliminar los corchetes
        String observacion_trafo ="";
        if (selection5 != null) {
           // Log.i( TAG, "seleccion5 : " + selection5);
            int sx = selection5.indexOf( "[" );
            if (sx < 0) {
                observacion_trafo = selection5.trim();
            } else {
                observacion_trafo = selection5.replace( "]", "" ).replace( "[", "" );
            }
        } else {
            //selection5 = "NINGUNA";
            observacion_trafo ="NINGUNA";
        }
       // Log.i( TAG, "seleccion5 : " + observacion_trafo);


        String nomb_trafoText = selection2;
        String capacidadText = cmp_capacid.getText().toString();
        String zonaurText = cmp_ubica;
        String mpioText = selection1;
        String propiedad_trafoText = selection6;
        String tipoText = selection3;
        String lati_trafoText = textLa.getText().toString();
        String long_trafoText = txtLo.getText().toString();
        String alt_trafoText = textAl.getText().toString();
        String tipo_trafoText = selection4;
        String observacion_trafoText = observacion_trafo;
        String nro_postes_trafoText = String.valueOf( 0 );
        String espacio_n1Text = "T";
        String nro_poste_correspText = "0";
        String nomb_nodoText = "0";
        String id_trafo_nodoText = "0";
        String lati_nodoText = "0";
        String long_nodoText = "0";
        String alti_nodoText = "0";
        String altura_nodoText = "0";
        String material_nodoText = "0";
        String estruct_1_nodoText = "0";
        String tipo_1_estructText = "0";
        String estruct_2_nodoText = "0";
        String tipo_2_estructText = "0";
        String estruct_3_nodoText = "0";
        String tipo_3_estructText = "0";
        String estruct_4_nodoText = "0";
        String tipo_4_estructText = "0";
        String nr_retenText = "0";
        String antiguedadText = "0";
        String propiedadText = "0";
        String nro_acometText = "0";
        String con1Text = "0";
        String cal_fase_con1Text = "0";
        String cal_neutro_con1Text = "0";
        String cal_ap_con1Text = "0";
        String nhil_con1Text = "0";
        String con2Text = "0";
        String cal_fase_con2Text = "0";
        String cal_neutro_con2Text = "0";
        String cal_ap_con2Text = "0";
        String nhil_con2Text = "0";
        String con3Text = "0";
        String cal_fase_con3Text = "0";
        String cal_neutro_con3Text = "0";
        String cal_ap_con3Text = "0";
        String nhil_con3Text = "0";
        String con4Text = "0";
        String cal_fase_con4Text = "0";
        String cal_neutro_con4Text = "0";
        String cal_ap_con4Text = "0";
        String nhil_con4Text = "0";
        String observacion_nodoText = "0";
        String nro_acometidasText = "0";
        String espacio_n2Text = "0";
        String acometida_correspText = "0";
        String niuText = "0";
        String cont_actText = "0";
        String id_nodo_acometidaText = "0";
        String direccionText = "0";
        String cont_anteText = "0";
        String cont_marcaText = "0";
        String lati_usuText = "0";
        String long_usuText = "0";
        String alti_usuText = "0";
        String tip_caText = "0";
        String tip_asText = "0";
        String tip_mbtText = "0";
        String tip_rstText = "0";
        String calibreText = "0";
        String tableroText = "0";
        String fas_tableroText = "0";
        String tip_servicioText = "0";
        String observacion_usuText = "0";
        String usu_levant_usuText = String.valueOf( nombre );

        ContentValues values=new ContentValues();

        //ingreso de datos del trafo

        values.put( DatabaseManagerUser.Columnas.NOMB_TRAFO, nomb_trafoText);
        values.put( DatabaseManagerUser.Columnas.CAPACIDAD, capacidadText);
        values.put( DatabaseManagerUser.Columnas.ZONAUR, zonaurText);
        values.put( DatabaseManagerUser.Columnas.MPIO, mpioText);
        values.put( DatabaseManagerUser.Columnas.PROPIEDAD_TRAFO, propiedad_trafoText);
        values.put( DatabaseManagerUser.Columnas.TIPO, tipoText);
        values.put( DatabaseManagerUser.Columnas.LATI_TRAFO, lati_trafoText);
        values.put( DatabaseManagerUser.Columnas.LONG_TRAFO, long_trafoText);
        values.put( DatabaseManagerUser.Columnas.ALT_TRAFO, alt_trafoText);
        values.put( DatabaseManagerUser.Columnas.TIPO_TRAFO, tipo_trafoText);
        values.put( DatabaseManagerUser.Columnas.OBSERVACION_TRAFO, observacion_trafoText);
        values.put( DatabaseManagerUser.Columnas.NRO_POSTES_TRAFO, nro_postes_trafoText);
        values.put( DatabaseManagerUser.Columnas.ESPACIO_N1, espacio_n1Text);
        values.put( DatabaseManagerUser.Columnas.NRO_POSTE_CORRESP, nro_poste_correspText);
        values.put( DatabaseManagerUser.Columnas.NOMB_NODO, nomb_nodoText);
        values.put( DatabaseManagerUser.Columnas.ID_TRAFO_NODO, id_trafo_nodoText);
        values.put( DatabaseManagerUser.Columnas.LATI_NODO, lati_nodoText);
        values.put( DatabaseManagerUser.Columnas.LONG_NODO, long_nodoText);
        values.put( DatabaseManagerUser.Columnas.ALTI_NODO, alti_nodoText);
        values.put( DatabaseManagerUser.Columnas.ALTURA_NODO, altura_nodoText);
        values.put( DatabaseManagerUser.Columnas.MATERIAL_NODO, material_nodoText);
        values.put( DatabaseManagerUser.Columnas.ESTRUCT_1_NODO, estruct_1_nodoText);
        values.put( DatabaseManagerUser.Columnas.TIPO_1_ESTRUCT, tipo_1_estructText);
        values.put( DatabaseManagerUser.Columnas.ESTRUCT_2_NODO, estruct_2_nodoText);
        values.put( DatabaseManagerUser.Columnas.TIPO_2_ESTRUCT, tipo_2_estructText);
        values.put( DatabaseManagerUser.Columnas.ESTRUCT_3_NODO, estruct_3_nodoText);
        values.put( DatabaseManagerUser.Columnas.TIPO_3_ESTRUCT, tipo_3_estructText);
        values.put( DatabaseManagerUser.Columnas.ESTRUCT_4_NODO, estruct_4_nodoText);
        values.put( DatabaseManagerUser.Columnas.TIPO_4_ESTRUCT, tipo_4_estructText);
        values.put( DatabaseManagerUser.Columnas.NR_RETEN, nr_retenText);
        values.put( DatabaseManagerUser.Columnas.ANTIGUEDAD, antiguedadText);
        values.put( DatabaseManagerUser.Columnas.PROPIEDAD, propiedadText);
        values.put( DatabaseManagerUser.Columnas.NRO_ACOMET, nro_acometText);
        values.put( DatabaseManagerUser.Columnas.CON1, con1Text);
        values.put( DatabaseManagerUser.Columnas.CAL_FASE_CON1, cal_fase_con1Text);
        values.put( DatabaseManagerUser.Columnas.CAL_NEUTRO_CON1, cal_neutro_con1Text);
        values.put( DatabaseManagerUser.Columnas.CAL_AP_CON1, cal_ap_con1Text);
        values.put( DatabaseManagerUser.Columnas.NHIL_CON1, nhil_con1Text);
        values.put( DatabaseManagerUser.Columnas.CON2, con2Text);
        values.put( DatabaseManagerUser.Columnas.CAL_FASE_CON2, cal_fase_con2Text);
        values.put( DatabaseManagerUser.Columnas.CAL_NEUTRO_CON2, cal_neutro_con2Text);
        values.put( DatabaseManagerUser.Columnas.CAL_AP_CON2, cal_ap_con2Text);
        values.put( DatabaseManagerUser.Columnas.NHIL_CON2, nhil_con2Text);
        values.put( DatabaseManagerUser.Columnas.CON3, con3Text);
        values.put( DatabaseManagerUser.Columnas.CAL_FASE_CON3, cal_fase_con3Text);
        values.put( DatabaseManagerUser.Columnas.CAL_NEUTRO_CON3, cal_neutro_con3Text);
        values.put( DatabaseManagerUser.Columnas.CAL_AP_CON3, cal_ap_con3Text);
        values.put( DatabaseManagerUser.Columnas.NHIL_CON3, nhil_con3Text);
        values.put( DatabaseManagerUser.Columnas.CON4, con4Text);
        values.put( DatabaseManagerUser.Columnas.CAL_FASE_CON4, cal_fase_con4Text);
        values.put( DatabaseManagerUser.Columnas.CAL_NEUTRO_CON4, cal_neutro_con4Text);
        values.put( DatabaseManagerUser.Columnas.CAL_AP_CON4, cal_ap_con4Text);
        values.put( DatabaseManagerUser.Columnas.NHIL_CON4, nhil_con4Text);
        values.put( DatabaseManagerUser.Columnas.OBSERVACION_NODO, observacion_nodoText);
        values.put( DatabaseManagerUser.Columnas.NRO_ACOMETIDAS, nro_acometidasText);
        values.put( DatabaseManagerUser.Columnas.ESPACIO_N2, espacio_n2Text);
        values.put( DatabaseManagerUser.Columnas.ACOMETIDA_CORRESP, acometida_correspText);
        values.put( DatabaseManagerUser.Columnas.NIU, niuText);
        values.put( DatabaseManagerUser.Columnas.CONT_ACT, cont_actText);
        values.put( DatabaseManagerUser.Columnas.ID_NODO_ACOMETIDA, id_nodo_acometidaText);
        values.put( DatabaseManagerUser.Columnas.DIRECCION, direccionText);
        values.put( DatabaseManagerUser.Columnas.CONT_ANTE, cont_anteText);
        values.put( DatabaseManagerUser.Columnas.CONT_MARCA, cont_marcaText);
        values.put( DatabaseManagerUser.Columnas.LATI_USU, lati_usuText);
        values.put( DatabaseManagerUser.Columnas.LONG_USU, long_usuText);
        values.put( DatabaseManagerUser.Columnas.ALTI_USU, alti_usuText);
        values.put( DatabaseManagerUser.Columnas.TIP_CA, tip_caText);
        values.put( DatabaseManagerUser.Columnas.TIP_AS, tip_asText);
        values.put( DatabaseManagerUser.Columnas.TIP_MBT, tip_mbtText);
        values.put( DatabaseManagerUser.Columnas.TIP_RST, tip_rstText);
        values.put( DatabaseManagerUser.Columnas.CALIBRE, calibreText);
        values.put( DatabaseManagerUser.Columnas.TABLERO, tableroText);
        values.put( DatabaseManagerUser.Columnas.FAS_TABLERO, fas_tableroText);
        values.put( DatabaseManagerUser.Columnas.TIP_SERVICIO, tip_servicioText);
        values.put( DatabaseManagerUser.Columnas.OBSERVACION_USU, observacion_usuText);
        values.put( DatabaseManagerUser.Columnas.USU_LEVANT_USU, usu_levant_usuText);
        values.put( DatabaseManagerUser.Columnas.PENDIENTE_INSERCION, 1 );

        getContentResolver().insert( ContractParaG.CONTENT_URI, values);
        SyncAdapter.sincronizarAhora(this, true);

        if (Utilidades.materialDesign()) {
            finishAfterTransition();
        }else{ finish();}

        }

    //colocar en todas las actividades boton de atras
    @Override
    public boolean onSupportNavigateUp() {
       // onBackPressed();
        finish();
        return false;
    }

    private void consultarListaTrafos(String municipio) {
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
        Ntrafo ntrafos=null;
        trafosList = new ArrayList<Ntrafo>();
        Cursor cursor = databaseManager.getDb().rawQuery( "SELECT "+DatabaseManagerUser.NTRAFOS+".id_nmb_ntrafo FROM "+DatabaseManagerUser.NTRAFOS+" WHERE NOT "+DatabaseManagerUser.NTRAFOS+".id_nmb_ntrafo  IN (SELECT "+ContractParaG.TRAFO+".nomb_trafo FROM "+ContractParaG.TRAFO+") AND "+DatabaseManagerUser.NTRAFOS+ ".mpio='"+municipio+"'", null);
        while(cursor.moveToNext()){
            ntrafos=new Ntrafo();
            ntrafos.setId_nmb_ntrafo(cursor.getString(0));
            trafosList.add( ntrafos );

        }
        obtenerlista();
        cursor.close();

    }
     private void obtenerlista() {
        listaTrafos =new ArrayList <String>();
        //listaTrafos.add("Seleccione");
        for(int i=0;i<trafosList.size();i++){
            listaTrafos.add(trafosList.get(i).getId_nmb_ntrafo());
        }
    }
}


