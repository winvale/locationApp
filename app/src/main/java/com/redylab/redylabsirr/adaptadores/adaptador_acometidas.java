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
import com.redylab.redylabsirr.database.DatabaseManagerUser;
import com.redylab.redylabsirr.entity.Trafo;
import com.redylab.redylabsirr.frm_acometidas_update;
import com.redylab.redylabsirr.frm_trafos_update;
import com.redylab.redylabsirr.mainActivity;
import com.redylab.redylabsirr.sync.SyncAdapter;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.redylab.redylabsirr.sync.SyncAdapter.PROJECTION;


public class adaptador_acometidas extends RecyclerView.Adapter<adaptador_acometidas.ViewHolderacometidas> {

    private ArrayList<Trafo> listaAcometidasf;
    private Context context;
    private ContentResolver resolver;
    String operador,tip_casud,tip_assud,tip_mbtsud,tablerud;
    ImageView movt4;
    int snc1a = 2,snc2a = 2;
    public adaptador_acometidas( ArrayList<Trafo> listaAcometidasf){this.listaAcometidasf = listaAcometidasf;}

    @NonNull
    @Override
    public ViewHolderacometidas onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.registros_acometidas,parent,false);
        context = vista.getContext();// se agrego
        resolver = context.getContentResolver();
        movt4=(ImageView)vista.findViewById( R.id.movil4 ) ;
        return new ViewHolderacometidas(vista);
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolderacometidas holder, int position ) {
        holder.txtCodAcom.setText(listaAcometidasf.get(position).getNro_acometida_corresp());
        holder.txtCodPosaco.setText(listaAcometidasf.get(position).getId_nodo_usu());
        holder.txtContaco.setText(listaAcometidasf.get(position).getCont_act());


        String txtTipu =(listaAcometidasf.get(position).getTip_mbt());
        if(txtTipu.equals("M")){
            tip_mbtsud="MONOFÁSICO";
            holder.txtTip.setText(tip_mbtsud);
        }else if(txtTipu.equals("B")){
            tip_mbtsud="BIFÁSICO";
            holder.txtTip.setText(tip_mbtsud);
        }else if(txtTipu.equals("T")){
            tip_mbtsud="TRIFÁSICO";
            holder.txtTip.setText(tip_mbtsud);
        }
        holder.txtDir.setText(listaAcometidasf.get(position).getDireccion());
        holder.txtTipser.setText(listaAcometidasf.get(position).getTip_servicio());
        holder.txtobsru.setText(listaAcometidasf.get(position).getObservacion_usu());
        holder.txtLonu.setText(String.valueOf(listaAcometidasf.get(position).getLong_usu()));
        holder.txtLatu.setText(String.valueOf(listaAcometidasf.get(position).getLati_usu()));
        holder.txtAltiu.setText(String.valueOf(listaAcometidasf.get(position).getAlti_usu()));
        //
        int movie3 =listaAcometidasf.get(position).getEstado();
        int movip3 = listaAcometidasf.get(position).getPendiente_insercion();
        String np = listaAcometidasf.get(position).getNro_acometida_corresp();
        Log.i("parametro5: " , np);
        Log.i("parametro6: " , String.valueOf(movie3));
        Log.i("parametro7: " , String.valueOf(movip3));

        if(SyncAdapter.repuesta_act.equals("1")){

        }
        if(movie3 == 0 && movip3 == 0 )
        {
            movt4.setImageResource( R.drawable.sinc_movil_y );
        }
        else{
            movt4.setImageResource( R.drawable.sinc_movil_n );
        }
    }

    @Override
    public int getItemCount() {
        return listaAcometidasf.size();
    }

    class ViewHolderacometidas extends RecyclerView.ViewHolder {

        TextView txtCodAcom, txtCodPosaco,txtContaco,txtTip,txtDir,txtTipser,txtobsru,txtLonu,txtLatu,txtAltiu;
        ImageView elimi,edit,sincr3;
        ViewHolderacometidas( View itemView ) {
            super( itemView );
            txtCodAcom= itemView.findViewById(R.id.textCodAcom);
            txtCodPosaco= itemView.findViewById(R.id.textCodPost);
            txtContaco=  itemView.findViewById(R.id.textConta);
            txtTip= itemView.findViewById(R.id.textTipo);
            txtDir= itemView.findViewById(R.id.textDir);
            txtTipser= itemView.findViewById(R.id.textSec);
            txtobsru= itemView.findViewById(R.id.textObsra);
            txtLonu= itemView.findViewById(R.id.textlonga);
            txtLatu= itemView.findViewById(R.id.textlatia);
            txtAltiu= itemView.findViewById(R.id.textaltua);

            edit = itemView.findViewById( R.id.editaru );
            edit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION){

                        operador = String.valueOf(listaAcometidasf.get(position).getUsu_levant_usu());
                        String cont_marc =listaAcometidasf.get(position).getCont_marca();
                        String tip_cau =listaAcometidasf.get(position).getTip_ca();
                        if(tip_cau.equals("C")){
                            tip_casud="CONCÉNTRICA";
                        }else if(tip_cau.equals("A")){
                            tip_casud="ABIERTA";
                        }
                        String tip_asu =listaAcometidasf.get(position).getTip_as();
                        if(tip_asu.equals("A")){
                            tip_assud="AÉREA";
                        }else if(tip_asu.equals("S")){
                            tip_assud="SUBTERRÁNEA";
                        }
                        String tip_rstu =listaAcometidasf.get(position).getTip_rst();

                        String tablerou =listaAcometidasf.get(position).getTablero();
                        Log.i( TAG, "prueba  tablero: " + tablerou);
                        if(tablerou.equals( "N" )){
                            tablerud="NO";
                        }else if(tablerou.equals( "S" )){
                            tablerud="SI";
                        }


                        String calibreu =String.valueOf(listaAcometidasf.get(position).getCalibre());
                        String fastablerou =listaAcometidasf.get(position).getFas_tablero();

                        Intent intent =new Intent( context, frm_acometidas_update.class );
                        intent.putExtra( "u_codaco", txtCodAcom.getText());
                        intent.putExtra( "u_codPost", txtCodPosaco.getText());
                        intent.putExtra( "u_cont", txtContaco.getText());
                        intent.putExtra( "u_tipom", txtTip.getText());
                        intent.putExtra( "u_dir", txtDir.getText());
                        intent.putExtra( "u_serv", txtTipser.getText());
                        intent.putExtra( "u_obs", txtobsru.getText());
                        intent.putExtra( "u_lon", txtLonu.getText());
                        intent.putExtra( "u_lat", txtLatu.getText());
                        intent.putExtra( "u_alt", txtAltiu.getText());
                        intent.putExtra( "u_contmar", cont_marc);
                        intent.putExtra( "u_tipca", tip_casud);
                        intent.putExtra( "u_tipas", tip_assud);
                        intent.putExtra( "u_tiprst", tip_rstu);
                        intent.putExtra( "u_calibre", calibreu);
                        intent.putExtra( "u_tablero", tablerud);
                        intent.putExtra( "u_fastablero", fastablerou);
                        intent.putExtra( "IDENT", operador);
                        context.startActivity( intent );
                    }
                }
            } );
            elimi= (ImageView)itemView.findViewById( R.id.eliminaru );
            elimi.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION){
                        operador = String.valueOf(listaAcometidasf.get(position).getUsu_levant_usu());
                        abre_dialogo(txtCodAcom);
                    }
                }
            } );
            sincr3=(ImageView)itemView.findViewById( R.id.movil4 );
            sincr3.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        operador = String.valueOf( listaAcometidasf.get( position ).getUsu_levant_usu() );
                        snc1a=listaAcometidasf.get(position).getEstado();
                        snc2a=listaAcometidasf.get(position).getPendiente_insercion();
                        if(snc1a == 0 && snc2a == 0 ) {
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
        builder.setMessage( "Desea Borrar Este Codigo de Acometida: "+codigo.getText() );
        builder.setPositiveButton( "Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                resolver.delete( ContractParaG.CONTENT_URI, DatabaseManagerUser.Columnas.ACOMETIDA_CORRESP + "=?" ,new String[]{codigo.getText().toString()});
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
    public void setFilter( ArrayList <Trafo> newlistaAcometidasf ){
        this.listaAcometidasf = new ArrayList <>();
        this.listaAcometidasf.addAll( newlistaAcometidasf );
        notifyDataSetChanged();
    }
}
