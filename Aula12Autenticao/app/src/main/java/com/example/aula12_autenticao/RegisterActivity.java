
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

public class RegisterActivity extends AppCompatActivity {

    EditText edtNome, edtEmail, edtSenha;
    Button btnCadastrar, btnVoltar;

    String URL = "http://10.0.2.2/mobile_app/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltar = findViewById(R.id.btnVoltar);

        btnCadastrar.setOnClickListener(v -> cadastrarUsuario());

        btnVoltar.setOnClickListener(v -> finish());
    }

    private void cadastrarUsuario() {

        String nome = edtNome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {

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

                        Toast.makeText(this,
                                mensagem,
                                Toast.LENGTH_LONG).show();

                        if (sucesso) {

                            Intent intent = new Intent(
                                    RegisterActivity.this,
                                    LoginActivity.class
                            );

                            intent.addFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                            Intent.FLAG_ACTIVITY_SINGLE_TOP
                            );

                            startActivity(intent);
                            finish();
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

                params.put("usu_nome", nome);
                params.put("usu_email", email);
                params.put("usu_password", senha);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
