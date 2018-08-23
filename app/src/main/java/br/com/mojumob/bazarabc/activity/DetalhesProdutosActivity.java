package br.com.mojumob.bazarabc.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;

import br.com.mojumob.bazarabc.R;

public class DetalhesProdutosActivity extends AppCompatActivity {

    //Atributos
    private CarouselView carouselView;
    private TextView txtTitulo, txtPreco, txtDescricao, txtCidade;
    private Button btnLigar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produtos);

        inicializaComponentes();

    }

    private void inicializaComponentes() {
        txtTitulo = findViewById(R.id.detalhes_txtTitulo);
        txtPreco = findViewById(R.id.detalhes_txtPreco);
        txtCidade = findViewById(R.id.detalhes_txtCidade);
        txtDescricao = findViewById(R.id.detalhes_txtDescricao);
        btnLigar = findViewById(R.id.detalhes_btnLigar);
    }
}
