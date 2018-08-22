package br.com.mojumob.bazarabc.activity;

import android.os.Bundle;
import android.app.Activity;

import br.com.mojumob.bazarabc.R;

public class MeusAnunciosActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
