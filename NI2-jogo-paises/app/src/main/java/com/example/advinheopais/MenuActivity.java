
package com.example.advinheopais;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private TextView txtNomeUsuario;
    private Button btnJogar, btnDesempenho, btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences sessao = getSharedPreferences(
                "SessaoUsuario", MODE_PRIVATE);

        if (!sessao.getBoolean("logado", false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }

        txtNomeUsuario = findViewById(R.id.txtNomeUsuario);
        btnJogar = findViewById(R.id.btnJogar);
        btnDesempenho = findViewById(R.id.btnDesempenho);
        btnSair = findViewById(R.id.btnSair);

        String nome = sessao.getString("nome", "Jogador");
        txtNomeUsuario.setText("Olá, " + nome + "!");

        btnJogar.setOnClickListener(v -> {
            startActivity(new Intent(this, JogoActivity.class));
        });

        btnDesempenho.setOnClickListener(v -> {
            startActivity(new Intent(this, DesempenhoActivity.class));
        });

        btnSair.setOnClickListener(v -> {
            sessao.edit().clear().apply();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();
        });
    }
}
