package br.com.mojumob.bazarabc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {

    //Atributos
    private EditText edtNome, edtEmail, edtSenha, edtSenhaRepetida;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();




    }

    private void inicializaComponentes() {
        //Inicializações
        edtNome = findViewById(R.id.cadastro_edtNome);
        edtEmail = findViewById(R.id.cadastro_edtEmail);
        edtSenha = findViewById(R.id.cadastro_edtSenha);
        edtSenhaRepetida = findViewById(R.id.cadastro_edtSenhaRepetida);
    }
}
