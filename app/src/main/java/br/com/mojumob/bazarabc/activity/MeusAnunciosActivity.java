package br.com.mojumob.bazarabc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import br.com.mojumob.bazarabc.R;


public class MeusAnunciosActivity extends AppCompatActivity {

    //Atributos
    private FloatingActionButton fab;
    private RecyclerView recyclerAnuncios;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);

        inicializaComponentes();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //Evento de click no FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeusAnunciosActivity.this, CadastroAnuncioActivity.class));
            }
        });


    }

    private void inicializaComponentes() {
        //Inicializações
        //toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fabMeusAnuncios);
        recyclerAnuncios = findViewById(R.id.recyclerAnuncios);

    }

}
