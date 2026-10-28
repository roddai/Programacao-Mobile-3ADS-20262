package com.example.aula09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Pega os dados que vieram do formulário
        Intent dados = getIntent();
        String nome = dados.getStringExtra("nome");
        String idade = dados.getStringExtra("idade");
        String tamanho = dados.getStringExtra("tamanho");
        String cor = dados.getStringExtra("cor");
        String pecas = dados.getStringExtra("pecas");
        int nota = dados.getIntExtra("nota", 0);

        // Monta as estrelas (cheias e vazias)
        StringBuilder estrelas = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            estrelas.append(i <= nota ? "★" : "☆");
        }

        TextView tvResultado = findViewById(R.id.tvResultado);
        tvResultado.setText(
                "Nome: " + nome + "\n" +
                        "Idade: " + idade + " anos\n" +
                        "Tamanho: " + tamanho + "\n" +
                        "Cor: " + cor + "\n" +
                        "Peças: " + pecas + "\n" +
                        "Avaliação: " + estrelas + " (" + nota + "/5)"
        );

        // Volta para a tela inicial
        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            Intent voltar = new Intent(ResultActivity.this, MainActivity.class);
            voltar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(voltar);
            finish();
        });
    }
}