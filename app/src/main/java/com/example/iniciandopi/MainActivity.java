package com.example.iniciandopi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnInicio;
    private Button btnAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnInicio = findViewById(R.id.btnInicio);
        btnAgenda = findViewById(R.id.btnAgenda);

        btnInicio.setOnClickListener(v -> {
            irParaInicio();
        });

        btnAgenda.setOnClickListener(v -> {
            irParaAgenda();
        });
    }

    private void irParaInicio() {
        Intent inicio = new Intent(MainActivity.this, Certificados.class);
        startActivity(inicio);
    }

    private void irParaAgenda() {
        Intent agenda = new Intent(MainActivity.this, Agenda.class);
        startActivity(agenda);
    }
}