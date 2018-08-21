package br.com.mojumob.bazarabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Atributos
    private EditText edtLogin, edtSenha;
    private Button btnEntrar;
    private TextView txtCadastrar, txtNovaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializaComponentes();


        //Evento clique no textview Criar conta
        txtCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });


    }

    private void inicializaComponentes() {
        //Inicializações
        edtLogin = findViewById(R.id.cadastro_edtSenhaRepetida);
        edtSenha = findViewById(R.id.cadastro_edtSenha);
        btnEntrar = findViewById(R.id.cadastro_btnCadastrar);
        txtCadastrar = findViewById(R.id.txt_criarConta);
        txtNovaSenha = findViewById(R.id.txt_esqueciSenha);
    }
}
