package com.example.app_pets;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class Detalhes extends AppCompatActivity {
    private TextView detalhes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes); // Layout da segunda tela
        detalhes = findViewById(R.id.detalhes);
// Pegando o nome enviado da MainActivity
        String infoPet = getIntent().getStringExtra("infoPet");
        detalhes.setText(  infoPet );
    }
}