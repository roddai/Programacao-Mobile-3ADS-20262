package com.example.aula11_moeda;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerBase, spinnerOrigin;
    private Button btnConsult, btnConvert;
    private EditText editValue;
    private ProgressBar progressBar;
    
    private View cardSelectedBase;
    private TextView textSelectedBaseCode, textSelectedBaseName;
    
    private View layoutRates;
    private View cardBrl, cardUsd, cardEur;
    
    private View layoutConversionResults;
    private TextView textResult1, textResult2;
    private TextView textUpdateDate;

    private CurrencyApiService apiService;
    private JSONObject lastRates;
    private String lastBaseCurrency;

    private final String[] currencyCodes = {"BRL", "USD", "EUR"};
    private final String[] currencyNames = {"Real brasileiro", "Dólar americano", "Euro"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = new CurrencyApiService();
        initViews();
        setupSpinners();

        btnConsult.setOnClickListener(v -> consultRates());
        btnConvert.setOnClickListener(v -> convertValue());
    }

    private void initViews() {
        spinnerBase = findViewById(R.id.spinner_base);
        spinnerOrigin = findViewById(R.id.spinner_origin);
        btnConsult = findViewById(R.id.btn_consult);
        btnConvert = findViewById(R.id.btn_convert);
        editValue = findViewById(R.id.edit_value);
        progressBar = findViewById(R.id.progress_bar);

        cardSelectedBase = findViewById(R.id.card_selected_base);
        textSelectedBaseCode = findViewById(R.id.text_selected_base_code);
        textSelectedBaseName = findViewById(R.id.text_selected_base_name);

        layoutRates = findViewById(R.id.layout_rates);
        cardBrl = findViewById(R.id.card_brl);
        cardUsd = findViewById(R.id.card_usd);
        cardEur = findViewById(R.id.card_eur);

        layoutConversionResults = findViewById(R.id.layout_conversion_results);
        textResult1 = findViewById(R.id.text_result_1);
        textResult2 = findViewById(R.id.text_result_2);
        textUpdateDate = findViewById(R.id.text_update_date);

        setupRateCard(cardBrl, "BRL", getString(R.string.label_brl));
        setupRateCard(cardUsd, "USD", getString(R.string.label_usd));
        setupRateCard(cardEur, "EUR", getString(R.string.label_eur));
    }

    private void setupRateCard(View card, String code, String name) {
        ((TextView) card.findViewById(R.id.text_currency_code)).setText(code);
        ((TextView) card.findViewById(R.id.text_currency_name)).setText(name);
    }

    private void setupSpinners() {
        String[] spinnerItems = new String[currencyCodes.length];
        for (int i = 0; i < currencyCodes.length; i++) {
            spinnerItems[i] = currencyCodes[i] + " — " + currencyNames[i];
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerBase.setAdapter(adapter);
        spinnerOrigin.setAdapter(adapter);
        
        // BRL inicial
        spinnerBase.setSelection(0);
        spinnerOrigin.setSelection(0);
    }

    private void consultRates() {
        int selectedIndex = spinnerBase.getSelectedItemPosition();
        String baseCurrency = currencyCodes[selectedIndex];

        showLoading(true);
        apiService.getLatestRates(baseCurrency, new CurrencyApiService.ApiCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                showLoading(false);
                lastRates = response;
                lastBaseCurrency = baseCurrency;
                updateUIWithRates(response, baseCurrency, selectedIndex);
            }

            @Override
            public void onError(String message) {
                showLoading(false);
                Toast.makeText(MainActivity.this, getString(R.string.error_generic), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUIWithRates(JSONObject response, String baseCode, int baseIndex) {
        try {
            JSONObject rates = response.getJSONObject("rates");
            String date = response.optString("date", "");
            textUpdateDate.setText("Data de referência: " + date);

            cardSelectedBase.setVisibility(View.VISIBLE);
            textSelectedBaseCode.setText(baseCode);
            textSelectedBaseName.setText(currencyNames[baseIndex]);

            updateCardValue(cardBrl, rates.getDouble("BRL"), baseCode, "BRL");
            updateCardValue(cardUsd, rates.getDouble("USD"), baseCode, "USD");
            updateCardValue(cardEur, rates.getDouble("EUR"), baseCode, "EUR");

        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.error_parse), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCardValue(View card, double rate, String baseCode, String targetCode) {
        TextView textRate = card.findViewById(R.id.text_currency_rate);
        TextView textAux = card.findViewById(R.id.text_currency_aux);

        textRate.setText(String.format(Locale.getDefault(), "%.2f %s", rate, targetCode));
        textAux.setText(getString(R.string.rate_template, baseCode, String.format(Locale.getDefault(), "%.4f", rate), targetCode));
    }

    private void convertValue() {
        String input = editValue.getText().toString().replace(",", ".");
        if (input.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_invalid_value), Toast.LENGTH_SHORT).show();
            return;
        }

        double value;
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.error_invalid_value), Toast.LENGTH_SHORT).show();
            return;
        }

        int originIndex = spinnerOrigin.getSelectedItemPosition();
        String originCurrency = currencyCodes[originIndex];

        // Se já temos as taxas para esta moeda de origem, calculamos direto
        if (lastRates != null && lastBaseCurrency.equals(originCurrency)) {
            performConversion(value, originCurrency, lastRates);
        } else {
            // Caso contrário, busca as taxas para a moeda de origem
            showLoading(true);
            apiService.getLatestRates(originCurrency, new CurrencyApiService.ApiCallback() {
                @Override
                public void onSuccess(JSONObject response) {
                    showLoading(false);
                    lastRates = response;
                    lastBaseCurrency = originCurrency;
                    performConversion(value, originCurrency, response);
                }

                @Override
                public void onError(String message) {
                    showLoading(false);
                    Toast.makeText(MainActivity.this, getString(R.string.error_generic), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void performConversion(double value, String originCode, JSONObject response) {
        try {
            JSONObject rates = response.getJSONObject("rates");
            layoutConversionResults.setVisibility(View.VISIBLE);

            int resultCount = 0;
            for (String code : currencyCodes) {
                if (!code.equals(originCode)) {
                    double rate = rates.getDouble(code);
                    double result = value * rate;
                    String resultText = String.format(Locale.getDefault(), "%.2f %s = %.2f %s", value, originCode, result, code);
                    
                    if (resultCount == 0) {
                        textResult1.setText(resultText);
                        resultCount++;
                    } else {
                        textResult2.setText(resultText);
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.error_parse), Toast.LENGTH_SHORT).show();
        }
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnConsult.setEnabled(!show);
        btnConvert.setEnabled(!show);
    }
}