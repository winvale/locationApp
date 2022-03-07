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
import android.widget.TextView;

import com.redylab.redylabsirr.adaptadores.adaptador_trafos2;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Trafo;

import java.util.ArrayList;

public class ListaTrafosRecycler2 extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ArrayList<Trafo> listTrafos2;
    RecyclerView recyclerViewTrafos2;
    adaptador_trafos2 adapter;
    int vcodigo;
    TextView titulo;

    @Override
    protected void onCreate( Bundle saveInstanceState) {
        super.onCreate( saveInstanceState );
        setContentView( R.layout.vista_trafos );
        //
         titulo = findViewById(R.id.tit_pt );
         titulo.setText( "Para Registrar Poste" );
        //
        Bundle extrast = this.getIntent().getExtras();
        if (extrast != null) {
            vcodigo = extrast.getInt( "codigoperador" );
        }
        listTrafos2 = new ArrayList <>();
        adapter = new adaptador_trafos2( listTrafos2 );
        recyclerViewTrafos2 = findViewById( R.id.recycler_trafo);
        //LinearLayoutManager layoutManager2 = new LinearLayoutManager( this );
        recyclerViewTrafos2.setLayoutManager( new LinearLayoutManager (this));
        recyclerViewTrafos2.setAdapter(adapter);

        consultarListaTrafos2();
    }

    //colocar en todas las actividades boton de atras
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( ListaTrafosRecycler2.this, mainActivity.class );
        Bundle miBundle = new Bundle();
        miBundle.putString( "IDENT", ( String.valueOf( vcodigo ) ));
        intent.putExtras( miBundle );
        intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity( intent );
        finish();

        return false;
    }
    private void consultarListaTrafos2() {

        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(this);
        Trafo traf;
        Cursor cursortraf2 = databaseManager.getDb().rawQuery( "select nomb_trafo,tipo,capacidad,long_trafo,lati_trafo,alt_trafo," +
                "zonaur,propiedad_trafo,tipo_trafo,observacion_trafo,usu_levant_usu,mpio from trafo where length(nomb_trafo)>1 and capacidad>1 and usu_levant_usu = "+vcodigo+" group by nomb_trafo", null);
        while(cursortraf2.moveToNext()) {
            traf = new Trafo();
            traf.setNomb_trafo(cursortraf2.getString( 0 ) );
            traf.setTipo( cursortraf2.getString( 1 ) );
            traf.setCapacidad( cursortraf2.getInt( 2 ) );
            traf.setLong_trafo( cursortraf2.getDouble( 3 ) );
            traf.setLati_trafo( cursortraf2.getDouble( 4 ) );
            traf.setAlt_trafo( cursortraf2.getDouble( 5 ) );
            traf.setZonaur( cursortraf2.getInt( 6 ) );
            traf.setPropiedad_trafo( cursortraf2.getString( 7 ) );
            traf.setTipo_trafo( cursortraf2.getString( 8 ) );
            traf.setObservacion_trafo( cursortraf2.getString( 9 ) );
            traf.setUsu_levant_usu( cursortraf2.getInt( 10 ) );
            traf.setMpio( cursortraf2.getString( 11 ) );
          listTrafos2.add(traf);
        }
        cursortraf2.close();
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
                adapter.setFilter( listTrafos2 );
                return true;
            }
        } );
        return true;
    }
    //
    //
    @Override
    public boolean onQueryTextSubmit( String query ) {
        return false;
    }

    @Override
    public boolean onQueryTextChange( String newText ) {

        try{
            ArrayList<Trafo>Listafiltrada = filter( listTrafos2 ,newText );
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
            for(Trafo lista : listTrafos2 ) {
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
