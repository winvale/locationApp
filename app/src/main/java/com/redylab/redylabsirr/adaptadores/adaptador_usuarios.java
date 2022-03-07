package com.redylab.redylabsirr.adaptadores;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.ListaUsuariosRecycler;
import com.redylab.redylabsirr.R;
import com.redylab.redylabsirr.Utilidades.Constantes;
import com.redylab.redylabsirr.database.DatabaseManager;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Users;
import com.redylab.redylabsirr.frm_acometidas_update;
import com.redylab.redylabsirr.mainActivity;
import com.redylab.redylabsirr.sync.SyncAdapter;
import com.redylab.redylabsirr.userRegistro;
import com.redylab.redylabsirr.usuarios_update;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class adaptador_usuarios extends RecyclerView.Adapter<adaptador_usuarios.ViewHolderusuarios> {

    private ArrayList<Users> listaUsuarios;
    private Context context;
    private ContentResolver resolver;
    private DatabaseManager databaseManager;
    private DatabaseManagerUser managerUsuario;
    String operador;
    String idborrar;
    String est,tips,estdu,tipu;
    int snc3 = 2;
    public adaptador_usuarios( ArrayList<Users> listaUsuarios){this.listaUsuarios = listaUsuarios;}

    @NonNull
    @Override
    public ViewHolderusuarios onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.registros_usuarios,parent,false);
        context = vista.getContext();// se agrego
        resolver = context.getContentResolver();
        return new ViewHolderusuarios(vista);
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolderusuarios holder, int position ) {
        holder.txtId.setText(listaUsuarios.get(position).getId_user());
        holder.txtCedula.setText(listaUsuarios.get(position).getCedula());
        holder.txtNombre.setText(listaUsuarios.get(position).getNombre());
        holder.txtPass.setText(listaUsuarios.get(position).getPass());
        estdu= listaUsuarios.get(position).getEstado();
        tipu= listaUsuarios.get(position).getTipo_usu();
        if(estdu.equals( "A" )){
            est="ACTIVO";
        }else if(estdu.equals( "I" )){
            est="INACTIVO";
        }
        if(tipu.equals( "A" )){
            tips="ADMINISTRADOR";
        }else if(tipu.equals( "O" )){
            tips="OPERADOR";
        }

        holder.txtEstado.setText(est);
        holder.txtTipo.setText(tips);
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    class ViewHolderusuarios extends RecyclerView.ViewHolder {

        TextView txtId, txtCedula,txtNombre,txtPass,txtEstado,txtTipo;
        ImageView elimi,edit;

        ViewHolderusuarios( View itemView ) {
            super( itemView );
            txtId = itemView.findViewById( R.id.textidusu );
            txtCedula = itemView.findViewById( R.id.textcedul );
            txtNombre = itemView.findViewById( R.id.textnombr );
            txtPass = itemView.findViewById( R.id.textpassw );
            txtEstado = itemView.findViewById( R.id.textestad );
            txtTipo = itemView.findViewById( R.id.texttipou );


            edit = itemView.findViewById( R.id.editaru );
            edit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {

                        operador = String.valueOf( listaUsuarios.get( position ).getId_user() );
                        String cedul = listaUsuarios.get( position ).getCedula();
                        String nomb = listaUsuarios.get( position ).getNombre();
                        String pas = listaUsuarios.get( position ).getPass();
                        String estd = listaUsuarios.get( position ).getEstado();
                        String tip = String.valueOf( listaUsuarios.get( position ).getTipo_usu() );

                        Intent intent = new Intent( context, usuarios_update.class );
                        intent.putExtra( "u_id", txtId.getText() );
                        intent.putExtra( "u_ce", cedul );
                        intent.putExtra( "u_no", nomb );
                        intent.putExtra( "u_pa", pas );
                        intent.putExtra( "u_es", estd );
                        intent.putExtra( "u_ti", tip );
                        intent.putExtra( "IDENT", operador );
                        context.startActivity( intent );
                    }
                }
            } );
            elimi = (ImageView) itemView.findViewById( R.id.eliminaru );
            elimi.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        operador = listaUsuarios.get(position).getId_user();
                        //operador = String.valueOf(listaAcometidasf.get(position).getUsu_levant_usu());
                        //operador = String.valueOf(listaUsuarios.get(position).);
                        abre_dialogo(operador);
                    }
                }
            } );


        }
    }

    public void abre_dialogo( final String codigo ){

        AlertDialog.Builder builder =new AlertDialog.Builder( context );
        builder.setTitle( "Eliminar" );
        builder.setMessage( "Desea Borrar Este Codigo de Usuario: "+codigo );
        builder.setPositiveButton( "Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {

                resolver.delete( ContractParaG.CONTENT_URI2, DatabaseManagerUser.Columnas.ID_USUARIO + "=?" ,new String[]{codigo});
                Intent intent =new Intent( context, ListaUsuariosRecycler.class );
                context.startActivity( intent );
            }
        } );
        builder.setNegativeButton( "Cancel",null );
        Dialog dialog = builder.create();
        dialog.show();
    }
    public void setFilter( ArrayList <Users> newlistaUsuarios ){
        this.listaUsuarios = new ArrayList <>();
        this.listaUsuarios.addAll( newlistaUsuarios );
        notifyDataSetChanged();
    }
}
