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


public class frm_nodos extends AppCompatActivity{

    private static final String TAG ="frm_nodos" ;
    TextView tv_operario;
    int posicion3p ,posicion4p,posicion5p,posicion6p,posicion7p,posicion8p,posicion9p,posicion10p;
    String seleccion3p,seleccion4p,seleccion5p,seleccion6p,seleccion7p,seleccion8p,seleccion9p,seleccion10p,operario;
    EditText cmp_retenidas,cmp_antiguedad;
    RGps gps;
    Button botonRegistro;
    TextView tvcodigo,tv_cod_post,txtLo,textLa,textAl;
    String vcodigo;
    Spinner cmp_propiedad,cmp_altura_poste,cmp_material_poste,cmp_tipo_poste1,cmp_estuctura_poste1,cmp_tipo_poste2,cmp_estuctura_poste2,cmp_tipo_poste3,cmp_estuctura_poste3,cmp_tipo_poste4,cmp_estuctura_poste4;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.frm_nodos );

        //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        } else {
            locationStart();
        }
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
            vcodigo= extras.getString( "codigotrafo");
        }
        tvcodigo.setText(vcodigo);
        //
        //
        //codigo poste
        cargarNodos(vcodigo);
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
        //
        //
        //antiguedad
        cmp_antiguedad = findViewById( R.id.antiguedad );
        //
        //
        //propiedad
        cmp_propiedad =  findViewById( R.id.propiedad );
        //
        //
       cmp_estuctura_poste1 = findViewById(R.id.estuct1);
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
                    cmp_estuctura_poste1 = findViewById(R.id.estuct1);
                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipotrenzada , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste1.setAdapter(spinner_adapter);
                }else if(posicion3p == 2){
                    cmp_estuctura_poste1 =  findViewById(R.id.estuct1);
                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipoabrierta , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste1.setAdapter(spinner_adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        cmp_estuctura_poste1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        //
        cmp_estuctura_poste2 = findViewById(R.id.estuct2);
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
                    cmp_estuctura_poste2.setAdapter(spinner_adapter);
                }else if(posicion5p == 2){

                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipoabrierta , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste2.setAdapter(spinner_adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //Tipo 2 de estructura de la red
        cmp_estuctura_poste2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
           seleccion6p ="0";
        }

        //
        //
        cmp_estuctura_poste3 = findViewById(R.id.estuct3);
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
                    cmp_estuctura_poste3.setAdapter(spinner_adapter);
                }else if(posicion7p == 2){

                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipoabrierta , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste3.setAdapter(spinner_adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //Tipo 3 de estructura de la red
        cmp_estuctura_poste3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            seleccion8p ="0";
        }
        //
        //
        cmp_estuctura_poste4 = findViewById(R.id.estuct4);
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
                    cmp_estuctura_poste4.setAdapter(spinner_adapter);
                }else if(posicion9p == 2){

                    ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( getBaseContext(), R.array.tipoabrierta , android.R.layout.simple_spinner_item);
                    spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmp_estuctura_poste4.setAdapter(spinner_adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            { }
        });
        //
        //
        //Tipo 4 de estructura de la red
        cmp_estuctura_poste4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            seleccion10p ="0";
        }
        //
        //
        // localizacion trafo
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        }else {
            // create class object
            gps = new RGps(this);
            // check if GPS enabled
            if(gps.canGetLocation()){
                cmp_latitud_nodo = gps.getLatitude();
                cmp_longitu_nodo = gps.getLongitude();
                cmp_altitud_nodo = gps.getAltitude();
            }else{
                gps.showSettingsAlert();
            }
        }*/
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
                     registarPostea();
                }

            }
        });
        //
        //
        //

    }
    // cargar el codigo que le corresponde del nodo.. automaticamente en el TextView
    private void cargarNodos(String cod_traf) {
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
        Cursor cursornodos = databaseManager.getDb().rawQuery( "SELECT (case when length(nomb_nodo) = 1 and length(id_trafo_nodo) = 1 then 'P' || substr(nomb_trafo,2,1) || substr(nomb_trafo,5,4) || '0001' else (case when length(nomb_nodo) > 1 and length(id_trafo_nodo)> 1 then (case when max(cast(substr(nomb_nodo,7,4) as integer)) < 9 then 'P' || substr(nomb_nodo,2,1) || substr(nomb_nodo,3,4) || 000 || 000 || 000 || (max(cast(substr(nomb_nodo,7,4) as integer))+1) else 'P' || substr(nomb_nodo,2,1) || substr(nomb_nodo,3,4) || 000 ||000 || (max(cast(substr(nomb_nodo,7,4) as integer))+1) end) end) end)as nw_cod_poste FROM "+ContractParaG.TRAFO+" WHERE id_trafo_nodo= '"+cod_traf+"' OR nomb_trafo='"+cod_traf+"'", null);

        while(cursornodos.moveToNext()) {
            String resultado = cursornodos.getString( 0 );
            tv_cod_post = findViewById( R.id.codPost );
            tv_cod_post.setText( resultado );
        }
        cursornodos.close();
        databaseManager.getDb().close();
    }
    private void operarios(){
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
        Cursor cursoroperario = databaseManager.getDb().rawQuery( "select distinct(usu_levant_usu) from trafo where id_trafo_nodo='"+tvcodigo+"' or nomb_trafo='"+tvcodigo+"'", null);
        //Cursor cursoroperario = databaseManager.getDb().rawQuery( "select distinct(usu_levant_usu) from trafo where length(usu_levant_usu) >1 and (id_trafo_nodo='"+vcodigo+"' OR nomb_trafo='"+vcodigo+"'", null);
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
    //
    // ESTA FUNCION SE AGREGO PARA HACER MAS FACIL LAS COORDENADAS //
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        frm_nodos.Localizacion Local = new frm_nodos.Localizacion();
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
        mainActivity frm_nodos;
        public mainActivity getMainActivity() {
            return frm_nodos;
        }

        public void setMainActivity(mainActivity mainActivity) {
            this.frm_nodos = mainActivity;
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
            String cmp_latitud_nodo,cmp_longitu_nodo,cmp_altitud_nodo;
            String Textxx = "Lat = "+ loc.getLatitude() + "n Long = " + loc.getLongitude()+"n Alt = "+loc.getAltitude()+"n Pre = "+loc.getAccuracy();
            //Log.i( TAG, "seleccion5 : " + Textxx);

            // String Text = "Lat = "+ loc.getLatitude() + "n Long = " + loc.getLongitude()+"n Alt = "+loc.getAltitude()+"n Pre = "+loc.getAccuracy();
            cmp_latitud_nodo = ""+loc.getLatitude();
            cmp_longitu_nodo = ""+loc.getLongitude();
            cmp_altitud_nodo = ""+loc.getAltitude();

            txtLo.setText(cmp_longitu_nodo );
            textLa.setText(cmp_latitud_nodo );
            textAl.setText(cmp_altitud_nodo );


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
        public void setMainActivity( frm_nodos frm_nodos ) {
        }
    }
    private void registarPostea() {


        // Pasaremos de la actividad actual a OtraActivity
        Intent intent = new Intent(frm_nodos.this, frm_fnb.class);

        intent.putExtra("codigotrafo", tvcodigo.getText().toString());
        intent.putExtra("codigoposte", tv_cod_post.getText().toString());
        intent.putExtra("alturaposte", String.valueOf(cmp_altura_poste.getSelectedItem().toString()));
        intent.putExtra("materialposte", String.valueOf(cmp_material_poste.getSelectedItem().toString()));
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

        Log.i( TAG, "RETENIDA : " + cmp_retenidas.getText().toString());

        startActivity(intent);
        frm_nodos.this.finish();
     }

    //
    //



}
