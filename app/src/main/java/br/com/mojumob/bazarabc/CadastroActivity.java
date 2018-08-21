package br.com.mojumob.bazarabc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    //Atributos
    private EditText edtNome, edtEmail, edtSenha, edtSenhaRepetida;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();

        //Tratando o evento de click no botao cadastar
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nome = edtNome.getText().toString();
                String email = edtEmail.getText().toString();
                String senha = edtSenha.getText().toString();
                String senhaRepetida = edtSenhaRepetida.getText().toString();

                verificaSenhaIgual(senha, senhaRepetida);
                verificaCamposVazios(nome, email, senha, senhaRepetida);

                Toast.makeText(CadastroActivity.this, "Tudo OK", Toast.LENGTH_SHORT).show();



            }
        });


    }

    private void verificaCamposVazios(String nome, String email, String senha, String senhaRepetida) {
        if(nome.isEmpty()){
            Toast.makeText(CadastroActivity.this, "Por fafor, preencha seu nome", Toast.LENGTH_SHORT).show();
        }else if(email.isEmpty()){
            Toast.makeText(CadastroActivity.this, "Por fafor, preencha seu e-mail", Toast.LENGTH_SHORT).show();
        }else if(senha.isEmpty()){
            Toast.makeText(CadastroActivity.this, "Por fafor, preencha sua senha", Toast.LENGTH_SHORT).show();
        }else if(senhaRepetida.isEmpty()){
            Toast.makeText(CadastroActivity.this, "Por fafor, preencha sua senha novamente", Toast.LENGTH_SHORT).show();
        }
    }

    private void verificaSenhaIgual(String senha, String senhaRepetida) {
        if(!senha.equals(senhaRepetida)){
            Toast.makeText(CadastroActivity.this, "As senhas estão diferentes!", Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializaComponentes() {
        //Inicializações
        edtNome = findViewById(R.id.cadastro_edtNome);
        edtEmail = findViewById(R.id.cadastro_edtEmail);
        edtSenha = findViewById(R.id.cadastro_edtSenha);
        edtSenhaRepetida = findViewById(R.id.cadastro_edtSenhaRepetida);
        btnCadastrar = findViewById(R.id.cadastro_btnCadastrar);
    }
}
