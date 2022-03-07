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
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.Utilidades.Constantes;
import com.redylab.redylabsirr.adaptadores.ContractParaG;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Ntrafo;
import com.redylab.redylabsirr.entity.Trafo;
import com.redylab.redylabsirr.sync.SyncAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.util.Objects.isNull;

public class frm_acometidas extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    private static final String TAG = "frm_acometidas";
    Spinner sdireccion,cmp_calibreaco;
    ArrayList<String> listaTrafos;
    ArrayList<Ntrafo> trafosList;
    int numero = 0;
    String barrio,resul_operario, resultado_cod_aco,selectionacometida1;
    private static int resultado_codaco;
    Integer positionA1,positionA2,positionA3,positionA4,positionA5,positionA6,positionA7;
    String selectionA1,selectionA2,selectionA3,selectionA4,selectionA5,selectionA6,selectionA7,codposte,codtr,tip_cas,tip_ass,tip_mbts,tabler;
    EditText cmp_observa_aco,cmp_contador,cmp_marcaconta;
    RGps gpsaco;
    String f_ch1R="",f_ch1T="",f_ch1S="",f_ch2R="",f_ch2S="",f_ch2T="",resul_ch1,resul_ch2;
    CheckBox cmp_ch_fsr,cmp_ch_fst,cmp_ch_fss,cmp_ch_fstbr,cmp_ch_fstbt,cmp_ch_fstbs;
    Button botonRegistro;
    TextView coposte,verificar,txtLo,textLa,textAl;

    private LinearLayout ltitulofasestablero,lfasesTablero;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.frm_acometidas );
        //
        //
        //
        ltitulofasestablero = (LinearLayout) findViewById(R.id.titulofasestablero);
        lfasesTablero = (LinearLayout) findViewById(R.id.fasesTablero);
        //
        //
        txtLo =  this.findViewById(R.id.txtLona);
        textLa =  this.findViewById(R.id.textLata);
        textAl =  this.findViewById(R.id.textAlta);
        //
        //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        } else {
            locationStart();
        }
        //
        //
        final Spinner sdireccion = (Spinner) findViewById(R.id.direccionaco);
        //codPoste
        coposte = (TextView) findViewById( R.id.codigoposte );
        Bundle miBundle2 = this.getIntent().getExtras();
        if (miBundle2 != null) {
            codposte = miBundle2.getString( "codigoposte" );
            codtr = miBundle2.getString( "codigotrafo" );
            coposte.setText( codposte );
            barrio = codposte.substring(1,2);
            //
            operarios();
            //
            nroacometida(codposte);
           //
            numero = Integer.parseInt( String.valueOf( resultado_codaco ) );
            //PV00040009
            //AV00040009902
            //AV0004000902
            if(resultado_codaco == 0){
                resultado_cod_aco ="A"+barrio+codposte.substring(2,10)+"01";
            }else
            {
                if(numero >9 ) {
                    resultado_cod_aco = "A" + barrio + codposte.substring( 2,10 ) + resultado_codaco;
               }else
              {
                   resultado_cod_aco = "A" + barrio + codposte.substring( 2,10 ) +"0"+ resultado_codaco;
              }
            }
            //Log.i( TAG,"codigo de acometida " + resultado_codaco);
            //Log.i( TAG,"codigo de acometida " + resultado_cod_aco);

            //
            if(barrio.equals("M")){

                ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.barrios_mocoa , android.R.layout.simple_spinner_item);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sdireccion.setAdapter(spinner_adapter);
            }else if(barrio.equals("V")){

                ArrayAdapter adapter = ArrayAdapter.createFromResource( this, R.array.barrrios_villa , android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sdireccion.setAdapter(adapter);
            }else if(barrio.equals("G")){

                ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.barrios_guzman , android.R.layout.simple_spinner_item);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sdireccion.setAdapter(spinner_adapter);
            }else if(barrio.equals("P")){

                ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.barrios_piamonte , android.R.layout.simple_spinner_item);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sdireccion.setAdapter(spinner_adapter);
            }else if(barrio.equals("O")){

                ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.barrios_orito , android.R.layout.simple_spinner_item);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sdireccion.setAdapter(spinner_adapter);
            }

        }
        //
        //
        verificar = (TextView) this.findViewById( R.id.cod_acometida );
        //
        //
        //numero de contador
        cmp_contador = (EditText) this.findViewById( R.id.contador );
        //
        //
        // marca contador
        cmp_marcaconta = (EditText) this.findViewById( R.id.marcacon );
        //
        //
        //observaciones acometida
        String[] array ={"NINGUNA",
                "SIN CONECTORES",
                "CONEXION DIRECTA",
                "MEDIDOR INTERNO",
                "MEDIDOR SIN POLO A TIERRA",
                "MEDIDOR SIN TAPA BORNERA",
                "MEDIDOR SIN SELLO",
                "MEDIDOR DAÑADO",
                "MEDIDOR BORROSO",
                "MANTENIMIENTO ACOMETIDA",
                "POSIBLE FRAUDE"};
        MultiSelectionSpinner cmp_observa_aco = (MultiSelectionSpinner) findViewById(R.id.obseraco);
        cmp_observa_aco.setItems(array);
        //cmp_observa.setSelection(new int[]{2, 6});
        cmp_observa_aco.setListener(this);

        /*final MultiSpinner cmp_observa_aco = ( MultiSpinner) this.findViewById( R.id.obseraco );
        selectionacometida1="NINGUNA";
        cmp_observa_aco.setMultiSpinnerListener( new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected( boolean[] selected ) {
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i]) {
                        selectionacometida1 =cmp_observa_aco.getSelectedItem().toString().trim();
                        Log.i( TAG, i + "verojo : " + selected[i] + " valor :"+selectionacometida1 );

                    }
                }
            }
        });*/

        //
        //
        cmp_calibreaco = (Spinner) this.findViewById( R.id.calibreacometida );
        cmp_calibreaco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                positionA7 = pos;
                selectionA7 = adapterView.getItemAtPosition(positionA7).toString();
                //Toast.makeText(getBaseContext(),"direccion "+selectionA1,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });


        //
        //
        //direccion
         sdireccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                positionA1 = pos;
                selectionA1 = adapterView.getItemAtPosition(positionA1).toString();
                //Toast.makeText(getBaseContext(),"direccion "+selectionA1,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //
        //sector
        final Spinner ssectorac = (Spinner) this.findViewById(R.id.ssector);
        ssectorac.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                positionA2 = pos;
                selectionA2 = adapterView.getItemAtPosition(positionA2).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //
        //CONCENTRICA - ABIERTA
        final Spinner sCA = (Spinner) this.findViewById(R.id.ca);
        sCA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                positionA3 = pos;
                selectionA3 = adapterView.getItemAtPosition(positionA3).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //AEREA - SUBTERRANEA
        final Spinner sAS = (Spinner) this.findViewById(R.id.as);
        sAS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                positionA4 = pos;
                selectionA4 = adapterView.getItemAtPosition(positionA4).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //
        //MONOFASICO - BIFASICO -TRIFASICO
        final Spinner sMBT = (Spinner) this.findViewById(R.id.mbt);
        sMBT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                positionA5 = pos;
                selectionA5 = adapterView.getItemAtPosition(positionA5).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //
        //  TABLERO DE MEDIDORES
        final Spinner sTBLRO = (Spinner) this.findViewById(R.id.tblromed);
        sTBLRO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                positionA6 = pos;
                selectionA6 = adapterView.getItemAtPosition(positionA6).toString();
                if (positionA6 == 1){
                    ltitulofasestablero.setVisibility(View.VISIBLE);
                    lfasesTablero.setVisibility(View.VISIBLE);
                }else if(positionA6 == 2){
                    ltitulofasestablero.setVisibility(View.GONE);
                    lfasesTablero.setVisibility(View.GONE);
                }else{
                    ltitulofasestablero.setVisibility(View.GONE);
                    lfasesTablero.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //

        //chexbox
        cmp_ch_fsr =(CheckBox) findViewById( R.id.ch_faser );
        cmp_ch_fst =(CheckBox) findViewById( R.id.ch_faset );
        cmp_ch_fss =(CheckBox) findViewById( R.id.ch_fases );
        cmp_ch_fstbr =(CheckBox)findViewById( R.id.ch_fasert );
        cmp_ch_fstbs =(CheckBox) findViewById( R.id.ch_fasest );
        cmp_ch_fstbt =(CheckBox) findViewById( R.id.ch_fasett );
        //
        //
        //boton registro
        botonRegistro = (Button) findViewById( R.id.btnGuardaracometida );
        botonRegistro.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                if(cmp_contador.getText().toString().trim().isEmpty()){
                    Toast.makeText(getBaseContext(),"Error! Dijite un numero de Medidor",Toast.LENGTH_SHORT).show();
                }
                else if(cmp_marcaconta.getText().toString().trim().isEmpty()){
                    Toast.makeText(getBaseContext(),"Error! Dijite la Marca del Medidor",Toast.LENGTH_SHORT).show();
                }
                else if(sdireccion.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione una Dirección",Toast.LENGTH_SHORT).show();
                }
                else if(ssectorac.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione un Sector",Toast.LENGTH_SHORT).show();
                }
                else if(sCA.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione un tipo Concentrica ó Abierta",Toast.LENGTH_SHORT).show();
                }
                else if(sAS.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione un tipo Aerea ó Subterranea",Toast.LENGTH_SHORT).show();
                }
                else if(sMBT.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione un tipo Monofasico, Bifasico ó Trifasico",Toast.LENGTH_SHORT).show();
                }
                else if(cmp_ch_fsr.isChecked() || cmp_ch_fst.isChecked() || cmp_ch_fss.isChecked() ){

                    if(cmp_calibreaco.getSelectedItem().toString().trim().equals("SELECCIONE")){
                        Toast.makeText(getBaseContext(),"Error! Seleccione el Calibre de la Acometida",Toast.LENGTH_SHORT).show();
                    }
                    else if(sTBLRO.getSelectedItem().toString().trim().equals("SELECCIONE")){
                        Toast.makeText(getBaseContext(),"Error! Seleccione si hay Tablero de Medidores ",Toast.LENGTH_SHORT).show(); //
                    }
                    else if(sTBLRO.getSelectedItem().toString().trim().equals("SI")){
                        if(cmp_ch_fstbr.isChecked() || cmp_ch_fstbt.isChecked() ||  cmp_ch_fstbs.isChecked()){
                            registarAcometida();
                        }else{
                            Toast.makeText(getBaseContext(),"Error! Seleccione Fases de Tablero ",Toast.LENGTH_SHORT).show(); //cmp_ch_fstbr
                        }
                    }else{
                        registarAcometida();
                    }
                }
                else {
                        Toast.makeText(getBaseContext(),"Error! Seleccione Fases de Acometida ",Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
    //
    // ESTA FUNCION SE AGREGO PARA HACER MAS FACIL LAS COORDENADAS //
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        frm_acometidas.Localizacion Local = new frm_acometidas.Localizacion();
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
        selectionacometida1 = valobser;
    }

    /* Aqui empieza la Clase Localizacion */
    class Localizacion implements LocationListener {
        mainActivity frm_acometidas;
        public mainActivity getMainActivity() {
            return frm_acometidas;
        }

        public void setMainActivity(mainActivity mainActivity) {
            this.frm_acometidas = mainActivity;
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
            String cmp_latitud_aco,cmp_longitu_aco,cmp_altitud_aco;
            String Textxx = "Lat = "+ loc.getLatitude() + "n Long = " + loc.getLongitude()+"n Alt = "+loc.getAltitude()+"n Pre = "+loc.getAccuracy();
            //Log.i( TAG, "seleccion5 : " + Textxx);
            // String Text = "Lat = "+ loc.getLatitude() + "n Long = " + loc.getLongitude()+"n Alt = "+loc.getAltitude()+"n Pre = "+loc.getAccuracy();
            cmp_latitud_aco = ""+loc.getLatitude();
            cmp_longitu_aco = ""+loc.getLongitude();
            cmp_altitud_aco = ""+loc.getAltitude();


            txtLo.setText(cmp_longitu_aco );
            textLa.setText(cmp_latitud_aco );
            textAl.setText(cmp_altitud_aco );


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
        public void setMainActivity( frm_acometidas frm_acometidas ) {
        }
    }

    private void registarAcometida() {
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser( getApplicationContext() );

        // Toast.makeText(getBaseContext(),"fases Acometida "+resul_ch1+" fases Tablero "+resul_ch2,Toast.LENGTH_SHORT).show();

        String observacion_trafo ="";
        if (selectionacometida1 != null) {
            //Log.i( TAG, "seleccion5 : " + selectionacometida1);
            int sx = selectionacometida1.indexOf( "[" );
            if (sx < 0) {
                observacion_trafo = selectionacometida1.trim();
            } else {
                observacion_trafo = selectionacometida1.replace( "]", "" ).replace( "[", "" );
            }
        } else {
            //selection5 = "NINGUNA";
            observacion_trafo ="NINGUNA";
        }

        if(selectionA3.equals("CONCÉNTRICA")){
            tip_cas="C";
        }else if(selectionA3.equals("ABIERTA")){
            tip_cas="A";
        }
        if(selectionA4.equals("AÉREA")){
            tip_ass="A";
        }else if(selectionA4.equals("SUBTERRÁNEA")){
            tip_ass="S";
        }
        if(selectionA5.equals("MONOFÁSICO")){
            tip_mbts="M";
        }else if(selectionA5.equals("BIFÁSICO")){
            tip_mbts="B";
        }else if(selectionA5.equals("TRIFÁSICO")){
            tip_mbts="T";
        }
        if(selectionA6.equals( "NO" )){
            tabler="N";
        }else if(selectionA6.equals( "SI" )){
            tabler="S";
        }


        // Log.i( TAG, "seleccion5 : " + observacion_trafo);
        //
        String nomb_trafoText ="0";
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
        String espacio_n1Text = "A";
        String nro_poste_correspText = "2";
        String nomb_nodoText = "0";
        String id_trafo_nodoText = "0";
        String lati_nodoText = String.valueOf("0");
        String long_nodoText = String.valueOf("0");
        String alti_nodoText = String.valueOf("0");
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
        String nro_acometidasText = "1";
        String espacio_n2Text = "0";
        String acometida_correspText = resultado_cod_aco;//
        String niuText = "0";
        String cont_actText = cmp_contador.getText().toString();
        String id_nodo_acometidaText = codposte;//
        String direccionText = selectionA1;
        String cont_anteText = "0";
        String cont_marcaText = cmp_marcaconta.getText().toString();
        String lati_usuText = textLa.getText().toString();
        String long_usuText = txtLo.getText().toString();
        String alti_usuText = textAl.getText().toString();
        String tip_caText = tip_cas;
        String tip_asText = tip_ass;
        String tip_mbtText = tip_mbts;
        String tip_rstText = resul_ch1;
        String calibreText = selectionA7;
        String tableroText = tabler;
        String fas_tableroText =resul_ch2;
        String tip_servicioText = selectionA2;
        String observacion_usuText = observacion_trafo;
        String usu_levant_usuText = resul_operario;

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
        values.put( Constantes.ESTADO, 0 );
        values.put( DatabaseManagerUser.Columnas.PENDIENTE_INSERCION, 1 );

        getContentResolver().insert( ContractParaG.CONTENT_URI, values);
        SyncAdapter.sincronizarAhora(this, true);

        /*if (Utilidades.materialDesign()) {
            finishAfterTransition();
        }else {*/

       //
        databaseManager.getDb().close();
        finish();


    }

    private void operarios(){
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
        Trafo traf=null;
        Cursor cursoroperario = databaseManager.getDb().rawQuery( "select distinct(usu_levant_usu) from trafo where nomb_trafo='"+codtr+"'", null);
        while(cursoroperario.moveToNext()) {
             resul_operario = cursoroperario.getString( 0 );
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
    public void nroacometida(String cod_poste){
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
        Cursor cursoraco = databaseManager.getDb().rawQuery( "SELECT(case when length(nro_acometida_corresp) = 1 and length(id_nodo_usu) = 1 then  (00 || 01) else (case when max(cast(substr(nro_acometida_corresp,11,12) as integer)) <9 then 00 || (max(cast(substr(nro_acometida_corresp,11,12) as integer))+1) else (max(cast(substr(nro_acometida_corresp,11,12) as integer))+1) end) end)as cod_final FROM "+ContractParaG.TRAFO+" WHERE id_nodo_usu= '"+cod_poste+"'", null);
        //select (case when length(nomb_nodo) = 1 and length(id_trafo_nodo) = 1 then 'P' || substr(nomb_trafo,2,1) || substr(nomb_trafo,5,4) || '0001' else (case when length(nomb_nodo) > 1 and length(id_trafo_nodo)> 1 then (case when max(cast(substr(nomb_nodo,7,4) as integer)) < 10 then 'P' || substr(nomb_nodo,2,1) || substr(nomb_nodo,3,4) || 000 || 000 || (max(cast(substr(nomb_nodo,7,4) as integer))+1) else 'P' || substr(nomb_nodo,2,1) || substr(nomb_nodo,3,4) || 000 || 000 || (max(cast(substr(nomb_nodo,7,4) as integer))+1) end) end) end)
        while(cursoraco.moveToNext()) {
            resultado_codaco = cursoraco.getInt( 0 );
        }

        cursoraco.close();
        databaseManager.getDb().close();
    }

    public void chbox( View view ) {
        //chexbox

        if(cmp_ch_fsr.isChecked()){
            f_ch1R="R";
        }else{
            f_ch1R="";
        }

        if(cmp_ch_fst.isChecked()) {
            f_ch1T="T";
        }else{
            f_ch1T="";
        }

        if(cmp_ch_fss.isChecked()){
            f_ch1S="S";
        }else{
            f_ch1S="";
        }
        //
        resul_ch1=f_ch1R+f_ch1T+f_ch1S;
        //

        if(cmp_ch_fstbr.isChecked()){
            f_ch2R="R";
        }else{
            f_ch2R="";
        }


        if(cmp_ch_fstbs.isChecked()){
            f_ch2S="S";
        }else{
            f_ch2S="";
        }

        if(cmp_ch_fstbt.isChecked()){
            f_ch2T="T";
        }else{
            f_ch2T="";
        }
        if (positionA6 == 1) {
            resul_ch2 = f_ch2R + f_ch2S + f_ch2T;
        }else{
            resul_ch2 = "";
        }

    }
}
