package com.example.aula11moeda;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView autoCompleteBase, autoCompleteTarget;
    private TextInputEditText editValue;
    private Button btnConvert;
    private TextView txtResults;

    private ExchangeRateService service;
    private Map<String, Double> currentRates;
    private String lastFetchedBase = "";

    private final String[] currencies = {"BRL", "USD", "EUR"};

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

        // Corrigido: Usando os IDs autoCompleteFrom e autoCompleteTo definidos no XML
        autoCompleteBase = findViewById(R.id.autoCompleteFrom);
        autoCompleteTarget = findViewById(R.id.autoCompleteTo);
        editValue = findViewById(R.id.editValue);
        btnConvert = findViewById(R.id.btnConvert);
        txtResults = findViewById(R.id.txtResults);

        // Configurar os seletores de moeda
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencies);
        autoCompleteBase.setAdapter(adapter);
        autoCompleteTarget.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.exchangerate-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ExchangeRateService.class);

        btnConvert.setOnClickListener(v -> startConversion());
    }

    private void startConversion() {
        String base = autoCompleteBase.getText().toString();
        String target = autoCompleteTarget.getText().toString();
        String valueStr = editValue.getText().toString();

        if (valueStr.isEmpty()) {
            Toast.makeText(this, "Informe um valor", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(valueStr);

        // Se a moeda base mudou ou não temos taxas, buscamos na API
        if (!base.equals(lastFetchedBase) || currentRates == null) {
            fetchRatesAndConvert(base, target, value);
        } else {
            performCalculation(base, target, value);
        }
    }

    private void fetchRatesAndConvert(String base, String target, double value) {
        service.getLatestRates(base).enqueue(new Callback<ExchangeRateResponse>() {
            @Override
            public void onResponse(Call<ExchangeRateResponse> call, Response<ExchangeRateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    currentRates = response.body().getRates();
                    lastFetchedBase = base;
                    performCalculation(base, target, value);
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao buscar taxas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ExchangeRateResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro de rede: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performCalculation(String base, String target, double value) {
        if (currentRates != null && currentRates.containsKey(target)) {
            double rate = currentRates.get(target);
            double result = value * rate;

            String resultText = String.format("%s %.2f = %s %.2f", base, value, target, result);
            txtResults.setText(resultText);
        } else {
            Toast.makeText(this, "Taxa de câmbio não disponível", Toast.LENGTH_SHORT).show();
        }
    }
}
