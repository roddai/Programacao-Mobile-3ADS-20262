package com.example.aula09;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        TextView tvResultado = findViewById(R.id.tvResultado);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nome = extras.getString("NOME", "");
            String idade = extras.getString("IDADE", "");
            String tamanho = extras.getString("TAMANHO", "");
            String cor = extras.getString("COR", "");
            String itens = extras.getString("ITENS", "");
            float avaliacao = extras.getFloat("AVALIACAO", 0);

            String dados = "Nome: " + nome + "\n" +
                    "Idade: " + idade + " anos\n" +
                    "Tamanho: " + tamanho + "\n" +
                    "Cor: " + cor + "\n" +
                    "Itens: " + itens + "\n" +
                    "Avaliação: " + (int) avaliacao + " estrela(s)";

            tvResultado.setText(dados);
        }

        btnVoltar.setOnClickListener(v -> finish());
    }
}