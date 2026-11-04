
package com.example.aula12_autenticao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtSenha;
    Button btnEntrar, btnCadastro;

    String URL = "http://10.0.2.2/mobile_app/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);

        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastro = findViewById(R.id.btnCadastro);

        btnEntrar.setOnClickListener(v -> fazerLogin());

        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            );
            startActivity(intent);
        });
    }

    private void fazerLogin() {

        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this,
                    "Preencha todos os campos!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,

                response -> {
                    try {
                        JSONObject json = new JSONObject(response);

                        boolean sucesso = json.getBoolean("success");
                        String mensagem = json.getString("message");

                        if (sucesso) {

                            Toast.makeText(this,
                                    mensagem,
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(
                                    LoginActivity.this,
                                    HomeActivity.class
                            );

                            intent.putExtra("email", email);

                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(this,
                                    mensagem,
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(this,
                                "Erro ao processar resposta!",
                                Toast.LENGTH_SHORT).show();
                    }
                },

                error -> Toast.makeText(this,
                        "Erro de conexão com o servidor!",
                        Toast.LENGTH_LONG).show()
        ) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("usu_email", email);
                params.put("usu_password", senha);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}

