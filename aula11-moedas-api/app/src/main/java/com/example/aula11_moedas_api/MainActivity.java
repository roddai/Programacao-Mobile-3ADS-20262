package com.example.aula11_moedas_api;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etBaseCurrency;
    private TextInputEditText etAmount;
    private Button btnConvert;
    private CardView cvResults;
    private TextView tvResults;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referência aos componentes (usando IDs claros)
        etBaseCurrency = findViewById(R.id.etBaseCurrency);
        etAmount = findViewById(R.id.etAmount);
        btnConvert = findViewById(R.id.btnConvert);
        cvResults = findViewById(R.id.cvResults);
        tvResults = findViewById(R.id.tvResults);
        progressBar = findViewById(R.id.progressBar);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fecharTeclado();
                buscarCotacao();
            }
        });
    }

    private void buscarCotacao() {
        String moedaBase = etBaseCurrency.getText().toString().trim().toUpperCase();
        String valorStr = etAmount.getText().toString().trim();

        // Validação simples
        if (moedaBase.isEmpty()) {
            etBaseCurrency.setError("Informe a moeda");
            return;
        }
        if (valorStr.isEmpty()) {
            etAmount.setError("Informe o valor");
            return;
        }

        double valor;
        try {
            valor = Double.parseDouble(valorStr);
        } catch (NumberFormatException e) {
            etAmount.setError("Valor inválido");
            return;
        }

        // Mostrar progresso e esconder resultado anterior
        progressBar.setVisibility(View.VISIBLE);
        btnConvert.setEnabled(false);
        cvResults.setVisibility(View.GONE);

        String urlAPI = "https://api.exchangerate-api.com/v4/latest/" + moedaBase;

        new Thread(() -> {
            try {
                URL url = new URL(urlAPI);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setConnectTimeout(5000);

                if (conexao.getResponseCode() == 200) {
                    BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                    StringBuilder resposta = new StringBuilder();
                    String linha;
                    while ((linha = leitor.readLine()) != null) {
                        resposta.append(linha);
                    }
                    leitor.close();
                    processarDados(resposta.toString(), moedaBase, valor);
                } else {
                    mostrarErro("Moeda não encontrada!");
                }
            } catch (Exception e) {
                mostrarErro("Erro de conexão. Tente novamente.");
            } finally {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    btnConvert.setEnabled(true);
                });
            }
        }).start();
    }

    private void processarDados(String json, String base, double valorOriginal) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject rates = obj.getJSONObject("rates");

            StringBuilder sb = new StringBuilder();
            sb.append("Base: ").append(base).append("\n\n");

            String[] moedasInteresse = {"BRL", "USD", "EUR"};

            for (String m : moedasInteresse) {
                if (rates.has(m)) {
                    double taxa = rates.getDouble(m);
                    double convertido = valorOriginal * taxa;
                    
                    sb.append(String.format(Locale.getDefault(), 
                        "• %s: %.2f  ➜  Total: %.2f\n", 
                        m, taxa, convertido));
                }
            }

            runOnUiThread(() -> {
                tvResults.setText(sb.toString());
                cvResults.setVisibility(View.VISIBLE);
            });

        } catch (Exception e) {
            mostrarErro("Erro ao ler dados da API.");
        }
    }

    private void mostrarErro(String msg) {
        runOnUiThread(() -> {
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            btnConvert.setEnabled(true);
        });
    }

    private void fecharTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}