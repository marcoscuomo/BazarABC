package br.com.mojumob.bazarabc.activity;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;

import java.util.Locale;

import br.com.mojumob.bazarabc.R;

public class CadastroAnuncioActivity extends AppCompatActivity {

    //Atributos
    private EditText edtTitulo, edtValor, edtDescricao;
    private CurrencyEditText campoValor;
    private Spinner spCidade, spCategoria;
    private ImageView imgAnuncio1, imgAnuncio2, imgAnuncio3;
    private Button btnCadastrar;

    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_anuncio);

        //Validar permissoes


        inicializaComponentes();


        //Evento clique do botao cadastrar anuncio
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarAnuncio();
            }
        });


    }

    private void salvarAnuncio() {

        //Pega os valores
        String valor = campoValor.getHintString();
        Toast.makeText(CadastroAnuncioActivity.this, "Valor: " + valor, Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        //Inicializações
        edtTitulo    = findViewById(R.id.cadastroAnuncio_edtTitulo);
        campoValor   = findViewById(R.id.cadastroAnuncio_edtValor);
        edtDescricao = findViewById(R.id.cadastroAnuncio_edtDescricao);
        spCidade     = findViewById(R.id.cadastroAnuncio_spCidade);
        spCategoria  = findViewById(R.id.cadastroAnuncio_spCategoria);
        btnCadastrar = findViewById(R.id.cadastroAnuncio_btnCadastrar);

        //Configura a localidade para pt-br
        Locale locale = new Locale("pt", "BR");
        campoValor.setLocale(locale);
    }
}
