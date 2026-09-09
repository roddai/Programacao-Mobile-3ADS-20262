package com.example.desafio_pet;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalhesActivity extends AppCompatActivity {

    private TextView txtDetalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhesactivity);

        txtDetalhes = findViewById(R.id.txtDetalhes);

        String petRecebido = getIntent().getStringExtra("pet");

        txtDetalhes.setText(petRecebido);
    }
}
