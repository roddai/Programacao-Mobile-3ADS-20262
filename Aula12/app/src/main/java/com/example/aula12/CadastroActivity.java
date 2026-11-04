package com.example.aula12;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Tela 3 - CADASTRO
 * Cadastra um novo usuário.
 *  - Botão "Voltar para o login" retorna à tela de login.
 *  - Após cadastro com sucesso, volta automaticamente ao login,
 *    que mostra a mensagem de sucesso.
 *
 * Sem integração com XAMPP/MySQL por enquanto: os dados ficam em SharedPreferences.
 * Depois, basta trocar salvarUsuario() pela chamada HTTP POST ao register.php.
 */
public class CadastroActivity extends AppCompatActivity {

    public static final String EXTRA_MENSAGEM = "extra_mensagem";
    public static final String EXTRA_EMAIL = "extra_email";

    private EditText etNome, etEmail, etSenha, etConfirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);
        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        btnCadastrar.setOnClickListener(v -> cadastrar());

        // Volta para o login sem cadastrar
        btnVoltar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void cadastrar() {
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim().toLowerCase();
        String senha = etSenha.getText().toString();
        String confirmar = etConfirmarSenha.getText().toString();

        // Validações
        if (TextUtils.isEmpty(nome)) {
            etNome.setError("Informe o nome");
            etNome.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("E-mail inválido");
            etEmail.requestFocus();
            return;
        }
        if (senha.length() < 6) {
            etSenha.setError("A senha deve ter pelo menos 6 caracteres");
            etSenha.requestFocus();
            return;
        }
        if (!senha.equals(confirmar)) {
            etConfirmarSenha.setError("As senhas não conferem");
            etConfirmarSenha.requestFocus();
            return;
        }

        SharedPreferences prefs = getSharedPreferences(MainActivity.PREFS_USUARIOS, MODE_PRIVATE);

        // E-mail único (mesma regra do UNIQUE na tabela usuario)
        if (prefs.contains("senha_" + email)) {
            etEmail.setError("Este e-mail já está cadastrado");
            etEmail.requestFocus();
            return;
        }

        salvarUsuario(prefs, nome, email, senha);

        // Cadastro OK -> devolve o resultado e volta automaticamente para o login
        Intent resultado = new Intent();
        resultado.putExtra(EXTRA_MENSAGEM, "Usuário cadastrado com sucesso!");
        resultado.putExtra(EXTRA_EMAIL, email);
        setResult(RESULT_OK, resultado);
        finish();
    }

    private void salvarUsuario(SharedPreferences prefs, String nome, String email, String senha) {
        prefs.edit()
                .putString("nome_" + email, nome)
                .putString("senha_" + email, senha)
                .apply();
    }
}