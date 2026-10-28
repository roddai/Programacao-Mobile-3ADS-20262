package com.example.aula11_moedas_api;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String URL_API = "https://api.exchangerate-api.com/v4/latest/";
    private static final Locale BRASIL = Locale.forLanguageTag("pt-BR");

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

        EditText etMoedaBase = findViewById(R.id.etMoedaBase);
        Button btnBuscar = findViewById(R.id.btnBuscar);
        TextView tvResultado = findViewById(R.id.tvResultado);

        EditText etValor = findViewById(R.id.etValor);
        Spinner spinnerMoedas = findViewById(R.id.spinnerMoedas);
        Button btnConverter = findViewById(R.id.btnConverter);
        TextView tvConversao = findViewById(R.id.tvConversao);

        String[] moedas = {"BRL", "USD", "EUR"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, moedas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMoedas.setAdapter(adapter);

        // 1) Cotação da moeda base para BRL, USD e EUR
        btnBuscar.setOnClickListener(v -> {

            String moedaBase = etMoedaBase.getText().toString().trim().toUpperCase();

            if (moedaBase.isEmpty()) {
                etMoedaBase.setError("Informe a moeda base");
                return;
            }
            if (!moedaBase.matches("[A-Z]{3}")) {
                etMoedaBase.setError("Entrada inválida: use 3 letras (ex: USD)");
                return;
            }

            tvResultado.setText("Buscando cotação...");
            tvResultado.setVisibility(View.VISIBLE);

            new Thread(() -> {
                try {
                    JSONObject taxas = buscarTaxas(moedaBase);

                    String resultado = "Moeda base: " + moedaBase +
                            "\n\nCotação para:" +
                            "\nReal (BRL): " + String.format(BRASIL, "%.4f", taxas.getDouble("BRL")) +
                            "\nDólar (USD): " + String.format(BRASIL, "%.4f", taxas.getDouble("USD")) +
                            "\nEuro (EUR): " + String.format(BRASIL, "%.4f", taxas.getDouble("EUR"));

                    runOnUiThread(() -> tvResultado.setText(resultado));

                } catch (IOException e) {
                    runOnUiThread(() -> tvResultado.setText("Falha de conexão. Verifique sua internet."));
                } catch (Exception e) {
                    runOnUiThread(() -> tvResultado.setText(e.getMessage()));
                }
            }).start();
        });

        // 2) Converte um valor em BRL, USD ou EUR para as outras duas moedas
        btnConverter.setOnClickListener(v -> {

            String textoValor = etValor.getText().toString().trim().replace(",", ".");

            if (textoValor.isEmpty()) {
                etValor.setError("Informe um valor");
                return;
            }

            double valor;
            try {
                valor = Double.parseDouble(textoValor);
            } catch (NumberFormatException e) {
                etValor.setError("Entrada inválida: digite um número");
                return;
            }
            if (valor <= 0) {
                etValor.setError("O valor deve ser maior que zero");
                return;
            }

            String moedaOrigem = spinnerMoedas.getSelectedItem().toString();
            tvConversao.setText("Convertendo...");
            tvConversao.setVisibility(View.VISIBLE);

            new Thread(() -> {
                try {
                    JSONObject taxas = buscarTaxas(moedaOrigem);

                    String resultado = String.format(BRASIL, "%.2f", valor) + " " + moedaOrigem + " equivale a:";
                    for (String moeda : moedas) {
                        if (!moeda.equals(moedaOrigem)) {
                            double convertido = valor * taxas.getDouble(moeda);
                            resultado += "\n" + String.format(BRASIL, "%.2f", convertido) + " " + moeda;
                        }
                    }

                    String finalResultado = resultado;
                    runOnUiThread(() -> tvConversao.setText(finalResultado));

                } catch (IOException e) {
                    runOnUiThread(() -> tvConversao.setText("Falha de conexão. Verifique sua internet."));
                } catch (Exception e) {
                    runOnUiThread(() -> tvConversao.setText(e.getMessage()));
                }
            }).start();
        });
    }

    // Monta a URL com a moeda informada, chama a API e devolve o objeto "rates"
    private JSONObject buscarTaxas(String moeda) throws Exception {
        URL url = new URL(URL_API + moeda);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        conexao.setConnectTimeout(10000);
        conexao.setReadTimeout(10000);

        try {
            int codigo = conexao.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_NOT_FOUND) {
                throw new Exception("Moeda \"" + moeda + "\" não encontrada na API.");
            }
            if (codigo != HttpURLConnection.HTTP_OK) {
                throw new Exception("Erro na API (código " + codigo + ").");
            }

            BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }
            leitor.close();

            return new JSONObject(resposta.toString()).getJSONObject("rates");
        } finally {
            conexao.disconnect();
        }
    }
}
