package com.example.aula08;

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
        setContentView(R.layout.activity_resultado);


        TextView tvResultado = findViewById(R.id.tvResultado);
        Button btnVoltar = findViewById(R.id.btnVoltar);

        String resultado = getIntent().getStringExtra("resultado");if (tvResultado != null) {
            tvResultado.setText(resultado);
        }

        if (btnVoltar != null) {
            btnVoltar.setOnClickListener(v -> {
                finish();
            });
        }
    }
}