package com.example.pi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Certificados extends AppCompatActivity {

    private Button btnInicio;
    private Button btnAgenda;
    private Button btnCertificados;
    private Button btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_certificados);

        btnInicio = findViewById(R.id.btnInicio);
        btnAgenda = findViewById(R.id.btnAgenda);
        btnCertificados = findViewById(R.id.btnCertificados);
        btnPerfil = findViewById(R.id.btnPerfil);

        btnInicio.setOnClickListener(v -> {
            startActivity(new Intent(
                    Certificados.this,
                    MainActivity.class));
            finish();
        });

        btnAgenda.setOnClickListener(v -> {
            startActivity(new Intent(
                    Certificados.this,
                    Agenda.class));
            finish();
        });

        btnCertificados.setOnClickListener(v -> {
        });

        btnPerfil.setOnClickListener(v -> {
            startActivity(new Intent(
                    Certificados.this,
                    Perfil.class));
            finish();
        });
    }
}