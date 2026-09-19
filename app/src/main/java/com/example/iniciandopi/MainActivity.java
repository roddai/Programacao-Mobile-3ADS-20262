package com.example.iniciandopi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnInicio;
    private Button btnAgenda;
    private Button btnCertificados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnInicio = findViewById(R.id.btnInicio);
        btnAgenda = findViewById(R.id.btnAgenda);
        btnCertificados = findViewById(R.id.btnCertificados);

        btnInicio.setOnClickListener(v -> {
            irParaInicio();
        });

        btnAgenda.setOnClickListener(v -> {
            irParaAgenda();
        });

        btnCertificados.setOnClickListener(v -> {
            irParaCertificados();
        });
    }

    private void irParaInicio() {

    }

    private void irParaAgenda() {

        Intent agenda =
                new Intent(MainActivity.this,
                        Agenda.class);

        startActivity(agenda);
    }

    private void irParaCertificados() {

        Intent certificados =
                new Intent(MainActivity.this,
                        Certificados.class);

        startActivity(certificados);
    }
}