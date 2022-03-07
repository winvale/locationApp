package com.redylab.redylabsirr;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.redylab.redylabsirr.adaptadores.adaptador_acometidas;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Trafo;

import java.util.ArrayList;

public class ListaAcometidasRecycler extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ArrayList<Trafo> listAcometidas;
    RecyclerView recyclerViewAcometidas;
    adaptador_acometidas adapter4;
    int vcodigo;

    @Override
    protected void onCreate( Bundle saveInstanceState) {
        super.onCreate( saveInstanceState );
        setContentView( R.layout.vista_acometidas );
        //
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            vcodigo = extras.getInt( "codigoperador" );
        }
        //
        listAcometidas = new ArrayList <>();
        adapter4 = new adaptador_acometidas( listAcometidas );
        recyclerViewAcometidas =findViewById( R.id.recycler_acometida);
        recyclerViewAcometidas.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerViewAcometidas.setAdapter(adapter4);

        consultarListaAcometidas();

    }

    //colocar en todas las actividades boton de atras
    @Override
    public boolean onSupportNavigateUp() {
        // onBackPressed();
        finish();
        return false;
    }
    private void consultarListaAcometidas() {

        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(this);
        Trafo acome;
        Cursor cursoracome = databaseManager.getDb().rawQuery( "select distinct nro_acometida_corresp,cont_act,id_nodo_usu,tip_mbt," +
                "direccion,tip_servicio,observacion_usu,long_usu,lati_usu,alti_usu,cont_marca,tip_ca,tip_as,tip_rst,calibre,tablero,fas_tablero,usu_levant_usu,estado,pendiente_insercion from trafo where length(id_nodo_usu)>1 and usu_levant_usu = "+vcodigo, null);

        while(cursoracome.moveToNext()) {
            acome = new Trafo();
            acome.setNro_acometida_corresp(cursoracome.getString( 0 ) );
            acome.setCont_act(cursoracome.getString( 1 ) );
            acome.setId_nodo_usu(cursoracome.getString( 2 ) );
            acome.setTip_mbt( cursoracome.getString( 3) );
            acome.setDireccion( cursoracome.getString( 4) );
            acome.setTip_servicio( cursoracome.getString( 5) );
            acome.setObservacion_usu( cursoracome.getString( 6) );
            acome.setLong_usu( cursoracome.getDouble( 7) );
            acome.setLati_usu( cursoracome.getDouble( 8) );
            acome.setAlti_usu( cursoracome.getDouble( 9) );
            acome.setCont_marca( cursoracome.getString( 10) );
            acome.setTip_ca( cursoracome.getString( 11) );
            acome.setTip_as( cursoracome.getString( 12) );
            acome.setTip_rst( cursoracome.getString( 13) );
            acome.setCalibre( cursoracome.getInt( 14) );
            acome.setTablero( cursoracome.getString( 15) );
            acome.setFas_tablero( cursoracome.getString( 16) );
            acome.setUsu_levant_usu( cursoracome.getInt( 17) );
            acome.setEstado( cursoracome.getInt( 18) );
            acome.setPendiente_insercion( cursoracome.getInt( 19) );

            listAcometidas.add(acome);
        }
        cursoracome.close();
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
                adapter4.setFilter( listAcometidas );
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
            ArrayList<Trafo>Listafiltrada = filter( listAcometidas ,newText );
            adapter4.setFilter( Listafiltrada );
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //
    private ArrayList<Trafo> filter(ArrayList<Trafo>listAcometida ,String texto){
        ArrayList<Trafo>ListaFiltrada=new ArrayList <>();//null;
        try {
            texto=texto.toLowerCase();
            for(Trafo lista : listAcometidas ) {
                String lista2=lista.getNro_acometida_corresp().toLowerCase();
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
