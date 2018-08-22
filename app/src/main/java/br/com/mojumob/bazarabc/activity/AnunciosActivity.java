package br.com.mojumob.bazarabc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import br.com.mojumob.bazarabc.R;
import br.com.mojumob.bazarabc.helper.ConfiguracaoFirebase;


public class AnunciosActivity extends AppCompatActivity {

    //Atributos
    private FirebaseAuth autenticacao;
    //private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);
        inicializacoes();

        //Toolbar
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);


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

    private void inicializacoes() {
        //Inicializações
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //toolbar = findViewById(R.id.toolbar);
    }
}
