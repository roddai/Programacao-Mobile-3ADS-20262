package com.example.pi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnInicio;
    private Button btnAgenda;
    private Button btnCertificados;
    private Button btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnInicio = findViewById(R.id.btnInicio);
        btnAgenda = findViewById(R.id.btnAgenda);
        btnCertificados = findViewById(R.id.btnCertificados);
        btnPerfil = findViewById(R.id.btnPerfil);

        btnInicio.setOnClickListener(v -> {
        });

        btnAgenda.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    Agenda.class);

            startActivity(intent);
        });

        btnCertificados.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    Certificados.class);

            startActivity(intent);
        });

        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    Perfil.class);

            startActivity(intent);
        });
    }
}