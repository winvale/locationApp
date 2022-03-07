package com.redylab.redylabsirr.adaptadores;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.R;
import com.redylab.redylabsirr.Utilidades.Constantes;
import com.redylab.redylabsirr.database.DatabaseManager;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Trafo;
import com.redylab.redylabsirr.frm_trafos_update;
import com.redylab.redylabsirr.mainActivity;
import com.redylab.redylabsirr.sync.SyncAdapter;

import java.io.File;
import java.util.ArrayList;

import static com.redylab.redylabsirr.sync.SyncAdapter.PROJECTION;


public class adaptador_trafos extends RecyclerView.Adapter<adaptador_trafos.ViewHolderTrafos> {

    private ArrayList<Trafo> listaTrafosf;//mArraylist
    String municipio;
    ImageView movt2;
    int snc1 = 2,snc2 = 2;

    private Context context;
    private ContentResolver resolver;
    private static String operador;

    public adaptador_trafos( ArrayList<Trafo> listTrafos ) {
        this.listaTrafosf =listTrafos;
    }

    @NonNull
    @Override
    public ViewHolderTrafos onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.registros_trafos,parent,false);
        context = vista.getContext();// se agrego
        resolver = context.getContentResolver();
        movt2=(ImageView)vista.findViewById( R.id.movil2 ) ;
        return new ViewHolderTrafos(vista);
    }
    @Override
    public void onBindViewHolder( @NonNull ViewHolderTrafos holder, int position ) {
        holder.txtCodTrafo.setText(listaTrafosf.get(position).getNomb_trafo());
        holder.txtClasTrafo.setText(listaTrafosf.get(position).getTipo());/////
        holder.txtCapTrafo.setText(String.valueOf(listaTrafosf.get(position).getCapacidad())+" Kva");
        holder.txtLongTrafo.setText(String.valueOf(listaTrafosf.get(position).getLong_trafo()));
        holder.txtLatiTrafo.setText(String.valueOf(listaTrafosf.get(position).getLati_trafo()));
        holder.txtAltuTrafo.setText(String.valueOf(listaTrafosf.get(position).getAlt_trafo()));
        holder.txtTipoTrafo.setText(String.valueOf(listaTrafosf.get(position).getTipo_trafo()));
        int movie =listaTrafosf.get(position).getEstado();
        int movip = listaTrafosf.get(position).getPendiente_insercion();

        Log.i("parametro1: " , String.valueOf(movie));
        Log.i("parametro2: " , String.valueOf(movip));

        if(movie == 0 && movip == 0 )
        {
            movt2.setImageResource( R.drawable.sinc_movil_y );
        }
        else{
            movt2.setImageResource( R.drawable.sinc_movil_n );
        }
        int pr =listaTrafosf.get(position).getZonaur();
        String vrl = "";
        if(pr == 3){
            vrl="URBANO";
        }else if(pr ==4 ){
            vrl="RURAL";
        }
        holder.txtUbicTrafo.setText(vrl.toString().trim());
        holder.txtPropTrafo.setText(String.valueOf(listaTrafosf.get(position).getPropiedad_trafo()));
        holder.txtObsrTrafo.setText(String.valueOf(listaTrafosf.get(position).getObservacion_trafo()));
    }
    @Override
    public int getItemCount() {
        return listaTrafosf.size();
    }

    //original
    //class ViewHolderTrafos extends RecyclerView.ViewHolder {
    public class ViewHolderTrafos extends RecyclerView.ViewHolder {
        TextView txtCodTrafo, txtTipoTrafo, txtCapTrafo, txtLongTrafo, txtLatiTrafo, txtAltuTrafo, txtClasTrafo, txtUbicTrafo, txtPropTrafo, txtObsrTrafo;
        ImageView elimi, edit, sincr;

        public ViewHolderTrafos( View itemView ) {
            super( itemView );
            txtCodTrafo = itemView.findViewById( R.id.textCodTraf );
            txtClasTrafo = itemView.findViewById( R.id.textClas );
            txtCapTrafo = itemView.findViewById( R.id.textCapa );
            txtTipoTrafo = itemView.findViewById( R.id.textTipo );
            txtUbicTrafo = itemView.findViewById( R.id.textUbic );
            txtPropTrafo = itemView.findViewById( R.id.textProp );
            txtObsrTrafo = itemView.findViewById( R.id.textObsr );
            txtLongTrafo = itemView.findViewById( R.id.textlong );
            txtLatiTrafo = itemView.findViewById( R.id.textlati );
            txtAltuTrafo = itemView.findViewById( R.id.textaltu );

            edit = itemView.findViewById( R.id.editar );
            edit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        municipio = listaTrafosf.get( position ).getMpio();
                        operador = String.valueOf( listaTrafosf.get( position ).getUsu_levant_usu() );
                        //String ncadena= potencia.getText().toString().replace( "W","" );

                        // Toast toast1 = Toast.makeText(context, "Prueba editar " + codigo.getText(), Toast.LENGTH_SHORT);
                        // toast1.show();
                        Intent intent = new Intent( context, frm_trafos_update.class );
                        intent.putExtra( "codtraf", txtCodTrafo.getText() );
                        intent.putExtra( "codclas", txtClasTrafo.getText() );
                        intent.putExtra( "codcapa", txtCapTrafo.getText().toString().replace( " Kva", "" ) );
                        intent.putExtra( "codtipo", txtTipoTrafo.getText() );
                        intent.putExtra( "codubic", txtUbicTrafo.getText() );
                        intent.putExtra( "codprop", txtPropTrafo.getText() );
                        intent.putExtra( "codobsr", txtObsrTrafo.getText() );
                        intent.putExtra( "codlong", txtLongTrafo.getText() );
                        intent.putExtra( "codlati", txtLatiTrafo.getText() );
                        intent.putExtra( "codaltu", txtAltuTrafo.getText() );
                        intent.putExtra( "IDENT", operador );
                        intent.putExtra( "municipio", String.valueOf( municipio ).toString().trim() );
                        context.startActivity( intent );
                    }
                }
            } );
            elimi = (ImageView) itemView.findViewById( R.id.eliminar );
            elimi.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        operador = String.valueOf( listaTrafosf.get( position ).getUsu_levant_usu() );
                        abre_dialogo( txtCodTrafo );

                    }
                }
            } );
            sincr = (ImageView) itemView.findViewById( R.id.movil2 );
            sincr.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        operador = String.valueOf( listaTrafosf.get( position ).getUsu_levant_usu() );
                        snc1=listaTrafosf.get(position).getEstado();
                        snc2=listaTrafosf.get(position).getPendiente_insercion();
                        if(snc1 == 0 && snc2 == 0 ) {
                            Toast.makeText( context, "Se Encuentra Sincronizado.", Toast.LENGTH_LONG ).show();
                        }else {
                            if (!isConnected( context )) {
                                Toast.makeText( context, "No se Pudo Sincronizar, verifique el acceso a Internet e Intente nuevamente", Toast.LENGTH_LONG ).show();

                            } else {
                                Toast.makeText( context, "Verificando Dato a Sincronizar.....", Toast.LENGTH_LONG ).show();
                                Cursor c = obtenerRegistrosSucios();
                                if (c.getCount() > 0) {

                                    Toast.makeText( context, "Sincronizando Datos...", Toast.LENGTH_LONG ).show();
                                    //abre_dialogo(txtCodTrafo);
                                    //ContentValues valuesTs = new ContentValues();
                                    //valuesTs.put( Constantes.ESTADO, 0 );
                                    //valuesTs.put( DatabaseManagerUser.Columnas.PENDIENTE_INSERCION, 1 );
                                    //resolver.delete( ContractParaG.CONTENT_URI, DatabaseManagerUser.Columnas.NOMB_TRAFO + "=?" ,new String[]{codigo.getText().toString()});
                                    //resolver.update( ContractParaG.CONTENT_URI, valuesTs, DatabaseManagerUser.Columnas.NOMB_TRAFO + "=?", new String[]{txtCodTrafo.getText().toString()} );
                                    SyncAdapter.sincronizarAhora( context, true );
                                    Toast.makeText( context, "Se Encuentra Sincronizando... ", Toast.LENGTH_LONG ).show();
                                    Toast.makeText( context, "Sincronizacion de Datos Completa.", Toast.LENGTH_LONG ).show();
                                    Intent intent = new Intent( context, mainActivity.class );
                                    intent.putExtra( "IDENT", operador.trim() );
                                    context.startActivity( intent );

                                } else {
                                    Toast.makeText( context, "Se Encuentra Sincronizado.", Toast.LENGTH_LONG ).show();
                                }
                            }
                        }
                    }
                }
            } );


        }
    }
    private Cursor obtenerRegistrosSucios() {
        Uri uri = ContractParaG.CONTENT_URI;
        String selection = DatabaseManagerUser.Columnas.PENDIENTE_INSERCION + "=? AND "
                + Constantes.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaG.ESTADO_SYNC + ""};
        return resolver.query(uri, PROJECTION, selection, selectionArgs, null);
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
    public void abre_dialogo( final TextView codigo ){
        AlertDialog.Builder builder =new AlertDialog.Builder( context );
        builder.setTitle( "Eliminar" );
        builder.setMessage( "Desea Borrar Este Codigo de Transformador: "+codigo.getText() );
        builder.setPositiveButton( "Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                // operador =String.valueOf(listaTrafosf.get(position).getUsu_levant_usu());
                /*Toast toast1 = Toast.makeText(context, "Prueba Eliminar" + codigo.getText()+ "usuario:" +operador, Toast.LENGTH_SHORT);
                toast1.show();*/

                resolver.delete( ContractParaG.CONTENT_URI, DatabaseManagerUser.Columnas.NOMB_TRAFO + "=?" ,new String[]{codigo.getText().toString()});
                SyncAdapter.sincronizarAhora(context, true);

                Intent intent =new Intent( context, mainActivity.class );
                intent.putExtra( "IDENT", operador.trim());
                context.startActivity( intent );
            }
        } );
        builder.setNegativeButton( "Cancel",null );
        Dialog dialog = builder.create();
        dialog.show();
    }
   public void setFilter( ArrayList <Trafo> newlistaTrafosf ){
        this.listaTrafosf = new ArrayList <>();
        this.listaTrafosf.addAll( newlistaTrafosf );
        notifyDataSetChanged();
    }

}
