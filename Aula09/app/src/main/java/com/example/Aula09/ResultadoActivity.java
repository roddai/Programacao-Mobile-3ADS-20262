package com.example.Aula09;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        TextView txtResultado = findViewById(R.id.txtResultado);
        RatingBar rbResultado = findViewById(R.id.rbResultado);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        // Recebe os dados enviados pelo formulário
        String nome = getIntent().getStringExtra("nome");
        String idade = getIntent().getStringExtra("idade");
        String tamanho = getIntent().getStringExtra("tamanho");
        String cor = getIntent().getStringExtra("cor");
        String pecas = getIntent().getStringExtra("pecas");
        float nota = getIntent().getFloatExtra("nota", 0);

        String texto = "Nome: " + nome +
                "\nIdade: " + idade +
                "\nTamanho: " + tamanho +
                "\nCor: " + cor +
                "\nPeças: " + pecas +
                "\nAvaliação: " + (int) nota + " estrela(s)";

        txtResultado.setText(texto);
        rbResultado.setRating(nota);

        btnVoltar.setOnClickListener(v -> finish());
    }
}