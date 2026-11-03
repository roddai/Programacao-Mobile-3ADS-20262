package com.example.aula12;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private Button btnEntrar, btnIrCadastro;
    private static final String URL_LOGIN = "http://10.0.2.2/mobile_app_teste/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etLoginEmail);
        etSenha = findViewById(R.id.etLoginSenha);
        btnEntrar = findViewById(R.id.btnLogin);
        btnIrCadastro = findViewById(R.id.btnIrCadastro);

        btnEntrar.setOnClickListener(v -> realizarLogin());

        btnIrCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void realizarLogin() {
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {
                URL url = new URL(URL_LOGIN);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                String postData = "email=" + URLEncoder.encode(email, "UTF-8") +
                        "&senha=" + URLEncoder.encode(senha, "UTF-8");

                OutputStream os = conn.getOutputStream();
                os.write(postData.getBytes("UTF-8"));
                os.flush();
                os.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                String status = jsonResponse.getString("status");
                String mensagem = jsonResponse.getString("mensagem");

                runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.this, mensagem, Toast.LENGTH_SHORT).show();
                    if (status.equals("sucesso")) {
                        Intent intent = new Intent(LoginActivity.this, SucessoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Erro de conexão: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }
}