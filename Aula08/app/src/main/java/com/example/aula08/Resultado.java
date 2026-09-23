package com.example.aula08;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Resultado extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.resultado);

        TextView tvResultado = findViewById(R.id.tvresultado);
        Button btnvoltar = findViewById(R.id.btnvoltar);

        String resultado = getIntent().getStringExtra("resultado");
        tvResultado.setText(resultado);
        btnvoltar.setOnClickListener( v->finish());
    }
}
