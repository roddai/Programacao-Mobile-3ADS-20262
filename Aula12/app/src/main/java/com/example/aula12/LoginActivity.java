package com.example.aula12;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnLogin, btnIrCadastro;

    // URL do script PHP no XAMPP (10.0.2.2 aponta para o localhost do seu PC no emulador)
    private static final String URL_LOGIN = "http://10.0.2.2/mobile_app/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Carrega o layout correto de login
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializando os componentes da tela
        etEmail = findViewById(R.id.etEmailLogin);
        etSenha = findViewById(R.id.etSenhaLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnIrCadastro = findViewById(R.id.btnIrCadastro);

        // Ação do botão de Login
        btnLogin.setOnClickListener(v -> realizarLogin());

        // Ação do botão para ir para a tela de cadastro
        btnIrCadastro.setOnClickListener(v -> {
            Toast.makeText(this, "Vamos criar a tela de cadastro agora!", Toast.LENGTH_SHORT).show();
        });
    }

    private void realizarLogin() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        String message = jsonObject.getString("message");

                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();

                        if (success) {
                            // Se login deu certo, vai para a tela de sucesso
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Erro ao processar resposta do servidor", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(LoginActivity.this, "Erro de rede: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            public byte[] getBody() {
                try {
                    String data = "usu_email=" + URLEncoder.encode(email, "UTF-8") +
                            "&usu_password=" + URLEncoder.encode(senha, "UTF-8");
                    return data.getBytes("UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}