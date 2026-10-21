package com.example.aula10;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextCep;
    private Button buttonBuscar;
    private TextView textViewLogradouro;
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

        editTextCep = findViewById(R.id.editTextCep);
        buttonBuscar = findViewById(R.id.buttonBuscar);
        textViewLogradouro = findViewById(R.id.textViewLogradouro);

        buttonBuscar.setOnClickListener(v -> {
            String cep = editTextCep.getText().toString().trim();
            if (cep.length() == 8) {
                buscarCep(cep);
            } else {
                editTextCep.setError("Digite um CEP válido com 8 dígitos");
            }
        });
    }

    private void buscarCep(String cep) {
        textViewLogradouro.setText("Buscando...");
        
        executorService.execute(() -> {
            String result = "";
            try {
                // Utilizando HTTP para evitar problemas de SSL em dispositivos de teste
                URL url = new URL("http://viacep.com.br/ws/" + cep + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                if (connection.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    result = response.toString();
                    
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.has("erro")) {
                        updateUI("CEP não encontrado.");
                    } else {
                        String logradouro = jsonObject.optString("logradouro", "");
                        String complemento = jsonObject.optString("complemento", "");
                        String bairro = jsonObject.optString("bairro", "");
                        String localidade = jsonObject.optString("localidade", "");
                        String uf = jsonObject.optString("uf", "");
                        String estado = jsonObject.optString("estado", "");
                        String regiao = jsonObject.optString("regiao", "");
                        String ddd = jsonObject.optString("ddd", "");
                        
                        StringBuilder sb = new StringBuilder();
                        if (!logradouro.isEmpty()) sb.append("Logradouro: ").append(logradouro).append("\n");
                        if (!complemento.isEmpty()) sb.append("Complemento: ").append(complemento).append("\n");
                        if (!bairro.isEmpty()) sb.append("Bairro: ").append(bairro).append("\n");
                        if (!localidade.isEmpty()) sb.append("Localidade: ").append(localidade).append("\n");
                        if (!uf.isEmpty()) sb.append("UF: ").append(uf).append("\n");
                        if (!estado.isEmpty()) sb.append("Estado: ").append(estado).append("\n");
                        if (!regiao.isEmpty()) sb.append("Região: ").append(regiao).append("\n");
                        if (!ddd.isEmpty()) sb.append("DDD: ").append(ddd);
                        
                        updateUI(sb.toString().trim());
                    }
                } else {
                    updateUI("Erro na conexão: " + connection.getResponseCode());
                }
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                updateUI("Erro ao buscar CEP: " + e.getMessage());
            }
        });
    }

    private void updateUI(String message) {
        handler.post(() -> textViewLogradouro.setText(message));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}