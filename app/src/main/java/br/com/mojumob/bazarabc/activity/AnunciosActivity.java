package br.com.mojumob.bazarabc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;

import br.com.mojumob.bazarabc.R;
import br.com.mojumob.bazarabc.helper.ConfiguracaoFirebase;


public class AnunciosActivity extends AppCompatActivity {

    //Atributos
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);
        inicializacoes();


    }

    private void inicializacoes() {
        //Inicializações
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        //Verifica se o usuario esta logado
        if(autenticacao == null){
            //Usuario deslogado
            menu.setGroupVisible(R.id.group_deslogado, true);
        }else{
            //Usuario logado
            menu.setGroupVisible(R.id.group_logado, true);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}
