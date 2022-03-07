package com.redylab.redylabsirr.adaptadores;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.redylab.redylabsirr.R;
import com.redylab.redylabsirr.entity.Trafo;
import com.redylab.redylabsirr.frm_nodos;

import java.util.ArrayList;

public class adaptador_trafos2 extends RecyclerView.Adapter<adaptador_trafos2.ViewHolderTrafos2> {
    private Context context;//se agrego
    private ArrayList<Trafo> listaTrafosf2;
    private ContentResolver resolver;
    String operador;
    String municipio;
    public adaptador_trafos2( ArrayList<Trafo> listaTrafosf){
        this.listaTrafosf2 = listaTrafosf;
    }

    @NonNull
    @Override
    public ViewHolderTrafos2 onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.registros_trafos2,parent,false);
        context = vista.getContext();// se agrego
        return new ViewHolderTrafos2(vista);
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolderTrafos2 holder, int position ) {
        holder.txtCodTrafo2.setText(listaTrafosf2.get(position).getNomb_trafo());
        holder.txtClasTrafo2.setText(listaTrafosf2.get(position).getTipo());
        holder.txtCapTrafo2.setText(String.valueOf(listaTrafosf2.get(position).getCapacidad())+" Kva");
        holder.txtLongTrafo2.setText(String.valueOf(listaTrafosf2.get(position).getLong_trafo()));
        holder.txtLatiTrafo2.setText(String.valueOf(listaTrafosf2.get(position).getLati_trafo()));
        holder.txtAltuTrafo2.setText(String.valueOf(listaTrafosf2.get(position).getAlt_trafo()));
        holder.txtTipoTrafo2.setText(String.valueOf(listaTrafosf2.get(position).getTipo_trafo()));
        municipio=listaTrafosf2.get(position).getMpio();
        operador = String.valueOf(listaTrafosf2.get(position).getUsu_levant_usu());
        int pr =listaTrafosf2.get(position).getZonaur();
        String vrl = "";
        if(pr == 3){
            vrl="URBANO";
        }else if(pr ==4 ){
            vrl="RURAL";
        }
        holder.txtUbicTrafo2.setText(vrl.toString().trim());
        holder.txtPropTrafo2.setText(String.valueOf(listaTrafosf2.get(position).getPropiedad_trafo()));
        holder.txtObsrTrafo2.setText(String.valueOf(listaTrafosf2.get(position).getObservacion_trafo()));
    }

    @Override
    public int getItemCount() {
        return listaTrafosf2.size();
    }

    class ViewHolderTrafos2 extends RecyclerView.ViewHolder {

        TextView txtCodTrafo2,txtTipoTrafo2,txtCapTrafo2,txtLongTrafo2,txtLatiTrafo2,txtAltuTrafo2,txtClasTrafo2,txtUbicTrafo2,txtPropTrafo2,txtObsrTrafo2;//
        ImageView env_frm;

        ViewHolderTrafos2( View itemView ) {
            super( itemView );
            txtCodTrafo2= itemView.findViewById(R.id.textCodTraf2);
            txtClasTrafo2=itemView.findViewById(R.id.textClas2);
            txtCapTrafo2= itemView.findViewById(R.id.textCapa2);
            txtTipoTrafo2= itemView.findViewById(R.id.textTipo2);
            txtUbicTrafo2= itemView.findViewById(R.id.textUbic2);
            txtPropTrafo2= itemView.findViewById(R.id.textProp2);
            txtObsrTrafo2= itemView.findViewById(R.id.textObsr2);
            txtLongTrafo2=itemView.findViewById(R.id.textlong2);
            txtLatiTrafo2=itemView.findViewById(R.id.textlati2);
            txtAltuTrafo2=itemView.findViewById(R.id.textaltu2);

            env_frm = itemView.findViewById(R.id.enviar2);// se agrego
            env_frm.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {//se agrego
                   int position =getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION){
                        Intent intent = new Intent( context, frm_nodos.class );
                        intent.putExtra("codigotrafo", txtCodTrafo2.getText());
                        context.startActivity( intent );

                    }
                }
            } );

        }
    }
    public void setFilter( ArrayList <Trafo> newlistaTrafosf ){
        this.listaTrafosf2 = new ArrayList <>();
        this.listaTrafosf2.addAll( newlistaTrafosf );
        notifyDataSetChanged();
    }
}
