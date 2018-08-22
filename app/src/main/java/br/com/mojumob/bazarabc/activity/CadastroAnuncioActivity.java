package br.com.mojumob.bazarabc.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaDataSource;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.mojumob.bazarabc.R;
import br.com.mojumob.bazarabc.helper.Permissoes;

public class CadastroAnuncioActivity extends AppCompatActivity implements View.OnClickListener {

    //Atributos
    private EditText edtTitulo, edtValor, edtDescricao;
    private CurrencyEditText campoValor;
    private Spinner spCidade, spCategoria;
    private ImageView imgAnuncio1, imgAnuncio2, imgAnuncio3;
    private Button btnCadastrar;

    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private List<String> listaFotosRecuperada = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_anuncio);

        //Validar permissoes
        Permissoes.validarPermissoes(permissoes, this, 1);

        inicializaComponentes();
        carregarDadosSpinner();


        //Evento clique do botao cadastrar anuncio
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarAnuncio();
            }
        });


    }

    private void carregarDadosSpinner() {

        String[] cidades = new String[]{
            "Diadema","Santo André", "São Bernardo", "São Caetano"
        };

        String[] categorias = new String[]{
                "Automóveis","Imóveis", "Eletronicos", "Moda", "Esportes", "Música", "Infantil", "Agro"
        };

        //Configura o spinner Cidades
        ArrayAdapter<String> adapterCidades = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, cidades
        );
        adapterCidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCidade.setAdapter(adapterCidades);

        //Configura o spinner Categorias
        ArrayAdapter<String> adapterCategorias = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, categorias);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapterCategorias);


    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.cadastroAnuncio_imgAnuncio1:
                escolherImagem(1);
                break;
            case R.id.cadastroAnuncio_imgAnuncio2:
                escolherImagem(2);
                break;
            case R.id.cadastroAnuncio_imgAnuncio3:
                escolherImagem(3);
                break;
        }

    }

    private void escolherImagem(int requestCode) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    //Pega o resultado da Activity


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){

            //Recuperar imagem
            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();

            //Configura a imagem no imageview
            if(requestCode == 1){
                imgAnuncio1.setImageURI(imagemSelecionada);
            }else if(requestCode == 2){
                imgAnuncio2.setImageURI(imagemSelecionada);
            }else if(requestCode == 3) {
                imgAnuncio3.setImageURI(imagemSelecionada);
            }

            listaFotosRecuperada.add(caminhoImagem);
        }

    }

    private void salvarAnuncio() {

        if(validaDadosAnuncio()){

        }

    }

    private boolean validaDadosAnuncio() {

        //Pegando os valores
        String cidade = spCidade.getSelectedItem().toString();
        String categoria = spCategoria.getSelectedItem().toString();
        String titulo = edtTitulo.getText().toString();
        String valor = String.valueOf(campoValor.getRawValue());
        String descricao = edtDescricao.getText().toString();

        if(listaFotosRecuperada.size() != 0){
            if(!cidade.isEmpty()){
                if(!categoria.isEmpty()){
                    if(!titulo.isEmpty()){
                        if(!valor.isEmpty() && !valor.equals("0")){
                            if(!descricao.isEmpty()){
                                return true;
                            }else{
                                exibirMensagemErro("Coloque uma descricao para o seu anúncio");
                            }
                        }else{
                            exibirMensagemErro("Infome um valor para seu produto");
                        }
                    }else{
                        exibirMensagemErro("Escreve um titulo para seu anúncio");
                    }
                }else {
                    exibirMensagemErro("Selecione a categoria do produto");
                }
            }else{
                exibirMensagemErro("Selecione sua cidade ");
            }
        }else{
            exibirMensagemErro("Selecione ao menos uma foto");
        }

        return false;
    }

    private void exibirMensagemErro(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        //Inicializações
        edtTitulo    = findViewById(R.id.cadastroAnuncio_edtTitulo);
        campoValor   = findViewById(R.id.cadastroAnuncio_edtValor);
        edtDescricao = findViewById(R.id.cadastroAnuncio_edtDescricao);
        spCidade     = findViewById(R.id.cadastroAnuncio_spCidade);
        spCategoria  = findViewById(R.id.cadastroAnuncio_spCategoria);
        btnCadastrar = findViewById(R.id.cadastroAnuncio_btnCadastrar);
        imgAnuncio1  = findViewById(R.id.cadastroAnuncio_imgAnuncio1);
        imgAnuncio2  = findViewById(R.id.cadastroAnuncio_imgAnuncio2);
        imgAnuncio3  = findViewById(R.id.cadastroAnuncio_imgAnuncio3);

        imgAnuncio1.setOnClickListener(this);
        imgAnuncio2.setOnClickListener(this);
        imgAnuncio3.setOnClickListener(this);

        //Configura a localidade para pt-br
        Locale locale = new Locale("pt", "BR");
        campoValor.setLocale(locale);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoResultado : grantResults){
            if(permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Permissao negada");
        alert.setMessage("Para utilizar o app é necessário aceitar as permissões");
        alert.setCancelable(false);
        alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = alert.create();
        alert.show();
    }
}
