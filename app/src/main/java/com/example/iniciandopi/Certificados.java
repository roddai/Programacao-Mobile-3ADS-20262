package com.example.iniciandopi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Certificados extends AppCompatActivity {

    public Button btnInicio, btnAgenda, btnCertificados;
    public Button btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_certificados);

        btnInicio = findViewById(R.id.btnInicio);
        btnAgenda = findViewById(R.id.btnAgenda);
        btnCertificados = findViewById(R.id.btnCertificados);
        btnPerfil = findViewById(R.id.btnPerfil);

        btnInicio.setOnClickListener(v -> {Intent intent = new Intent(Certificados.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnAgenda.setOnClickListener(v -> {Intent intent = new Intent(Certificados.this, Agenda.class);
            startActivity(intent);
            finish();
        });

        btnPerfil.setOnClickListener(v -> {Intent intent = new Intent(Certificados.this, Perfil.class);
            startActivity(intent);
            finish();
        });

        btnCertificados.setOnClickListener(v -> {Intent intent = new Intent(Certificados.this, Perfil.class);
            startActivity(intent);
            finish();
        });
    }
}