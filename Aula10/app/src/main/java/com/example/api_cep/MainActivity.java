package com.example.api_cep;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etCep;
    private Button btnBuscar;
    private TextView tvLogradouro;

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

        // Inicialização dos componentes
        etCep = findViewById(R.id.etCep);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvLogradouro = findViewById(R.id.tvLogradouro);

        btnBuscar.setOnClickListener(v -> {
            String cep = etCep.getText().toString();
            if (cep.length() == 8) {
                // Aqui entraria a lógica de chamada da API
                Toast.makeText(this, "Buscando CEP: " + cep, Toast.LENGTH_SHORT).show();
            } else {
                etCep.setError("O CEP deve ter 8 dígitos");
            }
        });
    }
}