package com.example.programaomobile2;


package com.example.proximaetapa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtSenha;
    Button btnEntrar;
    TextView txtCadastro, txtEsqueciSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        txtCadastro = findViewById(R.id.txtCadastro);
        txtEsqueciSenha = findViewById(R.id.txtEsqueciSenha);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString().trim();
                String senha = edtSenha.getText().toString();

                if (email.isEmpty()) {
                    edtEmail.setError("Digite seu e-mail");
                    edtEmail.requestFocus();
                } else if (senha.isEmpty()) {
                    edtSenha.setError("Digite sua senha");
                    edtSenha.requestFocus();
                } else {
                    Toast.makeText(
                            LoginActivity.this,
                            "Campos preenchidos! Login ainda não conectado.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        LoginActivity.this,
                        "Abrir tela de cadastro",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        txtEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        LoginActivity.this,
                        "Recuperação de senha",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
