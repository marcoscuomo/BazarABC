package br.com.mojumob.bazarabc.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.mojumob.bazarabc.R;
import br.com.mojumob.bazarabc.helper.ConfiguracaoFirebase;

public class LoginActivity extends AppCompatActivity {

    //Atributos
    private EditText edtLogin, edtSenha;
    private Button btnEntrar;
    private TextView txtCadastrar, txtNovaSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializaComponentes();

        //Evento clique no Botao entrar
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();

                autenticacao.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, AnunciosActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Erro ao fazer login " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        //Evento clique no textview Criar conta
        txtCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
            }
        });


    }

    private void inicializaComponentes() {
        //Inicializações
        edtLogin = findViewById(R.id.login_edtLogin);
        edtSenha = findViewById(R.id.login_edtSenha);
        btnEntrar = findViewById(R.id.cadastro_btnCadastrar);
        txtCadastrar = findViewById(R.id.txt_criarConta);
        txtNovaSenha = findViewById(R.id.txt_esqueciSenha);
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }
}
