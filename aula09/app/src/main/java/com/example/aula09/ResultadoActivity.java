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

        String resultado = getIntent().getStringExtra("resultado");

        if (resultado != null) {
            tvResultado.setText(resultado);
        }

        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }
}
