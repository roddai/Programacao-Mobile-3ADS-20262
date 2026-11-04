package com.example.aula12;

import android.content.Intent;
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

public class Activity_Login extends AppCompatActivity {

    // 1. Declarar os componentes da tela
    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView txtIrParaCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 2. Ligar os componentes do Java aos IDs do XML
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtIrParaCadastro = findViewById(R.id.txtIrParaCadastro);

        // 3. O que acontece quando clica no botão de entrar
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLogin();
            }
        });

        // 4. Navegação para a tela de cadastro
        txtIrParaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Login.this, ActivityCadastro.class);
                startActivity(intent);
            }
        });
    }

    // 5. Método que envia os dados para o PHP (XAMPP)
    private void fazerLogin() {
        final String email = edtEmail.getText().toString();
        final String senha = edtPassword.getText().toString();

        // O endereço do seu PHP no XAMPP (10.0.2.2 é o localhost para o emulador)
        String url = "http://10.0.2.2/mobile_app/login.php";

        // Cria a fila de requisição da biblioteca Volley
        RequestQueue fila = Volley.newRequestQueue(this);

        StringRequest requisicao = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
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
            // Envia os dados (email e senha) via POST para o PHP
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("usu_email", email);
                parametros.put("usu_password", senha);
                return parametros;
            }
        };

        // Adiciona a requisição na fila para enviar
        fila.add(requisicao);
    }
}