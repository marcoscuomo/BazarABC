package br.com.mojumob.bazarabc.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.mojumob.bazarabc.R;
import br.com.mojumob.bazarabc.helper.ConfiguracaoFirebase;
import br.com.mojumob.bazarabc.helper.Permissoes;
import br.com.mojumob.bazarabc.model.Anuncio;

public class CadastroAnuncioActivity extends AppCompatActivity implements View.OnClickListener {

    //Atributos
    private EditText edtTitulo, edtValor, edtDescricao;
    private CurrencyEditText campoValor;
    private Spinner spCidade, spCategoria;
    private ImageView imgAnuncio1, imgAnuncio2, imgAnuncio3;
    private Button btnCadastrar;
    private Anuncio anuncio;
    private StorageReference storage;

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

        //Firebaseauth
        storage = ConfiguracaoFirebase.getFirebaseStorage();


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

            //Salvar imagens no storage
            for(int i=0; i < listaFotosRecuperada.size(); i++){
                String urlImagem = listaFotosRecuperada.get(i);
                int tamanhoLista = listaFotosRecuperada.size();
                salvarFotosStorage(urlImagem, tamanhoLista, i);
            }

        }

    }

    private void salvarFotosStorage(String urlString, int totalFotos, int contador) {

        //Cria nó no stoge
        StorageReference imagemAnuncio = storage
                .child("imagens")
                .child("anuncios")
                .child(anuncio.getIdAnuncio())
                .child("imagem"+contador);

        //Faz o upload do arquivo
        UploadTask uploadTask = imagemAnuncio.putFile(Uri.parse(urlString));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri firebaseUrl = taskSnapshot.getDownloadUrl();
                String urlCovertida = firebaseUrl.toString();
                exibirMensagemErro("Tudo ok, foi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemErro("Falha ao fazer o upload");
                Log.i("INFO", "Falha ao fazer o upload: " + e.getMessage());
            }
        });
    }

    private Anuncio configurarAnuncio(){

        //Pegando os valores
        String cidade = spCidade.getSelectedItem().toString();
        String categoria = spCategoria.getSelectedItem().toString();
        String titulo = edtTitulo.getText().toString();
        String valor = String.valueOf(campoValor.getRawValue());
        String descricao = edtDescricao.getText().toString();

        Anuncio anuncio = new Anuncio();
        anuncio.setCidade(cidade);
        anuncio.setCategoria(categoria);
        anuncio.setTitulo(titulo);
        anuncio.setValor(valor);
        anuncio.setDescricao(descricao);

        return anuncio;

    }

    private boolean validaDadosAnuncio() {

        anuncio = configurarAnuncio();

        if(listaFotosRecuperada.size() != 0){
            if(!anuncio.getCidade().isEmpty()){
                if(!anuncio.getCategoria().isEmpty()){
                    if(!anuncio.getTitulo().isEmpty()){
                        if(!anuncio.getValor().isEmpty() && !anuncio.getValor().equals("0")){
                            if(!anuncio.getDescricao().isEmpty()){
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
