package com.example.aula12;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CadastroActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha, etConfirmarSenha;
    private Button btnCadastrar;
    private TextView tvVoltarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cadastro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etNome = findViewById(R.id.etNome);
        etEmail = findViewById(R.id.etEmailCadastro);
        etSenha = findViewById(R.id.etSenhaCadastro);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        tvVoltarLogin = findViewById(R.id.tvVoltarLogin);

        btnCadastrar.setOnClickListener(v -> cadastrar());
        tvVoltarLogin.setOnClickListener(v -> finish());
    }

    private void cadastrar() {
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString();
        String confirmar = etConfirmarSenha.getText().toString();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmar.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!senha.equals(confirmar)) {
            Toast.makeText(this, "As senhas não conferem", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("usuarios", MODE_PRIVATE);

        if (prefs.contains(email)) {
            Toast.makeText(this, "E-mail já cadastrado", Toast.LENGTH_SHORT).show();
            return;
        }

        prefs.edit().putString(email, senha).apply();
        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
        finish(); // volta para o login
    }
}