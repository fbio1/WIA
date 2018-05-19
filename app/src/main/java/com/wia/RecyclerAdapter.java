package com.wia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wia.model.Local;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter{
    private List<Local> listaLocal;
    private Context c;

    public RecyclerAdapter(List<Local> listaLocal, Context c) {
        if (listaLocal != null){
            this.listaLocal = listaLocal;
        }else{
            this.listaLocal = new ArrayList<>();
        }
        this.c = c;
    }

    public void add(Local local){
        listaLocal.add(local);
        notifyItemInserted(listaLocal.size()+1);
    }

    public void clear (){
        listaLocal = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_local, parent, false);
        LocalViewHolder localViewHolder = new LocalViewHolder(v);

        return localViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LocalViewHolder locaisViewHolder = (LocalViewHolder) holder;
        Local local = listaLocal.get(position);
        locaisViewHolder.nome.setText(local.getNome());
        locaisViewHolder.setor.setText(local.getSetor());

        Glide.with(locaisViewHolder.img.getContext())
                .load(local.getImage())
                .into(locaisViewHolder.img);
    }

    @Override
    public int getItemCount() {
        return listaLocal != null ? listaLocal.size() : 0;
    }

    public Local getLocal(int pos){
        return listaLocal.get(pos);
    }

    public class LocalViewHolder extends RecyclerView.ViewHolder{
        final LinearLayout linearLayout;
        final TextView nome;
        final ImageView img;
        final TextView setor;
        public LocalViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomeLocal);
            img = itemView.findViewById(R.id.foto_local);
            setor = itemView.findViewById(R.id.nomeSetor);
            linearLayout = itemView.findViewById(R.id.linearLayoutLocal);
        }
    }
}
