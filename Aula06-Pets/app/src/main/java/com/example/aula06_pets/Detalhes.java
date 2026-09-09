package com.example.aula06_pets;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Detalhes extends AppCompatActivity {

    private TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        textInfo = findViewById(R.id.textIfo);

        // Pegando o nome enviado da MainActivity
        String nomeRecebido = getIntent().getStringExtra("nomeUsuario");

        textInfo.setText("Olá, " + nomeRecebido + "!");
    }
}