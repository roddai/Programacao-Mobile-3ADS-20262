package com.example.aula11_moedas_api;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etBaseCurrency, etAmount;
    private Button btnFetchRates, btnConvert;
    private TextView tvRatesDisplay, tvConversionResult;
    private RadioGroup rgSourceCurrency;

    private final OkHttpClient client = new OkHttpClient();
    private JsonObject currentRates;
    private String currentBase = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBaseCurrency = findViewById(R.id.etBaseCurrency);
        etAmount = findViewById(R.id.etAmount);
        btnFetchRates = findViewById(R.id.btnFetchRates);
        btnConvert = findViewById(R.id.btnConvert);
        tvRatesDisplay = findViewById(R.id.tvRatesDisplay);
        tvConversionResult = findViewById(R.id.tvConversionResult);
        rgSourceCurrency = findViewById(R.id.rgSourceCurrency);

        btnFetchRates.setOnClickListener(v -> {
            String base = etBaseCurrency.getText().toString().toUpperCase().trim();
            if (base.isEmpty()) {
                Toast.makeText(MainActivity.this, "Informe a moeda base", Toast.LENGTH_SHORT).show();
                return;
            }
            fetchRates(base);
        });

        btnConvert.setOnClickListener(v -> performConversion());
    }

    private void fetchRates(String base) {
        String url = "https://api.exchangerate-api.com/v4/latest/" + base;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao buscar dados", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
                            currentRates = jsonObject.getAsJsonObject("rates");
                            currentBase = jsonObject.get("base").getAsString();
                            displayRates();
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Erro ao processar dados", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Moeda inválida ou erro na API", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private void displayRates() {
        if (currentRates == null) return;

        double brl = currentRates.has("BRL") ? currentRates.get("BRL").getAsDouble() : 0;
        double usd = currentRates.has("USD") ? currentRates.get("USD").getAsDouble() : 0;
        double eur = currentRates.has("EUR") ? currentRates.get("EUR").getAsDouble() : 0;

        String text = String.format(Locale.getDefault(),
                "Moeda base: %s\n\nCotação para:\nReal (BRL): %.4f\nDólar (USD): %.4f\nEuro (EUR): %.4f",
                currentBase, brl, usd, eur);
        tvRatesDisplay.setText(text);
    }

    private void performConversion() {
        String amountStr = etAmount.getText().toString();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Informe um valor", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentRates == null) {
            Toast.makeText(this, "Busque as cotações primeiro", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        int checkedId = rgSourceCurrency.getCheckedRadioButtonId();
        String sourceCurrency = "";
        if (checkedId == R.id.rbBRL) sourceCurrency = "BRL";
        else if (checkedId == R.id.rbUSD) sourceCurrency = "USD";
        else if (checkedId == R.id.rbEUR) sourceCurrency = "EUR";

        if (!currentRates.has(sourceCurrency)) {
            Toast.makeText(this, "Taxa para " + sourceCurrency + " não disponível", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert amount from sourceCurrency to the base currency of currentRates, then to others
        double rateToSource = currentRates.get(sourceCurrency).getAsDouble();
        double amountInBase = amount / rateToSource;

        StringBuilder result = new StringBuilder("Conversão de " + amount + " " + sourceCurrency + ":\n\n");

        String[] targets = {"BRL", "USD", "EUR"};
        for (String target : targets) {
            if (!target.equals(sourceCurrency)) {
                if (currentRates.has(target)) {
                    double targetRate = currentRates.get(target).getAsDouble();
                    double converted = amountInBase * targetRate;
                    result.append(String.format(Locale.getDefault(), "%s: %.2f\n", target, converted));
                }
            }
        }

        tvConversionResult.setText(result.toString());
    }
}
