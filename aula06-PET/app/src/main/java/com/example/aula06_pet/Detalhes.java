package com.example.aula06_pet;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class Detalhes extends AppCompatActivity {
    private TextView txtdetalhes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes); // Layout da segunda tela
        txtdetalhes = findViewById(R.id.txtdetalhes);
        // Pegando o nome enviado da MainActivity
        String nomeRecebido = getIntent().getStringExtra("nomeUsuario");
        txtdetalhes.setText("Olá, " + nomeRecebido + "!");
    }
}