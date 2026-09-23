package com.example.aula06_pets;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesPetActivity extends AppCompatActivity {

    private TextView detalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        detalhes = findViewById(R.id.detalhes);
        String nomePet = getIntent().getStringExtra("nomePet");

        detalhes.setText(nomePet);
    }
}