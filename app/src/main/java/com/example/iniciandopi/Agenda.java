package com.example.iniciandopi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Agenda extends AppCompatActivity {

    private Button btnInicio;
    private Button btnAgenda;
    private Button btnCertificados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agenda);

        btnInicio = findViewById(R.id.btnInicio);
        btnAgenda = findViewById(R.id.btnAgenda);
        btnCertificados = findViewById(R.id.btnCertificados);

        btnInicio.setOnClickListener(v -> {

            Intent intent =
                    new Intent(Agenda.this,
                            MainActivity.class);

            startActivity(intent);
            finish();
        });

        btnCertificados.setOnClickListener(v -> {

            Intent intent =
                    new Intent(Agenda.this,
                            Certificados.class);

            startActivity(intent);
            finish();
        });
    }
}
