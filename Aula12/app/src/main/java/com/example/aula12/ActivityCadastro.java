package com.example.aula12;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityCadastro extends AppCompatActivity {

    // 1. Declarar os campos da tela de cadastro
    EditText edtNome, edtEmail, edtPassword;
    Button btnCadastrar;
    TextView txtIrParaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // 2. Ligar os componentes aos IDs do XML
        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        txtIrParaLogin = findViewById(R.id.txtIrParaLogin);

        // 3. Ação do botão de cadastro
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerCadastro();
            }
        });

        // 4. Voltar para a tela de login
        txtIrParaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // 5. Método que envia os dados para o cadastro.php via POST
    private void fazerCadastro() {
        final String nome = edtNome.getText().toString();
        final String email = edtEmail.getText().toString();
        final String senha = edtPassword.getText().toString();

        // Validação básica para ver se não há campos vazios
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // URL para o script PHP de cadastro no XAMPP (10.0.2.2 é o localhost do emulador)
        String url = "http://10.0.2.2/mobile_app/cadastro.php";

        RequestQueue fila = Volley.newRequestQueue(this);

        StringRequest requisicao = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Trata a resposta JSON enviada pelo PHP
                            JSONObject json = new JSONObject(response);
                            String mensagem = json.getString("message");

                            Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Erro ao ler JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Erro de conexão com o servidor", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            // Envia os dados (nome, email e senha) para os parâmetros POST do PHP
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("usu_nome", nome);
                parametros.put("usu_email", email);
                parametros.put("usu_password", senha);
                return parametros;
            }
        };

        fila.add(requisicao);
    }
}