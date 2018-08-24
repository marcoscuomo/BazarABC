package br.com.mojumob.bazarabc.ui.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.santalu.widget.MaskEditText;
import br.com.mojumob.bazarabc.R;
import br.com.mojumob.bazarabc.helper.ConfiguracaoFirebase;

public class CadastroActivity extends AppCompatActivity {

    //Atributos
    private EditText edtNome, edtEmail, edtSenha, edtSenhaRepetida;
    private Button btnCadastrar;
    private FirebaseAuth autenticacao;
    private MaskEditText campoTelefone;

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


                if(verificaCamposVazios(nome, email, senha, senhaRepetida) && verificaSenhaIgual(senha, senhaRepetida)){
                    autenticacao.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{

                                String erroExecao = "";

                                try{
                                    throw task.getException();
                                }catch (FirebaseAuthWeakPasswordException e){
                                    erroExecao = "Digite uma senha mais forte";
                                }catch (FirebaseAuthInvalidCredentialsException e){
                                    erroExecao = "Por favor, digite um e-mail válido";
                                }catch (FirebaseAuthUserCollisionException e){
                                    erroExecao = "Este e-mail já está cadastrado";
                                }catch (Exception e){
                                    erroExecao = "ao cadastrar o usuario " + e.getMessage();
                                    e.printStackTrace();
                                }
                                Toast.makeText(CadastroActivity.this, erroExecao, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean verificaCamposVazios(String nome, String email, String senha, String senhaRepetida) {
        if(nome.isEmpty()){
            Toast.makeText(CadastroActivity.this, "Por fafor, preencha seu nome", Toast.LENGTH_SHORT).show();
        }else if(email.isEmpty()){
            Toast.makeText(CadastroActivity.this, "Por fafor, preencha seu e-mail", Toast.LENGTH_SHORT).show();
        }else if(senha.isEmpty()){
            Toast.makeText(CadastroActivity.this, "Por fafor, preencha sua senha", Toast.LENGTH_SHORT).show();
        }else if(senhaRepetida.isEmpty()){
            Toast.makeText(CadastroActivity.this, "Por fafor, preencha sua senha novamente", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }

        return false;
    }

    private boolean verificaSenhaIgual(String senha, String senhaRepetida) {
        if(!senha.equals(senhaRepetida)){
            Toast.makeText(CadastroActivity.this, "As senhas estão diferentes!", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }

        return false;
    }

    private void inicializaComponentes() {
        //Inicializações
        edtNome          = findViewById(R.id.cadastro_edtNome);
        edtEmail         = findViewById(R.id.cadastro_edtEmail);
        edtSenha         = findViewById(R.id.login_edtLogin);
        edtSenhaRepetida = findViewById(R.id.login_edtSenha);
        btnCadastrar     = findViewById(R.id.cadastro_btnCadastrar);
        campoTelefone    = findViewById(R.id.login_edtTelefone);
        autenticacao     = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }
}
