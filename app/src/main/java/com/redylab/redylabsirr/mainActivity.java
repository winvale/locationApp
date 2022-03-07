package com.redylab.redylabsirr;

import android.Manifest;
import android.content.ContentResolver;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.Utilidades.Constantes;
import com.redylab.redylabsirr.adaptadores.ContractParaG;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Users;
import com.redylab.redylabsirr.sync.SyncAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.redylab.redylabsirr.sync.SyncAdapter.PROJECTION;


public class mainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks <Cursor> {

   // ArrayList<Trafo> listDatos;
    private RecyclerView recycler;
    private DatabaseManagerUser databaseManagerUser;
    private Users itemUsuario;
    private String ident;
    private Cursor comprobar;
    private LinearLayoutManager layoutManager;
    Menu menua;
    ContentResolver resolver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //
        //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1);
        }else {
            locationStart();
        }

        //
        getSupportLoaderManager().initLoader(0, null, this);
        SyncAdapter.inicializarSyncAdapter(this);
        //
        //
        //se agrego codigo
        Bundle b = getIntent().getExtras();
        if(b != null) {
            ident = b.getString( "IDENT" );

        }

        databaseManagerUser= new DatabaseManagerUser(getApplicationContext());
        itemUsuario = databaseManagerUser.getUsuario(ident); // encuentra al usuario registrado en la bbdd

        /*View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        ((TextView) header.findViewById(R.id.tv_nombre_usuario_menu)).setText(itemUsuario.getNombre());
        ((TextView) header.findViewById(R.id.tv_cedula)).setText((itemUsuario.getCedula()));
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //
        //
        //
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.vertrafos);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntent= new Intent(mainActivity.this,ListaTrafosRecycler.class);
                Bundle miBundle=new Bundle();
                miBundle.putInt("codigoperador", Integer.parseInt( itemUsuario.getCedula()));
                miIntent.putExtras(miBundle);
                startActivity( miIntent );
            }
        });
        //
        //
        //
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.verpostes);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntent= new Intent(mainActivity.this,ListaPostesRecycler.class);
                Bundle miBundle=new Bundle();
                miBundle.putInt("codigoperador", Integer.parseInt( itemUsuario.getCedula()));
                miIntent.putExtras(miBundle);
                startActivity( miIntent );

            }
        });
        //
        //
        //
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.veracometidas);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntent= new Intent(mainActivity.this,ListaAcometidasRecycler.class);
                Bundle miBundle=new Bundle();
                miBundle.putInt("codigoperador", Integer.parseInt( itemUsuario.getCedula()));
                miIntent.putExtras(miBundle);
                startActivity( miIntent );

            }
        });

        //
        //
        //

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        //drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //consulta si usuario es Administrador
        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(getApplicationContext());
        Log.i("tag","ident:"+ident);
        String nombr="" ,cedul="",estad="",tipo_usu="";
        //TextView nombx = findViewById( R.id.tv_nombre_usuario_menu );

        Cursor comprobar = databaseManager.getDb().rawQuery("SELECT cedula,nombre,tipo_usu,estado FROM users WHERE cedula='"+ident+"'",null);
        while(comprobar.moveToNext()) {
             cedul = comprobar.getString( 0 );
             nombr = comprobar.getString( 1 );
             tipo_usu = comprobar.getString( 2 );
             estad = comprobar.getString( 3 );

            if (tipo_usu.equals( "A" )) {
                menua = navigationView.getMenu();
                MenuItem visivel = menua.findItem( R.id.usuarios );
                //nombx.setText( String.valueOf( nombr ) );
                visivel.setVisible( true );
            } else {
                menua = navigationView.getMenu();
                MenuItem visivel = menua.findItem( R.id.usuarios );
               // nombx.setText( String.valueOf( nombr ) );
                visivel.setVisible( false );
            }
        }comprobar.close();
        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        ((TextView) header.findViewById(R.id.tv_nombre_usuario_menu)).setText("USUARIO: "+nombr);
        //((TextView) header.findViewById(R.id.tv_cedula)).setText((itemUsuario.getCedula()));





        //



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen( GravityCompat.START)) {
            drawer.closeDrawer( GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.trafo) {
            //llamamos formulario de trafos
            Intent miIntent= new Intent(mainActivity.this,frm_trafos.class);
            Bundle miBundle=new Bundle();
            miBundle.putInt("nombreOperario", Integer.parseInt( itemUsuario.getCedula() ) );
            miIntent.putExtras(miBundle);
            startActivity( miIntent );

        } else if (id == R.id.poste) {
            //llamamos formulario de trafos
            Intent miIntent= new Intent(mainActivity.this,ListaTrafosRecycler2.class);
            Bundle miBundle=new Bundle();
            miBundle.putInt("codigoperador", Integer.parseInt( itemUsuario.getCedula()));
            miIntent.putExtras(miBundle);
            startActivity( miIntent );

        } else if (id == R.id.acometida) {
            //llamamos formulario de trafos
            Intent miIntent= new Intent(mainActivity.this,ListaPostesRecycler2.class);
            Bundle miBundle=new Bundle();
            miBundle.putInt("codigoperador", Integer.parseInt( itemUsuario.getCedula()));
            miIntent.putExtras(miBundle);
            startActivity( miIntent );

        } else if (id == R.id.sincronizar) {
            //llamamos sincronizar remoto
            //SyncAdapter.inicializarSyncAdapter(this);
            if(!isConnected( this )){
                Toast.makeText(this,"No se Pudo Sincronizar, verifique el acceso a Internet e Intente nuevamente",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText( this, "Verificando Datos a Sincronizar.....", Toast.LENGTH_LONG ).show();
                Cursor c = obtenerRegistrosSucios();
                if (c.getCount() > 0) {
                    Toast.makeText( this, "Sincronizando Datos...", Toast.LENGTH_LONG ).show();
                    SyncAdapter.sincronizarAhora( this, true );
                    Toast.makeText( this, "Sincronizacion de Datos Completa.", Toast.LENGTH_LONG ).show();
                }else{
                    Toast.makeText( this, "  No hay Datos Pendientes para Sincronizar .", Toast.LENGTH_LONG ).show();
                }
            }

        }else if (id == R.id.usuarios) {
            //llamamos listado de usuarios
            Intent miIntent= new Intent(mainActivity.this,ListaUsuariosRecycler.class);
            Bundle miBundle=new Bundle();
            miBundle.putInt("codigoperador", Integer.parseInt( itemUsuario.getCedula()));
            miIntent.putExtras(miBundle);
            startActivity(miIntent);
        }else if (id == R.id.salir) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }

        }else if (id == R.id.salir) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();

            }finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer( GravityCompat.START);
        return true;
    }
    public boolean isConnected( Context context ) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()&& netinfo.getState() == NetworkInfo.State.CONNECTED ) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo( ConnectivityManager.TYPE_WIFI );
            android.net.NetworkInfo mobile = cm.getNetworkInfo( ConnectivityManager.TYPE_MOBILE );

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) {

                return true;
            } else {

                return false;
            }
        } else {

            return false;
        }
    }
    private Cursor obtenerRegistrosSucios() {
        Uri uri = ContractParaG.CONTENT_URI;
        String selection = DatabaseManagerUser.Columnas.PENDIENTE_INSERCION + "=? AND "
                + Constantes.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaG.ESTADO_SYNC + ""};
        return getContentResolver().query(uri, PROJECTION, selection, selectionArgs, null);
    }
    @Override
    public Loader <Cursor> onCreateLoader( int id,  Bundle args ) {

        return new CursorLoader(this,
                ContractParaG.CONTENT_URI,
                null, null, null, "substr(nomb_trafo,5,4) ASC");

    }

    @Override
    public void onLoadFinished( @NonNull Loader <Cursor> loader, Cursor data ) {
        //adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset( @NonNull Loader <Cursor> loader ) {
        //adapter.swapCursor(null);
    }

    // ESTA FUNCION SE AGREGO PARA HACER MAS FACIL LAS COORDENADAS //
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
        mainActivity.Localizacion Local = new Localizacion();
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
        mainActivity mainActivity;
        public mainActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(mainActivity mainActivity) {
            this.mainActivity = mainActivity;
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
        public void setMainActivity() {
        }

        private class MainActivity {
        }
    }



}

//SyncAdapter.inicializarSyncAdapter(this);




