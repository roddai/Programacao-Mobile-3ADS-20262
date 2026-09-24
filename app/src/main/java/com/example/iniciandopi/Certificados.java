package com.example.iniciandopi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Certificados extends AppCompatActivity {

    public Button btnInicio, btnAgenda, btnCertificados;
    public Button btnPerfil, btnVisualizar1, btnPdf1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_certificados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnInicio = findViewById(R.id.btnInicio);
        btnAgenda = findViewById(R.id.btnAgenda);
        btnCertificados = findViewById(R.id.btnCertificados);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnVisualizar1 = findViewById(R.id.btnVisualizar1);
        btnPdf1 = findViewById(R.id.btnPdf1);


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

        btnVisualizar1.setOnClickListener(v -> {Intent intent = new Intent(Certificados.this, Visualizar.class);
            startActivity(intent);
            finish();
        });

        btnPdf1.setOnClickListener(v -> {Intent intent = new Intent(Certificados.this, BaixarPDF.class);
            startActivity(intent);
            finish();
        });

    }
}