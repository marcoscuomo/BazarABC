package br.com.mojumob.bazarabc.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import br.com.mojumob.bazarabc.R;
import br.com.mojumob.bazarabc.model.Anuncio;

public class DetalhesProdutosActivity extends AppCompatActivity {

    //Atributos
    private CarouselView carouselview;
    private TextView txtTitulo, txtPreco, txtDescricao, txtCidade;
    private Button btnLigar;
    private Anuncio anuncioSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produtos);

        //Configura a Toolbar
        getSupportActionBar().setTitle("Detalhes do produto");

        //Inicializa os componentes da interface
        inicializaComponentes();


        //Recupera o anuncio para exibição
        anuncioSelecionado = (Anuncio) getIntent().getSerializableExtra("anuncioSelecionado");

        if(anuncioSelecionado != null){

            txtTitulo.setText(anuncioSelecionado.getTitulo());
            txtPreco.setText(anuncioSelecionado.getValor());
            txtCidade.setText(anuncioSelecionado.getCidade());
            txtDescricao.setText(anuncioSelecionado.getDescricao());

            //Configurando o Carrosel
            ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    String urlString = anuncioSelecionado.getFotos().get(position);
                    Picasso.get().load(urlString).into(imageView);
                }
            };

            carouselview.setPageCount(anuncioSelecionado.getFotos().size());
            carouselview.setImageListener(imageListener);

        }

    }

    public void visualizarTelefone(View view){

        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "11974570739", null));
        startActivity(i);

    }

    private void inicializaComponentes() {
        txtTitulo = findViewById(R.id.detalhes_txtTitulo);
        txtPreco = findViewById(R.id.detalhes_txtPreco);
        txtCidade = findViewById(R.id.detalhes_txtCidade);
        txtDescricao = findViewById(R.id.detalhes_txtDescricao);
        btnLigar = findViewById(R.id.detalhes_btnLigar);
        carouselview = findViewById(R.id.carouselView);
    }
}
