package com.example.iniciandopi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Agenda extends AppCompatActivity {

    private Button btnInicio, btnAgenda, btnCertificados, btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agenda);

        btnInicio = findViewById(R.id.btnInicio);
        btnAgenda = findViewById(R.id.btnAgenda);
        btnCertificados = findViewById(R.id.btnCertificados);
        btnPerfil = findViewById(R.id.btnPerfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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

        btnPerfil.setOnClickListener(v -> {

            Intent intent =
                    new Intent(Agenda.this,
                            Perfil.class);

            startActivity(intent);
            finish();
        });
    }
}
