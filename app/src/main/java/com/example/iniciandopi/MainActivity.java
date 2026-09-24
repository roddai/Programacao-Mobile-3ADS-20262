package com.example.iniciandopi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public Button btnInicio, btnAgenda, btnCertificados, btnPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnInicio = findViewById(R.id.btnInicio);
        btnAgenda = findViewById(R.id.btnAgenda);
        btnCertificados = findViewById(R.id.btnCertificados);
        btnPerfil = findViewById(R.id.btnPerfil);

        btnInicio.setOnClickListener(v -> {
            irParaInicio();
        });

        btnAgenda.setOnClickListener(v -> {
            irParaAgenda();
        });

        btnCertificados.setOnClickListener(v -> {
            irParaCertificados();
        });

        btnPerfil.setOnClickListener(v -> {
            irParaPerfil();
        });
    }

    public void irParaInicio() {

    }

    public void irParaAgenda() {

        Intent agenda =
                new Intent(MainActivity.this,
                        Agenda.class);

        startActivity(agenda);
    }

    public void irParaCertificados() {

        Intent certificados =
                new Intent(MainActivity.this,
                        Certificados.class);

        startActivity(certificados);
    }

    public void irParaPerfil() {
        Intent perfil = new Intent(MainActivity.this, Perfil.class);
        startActivity(perfil);
    }
}