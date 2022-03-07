package com.redylab.redylabsirr.adaptadores;

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
import com.redylab.redylabsirr.frm_acometidas;


import java.util.ArrayList;

public class adaptador_postes2 extends RecyclerView.Adapter<adaptador_postes2.ViewHolderPostes2> {
    private Context context;//se agrego
    private ArrayList<Trafo> listaPostesf2;

    public adaptador_postes2( ArrayList<Trafo> listaPostesf){
        this.listaPostesf2 = listaPostesf;
    }

    @NonNull
    @Override
    public ViewHolderPostes2 onCreateViewHolder( @NonNull ViewGroup parent, int viewType ) {
        View vista= LayoutInflater.from(parent.getContext()).inflate( R.layout.registros_postes2,parent,false);
        context = vista.getContext();// se agrego
        return new ViewHolderPostes2(vista);
    }

    @Override
    public void onBindViewHolder( @NonNull ViewHolderPostes2 holder, int position ) {
        holder.txtCodPoste2.setText(listaPostesf2.get(position).getNomb_nodo());
        holder.txtCodTrafo2.setText(listaPostesf2.get(position).getId_trafo_nodo());
        holder.txtAltura2.setText(String.valueOf(listaPostesf2.get(position).getAltura_nodo())+"Mts");
        holder.txtMaterial2.setText(listaPostesf2.get(position).getMaterial_nodo());
        holder.txtNroAco.setText(String.valueOf(listaPostesf2.get(position).getNro_acomet()));
        holder.txtObsnodo.setText(listaPostesf2.get(position).getObservacion_nodo());
        holder.txtlongnodo.setText(String.valueOf(listaPostesf2.get(position).getLong_nodo()));
        holder.txtlatinodo.setText(String.valueOf(listaPostesf2.get(position).getLati_nodo()));
        holder.txtaltinodo.setText(String.valueOf(listaPostesf2.get(position).getAlti_nodo()));
        holder.txtPropnodo.setText(listaPostesf2.get(position).getPropiedad_nodo());

    }

    @Override
    public int getItemCount() {
        return listaPostesf2.size();
    }

    class ViewHolderPostes2 extends RecyclerView.ViewHolder {

        TextView txtCodPoste2, txtCodTrafo2,txtAltura2,txtMaterial2,txtNroAco,txtObsnodo,txtlongnodo,txtlatinodo,txtaltinodo,txtPropnodo;
        ImageView env_frm2;

        ViewHolderPostes2( View itemView ) {
            super( itemView );
            txtCodPoste2= itemView.findViewById(R.id.textCodPost2);
            txtCodTrafo2=  itemView.findViewById(R.id.textCodTraf2);
            txtAltura2= itemView.findViewById(R.id.textAltu2);
            txtMaterial2=  itemView.findViewById(R.id.textMate2);
            txtNroAco = itemView.findViewById( R.id.textNraco );
            txtObsnodo = itemView.findViewById( R.id.textObsn );
            txtlongnodo = itemView.findViewById( R.id.textLonod );
            txtlatinodo = itemView.findViewById( R.id.textLanod );
            txtaltinodo = itemView.findViewById( R.id.textAlnod );
            txtPropnodo = itemView.findViewById( R.id.textPropn );

            env_frm2 = itemView.findViewById(R.id.enviar3);// se agrego
            env_frm2.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View v ) {//se agrego
                    int position =getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION){
                        Intent intentnodo = new Intent( context, frm_acometidas.class );
                        intentnodo.putExtra("codigoposte", txtCodPoste2.getText().toString());
                        intentnodo.putExtra("codigotrafo", txtCodTrafo2.getText().toString());
                        context.startActivity( intentnodo );
                    }
                }
            } );

        }
    }
    public void setFilter( ArrayList <Trafo> newlistaPostesf ){
        this.listaPostesf2 = new ArrayList <>();
        this.listaPostesf2.addAll( newlistaPostesf );
        notifyDataSetChanged();
    }
}
