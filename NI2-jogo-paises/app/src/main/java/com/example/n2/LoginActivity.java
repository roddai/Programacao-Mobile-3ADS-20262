package com.example.n2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private UsuarioRepositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repositorio = new UsuarioRepositorio(this);

        // Se já tem alguém logado, vai direto para o menu
        if (repositorio.usuarioLogado() != null) {
            abrirMenu();
            return;
        }

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        Button btnIrCadastro = findViewById(R.id.btnIrCadastro);

        btnEntrar.setOnClickListener(v -> entrar());
        btnIrCadastro.setOnClickListener(v ->
                startActivity(new Intent(this, CadastroActivity.class)));
    }

    private void entrar() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.erro_campo_obrigatorio));
            etEmail.requestFocus();
            return;
        }
        if (senha.isEmpty()) {
            etSenha.setError(getString(R.string.erro_campo_obrigatorio));
            etSenha.requestFocus();
            return;
        }

        if (!repositorio.emailExiste(email)) {
            Toast.makeText(this, R.string.erro_email_nao_cadastrado, Toast.LENGTH_LONG).show();
            etEmail.requestFocus();
            return;
        }

        if (!repositorio.senhaCorreta(email, senha)) {
            etSenha.setError(getString(R.string.erro_senha_incorreta));
            etSenha.setText("");
            etSenha.requestFocus();
            return;
        }

        repositorio.iniciarSessao(email);
        abrirMenu();
    }

    private void abrirMenu() {
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }
}
