package br.com.mojumob.bazarabc.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
import br.com.mojumob.bazarabc.helper.RecyclerItemClickListener;
import br.com.mojumob.bazarabc.model.Anuncio;
import dmax.dialog.SpotsDialog;


public class AnunciosActivity extends AppCompatActivity {

    //Atributos
    private FirebaseAuth autenticacao;
    private Button btnRegiao, btnCategoria;
    private RecyclerView recyclerAnunciosPublicos;
    private List<Anuncio> listaAnuncios = new ArrayList<>();
    private AdapterAnuncios adapter;
    private DatabaseReference anunciosPublicosRef;
    private AlertDialog dialog;
    private String filtroCidade = "";
    private String filtroCategoria = "";
    private Boolean filtrandoPorCidade = false;
    //private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);
        inicializaComponentes();

        //Configurações iniciais
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        anunciosPublicosRef = ConfiguracaoFirebase.getFirebase()
                                                    .child("anuncios");


        //Configura RecylerView
        recyclerAnunciosPublicos.setLayoutManager(new LinearLayoutManager(AnunciosActivity.this));
        recyclerAnunciosPublicos.setHasFixedSize(true);
        adapter = new AdapterAnuncios(AnunciosActivity.this, listaAnuncios);
        recyclerAnunciosPublicos.setAdapter(adapter);

        recuperaAnunciosPublicos();

        //Aplicar evento de clique no RecyclerView
        recyclerAnunciosPublicos.addOnItemTouchListener(new RecyclerItemClickListener(
                AnunciosActivity.this, recyclerAnunciosPublicos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Anuncio anuncioSelecionado = listaAnuncios.get(position);
                Intent i = new Intent(AnunciosActivity.this, DetalhesProdutosActivity.class);
                i.putExtra("anuncioSelecionado", anuncioSelecionado);
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

        //Toolbar
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    public void filtraPorCidade(View view){

        AlertDialog.Builder dialogCidade = new AlertDialog.Builder(this);
        dialogCidade.setTitle("Selecione a cidade desejada");

        //Configura o spinner
        View viewSpninner = getLayoutInflater() .inflate(R.layout.dialog_spinner, null);
        dialogCidade.setView(viewSpninner);

        final Spinner spCidade = viewSpninner.findViewById(R.id.spinnerFiltro);
        String[] cidades = new String[]{
                "Diadema","Santo André", "São Bernardo", "São Caetano"
        };

        //Configura o spinner Cidades
        ArrayAdapter<String> adapterCidades = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, cidades
        );
        adapterCidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCidade.setAdapter(adapterCidades);



        dialogCidade.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                filtroCidade = spCidade.getSelectedItem().toString();
                recuperarAnucioPorCidade();
                filtrandoPorCidade = true;

            }
        });

        dialogCidade.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        Dialog dialog = dialogCidade.create();
        dialog.show();

    }

    public void filtraPorCategoria(View view){

        if(filtrandoPorCidade){

            AlertDialog.Builder dialogCidade = new AlertDialog.Builder(this);
            dialogCidade.setTitle("Selecione a categoria desejada");

            //Configura o spinner
            View viewSpninner = getLayoutInflater() .inflate(R.layout.dialog_spinner, null);
            dialogCidade.setView(viewSpninner);

            final Spinner spCategoria = viewSpninner.findViewById(R.id.spinnerFiltro);
            String[] categorias = new String[]{
                    "Automóveis","Imóveis", "Eletronicos", "Moda", "Esportes", "Música", "Infantil", "Agro"
            };

            //Configura o spinner Cidades
            ArrayAdapter<String> adapterCidades = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, categorias
            );
            adapterCidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategoria.setAdapter(adapterCidades);



            dialogCidade.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    filtroCategoria = spCategoria.getSelectedItem().toString();
                    recuperarAnucioPorCategoria();

                }
            });

            dialogCidade.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            Dialog dialog = dialogCidade.create();
            dialog.show();

        }else{
            Toast.makeText(this, "Escolha primeiro uma região", Toast.LENGTH_SHORT).show();
        }



    }

    private void recuperarAnucioPorCategoria() {

        anunciosPublicosRef = ConfiguracaoFirebase.getFirebase()
                .child("anuncios")
                .child(filtroCidade);

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAnuncios.clear();
                for (DataSnapshot anunciosDS :
                        dataSnapshot.getChildren()) {
                    Anuncio anuncio = anunciosDS.getValue(Anuncio.class);
                    listaAnuncios.add(anuncio);
                }

                Collections.reverse(listaAnuncios);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void recuperarAnucioPorCidade() {
        anunciosPublicosRef = ConfiguracaoFirebase.getFirebase()
                                            .child("anuncios")
                                            .child(filtroCidade);

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAnuncios.clear();
                for (DataSnapshot categorias :
                        dataSnapshot.getChildren()) {
                    for (DataSnapshot anunciosDS :
                            categorias.getChildren()) {
                        Anuncio anuncio = anunciosDS.getValue(Anuncio.class);
                        listaAnuncios.add(anuncio);
                    }
                }

                Collections.reverse(listaAnuncios);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void recuperaAnunciosPublicos(){

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Recuperando o anúncios")
                .setCancelable(false)
                .build();
        dialog.show();

        listaAnuncios.clear();
        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot cidades :
                        dataSnapshot.getChildren()) {
                    for (DataSnapshot categorias :
                            cidades.getChildren()) {
                        for (DataSnapshot anunciosDS :
                                categorias.getChildren()) {
                            Anuncio anuncio = anunciosDS.getValue(Anuncio.class);
                            listaAnuncios.add(anuncio);
                        }

                    }
                }

                Collections.reverse(listaAnuncios);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        //Verifica se o usuario esta logado
        if(autenticacao.getCurrentUser() == null){
            //Usuario deslogado
            menu.setGroupVisible(R.id.group_deslogado, true);
        }else{
            //Usuario logado
            menu.setGroupVisible(R.id.group_logado, true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_cadastrar :
                vaiParaTelaCadastro();
                break;
            case R.id.menu_sair:
                autenticacao.signOut();
                invalidateOptionsMenu();
                break;
            case R.id.menu_anuncios:
                startActivity(new Intent(AnunciosActivity.this, MeusAnunciosActivity.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void vaiParaTelaCadastro() {
        startActivity(new Intent(AnunciosActivity.this, LoginActivity.class));
    }

    private void inicializaComponentes() {
        //Inicializações
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        btnCategoria = findViewById(R.id.anuncios_btnCategoria);
        btnRegiao    = findViewById(R.id.anuncios_btnRegia);
        recyclerAnunciosPublicos = findViewById(R.id.anuncios_recyclerAnunciosPublicos);
        //toolbar = findViewById(R.id.toolbar);
    }
}
