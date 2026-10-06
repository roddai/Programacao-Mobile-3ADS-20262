package com.example.n1_davibigottoebrunofaccio;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ResultadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultado);


        TextView tvResultado = findViewById(R.id.tvResultado);
        ;

        String resultado = getIntent().getStringExtra("resultado");
        if (tvResultado != null) {
            tvResultado.setText(resultado);
        }
    }
}
