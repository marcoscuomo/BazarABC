package br.com.mojumob.bazarabc.activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

import br.com.mojumob.bazarabc.R;

public class MeusAnunciosActivity extends Activity {

    //Atributos
    private FloatingActionButton fab;
    private RecyclerView recyclerAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Inicializações
        fab = findViewById(R.id.fabMeusAnuncios);
        recyclerAnuncios = findViewById(R.id.recyclerAnuncios);
    }

}
