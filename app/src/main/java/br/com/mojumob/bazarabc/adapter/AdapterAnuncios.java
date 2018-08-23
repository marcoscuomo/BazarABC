package br.com.mojumob.bazarabc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.mojumob.bazarabc.R;
import br.com.mojumob.bazarabc.model.Anuncio;

public class AdapterAnuncios extends RecyclerView.Adapter<AdapterAnuncios.MyViewHolder> {

    private Context context;
    private List<Anuncio> listaAnuncios;

    public AdapterAnuncios(Context context, List<Anuncio> listaAnuncios) {
        this.context = context;
        this.listaAnuncios = listaAnuncios;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_anuncio, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        Anuncio anuncio = listaAnuncios.get(i);
        holder.titulo.setText(anuncio.getTitulo());
        holder.valor.setText(anuncio.getValor());

    }

    @Override
    public int getItemCount() {
        return listaAnuncios.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, valor;
        ImageView foto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.adapterAnuncio_txtTituo);
            valor = itemView.findViewById(R.id.adapterAnuncio_txtPreco);
            foto = itemView.findViewById(R.id.adapterAnuncio_imgAnuncio);
        }
    }

}
