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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.Utilidades.Constantes;
import com.redylab.redylabsirr.Utilidades.Utilidades;
import com.redylab.redylabsirr.adaptadores.ContractParaG;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Ntrafo;
import com.redylab.redylabsirr.sync.SyncAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.text.TextUtils.concat;
import static android.text.TextUtils.indexOf;
import static android.text.TextUtils.substring;

public class frm_trafos_update extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener{
    private static final String TAG = "frm_trafos_update";
    Spinner comboTrafos;
    ArrayList<String> listaTrafos;
    ArrayList<Ntrafo> trafosList;

    Integer position1,position2,position3,position4,position5,position6;
    String selection1,selection3,selection4,selection5,selection6,cmp_ubica,cmp_propiet,operario;
    String vcodt,vclat,vcapt,vtipt,vubit,vprot,vobst,vlont,vlatt,valtt, vmunt,vnomt;;
    RadioGroup rg1;
    EditText cmp_capacid,cmp_marcatf;
    RGps gps;
    double cmp_latitud,cmp_longitu,cmp_altitud;
    Button botonRegistro;
    TextView textCo, txtLo,textLa,textAl;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.frm_trafos_update );
        //
        txtLo =  this.findViewById(R.id.txtLontr);
        textLa =  this.findViewById(R.id.textLatitr);
        textAl =  this.findViewById(R.id.textAltitr);
        textCo =  this.findViewById(R.id.cdtrafo);
        cmp_capacid = (EditText) this.findViewById( R.id.capaTrafo );
        //
        //
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            vnomt= extras.getString( "IDENT" );
            vcodt= extras.getString( "codtraf");
            vclat= extras.getString( "codclas");
            vcapt= extras.getString( "codcapa");
            vtipt= extras.getString( "codtipo");
            vubit= extras.getString( "codubic");
            vprot= extras.getString( "codprop");
            vobst= extras.getString( "codobsr");
            vlont= extras.getString( "codlong");
            vlatt= extras.getString( "codlati");
            valtt= extras.getString( "codaltu");
            vmunt= extras.getString( "municipio");
        }
        //
        Log.i("parametro NOMBRE: " , String.valueOf(vnomt ) );
        //
        textCo.setText( vcodt );
        txtLo.setText( vlont );
        textLa.setText( vlatt );
        textAl.setText( valtt );
        cmp_capacid.setText( vcapt );
        operario = String.valueOf( vnomt );

        selection1=vmunt;
        selection6=vprot;
        selection3=vclat;
        cmp_latitud = Double.parseDouble(vlatt);
        cmp_longitu = Double.parseDouble(vlont);
        cmp_altitud = Double.parseDouble(valtt);
        selection4 = vtipt;
        selection5 = vobst;
         //
        if(vubit.equals("URBANO")) {
            //Log.i("parametro u UBICACION: " , String.valueOf(vubit ) );
            RadioButton rb1x = findViewById( R.id.rb1 );
            rb1x.setChecked(true);
            cmp_ubica="3";
        }else if(vubit.equals("RURAL")){
            //Log.i("parametro r UBICACION: " , String.valueOf(vubit ) );
            RadioButton rb2x = findViewById( R.id.rb2 );
            rb2x.setChecked(true);
            cmp_ubica="4";
        }

       /* if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        } else {
            locationStart();
        }*/
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
        botonRegistro = (Button) this.findViewById( R.id.btnActualizart );
        botonRegistro.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg1.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getBaseContext(),"Error! Escoja la Zona de Ubicación ",Toast.LENGTH_LONG).show();
                }
                else if(claseTrafo.getSelectedItem().toString().trim().equals("SELECCIONE")){
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
                }else {
                    actualizarTrafo();
                }
            }
        });

        int posclase = obtenerPosicionItem(claseTrafo,vclat);
        int postipo = obtenerPosicionItem(tipoTrafo,vtipt);
        int posprop = obtenerPosicionItem(rg2,vprot);

        claseTrafo.setSelection(posclase);
        tipoTrafo.setSelection(postipo);
        rg2.setSelection(posprop);
        //
        //observaciones trafo
        String[] arrayt ={"NINGUNA",
                "BAJANTE PARA CAMBIO",
                "ESTADO DPS IRREGULAR",
                "FILTRACION EN DPS",
                "SIN PUESTA A TIERRA",
                "FILTRACION DE ACEITE EN TRANSFORMADOR",
                "AISLADORES AVERIADOS",
                "TRAFO NO EQUIPOTENCIADO",
                "BORNES SULFATADOS",
                "LEVANTAMIENTO SOLO DE REDES MEDIA",
                "LEVANTAMIENTO SOLO DE REDES BAJA"
        };
        MultiSelectionSpinner cmp_observa = (MultiSelectionSpinner) findViewById(R.id.obstrafo);
        cmp_observa.setItems(arrayt);
        cmp_observa.setListener(this);
        String vob ="";
        String vobb ="";
        List<String> list = new ArrayList<>();
        //
        if (vobst.contains("-")) {
            // Hacer split.
            int cant=vobst.split("-").length;
            String[] parts = vobst.split("-");
                for ( int i = 0; i < cant; i++){
                    vobb=String.valueOf( parts[i].toString().trim());
                    for(int j = 0; j < 9; j++) {
                        vob = String.valueOf( arrayt[j].toString() );
                        if(vob.equals( vobb )) {
                            list.add(vob);

                        }
                    }
            }
            //Log.i( "parametro cmp_observa: ", String.valueOf((list.toString().replace( "[","" ).replace( "]" ,""))) );
            cmp_observa.setSelection(list);

        } else {
            vobb = vobst;
            for (int j = 0; j < 9; j++) {
                vob = String.valueOf( arrayt[j].toString() );
                if (vob.equals( vobb )) {
                    list.add( vob );
                }
                cmp_observa.setSelection( list );
            }
        }
        //
        //
        //

    }// TERMINA ONCREATE
    //
    //
    @Override
    public void selectedIndices( List <Integer> indices ) {

    }

    @Override
    public void selectedStrings( List <String> strings ) {
        String valobser;
        valobser = strings.toString().replaceAll( ",","-" ).replace( "[","" ).replace( "]","" );
        selection5 = valobser;
    }
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



    private void actualizarTrafo() {

       final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
       //Log.i( "parametro cmp_observa: ",String.valueOf(selection5));

        String observacion_trafo ="";
        if (selection5 != null) {
            //Log.i( TAG, "seleccion5 : " + selection5);
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


        String nomb_trafoText = textCo.getText().toString();
        String capacidadText = cmp_capacid.getText().toString();
        String zonaurText = cmp_ubica;
        String mpioText = selection1;
        String propiedad_trafoText = selection6;
        String tipoText = selection3;
        String lati_trafoText = String.valueOf( cmp_latitud );
        String long_trafoText = String.valueOf(cmp_longitu);
        String alt_trafoText = String.valueOf(cmp_altitud);
        String tipo_trafoText = selection4;
        String observacion_trafoText = observacion_trafo;
        String nro_postes_trafoText = String.valueOf( 0 );
        String usu_levant_usuText = String.valueOf( operario);

        ContentValues valuesT=new ContentValues();

        //ingreso de datos del trafo

        valuesT.put( DatabaseManagerUser.Columnas.NOMB_TRAFO, nomb_trafoText);
        valuesT.put( DatabaseManagerUser.Columnas.CAPACIDAD, capacidadText);
        valuesT.put( DatabaseManagerUser.Columnas.ZONAUR, zonaurText);
        valuesT.put( DatabaseManagerUser.Columnas.MPIO, mpioText);
        valuesT.put( DatabaseManagerUser.Columnas.PROPIEDAD_TRAFO, propiedad_trafoText);
        valuesT.put( DatabaseManagerUser.Columnas.TIPO, tipoText);
        valuesT.put( DatabaseManagerUser.Columnas.LATI_TRAFO, lati_trafoText);
        valuesT.put( DatabaseManagerUser.Columnas.LONG_TRAFO, long_trafoText);
        valuesT.put( DatabaseManagerUser.Columnas.ALT_TRAFO, alt_trafoText);
        valuesT.put( DatabaseManagerUser.Columnas.TIPO_TRAFO, tipo_trafoText);
        valuesT.put( DatabaseManagerUser.Columnas.OBSERVACION_TRAFO, observacion_trafoText);
        valuesT.put( DatabaseManagerUser.Columnas.NRO_POSTES_TRAFO, nro_postes_trafoText);
        valuesT.put( DatabaseManagerUser.Columnas.USU_LEVANT_USU, usu_levant_usuText);
        valuesT.put( Constantes.ESTADO, 0 );
        valuesT.put( DatabaseManagerUser.Columnas.PENDIENTE_INSERCION, 1 );

        getContentResolver().update( ContractParaG.CONTENT_URI, valuesT,DatabaseManagerUser.Columnas.NOMB_TRAFO + "=?" ,new String[]{textCo.getText().toString()} );
        SyncAdapter.sincronizarAhora(this, true);

        if (Utilidades.materialDesign()) {
            finishAfterTransition();
            Intent intent = new Intent( frm_trafos_update.this, mainActivity.class );
            Bundle miBundle = new Bundle();
            miBundle.putString( "IDENT", usu_levant_usuText );
            intent.putExtras( miBundle );
            intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
            finish();
        }else {

            Intent intent = new Intent( frm_trafos_update.this, mainActivity.class );
            Bundle miBundle = new Bundle();
            miBundle.putString( "IDENT", usu_levant_usuText );
            intent.putExtras( miBundle );
            intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
            finish();
        }
    }

    //colocar en todas las actividades boton de atras
    @Override
    public boolean onSupportNavigateUp() {
       // onBackPressed();
        finish();
        return false;
    }
}


