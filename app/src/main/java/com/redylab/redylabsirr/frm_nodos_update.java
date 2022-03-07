package com.redylab.redylabsirr;

import android.Manifest;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.adaptadores.ContractParaG;
import com.redylab.redylabsirr.database.DatabaseManagerUser;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class frm_nodos_update extends AppCompatActivity{

    TextView tv_operario;
    int posicion3p ,posicion4p,posicion5p,posicion6p,posicion7p,posicion8p,posicion9p,posicion10p;
    String seleccion3p,seleccion4p,seleccion5p,seleccion6p,seleccion7p,seleccion8p,seleccion9p,seleccion10p,operario;
    EditText cmp_retenidas,cmp_antiguedad;
    RGps gps;
    double cmp_latitud_nodo,cmp_longitu_nodo,cmp_altitud_nodo;
    Button botonRegistro;
    TextView tvcodigo,tv_cod_post,txtLo,textLa,textAl;

    String vCodPoste,vCodTrafo,vAltura,vMaterial,vNroAco,vObsnodo,vEstr1,vTipEst1,vEstr2,vTipEst2,vEstr3,vTipEst3,vEstr4,vTipEst4,vlongnodo,vlatinodo,valtinodo;
    String vNroret,vAntig,vPropnodo,vCon1,vCl_fs_1,vCl_ne_1,vCl_ap_1,vNhil_1,vCon2,vCl_fs_2,vCl_ne_2,vCl_ap_2,vNhil_2,vCon3,vCl_fs_3,vCl_ne_3,vCl_ap_3,vNhil_3,vCon4,vCl_fs_4,vCl_ne_4,vCl_ap_4,vNhil_4,vUsuleva,operador;

    Spinner cmp_propiedad,cmp_altura_poste,cmp_material_poste,cmp_tipo_poste1,cmp_estuctura_poste12,cmp_tipo_poste2,cmp_estuctura_poste22,cmp_tipo_poste3,cmp_estuctura_poste32,cmp_tipo_poste4,cmp_estuctura_poste42;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.frm_nodos_update );

        //
        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        //} else {
        //    locationStart();
        //}
        //
        txtLo =  this.findViewById(R.id.txtLonp);
        textLa =  this.findViewById(R.id.textLatp);
        textAl =  this.findViewById(R.id.textAltip);
        //
        operarios();
        //
        //
        //
        // capturar el codigo del transformador  y operario
        tvcodigo= findViewById( R.id.codigoTrafo );
        Bundle extras = this.getIntent().getExtras();
        if(extras != null){

            vCodPoste = extras.getString(  "vcnodo");
            vCodTrafo = extras.getString( "vctraf");
            vAltura= extras.getString(  "valtur");
            vMaterial= extras.getString(  "vmater");
            vNroAco= extras.getString(  "vnroac");
            vObsnodo= extras.getString(  "vobsno");
            vEstr1= extras.getString(  "vestr1");
            vTipEst1= extras.getString(  "vtipe1");
            vEstr2= extras.getString(  "vestr2" );
            vTipEst2= extras.getString(  "vtipe2");
            vEstr3= extras.getString(  "vestr3");
            vTipEst3= extras.getString(  "vtipe3");
            vEstr4= extras.getString( "vestr4");
            vTipEst4= extras.getString(  "vtipe4");
            vlongnodo= extras.getString(  "vlonod");
            vlatinodo= extras.getString(  "vlatin");
            valtinodo= extras.getString(  "valtin");
            vNroret= extras.getString(  "vnroet");
            vAntig= extras.getString(  "vantig");
            vPropnodo= extras.getString(  "vpropn");
            vCon1= extras.getString(  "vconn1");
            vCl_fs_1= extras.getString(  "vclfs1");
            vCl_ne_1= extras.getString(  "vclne1");
            vCl_ap_1= extras.getString(  "vclap1");
            vNhil_1= extras.getString(  "vnhil1");
            vCon2= extras.getString(  "vconn2");
            vCl_fs_2= extras.getString(  "vclfs2");
            vCl_ne_2= extras.getString(  "vclne2");
            vCl_ap_2= extras.getString(  "vclap2");
            vNhil_2= extras.getString(  "vnhil2");
            vCon3= extras.getString(  "vconn3");
            vCl_fs_3= extras.getString(  "vclfs3");
            vCl_ne_3= extras.getString(  "vclne3");
            vCl_ap_3= extras.getString(  "vclap3");
            vNhil_3= extras.getString( "vnhil3");
            vCon4= extras.getString(  "vconn4");
            vCl_fs_4= extras.getString(  "vclfs4");
            vCl_ne_4= extras.getString(  "vclne4");
            vCl_ap_4= extras.getString(  "vclap4");
            vNhil_4= extras.getString(  "vnhil4");
            operador= extras.getString(  "IDENT");

        }
        Log.i("parametros","obervacion_nodo_ini:"+vObsnodo);
        /*Log.i("parametros","ESTRUCTURA_1:"+vEstr1);
        Log.i("parametros","ESTRUCTURA_2:"+vEstr2);
        Log.i("parametros","ESTRUCTURA_3"+vEstr3);
        Log.i("parametros","ESTRUCTURA_4"+vEstr4);*/


        txtLo.setText(vlongnodo);
        textLa.setText(vlatinodo);
        textAl.setText(valtinodo);


        tvcodigo.setText(vCodTrafo);
        //
        //
        //codigo poste
        tv_cod_post = findViewById( R.id.codPost );
        tv_cod_post.setText( vCodPoste );

        //
        //
        //altura
        cmp_altura_poste = findViewById( R.id.alturanodo );
        //
        //
        //material
        cmp_material_poste = findViewById( R.id.material );
        //
        //
        //retenidas
        cmp_retenidas = findViewById( R.id.retenida);
        cmp_retenidas.setText(vNroret);
        //
        //antiguedad
        cmp_antiguedad = findViewById( R.id.antiguedad );
        cmp_antiguedad.setText(vAntig);
        //
        //propiedad
        cmp_propiedad =  findViewById( R.id.propiedad );
        //
        cmp_estuctura_poste12 = findViewById(R.id.estuct12);
        cmp_estuctura_poste22 = findViewById(R.id.estuct22);
        cmp_estuctura_poste32 = findViewById(R.id.estuct32);
        cmp_estuctura_poste42 = findViewById(R.id.estuct42);

       // cmp_estuctura_poste12.setSelection( 3 );
        //vEstr1
        //
        // Tipo 1 de poste
        cmp_tipo_poste1 = findViewById( R.id.tipo1);
        cmp_tipo_poste1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                /*Obtienes el item actualmente seleccionado*/
                posicion3p = pos;
                seleccion3p = adapterView.getItemAtPosition(posicion3p).toString();
                if(posicion3p == 1){
                    //cmp_estuctura_poste1 = findViewById(R.id.estuct1);
                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipotrenzada , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste12.setAdapter(spinner_adapter);
                    //
                    cmp_estuctura_poste12.setSelection( obtenerPosicionItem( cmp_estuctura_poste12,vEstr1) );
                }else if(posicion3p == 2){
                    //cmp_estuctura_poste1 =  findViewById(R.id.estuct1);
                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipoabrierta , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste12.setAdapter(spinner_adapter);
                    //
                    cmp_estuctura_poste12.setSelection( obtenerPosicionItem( cmp_estuctura_poste12,vEstr1) );
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //
        cmp_estuctura_poste12.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView <?> adapterView, View view, int pos, long id ) {
                /*Obtienes el item actualmente seleccionado*/
                posicion4p = pos;
                seleccion4p = adapterView.getItemAtPosition(posicion4p).toString();
            }
            @Override
            public void onNothingSelected( AdapterView <?> parent ) {

            }
        });

        //

        // Tipo 2 de poste
        cmp_tipo_poste2 = findViewById( R.id.tipo2);
        cmp_tipo_poste2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                /*Obtienes el item actualmente seleccionado*/
                posicion5p = pos;
                seleccion5p = adapterView.getItemAtPosition(posicion5p).toString();
                if(posicion5p == 1){

                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipotrenzada , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste22.setAdapter(spinner_adapter);
                    //
                    cmp_estuctura_poste22.setSelection(obtenerPosicionItem(cmp_estuctura_poste22,vEstr2));
                }else if(posicion5p == 2){

                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipoabrierta , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste22.setAdapter(spinner_adapter);
                    //
                    cmp_estuctura_poste22.setSelection(obtenerPosicionItem(cmp_estuctura_poste22,vEstr2));

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //Tipo 2 de estructura de la red
        cmp_estuctura_poste22.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView <?> adapterView, View view, int pos, long id ) {
                /*Obtienes el item actualmente seleccionado*/
                posicion6p = pos;
                seleccion6p = adapterView.getItemAtPosition(posicion6p).toString();
            }
            @Override
            public void onNothingSelected( AdapterView <?> parent ) {

            }
        });

        //Tipo 2 de estructura de la red
        //cmp_estuctura_poste2 = findViewById( R.id.estuct2 );
        if(posicion6p == 0){
           seleccion6p ="";
        }

        //
        //
        // Tipo 3 de poste
        cmp_tipo_poste3 = findViewById( R.id.tipo3);
        cmp_tipo_poste3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                /*Obtienes el item actualmente seleccionado*/
                posicion7p = pos;
                seleccion7p = adapterView.getItemAtPosition(posicion7p).toString();
                if(posicion7p == 1){

                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipotrenzada , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste32.setAdapter(spinner_adapter);
                    //
                    cmp_estuctura_poste32.setSelection(obtenerPosicionItem(cmp_estuctura_poste32,vEstr3));

                }else if(posicion7p == 2){

                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipoabrierta , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste32.setAdapter(spinner_adapter);
                    //
                    cmp_estuctura_poste32.setSelection(obtenerPosicionItem(cmp_estuctura_poste32,vEstr3));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //Tipo 3 de estructura de la red
        cmp_estuctura_poste32.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView <?> adapterView, View view, int pos, long id ) {
                /*Obtienes el item actualmente seleccionado*/
                posicion8p = pos;
                seleccion8p = adapterView.getItemAtPosition(posicion8p).toString();
            }
            @Override
            public void onNothingSelected( AdapterView <?> parent ) {

            }
        });
        //
        //
        //Tipo 3 de estructura de la red
        //cmp_estuctura_poste3 = findViewById( R.id.estuct3 );
        if(posicion8p == 0){
            seleccion8p ="";
        }
        //
        //
        // Tipo 4 de poste
        cmp_tipo_poste4 = findViewById( R.id.tipo4);
        cmp_tipo_poste4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                /*Obtienes el item actualmente seleccionado*/
                posicion9p = pos;
                seleccion9p = adapterView.getItemAtPosition(posicion9p).toString();
                if(posicion9p == 1){

                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipotrenzada , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste42.setAdapter(spinner_adapter);
                    //
                    cmp_estuctura_poste42.setSelection(obtenerPosicionItem(cmp_estuctura_poste42,vEstr4));
                }else if(posicion9p == 2){

                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipoabrierta , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste42.setAdapter(spinner_adapter);
                    //
                    cmp_estuctura_poste42.setSelection(obtenerPosicionItem(cmp_estuctura_poste42,vEstr4));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //Tipo 4 de estructura de la red
        cmp_estuctura_poste42.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView <?> adapterView, View view, int pos, long id ) {
                /*Obtienes el item actualmente seleccionado*/
                posicion10p = pos;
                seleccion10p = adapterView.getItemAtPosition(posicion10p).toString();
            }
            @Override
            public void onNothingSelected( AdapterView <?> parent ) {

            }
        });

        //
        //
        //Tipo 4 de estructura de la red
        //cmp_estuctura_poste4 = findViewById(R.id.estuct4);
        if(posicion10p == 0){
            seleccion10p ="";
        }
        //
        //
        //
        //boton atras
        ImageButton img = findViewById( R.id.btnpatras2 );
        img.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                finish();
            }
        } );
        //
        //
        //boton registro
        botonRegistro = findViewById( R.id.btnsiguiente );
        botonRegistro.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                if(cmp_altura_poste.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione una Altura",Toast.LENGTH_LONG).show();
                }
                else if(cmp_material_poste.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione un Material",Toast.LENGTH_LONG).show();
                }
                else if(cmp_retenidas.getText().toString().trim().isEmpty()){
                    Toast.makeText(getBaseContext(),"Error! Completar la Nro. de Retenidas",Toast.LENGTH_LONG).show();
                }
                else if(cmp_antiguedad.getText().toString().trim().isEmpty()){
                    Toast.makeText(getBaseContext(),"Error! Completar la Antiguedad",Toast.LENGTH_LONG).show();
                }
                else if(cmp_propiedad.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione la Propiedad",Toast.LENGTH_LONG).show();
                }
                else if(cmp_tipo_poste1.getSelectedItem().toString().trim().equals("SELECCIONE")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione una opción del Tipo 1 ",Toast.LENGTH_LONG).show();
                }
                else if(seleccion4p.equals(" ")){
                    Toast.makeText(getBaseContext(),"Error! Seleccione una Estructura",Toast.LENGTH_LONG).show();
                }

                else {
                    actualizarPostea();
                }

            }
        });
        //
        //
        int posestr1=0;
        int postamaño = obtenerPosicionItem(cmp_altura_poste,vAltura.replace( "Mts","" ));
        cmp_altura_poste.setSelection(postamaño);
        int posmaterial = obtenerPosicionItem(cmp_material_poste,vMaterial);
        cmp_material_poste.setSelection(posmaterial);
        int pospropiedad = obtenerPosicionItem(cmp_propiedad,vPropnodo);
        cmp_propiedad.setSelection(pospropiedad);

        int postpestr1 = obtenerPosicionItem(cmp_tipo_poste1,vTipEst1);
        cmp_tipo_poste1.setSelection(postpestr1);
        int postpestr2 = obtenerPosicionItem(cmp_tipo_poste2,vTipEst2);
        cmp_tipo_poste2.setSelection(postpestr2);
        int postpestr3 = obtenerPosicionItem(cmp_tipo_poste3,vTipEst3);
        cmp_tipo_poste3.setSelection(postpestr3);
        int postpestr4 = obtenerPosicionItem(cmp_tipo_poste4,vTipEst4);
        cmp_tipo_poste4.setSelection(postpestr4);

       /* Log.i("parametros","ESTRUCTURA_1:"+vEstr1+" pos:"+obtenerPosicionItem(cmp_estuctura_poste12,vEstr1));
        Log.i("parametros","ESTRUCTURA_2:"+vEstr2+" pos:"+obtenerPosicionItem(cmp_estuctura_poste22,vEstr2));
        Log.i("parametros","ESTRUCTURA_3:"+vEstr3+" pos:"+obtenerPosicionItem(cmp_estuctura_poste32,vEstr3));
        Log.i("parametros","ESTRUCTURA_4:"+vEstr4+" pos:"+obtenerPosicionItem(cmp_estuctura_poste42,vEstr4));*/

    }

    //=========================================================================================//
    //=================================MÉTODO QUE REALIZA LA TAREA=============================//
    //=========================================================================================//

    //Método para obtener la posición de un ítem del spinner
    public static int obtenerPosicionItem(Spinner spinner, String datobuscado) {

       // Log.i("parametros","datobuscado:"+datobuscado+ " sipnner:"+spinner);
        //Log.i("parametros","cant_opciones:"+spinner.getCount());
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String observacion`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(datobuscado)) {
                //spinner.setSelection( i );
//               Log.i("parametros"," pon:"+i);
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }
    //=========================================================================================//

    private void operarios(){
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
        Cursor cursoroperario = databaseManager.getDb().rawQuery( "select distinct(usu_levant_usu) from trafo where nomb_trafo="+tvcodigo, null);
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
    private void actualizarPostea() {


        // Pasaremos de la actividad actual a OtraActivity
        Intent intent = new Intent(frm_nodos_update.this, frm_fnb_update.class);

        intent.putExtra("codigotrafo", tvcodigo.getText().toString());
        intent.putExtra("codigoposte", tv_cod_post.getText().toString());
        intent.putExtra("alturaposte", String.valueOf(cmp_altura_poste.getSelectedItem().toString()));
        intent.putExtra("materialposte", String.valueOf(cmp_material_poste.getSelectedItem().toString()));
        intent.putExtra("vobsno",vObsnodo);
        intent.putExtra("estructura1", seleccion4p);
        intent.putExtra("tipo1", seleccion3p);
        intent.putExtra("estructura2", seleccion6p);
        intent.putExtra("tipo2", seleccion5p);
        intent.putExtra("estructura3", seleccion8p);
        intent.putExtra("tipo3", seleccion7p);
        intent.putExtra("estructura4", seleccion10p);
        intent.putExtra("tipo4", seleccion9p);
        intent.putExtra("retenidas", cmp_retenidas.getText().toString());
        intent.putExtra("antiguedad", cmp_antiguedad.getText().toString());
        intent.putExtra("propiedad_nodo", String.valueOf(cmp_propiedad.getSelectedItem().toString()));
        intent.putExtra("vconn1", vCon1);
        intent.putExtra("vclfs1", vCl_fs_1);
        intent.putExtra("vclne1", vCl_ne_1);
        intent.putExtra("vclap1", vCl_ap_1);
        intent.putExtra("vnhil1", vNhil_1);
        intent.putExtra("vconn2", vCon2);
        intent.putExtra("vclfs2", vCl_fs_2);
        intent.putExtra("vclne2", vCl_ne_2);
        intent.putExtra("vclap2", vCl_ap_2);
        intent.putExtra("vnhil2", vNhil_2);
        intent.putExtra("vconn3", vCon3);
        intent.putExtra("vclfs3", vCl_fs_3);
        intent.putExtra("vclne3", vCl_ne_3);
        intent.putExtra("vclap3", vCl_ap_3);
        intent.putExtra("vnhil3", vNhil_3);
        intent.putExtra("vconn4", vCon4);
        intent.putExtra("vclfs4", vCl_fs_4);
        intent.putExtra("vclne4", vCl_ne_4);
        intent.putExtra("vclap4", vCl_ap_4);
        intent.putExtra("vnhil4", vNhil_4);

        intent.putExtra("IDENT", operador);

        startActivity(intent);
        frm_nodos_update.this.finish();
     }

    //
    //



}
