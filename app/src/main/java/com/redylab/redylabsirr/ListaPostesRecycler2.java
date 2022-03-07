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


import com.redylab.redylabsirr.adaptadores.adaptador_postes2;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Trafo;

import java.util.ArrayList;

public class ListaPostesRecycler2 extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ArrayList <Trafo> listPostes2;
    RecyclerView recyclerViewPostes2;
    adaptador_postes2 adapter2;
    int vcodigo;
    TextView titulo2;

    @Override
    protected void onCreate( Bundle saveInstanceState ) {
        super.onCreate( saveInstanceState );
        setContentView( R.layout.vista_postes );
        //
        //
        titulo2 = findViewById( R.id.tit_ptt );
        titulo2.setText( "Para Registrar Acometida" );
        //
        //
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            vcodigo = extras.getInt( "codigoperador" );
        }
        //
        listPostes2 = new ArrayList <>();
        adapter2 = new adaptador_postes2( listPostes2 );
        recyclerViewPostes2 = findViewById( R.id.recycler_poste );
        //LinearLayoutManager layoutManagerp2 = new LinearLayoutManager( this );
        recyclerViewPostes2.setLayoutManager( new LinearLayoutManager( this ) );
        // adaptador_postes2 adapter2 = new adaptador_postes2( listPostes2 );
        recyclerViewPostes2.setAdapter( adapter2 );

        consultarListaPostes();

    }

    //colocar en todas las actividades boton de atras
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent( ListaPostesRecycler2.this, mainActivity.class );
        Bundle miBundle = new Bundle();
        miBundle.putString( "IDENT", (String.valueOf( vcodigo )) );
        intent.putExtras( miBundle );
        intent.setFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity( intent );
        finish();
        return false;
    }

    private void consultarListaPostes() {

        final DatabaseManagerUser databaseManager = new DatabaseManagerUser( this );
        Trafo post;
        Cursor cursorpost2 = databaseManager.getDb().rawQuery( "select nomb_nodo,id_trafo_nodo,altura_nodo,material_nodo,nro_acomet,observacion_nodo," +
                "estruct_1_nodo,tipo_1_estruct,estruct_2_nodo,tipo_2_estruct,estruct_3_nodo,tipo_3_estruct,estruct_4_nodo,tipo_4_estruct,long_nodo,lati_nodo,alti_nodo," +
                "nr_reten,antiguedad,propiedad_nodo,con1,cal_fase_con1,cal_neutro_con1,cal_ap_con1,nhil_con1,con2,cal_fase_con2,cal_neutro_con2,cal_ap_con2,nhil_con2," +
                "con3,cal_fase_con3,cal_neutro_con3,cal_ap_con3,nhil_con3,con4,cal_fase_con4,cal_neutro_con4,cal_ap_con4,nhil_con4,usu_levant_usu" +
                " from trafo where length(nomb_nodo)>1 and usu_levant_usu = " + vcodigo + " group by nomb_nodo", null );
        while (cursorpost2.moveToNext()) {
            post = new Trafo();
            post.setNomb_nodo( cursorpost2.getString( 0 ) );
            post.setId_trafo_nodo( cursorpost2.getString( 1 ) );
            post.setAltura_nodo( cursorpost2.getInt( 2 ) );
            post.setMaterial_nodo( cursorpost2.getString( 3 ) );
            post.setNro_acomet( cursorpost2.getInt( 4 ) );
            post.setObservacion_nodo( cursorpost2.getString( 5 ) );
            post.setEstruct_1_nodo( cursorpost2.getString( 6 ) );
            post.setTipo_1_estruct( cursorpost2.getString( 7 ) );
            post.setEstruct_2_nodo( cursorpost2.getString( 8 ) );
            post.setTipo_2_estruct( cursorpost2.getString( 9 ) );
            post.setEstruct_3_nodo( cursorpost2.getString( 10 ) );
            post.setTipo_3_estruct( cursorpost2.getString( 11 ) );
            post.setEstruct_4_nodo( cursorpost2.getString( 12 ) );
            post.setTipo_4_estruct( cursorpost2.getString( 13 ) );
            post.setLong_nodo( cursorpost2.getDouble( 14 ) );
            post.setLati_nodo( cursorpost2.getDouble( 15 ) );
            post.setAlti_nodo( cursorpost2.getInt( 16 ) );
            post.setNr_reten( cursorpost2.getInt( 17 ) );
            post.setAntiguedad( cursorpost2.getInt( 18 ) );
            post.setPropiedad_nodo( cursorpost2.getString( 19 ) );
            post.setCon1( cursorpost2.getString( 20 ) );
            post.setCal_fase_con1( cursorpost2.getString( 21 ) );
            post.setCal_neutro_con1( cursorpost2.getString( 22 ) );
            post.setCal_ap_con1( cursorpost2.getString( 23 ) );
            post.setNhil_con1( cursorpost2.getString( 24 ) );
            post.setCon2( cursorpost2.getString( 25 ) );
            post.setCal_fase_con2( cursorpost2.getString( 26 ) );
            post.setCal_neutro_con2( cursorpost2.getString( 27 ) );
            post.setCal_ap_con2( cursorpost2.getString( 28 ) );
            post.setNhil_con2( cursorpost2.getString( 29 ) );
            post.setCon3( cursorpost2.getString( 30 ) );
            post.setCal_fase_con3( cursorpost2.getString( 31 ) );
            post.setCal_neutro_con3( cursorpost2.getString( 32 ) );
            post.setCal_ap_con3( cursorpost2.getString( 33 ) );
            post.setNhil_con3( cursorpost2.getString( 34 ) );
            post.setCon4( cursorpost2.getString( 35 ) );
            post.setCal_fase_con4( cursorpost2.getString( 36 ) );
            post.setCal_neutro_con4( cursorpost2.getString( 37 ) );
            post.setCal_ap_con4( cursorpost2.getString( 38 ) );
            post.setNhil_con4( cursorpost2.getString( 39 ) );
            post.setUsu_levant_usu( cursorpost2.getInt( 40 ) );
            listPostes2.add(post);

        }
        cursorpost2.close();
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
                adapter2.setFilter( listPostes2 );
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
            ArrayList<Trafo>Listafiltrada = filter( listPostes2 ,newText );
            adapter2.setFilter( Listafiltrada );
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //
    private ArrayList<Trafo> filter(ArrayList<Trafo>listaPostes2 ,String texto){
        ArrayList<Trafo>ListaFiltrada=new ArrayList <>();//null;
        try {
            texto=texto.toLowerCase();
            for(Trafo lista : listPostes2 ) {
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
