package com.redylab.redylabsirr.adaptadores;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.redylab.redylabsirr.R;
import com.redylab.redylabsirr.Utilidades.Constantes;
import com.redylab.redylabsirr.frm_nodos_update;
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Trafo;
import com.redylab.redylabsirr.mainActivity;
import com.redylab.redylabsirr.sync.SyncAdapter;

import java.util.ArrayList;

import static com.redylab.redylabsirr.sync.SyncAdapter.PROJECTION;


public class adaptador_postes extends RecyclerView.Adapter<adaptador_postes.ViewHolderPostes> {

    private ArrayList<Trafo> listaPostesf;
    String operador;
    String txtEstr1,txtTipEst1,txtEstr2,txtTipEst2,txtEstr3,txtTipEst3,txtEstr4,txtTipEst4;
    String txtNroret,txtAntig,txtCon1,txtCl_fs_1,txtCl_ne_1,txtCl_ap_1,txtNhil_1,txtCon2,txtCl_fs_2,txtCl_ne_2,txtCl_ap_2,txtNhil_2;
    String txtCon3,txtCl_fs_3,txtCl_ne_3,txtCl_ap_3,txtNhil_3,txtCon4,txtCl_fs_4,txtCl_ne_4,txtCl_ap_4,txtNhil_4,txtNroAco;
    ImageView movt3;
    int snc1p = 2,snc2p = 2;
    private Context context;
    private ContentResolver resolver;
    public adaptador_postes( ArrayList<Trafo> listaPostesf){
        this.listaPostesf = listaPostesf;
    }

