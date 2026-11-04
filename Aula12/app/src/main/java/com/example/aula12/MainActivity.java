package com.example.aula12;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnEntrar;
    private TextView tvCadastrar;

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

        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        tvCadastrar = findViewById(R.id.tvCadastrar);

        btnEntrar.setOnClickListener(v -> fazerLogin());

        tvCadastrar.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CadastroActivity.class)));
    }

    private void fazerLogin() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha e-mail e senha", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("usuarios", MODE_PRIVATE);
        String senhaSalva = prefs.getString(email, null);

        if (senhaSalva != null && senhaSalva.equals(senha)) {
            Intent intent = new Intent(this, RegistroActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        } else {
            Toast.makeText(this, "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }
}