package com.redylab.redylabsirr;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.view.Menu;
import android.view.MenuItem;

import com.redylab.redylabsirr.adaptadores.adaptador_trafos;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Trafo;
import java.util.ArrayList;

public class ListaTrafosRecycler extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ArrayList<Trafo> listTrafos;
    RecyclerView recyclerViewTrafos;
    adaptador_trafos adapter;
    int vcodigo;

    @Override
    protected void onCreate( Bundle saveInstanceState) {
        super.onCreate( saveInstanceState );
        setContentView( R.layout.vista_trafos );
        //
        Bundle extrastt = this.getIntent().getExtras();
        if (extrastt != null) {
            vcodigo = extrastt.getInt( "codigoperador" );
        }
        //
        listTrafos = new ArrayList <>();
        adapter = new adaptador_trafos( listTrafos );
        recyclerViewTrafos = findViewById( R.id.recycler_trafo);
        recyclerViewTrafos.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerViewTrafos.setAdapter(adapter);

        consultarListaTrafos();
    }


    //colocar en todas las actividades boton de atras
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( ListaTrafosRecycler.this, mainActivity.class );
        Bundle miBundle = new Bundle();
        miBundle.putString( "IDENT", ( String.valueOf( vcodigo ) ));
        intent.putExtras( miBundle );
        intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity( intent );
        finish();

        return false;
    }
    private void consultarListaTrafos() {

        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(this);
        Trafo traf;
        Cursor cursortraf = databaseManager.getDb().rawQuery( "select nomb_trafo,tipo,capacidad,long_trafo,lati_trafo,alt_trafo," +
                "zonaur,propiedad_trafo,tipo_trafo,observacion_trafo,usu_levant_usu,mpio,estado,pendiente_insercion from trafo where length(nomb_trafo)>1 and capacidad>1 and usu_levant_usu = "+vcodigo+" group by nomb_trafo", null);
        while(cursortraf.moveToNext()) {
          traf = new Trafo();
          traf.setNomb_trafo(cursortraf.getString( 0 ) );
          traf.setTipo( cursortraf.getString( 1 ) );
          traf.setCapacidad( cursortraf.getInt( 2 ) );
          traf.setLong_trafo( cursortraf.getDouble( 3 ) );
          traf.setLati_trafo( cursortraf.getDouble( 4 ) );
          traf.setAlt_trafo( cursortraf.getDouble( 5 ) );
          traf.setZonaur( cursortraf.getInt( 6 ) );
          traf.setPropiedad_trafo( cursortraf.getString( 7 ) );
          traf.setTipo_trafo( cursortraf.getString( 8 ) );
          traf.setObservacion_trafo( cursortraf.getString( 9 ) );
          traf.setUsu_levant_usu( cursortraf.getInt( 10 ) );
          traf.setMpio( cursortraf.getString( 11 ) );
          traf.setEstado( cursortraf.getInt( 12 ) );
          traf.setPendiente_insercion(cursortraf.getInt( 13 ));
          listTrafos.add(traf);

        }
        cursortraf.close();
    }

    // codigo nuevo
    @Override
    public boolean onCreateOptionsMenu( Menu menu){
        getMenuInflater().inflate( R.menu.toolbar_menu, menu );
        MenuItem Item = menu.findItem( R.id.action_search );//ojo ver
        SearchView searchView =(SearchView) MenuItemCompat.getActionView(Item);//menuItemCompat.etActionView
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener( Item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand( MenuItem item ) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse( MenuItem item ) {
                adapter.setFilter( listTrafos );
                return true;
            }
        } );
        return true;
    }
    //
    @Override
    public boolean onQueryTextSubmit( String query ) {
        return false;
    }

    @Override
    public boolean onQueryTextChange( String newText ) {

        try{
           ArrayList<Trafo>Listafiltrada = filter( listTrafos ,newText );
            adapter.setFilter( Listafiltrada );
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //
    private ArrayList<Trafo> filter(ArrayList<Trafo>listaTrafos ,String texto){
        ArrayList<Trafo>ListaFiltrada=new ArrayList <>();//null;
        try {
            texto=texto.toLowerCase();
            for(Trafo lista : listTrafos ) {
                String lista2=lista.getNomb_trafo().toLowerCase();
                if(lista2.contains( texto )){
                    ListaFiltrada.add( lista );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ListaFiltrada;
    }
}
