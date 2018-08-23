package br.com.mojumob.bazarabc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.mojumob.bazarabc.R;
import br.com.mojumob.bazarabc.adapter.AdapterAnuncios;
import br.com.mojumob.bazarabc.helper.ConfiguracaoFirebase;
import br.com.mojumob.bazarabc.model.Anuncio;


public class MeusAnunciosActivity extends AppCompatActivity {

    //Atributos
    private FloatingActionButton fab;
    private RecyclerView recyclerAnuncios;
    private Toolbar toolbar;
    private List<Anuncio> listaAnuncios = new ArrayList<>();
    private AdapterAnuncios adapter;
    private DatabaseReference anuncioUsuarioRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);

        inicializaComponentes();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configura RecylerView
        recyclerAnuncios.setLayoutManager(new LinearLayoutManager(MeusAnunciosActivity.this));
        recyclerAnuncios.setHasFixedSize(true);
        adapter = new AdapterAnuncios(MeusAnunciosActivity.this, listaAnuncios);
        recyclerAnuncios.setAdapter(adapter);

        //Recupera o anuncio para o usuario
        recuperaAnuncios();




        //Evento de click no FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeusAnunciosActivity.this, CadastroAnuncioActivity.class));
            }
        });


    }

    private void recuperaAnuncios() {
        anuncioUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listaAnuncios.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    listaAnuncios.add(ds.getValue(Anuncio.class));
                }
                Collections.reverse(listaAnuncios);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void inicializaComponentes() {
        //Inicializações

        //toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fabMeusAnuncios);
        recyclerAnuncios = findViewById(R.id.recyclerAnuncios);

        //Firebase
        anuncioUsuarioRef = ConfiguracaoFirebase.getFirebase().child("meus_anuncios")
                                                              .child(ConfiguracaoFirebase.getIdUsuario());

    }

}
