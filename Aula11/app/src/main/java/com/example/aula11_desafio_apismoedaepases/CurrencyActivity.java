package com.example.aula11_desafio_apismoedaepases;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class CurrencyActivity extends AppCompatActivity {

    private TextInputEditText etBaseCurrency, etAmount;
    private MaterialButton btnFetchCurrency;
    private MaterialCardView cardResults;
    private TextView tvBaseResult, tvRate1, tvRate2, tvConversion1, tvConversion2;
    private ProgressBar pbCurrency;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        etBaseCurrency = findViewById(R.id.etBaseCurrency);
        etAmount = findViewById(R.id.etAmount);
        btnFetchCurrency = findViewById(R.id.btnFetchCurrency);
        cardResults = findViewById(R.id.cardResults);
        tvBaseResult = findViewById(R.id.tvBaseResult);
        tvRate1 = findViewById(R.id.tvRate1);
        tvRate2 = findViewById(R.id.tvRate2);
        tvConversion1 = findViewById(R.id.tvConversion1);
        tvConversion2 = findViewById(R.id.tvConversion2);
        pbCurrency = findViewById(R.id.pbCurrency);

        requestQueue = Volley.newRequestQueue(this);

        btnFetchCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchCurrencyData();
            }
        });
    }

    private void fetchCurrencyData() {
        String base = etBaseCurrency.getText().toString().trim().toUpperCase();
        String amountStr = etAmount.getText().toString().trim();

        if (base.isEmpty()) {
            Toast.makeText(this, "Informe a moeda base (BRL, USD ou EUR)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!base.equals("BRL") && !base.equals("USD") && !base.equals("EUR")) {
            Toast.makeText(this, "Por favor, use apenas BRL, USD ou EUR.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Informe um valor para converter", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                Toast.makeText(this, "O valor deve ser positivo", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor numérico inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://api.exchangerate-api.com/v4/latest/" + base;

        pbCurrency.setVisibility(View.VISIBLE);
        cardResults.setVisibility(View.GONE);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pbCurrency.setVisibility(View.GONE);
                        try {
                            JSONObject rates = response.getJSONObject("rates");
                            displayResults(base, amount, rates);
                        } catch (JSONException e) {
                            Toast.makeText(CurrencyActivity.this, "Erro ao processar dados", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pbCurrency.setVisibility(View.GONE);
                        Toast.makeText(CurrencyActivity.this, "Erro na consulta. Verifique sua conexão.", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }

    private void displayResults(String base, double amount, JSONObject rates) throws JSONException {
        tvBaseResult.setText("Moeda Base: " + base);
        cardResults.setVisibility(View.VISIBLE);

        if (base.equals("BRL")) {
            double rateUSD = rates.getDouble("USD");
            double rateEUR = rates.getDouble("EUR");
            tvRate1.setText(String.format(Locale.getDefault(), "1 BRL = %.4f USD", rateUSD));
            tvRate2.setText(String.format(Locale.getDefault(), "1 BRL = %.4f EUR", rateEUR));
            tvConversion1.setText(String.format(Locale.getDefault(), "Valor em USD: $ %.2f", amount * rateUSD));
            tvConversion2.setText(String.format(Locale.getDefault(), "Valor em EUR: € %.2f", amount * rateEUR));
        } else if (base.equals("USD")) {
            double rateBRL = rates.getDouble("BRL");
            double rateEUR = rates.getDouble("EUR");
            tvRate1.setText(String.format(Locale.getDefault(), "1 USD = %.4f BRL", rateBRL));
            tvRate2.setText(String.format(Locale.getDefault(), "1 USD = %.4f EUR", rateEUR));
            tvConversion1.setText(String.format(Locale.getDefault(), "Valor em BRL: R$ %.2f", amount * rateBRL));
            tvConversion2.setText(String.format(Locale.getDefault(), "Valor em EUR: € %.2f", amount * rateEUR));
        } else if (base.equals("EUR")) {
            double rateBRL = rates.getDouble("BRL");
            double rateUSD = rates.getDouble("USD");
            tvRate1.setText(String.format(Locale.getDefault(), "1 EUR = %.4f BRL", rateBRL));
            tvRate2.setText(String.format(Locale.getDefault(), "1 EUR = %.4f USD", rateUSD));
            tvConversion1.setText(String.format(Locale.getDefault(), "Valor em BRL: R$ %.2f", amount * rateBRL));
            tvConversion2.setText(String.format(Locale.getDefault(), "Valor em USD: $ %.2f", amount * rateUSD));
        }
    }
}