    @NonNull
    @Override
    public ViewHolderPostes onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.registros_postes,parent,false);
        context = vista.getContext();// se agrego
        resolver = context.getContentResolver();
        movt3=(ImageView)vista.findViewById( R.id.movil3 );
        return new ViewHolderPostes(vista);
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolderPostes holder, int position ) {
        holder.txtCodPoste.setText(listaPostesf.get(position).getNomb_nodo());
        holder.txtCodTrafo.setText(listaPostesf.get(position).getId_trafo_nodo());
        holder.txtAltura.setText(String.valueOf(listaPostesf.get(position).getAltura_nodo())+"Mts");
        holder.txtMaterial.setText(listaPostesf.get(position).getMaterial_nodo());
        //holder.txtNroAco.setText(String.valueOf(listaPostesf.get(position).getNro_acomet()));
        holder.txtReten.setText(String.valueOf(listaPostesf.get(position).getNr_reten()));


        holder.txtObsnodo.setText(listaPostesf.get(position).getObservacion_nodo());
        holder.txtlongnodo.setText(String.valueOf(listaPostesf.get(position).getLong_nodo()));
        holder.txtlatinodo.setText(String.valueOf(listaPostesf.get(position).getLati_nodo()));
        holder.txtaltinodo.setText(String.valueOf(listaPostesf.get(position).getAlti_nodo()));
        holder.txtPropnodo.setText(listaPostesf.get(position).getPropiedad_nodo());
        int movie2 =listaPostesf.get(position).getEstado();
        int movip2 = listaPostesf.get(position).getPendiente_insercion();
        String np = listaPostesf.get(position).getNomb_nodo();
        Log.i("parametro0: " , np);
        Log.i("parametro1: " , String.valueOf(movie2));
        Log.i("parametro2: " , String.valueOf(movip2));

        if(movie2 == 0 && movip2 == 0 )
        {
            movt3.setImageResource( R.drawable.sinc_movil_y );
        }
        else{
            movt3.setImageResource( R.drawable.sinc_movil_n );
            }
    }

    @Override
    public int getItemCount() {
        return listaPostesf.size();
    }

    class ViewHolderPostes extends RecyclerView.ViewHolder {

        TextView txtCodPoste, txtCodTrafo,txtAltura,txtMaterial,txtObsnodo,txtlongnodo,txtlatinodo,txtaltinodo,txtPropnodo,txtReten;
        ImageView elimi,edit,sincr2;

        ViewHolderPostes( View itemView ) {
            super( itemView );
            txtCodPoste = itemView.findViewById( R.id.textCodPostn );
            txtCodTrafo = itemView.findViewById( R.id.textCodtn);
            txtAltura = itemView.findViewById( R.id.textAltun );
            txtMaterial = itemView.findViewById( R.id.textMaten );
            //txtNroAco = itemView.findViewById( R.id.textNrete );
            txtObsnodo = itemView.findViewById( R.id.textObsn );
            txtlongnodo = itemView.findViewById( R.id.textLonod );
            txtlatinodo = itemView.findViewById( R.id.textLanod );
            txtaltinodo = itemView.findViewById( R.id.textAlnod );
            txtPropnodo = itemView.findViewById( R.id.textPropn );
            txtReten =itemView.findViewById( R.id.textNrete);

            edit = itemView.findViewById( R.id.editar );
            edit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    //Log.i("parametros:","pos:"+position);
                    if(position!= RecyclerView.NO_POSITION){

                        //String ncadena= potencia.getText().toString().replace( "W","" );
                        txtNroAco = String.valueOf(listaPostesf.get(position).getNro_acomet());
                        txtEstr1 = String.valueOf(listaPostesf.get(position).getEstruct_1_nodo());
                        txtTipEst1 = listaPostesf.get(position).getTipo_1_estruct().trim();
                        txtEstr2= String.valueOf(listaPostesf.get(position).getEstruct_2_nodo());
                        txtTipEst2= listaPostesf.get(position).getTipo_2_estruct();
                        txtEstr3= String.valueOf(listaPostesf.get(position).getEstruct_3_nodo());
                        txtTipEst3= listaPostesf.get(position).getTipo_3_estruct();
                        txtEstr4= String.valueOf(listaPostesf.get(position).getEstruct_4_nodo());
                        txtTipEst4= listaPostesf.get(position).getTipo_4_estruct();
                        txtNroret = String.valueOf(String.valueOf(listaPostesf.get(position).getNr_reten()));
                        txtAntig = String.valueOf(String.valueOf(listaPostesf.get(position).getAntiguedad()));
                        txtCon1 = String.valueOf( listaPostesf.get(position).getCon1());
                        txtCl_fs_1 = String.valueOf( listaPostesf.get(position).getCal_fase_con1());
                        txtCl_ne_1 = String.valueOf( listaPostesf.get(position).getCal_neutro_con1());
                        txtCl_ap_1 = String.valueOf( listaPostesf.get(position).getCal_ap_con1());
                        txtNhil_1 = String.valueOf( listaPostesf.get(position).getNhil_con1());
                        txtCon2 = String.valueOf( listaPostesf.get(position).getCon2());
                        if(txtCon2.equals( "0" )){txtCon2="";}else{txtCon2=String.valueOf( listaPostesf.get(position).getCon2());}
                        txtCl_fs_2 = String.valueOf( listaPostesf.get(position).getCal_fase_con2());
                        txtCl_ne_2 = String.valueOf( listaPostesf.get(position).getCal_neutro_con2());
                        txtCl_ap_2 = String.valueOf( listaPostesf.get(position).getCal_ap_con2());
                        txtNhil_2 = String.valueOf( listaPostesf.get(position).getNhil_con2());
                        txtCon3 = String.valueOf( listaPostesf.get(position).getCon3());
                        if(txtCon3.equals( "0" )){txtCon3="";}else{txtCon3=String.valueOf( listaPostesf.get(position).getCon3());}
                        txtCl_fs_3 = String.valueOf( listaPostesf.get(position).getCal_fase_con3());
                        txtCl_ne_3 = String.valueOf( listaPostesf.get(position).getCal_neutro_con3());
                        txtCl_ap_3 = String.valueOf( listaPostesf.get(position).getCal_ap_con3());
                        txtNhil_3 = String.valueOf( listaPostesf.get(position).getNhil_con3());
                        txtCon4 = String.valueOf( listaPostesf.get(position).getCon4());
                        if(txtCon4.equals( "0" )){txtCon4="";}else{txtCon4=String.valueOf( listaPostesf.get(position).getCon4());}
                        txtCl_fs_4 = String.valueOf( listaPostesf.get(position).getCal_fase_con4());
                        txtCl_ne_4 = String.valueOf( listaPostesf.get(position).getCal_neutro_con4());
                        txtCl_ap_4 = String.valueOf( listaPostesf.get(position).getCal_ap_con4());
                        txtNhil_4 = String.valueOf( listaPostesf.get(position).getNhil_con4());
                        operador = String.valueOf( listaPostesf.get(position).getUsu_levant_usu());

                        /*Toast toast1 = Toast.makeText(context, "editar estructura1 " +txtEstr1+" - "+txtTipEst1 , Toast.LENGTH_SHORT);
                        toast1.show();*/

                        //String.valueOf( municipio).toString().trim()
                        Intent intent =new Intent( context, frm_nodos_update.class );
                        intent.putExtra( "vcnodo", txtCodPoste.getText());
                        intent.putExtra( "vctraf", txtCodTrafo.getText());
                        intent.putExtra( "valtur", txtAltura.getText());
                        intent.putExtra( "vmater", txtMaterial.getText());
                        intent.putExtra( "vnroac", txtNroAco);
                        intent.putExtra( "vobsno", txtObsnodo.getText());
                        intent.putExtra( "vestr1", txtEstr1);
                        intent.putExtra( "vtipe1", txtTipEst1);
                        intent.putExtra( "vestr2", txtEstr2);
                        intent.putExtra( "vtipe2", txtTipEst2);
                        intent.putExtra( "vestr3", txtEstr3);
                        intent.putExtra( "vtipe3", txtTipEst3);
                        intent.putExtra( "vestr4", txtEstr4);
                        intent.putExtra( "vtipe4", txtTipEst4);
                        intent.putExtra( "vlonod", txtlongnodo.getText());
                        intent.putExtra( "vlatin", txtlatinodo.getText());
                        intent.putExtra( "valtin", txtaltinodo.getText());
                        intent.putExtra( "vnroet", txtNroret);
                        intent.putExtra( "vantig", txtAntig);
                        intent.putExtra( "vpropn", txtPropnodo.getText());
                        intent.putExtra( "vconn1", txtCon1);
                        intent.putExtra( "vclfs1", txtCl_fs_1);
                        intent.putExtra( "vclne1", txtCl_ne_1);
                        intent.putExtra( "vclap1", txtCl_ap_1);
                        intent.putExtra( "vnhil1", txtNhil_1);
                        intent.putExtra( "vconn2", txtCon2);
                        intent.putExtra( "vclfs2", txtCl_fs_2);
                        intent.putExtra( "vclne2", txtCl_ne_2);
                        intent.putExtra( "vclap2", txtCl_ap_2);
                        intent.putExtra( "vnhil2", txtNhil_2);
                        intent.putExtra( "vconn3", txtCon3);
                        intent.putExtra( "vclfs3", txtCl_fs_3);
                        intent.putExtra( "vclne3", txtCl_ne_3);
                        intent.putExtra( "vclap3", txtCl_ap_3);
                        intent.putExtra( "vnhil3", txtNhil_3);
                        intent.putExtra( "vconn4", txtCon4);
                        intent.putExtra( "vclfs4", txtCl_fs_4);
                        intent.putExtra( "vclne4", txtCl_ne_4);
                        intent.putExtra( "vclap4", txtCl_ap_4);
                        intent.putExtra( "vnhil4", txtNhil_4);
                        intent.putExtra( "IDENT", operador);
                        //intent.putExtra( "municipio", String.valueOf( municipio).toString().trim());
                        context.startActivity( intent );
                    }
                }
            } );
            elimi= (ImageView)itemView.findViewById( R.id.eliminar );
            elimi.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION){
                        operador = String.valueOf( listaPostesf.get(position).getUsu_levant_usu());
                        abre_dialogo(txtCodPoste);
                    }
                }
            } );
            sincr2=(ImageView)itemView.findViewById( R.id.movil3 );
            sincr2.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        operador = String.valueOf( listaPostesf.get( position ).getUsu_levant_usu() );
                        snc1p=listaPostesf.get(position).getEstado();
                        snc2p=listaPostesf.get(position).getPendiente_insercion();
                        if(snc1p == 0 && snc2p == 0 ) {
                            Toast.makeText( context, "Se Encuentra Sincronizado.", Toast.LENGTH_LONG ).show();
                        }else {
                            if (!isConnected( context )) {
                                Toast.makeText( context, "No se Pudo Sincronizar, verifique el acceso a Internet e Intente nuevamente", Toast.LENGTH_LONG ).show();

                            } else {
                                Toast.makeText( context, "Verificando Dato a Sincronizar.....", Toast.LENGTH_LONG ).show();
                                Cursor c = obtenerRegistrosSucios();
                                if (c.getCount() > 0) {

                                    Toast.makeText( context, "Sincronizando Datos...", Toast.LENGTH_LONG ).show();
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
        builder.setMessage( "Desea Borrar Este Codigo de Poste: "+codigo.getText() );
        builder.setPositiveButton( "Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                                    /*Toast toast1 = Toast.makeText(context, "Prueba Eliminar" + codigo.getText(), Toast.LENGTH_SHORT);
                                     toast1.show();*/

                resolver.delete( ContractParaG.CONTENT_URI, DatabaseManagerUser.Columnas.NOMB_NODO + "=?" ,new String[]{codigo.getText().toString()});
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
    public void setFilter( ArrayList <Trafo> newlistaPostesf ){
        this.listaPostesf = new ArrayList <>();
        this.listaPostesf.addAll( newlistaPostesf );
        notifyDataSetChanged();
    }
}
