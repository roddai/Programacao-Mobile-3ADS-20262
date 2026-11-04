package com.example.aula12;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    public static final String EXTRA_NOME = "extra_nome";
    public static final String EXTRA_EMAIL = "extra_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        TextView tvBemVindo = findViewById(R.id.tvBemVindo);
        Button btnSair = findViewById(R.id.btnSair);

        // Dados enviados pela LoginActivity
        String nome = getIntent().getStringExtra(EXTRA_NOME);
        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        tvBemVindo.setText("Olá, " + nome + "!\n" + email);

        // Sair: fecha esta tela e volta para o login
        btnSair.setOnClickListener(v -> finish());
    }
}