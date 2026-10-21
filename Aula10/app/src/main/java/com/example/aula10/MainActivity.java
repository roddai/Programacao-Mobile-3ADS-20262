package com.example.aula10;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextCep, editTextLogradouro, editTextNumero, editTextComplemento,
            editTextBairro, editTextCidade, editTextUf, editTextEstado, editTextRegiao, editTextDdd;
    private Button buttonBuscar;
    private TextView textViewStatus;
    
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializando todos os campos
        editTextCep = findViewById(R.id.editTextCep);
        buttonBuscar = findViewById(R.id.buttonBuscar);
        
        editTextLogradouro = findViewById(R.id.editTextLogradouro);
        editTextNumero = findViewById(R.id.editTextNumero);
        editTextComplemento = findViewById(R.id.editTextComplemento);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextUf = findViewById(R.id.editTextUf);
        editTextEstado = findViewById(R.id.editTextEstado);
        editTextRegiao = findViewById(R.id.editTextRegiao);
        editTextDdd = findViewById(R.id.editTextDdd);
        textViewStatus = findViewById(R.id.textViewStatus);

        buttonBuscar.setOnClickListener(v -> {
            String cep = editTextCep.getText().toString().trim();
            if (cep.length() == 8) {
                buscarCep(cep);
            } else {
                editTextCep.setError("CEP inválido");
            }
        });
    }

    private void buscarCep(String cep) {
        textViewStatus.setText("Buscando...");
        executorService.execute(() -> {
            try {
                URL url = new URL("http://viacep.com.br/ws/" + cep + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject json = new JSONObject(response.toString());
                    if (json.has("erro")) {
                        updateUI(null, "CEP não encontrado.");
                    } else {
                        updateUI(json, "CEP localizado com sucesso!");
                    }
                } else {
                    updateUI(null, "Erro na conexão.");
                }
            } catch (Exception e) {
                updateUI(null, "Erro: " + e.getMessage());
            }
        });
    }

    private void updateUI(JSONObject json, String status) {
        handler.post(() -> {
            textViewStatus.setText(status);
            if (json != null) {
                editTextLogradouro.setText(json.optString("logradouro"));
                editTextBairro.setText(json.optString("bairro"));
                editTextCidade.setText(json.optString("localidade"));
                editTextUf.setText(json.optString("uf"));
                editTextEstado.setText(json.optString("estado"));
                editTextRegiao.setText(json.optString("regiao"));
                editTextDdd.setText(json.optString("ddd"));
                editTextComplemento.setText(json.optString("complemento"));
                
                // Foca no número para o usuário preencher
                editTextNumero.requestFocus();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}