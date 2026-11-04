package com.example.aula12;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    public static final String PREFS_USUARIOS = "usuarios";

    private EditText etEmail, etSenha;

    // Abre a tela de cadastro e recebe o resultado quando ela é fechada
    private final ActivityResultLauncher<Intent> cadastroLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String mensagem = result.getData().getStringExtra(CadastroActivity.EXTRA_MENSAGEM);
                    String email = result.getData().getStringExtra(CadastroActivity.EXTRA_EMAIL);

                    // Mensagem de sucesso exibida ao voltar para o login
                    Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();

                    // Já deixa o e-mail cadastrado preenchido
                    etEmail.setText(email);
                    etSenha.setText("");
                    etSenha.requestFocus();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        Button btnIrCadastro = findViewById(R.id.btnIrCadastro);

        btnEntrar.setOnClickListener(v -> fazerLogin());

        btnIrCadastro.setOnClickListener(v ->
                cadastroLauncher.launch(new Intent(this, CadastroActivity.class)));
    }

    private void fazerLogin() {
        String email = etEmail.getText().toString().trim().toLowerCase();
        String senha = etSenha.getText().toString();

        // Validações dos campos
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Informe o e-mail");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("E-mail inválido");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(senha)) {
            etSenha.setError("Informe a senha");
            etSenha.requestFocus();
            return;
        }

        String nome = autenticar(email, senha);
        if (nome != null) {
            // Login OK -> vai para a tela de Registro (usuário autenticado)
            Intent intent = new Intent(this, RegistroActivity.class);
            intent.putExtra(RegistroActivity.EXTRA_NOME, nome);
            intent.putExtra(RegistroActivity.EXTRA_EMAIL, email);
            startActivity(intent);
            etSenha.setText("");
        } else {
            Toast.makeText(this, "Usuário ou senha inválidos.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Verifica e-mail e senha nos usuários salvos localmente.
     * @return o nome do usuário se a autenticação der certo; null caso contrário.
     */
    private String autenticar(String email, String senha) {
        SharedPreferences prefs = getSharedPreferences(PREFS_USUARIOS, MODE_PRIVATE);
        String senhaSalva = prefs.getString("senha_" + email, null);
        if (senhaSalva != null && senhaSalva.equals(senha)) {
            return prefs.getString("nome_" + email, email);
        }
        return null;
    }
}