package br.com.mojumob.bazarabc.activity;

import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
        inicializaComponentes();

        //Evento de click no FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void inicializaComponentes() {
        //Inicializações
        fab = findViewById(R.id.fabMeusAnuncios);
        recyclerAnuncios = findViewById(R.id.recyclerAnuncios);
    }

}
