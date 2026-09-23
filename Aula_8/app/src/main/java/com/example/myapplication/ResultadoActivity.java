package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.form_resultado);
        TextView tvresultado = findViewById(R.id.tvresultado);
        Button btvoltar = findViewById(R.id.btvoltar);

        String resultado = getIntent().getStringExtra("resultado");
        tvresultado.setText(resultado);

        btvoltar.setOnClickListener(v -> finish());
    }
}
