package com.example.projetoaula04ex2;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SegundaActivity extends AppCompatActivity {

    private TextView txtSaudacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout correto
        setContentView(R.layout.activity_segunda); 

        txtSaudacao = findViewById(R.id.txtSaudacao);

        // Recupera o nome enviado da MainActivity
        String nomeRecebido = getIntent().getStringExtra("nomeUsuario");

        // Verifica se o nome não é nulo ou vazio para exibir a saudação corretamente
        if (nomeRecebido != null && !nomeRecebido.trim().isEmpty()) {
            txtSaudacao.setText("Olá, " + nomeRecebido + "!");
        } else {
            txtSaudacao.setText("Olá!");
        }
    }
}