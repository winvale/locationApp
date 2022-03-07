package com.redylab.redylabsirr;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.redylab.redylabsirr.adaptadores.adaptador_usuarios;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Users;

import java.util.ArrayList;

public class ListaUsuariosRecycler extends AppCompatActivity implements SearchView.OnQueryTextListener{

    ArrayList<Users> listUsuarios;
    RecyclerView recyclerViewUsuarios;
    adaptador_usuarios adapter4;
    int vcodigo;

    @Override
    protected void onCreate( Bundle saveInstanceState) {
        super.onCreate( saveInstanceState );
        setContentView( R.layout.vista_usuarios );
        //
        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            vcodigo = extras.getInt( "codigoperador" );
        }
        //
        listUsuarios = new ArrayList <>();
        adapter4 = new adaptador_usuarios( listUsuarios );
        recyclerViewUsuarios =findViewById( R.id.recycler_Usuarios);
        recyclerViewUsuarios.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerViewUsuarios.setAdapter(adapter4);

        //
        //
        //
        FloatingActionButton Ingr_usuarios =(FloatingActionButton) findViewById( R.id.agregar_usu );
        Ingr_usuarios.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent miIntent= new Intent(getBaseContext(),userRegistro.class);
                /*Bundle miBundle=new Bundle();
                miBundle.putInt("codigoperador", Integer.parseInt( itemUsuario.getCedula()));
                miIntent.putExtras(miBundle);*/
                startActivity( miIntent );
            }
        } );


        consultarListaUsuarios();

    }

    //colocar en todas las actividades boton de atras
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return false;
    }
    private void consultarListaUsuarios() {

        final DatabaseManagerUser databaseManager = new DatabaseManagerUser(this);
        Users usua;
        //Trafo acome;
        Cursor cursorusu = databaseManager.getDb().rawQuery( "select id_user,cedula,nombre, pass,estado,tipo_usu from users ", null);

        while(cursorusu.moveToNext()) {
            usua = new Users();
            usua.setId_user(cursorusu.getString( 0 ) );
            usua.setCedula(cursorusu.getString( 1 ) );
            usua.setNombre(cursorusu.getString( 2 ) );
            usua.setPass( cursorusu.getString( 3) );
            usua.setEstado( cursorusu.getString( 4) );
            usua.setTipo_usu( cursorusu.getString( 5) );

            listUsuarios.add(usua);
        }
        cursorusu.close();
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
                adapter4.setFilter( listUsuarios );
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
            ArrayList<Users>Listafiltrada = filter( listUsuarios ,newText );
            adapter4.setFilter( Listafiltrada );
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    //
    private ArrayList<Users> filter(ArrayList<Users>listUsuario ,String texto){
        ArrayList<Users>ListaFiltrada=new ArrayList <>();//null;
        try {
            texto=texto.toLowerCase();
            for(Users lista : listUsuarios ) {
                String lista2=lista.getNombre().toLowerCase();
                if(lista2.contains( texto )){
                    ListaFiltrada.add( lista );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ListaFiltrada;
    }

    //
    //
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //codigo adicional
        this.finish();
    }
    //


}
