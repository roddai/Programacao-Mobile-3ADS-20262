package com.example.projetoaula06_pets;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesActivity extends AppCompatActivity {

    TextView viewDetalhes;
    Button btnVolta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes);

        viewDetalhes = findViewById(R.id.Detalhes);
        btnVolta = findViewById(R.id.Voltar);

        String detalhes = getIntent().getStringExtra("Detalhes");

        viewDetalhes.setText(detalhes);

        btnVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}