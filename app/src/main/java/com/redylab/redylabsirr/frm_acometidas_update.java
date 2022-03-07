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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.Utilidades.Constantes;
import com.redylab.redylabsirr.Utilidades.Utilidades;
import com.redylab.redylabsirr.adaptadores.ContractParaG;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Ntrafo;
import com.redylab.redylabsirr.entity.Trafo;
import com.redylab.redylabsirr.sync.SyncAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class frm_acometidas_update extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    private static final String TAG = "frm_acometidas_update";
    Spinner sdireccion,cmp_calibreaco;
    ArrayList<String> listaTrafos;
    ArrayList<Ntrafo> trafosList;
    String barrio,resul_operario, resultado_cod_aco,selectionacometida1;
    private static int resultado_codaco;
    Integer positionA1,positionA2,positionA3,positionA4,positionA5,positionA6,positionA7;
    String selectionA1,selectionA2,selectionA3,selectionA4,selectionA5,selectionA6,selectionA7,codposte,codtr,tip_cas,tip_ass,tip_mbts,tabler;
    EditText cmp_contador,cmp_marcaconta;
    String f_ch1R="",f_ch1T="",f_ch1S="",f_ch2R="",f_ch2S="",f_ch2T="",resul_ch1,resul_ch2;
    String operario;
    CheckBox cmp_ch_fsr,cmp_ch_fst,cmp_ch_fss,cmp_ch_fstbr,cmp_ch_fstbt,cmp_ch_fstbs;
    Button botonRegistro;
    TextView copostet,verificar,txtLo,textLa,textAl;
    String codu_cdaco, codu_cont,codu_tpmd,codu_dir,codu_serv,codu_obs,codu_long,codu_lati,codu_alti,codu_contm,codu_tipca,codu_tipas,codu_tiprst,codu_calb,codu_tabl,codu_fastb,codu_ident;

    private LinearLayout ltitulofasestablero,lfasesTablero;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.frm_acometidas_update );
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
        //
        final Spinner sdireccion = (Spinner) findViewById(R.id.direccionaco);
        //codPoste
        copostet = (TextView) findViewById( R.id.codigoposteu );
        Bundle miBundle2 = this.getIntent().getExtras();
        if (miBundle2 != null) {

            codu_cdaco = miBundle2.getString( "u_codaco");
            codposte = miBundle2.getString( "u_codPost");
            codu_cont = miBundle2.getString( "u_cont");
            codu_tpmd = miBundle2.getString( "u_tipom");
            codu_dir = miBundle2.getString( "u_dir");
            codu_serv = miBundle2.getString( "u_serv");
            codu_obs = miBundle2.getString( "u_obs");
            codu_long = miBundle2.getString( "u_lon");
            codu_lati = miBundle2.getString( "u_lat");
            codu_alti = miBundle2.getString( "u_alt");
            codu_contm = miBundle2.getString( "u_contmar");
            codu_tipca = miBundle2.getString( "u_tipca");
            codu_tipas = miBundle2.getString( "u_tipas");
            codu_tiprst = miBundle2.getString( "u_tiprst");
            codu_calb = miBundle2.getString( "u_calibre");
            codu_tabl = miBundle2.getString( "u_tablero");
            codu_fastb = miBundle2.getString( "u_fastablero");
            codu_ident = miBundle2.getString( "IDENT");

            //
            //
            operario = codu_ident;
            copostet.setText( codposte );
            barrio = codposte.substring(1,2);
            selectionacometida1 =codu_obs ;
            //Log.i( TAG, "dato1 : " + codu_tiprst);
            //
            operarios();
            //
            nroacometida(codposte);
           //
            //
            if(barrio.equals("M")){

                ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.barrios_mocoa , android.R.layout.simple_spinner_item);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sdireccion.setAdapter(spinner_adapter);
                sdireccion.setSelection( obtenerPosicionItem( sdireccion,codu_dir) );
            }else if(barrio.equals("V")){

                ArrayAdapter adapter = ArrayAdapter.createFromResource( this, R.array.barrrios_villa , android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sdireccion.setAdapter(adapter);
                sdireccion.setSelection( obtenerPosicionItem( sdireccion,codu_dir) );
            }else if(barrio.equals("G")){

                ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.barrios_guzman , android.R.layout.simple_spinner_item);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sdireccion.setAdapter(spinner_adapter);
                sdireccion.setSelection( obtenerPosicionItem( sdireccion,codu_dir) );
            }else if(barrio.equals("P")){

                ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.barrios_piamonte , android.R.layout.simple_spinner_item);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sdireccion.setAdapter(spinner_adapter);
                sdireccion.setSelection( obtenerPosicionItem( sdireccion,codu_dir) );
            }else if(barrio.equals("O")){

                ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.barrios_orito , android.R.layout.simple_spinner_item);
                spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sdireccion.setAdapter(spinner_adapter);
                sdireccion.setSelection( obtenerPosicionItem( sdireccion,codu_dir) );
            }

        }

        txtLo.setText( codu_long);
        textLa.setText( codu_lati );
        textAl.setText(codu_alti );
        //
        //
        verificar = (TextView) this.findViewById( R.id.cod_acometida );
        //
        //
        //numero de contador
        cmp_contador = (EditText) this.findViewById( R.id.contador );
        cmp_contador.setText( codu_cont );
        //
        //
        // marca contador
        cmp_marcaconta = (EditText) this.findViewById( R.id.marcacon );
        cmp_marcaconta.setText( codu_contm );
        //
        //
        //observaciones acometida
        String[] arrayu ={"NINGUNA",
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
        cmp_observa_aco.setItems(arrayu);
        cmp_observa_aco.setListener(this);
        String vob ="";
        String vobb ="";
        List<String> list = new ArrayList<>();
        //
        if (codu_obs.contains("-")) {
            // Hacer split.
            int cant=codu_obs.split("-").length;
            String[] parts = codu_obs.split("-");
            for ( int i = 0; i < cant; i++){
                vobb=String.valueOf( parts[i].toString().trim());
                for(int j = 0; j < 11; j++) {
                    vob = String.valueOf( arrayu[j].toString() );
                    if(vob.equals( vobb )) {
                        list.add(vob);

                    }
                }
            }
            //Log.i( "parametro cmp_observa: ", String.valueOf((list.toString().replace( "[","" ).replace( "]" ,""))) );
            cmp_observa_aco.setSelection(list);

        } else {
            vobb = codu_obs;
            for (int j = 0; j < 11; j++) {
                vob = String.valueOf( arrayu[j].toString() );
                if (vob.equals( vobb )) {
                    list.add( vob );
                }
                cmp_observa_aco.setSelection( list );
            }
        }

        //
        //
        cmp_calibreaco = (Spinner) this.findViewById( R.id.calibreacometida );
        //
        cmp_calibreaco.setSelection( obtenerPosicionItem( cmp_calibreaco,codu_calb) );
        //
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
        //
        ssectorac.setSelection( obtenerPosicionItem( ssectorac,codu_serv) );
        //
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
        //
        sCA.setSelection( obtenerPosicionItem( sCA,codu_tipca) );
        Log.i( TAG, "prueba  ca: " + codu_tipca);
        //
        sCA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> adapterView, View view, int pos, long id)
            {
                positionA3 = pos;
                selectionA3 = adapterView.getItemAtPosition(positionA3).toString();
                Log.i( TAG, "prueba  ca_2: " + selectionA3);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //AEREA - SUBTERRANEA
        final Spinner sAS = (Spinner) this.findViewById(R.id.as);
        //
        sAS.setSelection( obtenerPosicionItem( sAS,codu_tipas) );
        //
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
        //
        sMBT.setSelection( obtenerPosicionItem( sMBT,codu_tpmd) );
        //
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
        sTBLRO.setSelection( obtenerPosicionItem( sTBLRO,codu_tabl) );
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
        String opa1="",opa2="",opa3="";
       // Log.i( TAG, "valorrst : " + codu_tiprst);
        int vrtiprst = codu_tiprst.length() ;
        // condicion de chequeado para fases de acometida
        if( vrtiprst == 3){
            cmp_ch_fsr.setChecked( true );
            cmp_ch_fss.setChecked( true );
            cmp_ch_fst.setChecked( true );
            resul_ch1="RST";
        }else if(vrtiprst == 2) {
            String opc1 = codu_tiprst.substring( 0,1 );
            String opc2 = codu_tiprst.substring( 1,2 );

             if (opc1.equals("R")){
                 cmp_ch_fsr.setChecked( true );
                 opa1="R";
             }else if(opc1.equals("T")){
                 cmp_ch_fst.setChecked( true );
                 opa1="T";
             } else if(opc1.equals("S")){
                 cmp_ch_fss.setChecked( true );
                 opa1="S";
             }
            if (opc2.equals("R")){
                cmp_ch_fsr.setChecked( true );
                opa2="R";
            }else if(opc2.equals("T")){
                cmp_ch_fst.setChecked( true );
                opa2="T";
            } else if(opc2.equals("S")){
                cmp_ch_fss.setChecked( true );
                opa2="S";
            }
            resul_ch1=opa1+opa2;
        }else if(vrtiprst == 1){
            String opc3 = codu_tiprst;
            if (opc3.equals("R")){
                cmp_ch_fsr.setChecked( true );
                opa3="R";
            }else if(opc3.equals("T")){
                cmp_ch_fst.setChecked( true );
                opa3="T";
            } else if(opc3.equals("S")){
                cmp_ch_fss.setChecked( true );
                opa3="S";
            }
            resul_ch1=opa3;
        }
        //
        // condicion de chequeado para fases de la tablero
        String op1="",op2="",op3="";
        if(codu_tabl.equals( "SI" )){

            if(codu_fastb.length() == 3){
                //Log.i( TAG, "oper ftbl2 : " + codu_fastb.length());
                cmp_ch_fstbr.setChecked( true );
                cmp_ch_fstbs.setChecked( true );
                cmp_ch_fstbt.setChecked( true );
                resul_ch2="RST";
            }else if(codu_fastb.length() == 2) {
                String opc1 = codu_fastb.substring( 0,1 );
                String opc2 = codu_fastb.substring( 1,2 );

                if (opc1.equals("R")){
                    cmp_ch_fstbr.setChecked( true );
                    op1="R";
                }else if(opc1.equals("T")){
                    cmp_ch_fstbt.setChecked( true );
                    op1 ="T";
                } else if(opc1.equals("S")){
                    cmp_ch_fstbs.setChecked( true );
                    op1="S";
                }

                if (opc2.equals("R")){
                    cmp_ch_fstbr.setChecked( true );
                    op2="R";
                }else if(opc2.equals("T")){
                    cmp_ch_fstbt.setChecked( true );
                    op2="T";
                } else if(opc2.equals("S")){
                    cmp_ch_fstbs.setChecked( true );
                    op2="S";
                }
                resul_ch2=op1+op2;

            }else if(codu_fastb.length()== 1){
                String opc3 = codu_fastb;
                if (opc3.equals("R")){
                    cmp_ch_fstbr.setChecked( true );
                    op3="R";
                }else if(opc3.equals("T")){
                    cmp_ch_fstbt.setChecked( true );
                    op3="T";
                } else if(opc3.equals("S")){
                    cmp_ch_fstbs.setChecked( true );
                    op3="S";
                }
                resul_ch2=op3;
            }
            lfasesTablero.setVisibility(View.VISIBLE);
        }else {
            lfasesTablero.setVisibility(View.GONE);
        }
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

    }// TERMINA EL ONCREATE
    //

    @Override
    public void selectedIndices( List <Integer> indices ) {

    }

    @Override
    public void selectedStrings( List <String> strings ) {
        String valobser;
        valobser = strings.toString().replaceAll( ",","-" );
        selectionacometida1 = valobser;
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




    private void registarAcometida() {
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser( getApplicationContext() );

        // Toast.makeText(getBaseContext(),"fases Acometida "+resul_ch1+" fases Tablero "+resul_ch2,Toast.LENGTH_SHORT).show();

        String observacion_trafo ="";
        if (selectionacometida1 != null) {
           // Log.i( TAG, "selectionacometida1 : " + selectionacometida1);
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

        //Log.i( TAG, "seleccion6 : " + selectionA6);
        if(selectionA6.equals("NO")){
            resul_ch2=" ";
        }
        // Log.i( TAG, "seleccion5 : " + observacion_trafo);
        //
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


        String cont_actText = cmp_contador.getText().toString();
        String id_nodo_acometidaText = codposte;//
        String direccionText = selectionA1;
        String cont_marcaText = cmp_marcaconta.getText().toString();
        String tip_caText = tip_cas;
        String tip_asText = tip_ass;
        String tip_mbtText = tip_mbts;
        String tip_rstText = resul_ch1;
        String calibreText = selectionA7;
        String tableroText = tabler;
        String fas_tableroText =resul_ch2;
        String tip_servicioText = selectionA2;
        String observacion_usuText = observacion_trafo;
        ContentValues values=new ContentValues();

        //ingreso de datos del trafo
        values.put( DatabaseManagerUser.Columnas.CONT_ACT, cont_actText);
        values.put( DatabaseManagerUser.Columnas.ID_NODO_ACOMETIDA, id_nodo_acometidaText);
        values.put( DatabaseManagerUser.Columnas.DIRECCION, direccionText);
        values.put( DatabaseManagerUser.Columnas.CONT_MARCA, cont_marcaText);
        values.put( DatabaseManagerUser.Columnas.TIP_CA, tip_caText);
        values.put( DatabaseManagerUser.Columnas.TIP_AS, tip_asText);
        values.put( DatabaseManagerUser.Columnas.TIP_MBT, tip_mbtText);
        values.put( DatabaseManagerUser.Columnas.TIP_RST, tip_rstText);
        values.put( DatabaseManagerUser.Columnas.CALIBRE, calibreText);
        values.put( DatabaseManagerUser.Columnas.TABLERO, tableroText);
        values.put( DatabaseManagerUser.Columnas.FAS_TABLERO, fas_tableroText);
        values.put( DatabaseManagerUser.Columnas.TIP_SERVICIO, tip_servicioText);
        values.put( DatabaseManagerUser.Columnas.OBSERVACION_USU, observacion_usuText);
        values.put( Constantes.ESTADO, 0 );
        values.put( DatabaseManagerUser.Columnas.PENDIENTE_INSERCION, 1 );

        getContentResolver().update( ContractParaG.CONTENT_URI, values,DatabaseManagerUser.Columnas.ACOMETIDA_CORRESP + "=?" ,new String[]{codu_cdaco} );
        SyncAdapter.sincronizarAhora(this, true);

        if (Utilidades.materialDesign()) {
            finishAfterTransition();
            Intent intent = new Intent( frm_acometidas_update.this, mainActivity.class );
            Bundle miBundle = new Bundle();
            miBundle.putString( "IDENT", (operario) );
            intent.putExtras( miBundle );
            intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
            finish();
        }else {

            Intent intent = new Intent( frm_acometidas_update.this, mainActivity.class );
            Bundle miBundle = new Bundle();
            miBundle.putString( "IDENT", ( operario ) );
            intent.putExtras( miBundle );
            intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity( intent );
            finish();
        }

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
