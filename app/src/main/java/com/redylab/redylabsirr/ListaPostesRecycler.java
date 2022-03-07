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

import com.redylab.redylabsirr.adaptadores.adaptador_postes;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Trafo;

import java.util.ArrayList;

public class ListaPostesRecycler extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ArrayList<Trafo> listPostes;
    RecyclerView recyclerViewPostes;
    adaptador_postes adapterp;
    int vcodigo;
    @Override
    protected void onCreate( Bundle saveInstanceState) {
        super.onCreate( saveInstanceState );
        setContentView( R.layout.vista_postes );
        //
        Bundle extrasp = this.getIntent().getExtras();
        if (extrasp != null) {
            vcodigo = extrasp.getInt( "codigoperador" );
        }
        //
        listPostes = new ArrayList <>();
        adapterp = new adaptador_postes( listPostes );
        recyclerViewPostes = findViewById( R.id.recycler_poste);
        recyclerViewPostes.setLayoutManager( new LinearLayoutManager( this));
        //adaptador_postes adapterp = new adaptador_postes(listPostes);
        recyclerViewPostes.setAdapter(adapterp);

        consultarListaPostes();
    }

    //colocar en todas las actividades boton de atras
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( ListaPostesRecycler.this, mainActivity.class );
        Bundle miBundle = new Bundle();
        miBundle.putString( "IDENT", ( String.valueOf( vcodigo ) ));
        intent.putExtras( miBundle );
        intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity( intent );
        finish();
        return false;
    }
    private void consultarListaPostes() {

        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(this);
        Trafo post;
        Cursor cursorpost = databaseManager.getDb().rawQuery( "select nomb_nodo,id_trafo_nodo,altura_nodo,material_nodo,nro_acomet,observacion_nodo," +
                "estruct_1_nodo,tipo_1_estruct,estruct_2_nodo,tipo_2_estruct,estruct_3_nodo,tipo_3_estruct,estruct_4_nodo,tipo_4_estruct,long_nodo,lati_nodo,alti_nodo," +
                "nr_reten,antiguedad,propiedad_nodo,con1,cal_fase_con1,cal_neutro_con1,cal_ap_con1,nhil_con1,con2,cal_fase_con2,cal_neutro_con2,cal_ap_con2,nhil_con2," +
                "con3,cal_fase_con3,cal_neutro_con3,cal_ap_con3,nhil_con3,con4,cal_fase_con4,cal_neutro_con4,cal_ap_con4,nhil_con4,usu_levant_usu,estado,pendiente_insercion" +
                " from trafo where length(nomb_nodo)>1 and usu_levant_usu = "+vcodigo+" group by nomb_nodo", null);
        while(cursorpost.moveToNext()) {
            post = new Trafo();
            post.setNomb_nodo(cursorpost.getString( 0 ) );
            post.setId_trafo_nodo(cursorpost.getString( 1 ) );
            post.setAltura_nodo( cursorpost.getInt( 2 ) );
            post.setMaterial_nodo( cursorpost.getString( 3 ) );
            post.setNro_acomet( cursorpost.getInt( 4 ) );
            post.setObservacion_nodo( cursorpost.getString( 5 ) );
            post.setEstruct_1_nodo( cursorpost.getString( 6 ) );
            post.setTipo_1_estruct( cursorpost.getString( 7 ) );
            post.setEstruct_2_nodo( cursorpost.getString( 8 ) );
            post.setTipo_2_estruct( cursorpost.getString( 9 ) );
            post.setEstruct_3_nodo( cursorpost.getString( 10 ) );
            post.setTipo_3_estruct( cursorpost.getString( 11 ) );
            post.setEstruct_4_nodo( cursorpost.getString( 12 ) );
            post.setTipo_4_estruct( cursorpost.getString( 13 ) );
            post.setLong_nodo( cursorpost.getDouble( 14 ) );
            post.setLati_nodo( cursorpost.getDouble( 15 ) );
            post.setAlti_nodo( cursorpost.getInt( 16 ) );
            post.setNr_reten( cursorpost.getInt( 17 ) );
            post.setAntiguedad( cursorpost.getInt( 18 ) );
            post.setPropiedad_nodo( cursorpost.getString( 19 ) );
            post.setCon1( cursorpost.getString( 20 ) );
            post.setCal_fase_con1( cursorpost.getString( 21 ) );
            post.setCal_neutro_con1( cursorpost.getString( 22 ) );
            post.setCal_ap_con1( cursorpost.getString( 23 ) );
            post.setNhil_con1( cursorpost.getString( 24 ) );
            post.setCon2( cursorpost.getString( 25 ) );
            post.setCal_fase_con2( cursorpost.getString( 26 ) );
            post.setCal_neutro_con2( cursorpost.getString( 27 ) );
            post.setCal_ap_con2( cursorpost.getString( 28 ) );
            post.setNhil_con2( cursorpost.getString( 29 ) );
            post.setCon3( cursorpost.getString( 30 ) );
            post.setCal_fase_con3( cursorpost.getString( 31 ) );
            post.setCal_neutro_con3( cursorpost.getString( 32 ) );
            post.setCal_ap_con3( cursorpost.getString( 33 ) );
            post.setNhil_con3( cursorpost.getString( 34 ) );
            post.setCon4( cursorpost.getString( 35 ) );
            post.setCal_fase_con4( cursorpost.getString( 36 ) );
            post.setCal_neutro_con4( cursorpost.getString( 37 ) );
            post.setCal_ap_con4( cursorpost.getString( 38 ) );
            post.setNhil_con4( cursorpost.getString( 39 ) );
            post.setUsu_levant_usu( cursorpost.getInt( 40 ) );
            post.setEstado( cursorpost.getInt( 41 ) );
            post.setPendiente_insercion( cursorpost.getInt( 42 ) );

          listPostes.add(post);
        }
        cursorpost.close();
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
                adapterp.setFilter( listPostes );
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
            ArrayList<Trafo>Listafiltrada = filter( listPostes ,newText );
            adapterp.setFilter( Listafiltrada );
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //
    private ArrayList<Trafo> filter(ArrayList<Trafo>listaPostes ,String texto){
        ArrayList<Trafo>ListaFiltrada=new ArrayList <>();//null;
        try {
            texto=texto.toLowerCase();
            for(Trafo lista : listPostes ) {
                String lista2=lista.getNomb_nodo().toLowerCase();
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
