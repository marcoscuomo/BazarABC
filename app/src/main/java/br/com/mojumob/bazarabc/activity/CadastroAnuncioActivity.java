package br.com.mojumob.bazarabc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.blackcat.currencyedittext.CurrencyEditText;

import br.com.mojumob.bazarabc.R;

public class CadastroAnuncioActivity extends AppCompatActivity {

    //Atributos
    private EditText edtTitulo, edtValor, edtDescricao;
    private CurrencyEditText campoValor;
    private Spinner spCidade, spCategoria;
    private ImageView imgAnuncio1, imgAnuncio2, imgAnuncio3;
    private Button btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_anuncio);

        //Inicializações
        edtTitulo    = findViewById(R.id.cadastroAnuncio_edtTitulo);
        campoValor   = findViewById(R.id.cadastroAnuncio_edtValor);
        edtDescricao = findViewById(R.id.cadastroAnuncio_edtDescricao);
        spCidade     = findViewById(R.id.cadastroAnuncio_spCidade);
        spCategoria  = findViewById(R.id.cadastroAnuncio_spCategoria);
        btnCadastrar = findViewById(R.id.cadastroAnuncio_btnCadastrar);

    }
}
