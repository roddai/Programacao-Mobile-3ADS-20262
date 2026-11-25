package com.example.aula12;

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

import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNome, etEmail, etSenha;
    private Button btnCadastrar, btnVoltarLogin;

    // URL do script PHP de cadastro no XAMPP
    private static final String URL_REGISTER = "http://10.0.2.2/mobile_app/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNome = findViewById(R.id.etNomeCadastro);
        etEmail = findViewById(R.id.etEmailCadastro);
        etSenha = findViewById(R.id.etSenhaCadastro);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnVoltarLogin = findViewById(R.id.btnVoltarLogin);

        // Ação do botão de cadastro
        btnCadastrar.setOnClickListener(v -> realizarCadastro());

        // Botão para retornar manualmente para a tela de login
        btnVoltarLogin.setOnClickListener(v -> finish());
    }

    private void realizarCadastro() {
        String nome = etNome.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String senha = etSenha.getText().toString().trim();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        String message = jsonObject.getString("message");

                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();

                        if (success) {
                            // Retorna automaticamente para a tela de login após cadastro realizado com sucesso
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(RegisterActivity.this, "Erro ao processar resposta", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(RegisterActivity.this, "Erro de rede: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            public byte[] getBody() {
                try {
                    String data = "usu_nome=" + URLEncoder.encode(nome, "UTF-8") +
                            "&usu_email=" + URLEncoder.encode(email, "UTF-8") +
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