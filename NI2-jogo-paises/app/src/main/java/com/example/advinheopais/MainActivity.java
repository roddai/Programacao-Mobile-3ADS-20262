
package com.example.advinheopais;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnEntrar, btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastro = findViewById(R.id.btnCadastro);

        SharedPreferences sessao = getSharedPreferences(
                "SessaoUsuario", MODE_PRIVATE);

        if (sessao.getBoolean("logado", false)) {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
            return;
        }

        btnCadastro.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        btnEntrar.setOnClickListener(v -> {

            String email = editEmail.getText().toString().trim();
            String senha = editSenha.getText().toString();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this,
                        "Preencha o e-mail e a senha.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences dados = getSharedPreferences(
                    "DadosUsuario", MODE_PRIVATE);

            String emailSalvo = dados.getString("email", "");
            String senhaSalva = dados.getString("senha", "");

            if (email.equalsIgnoreCase(emailSalvo)
                    && senha.equals(senhaSalva)) {

                sessao.edit()
                        .putBoolean("logado", true)
                        .putString("nome", dados.getString("nome", ""))
                        .putString("email", emailSalvo)
                        .apply();

                startActivity(new Intent(this, MenuActivity.class));
                finish();

            } else {
                Toast.makeText(this,
                        "E-mail ou senha incorretos.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
