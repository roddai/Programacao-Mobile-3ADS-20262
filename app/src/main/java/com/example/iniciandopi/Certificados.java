package com.example.iniciandopi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Certificados extends AppCompatActivity {

    private Button btnInicio;
    private Button btnAgenda;
    private Button btnCertificados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_certificados);

        btnInicio = findViewById(R.id.btnInicio);
        btnAgenda = findViewById(R.id.btnAgenda);
        btnCertificados = findViewById(R.id.btnCertificados);

        btnInicio.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Certificados.this,
                    MainActivity.class);

            startActivity(intent);
            finish();
        });

        btnAgenda.setOnClickListener(v -> {

            Intent intent = new Intent(
                    Certificados.this,
                    Agenda.class);

            startActivity(intent);
            finish();
        });

    }
}