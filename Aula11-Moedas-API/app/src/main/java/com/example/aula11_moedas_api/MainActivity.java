package com.example.aula11_moedas_api;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText edtMoeda, edtValor;
    private MaterialButton btnConsultar;
    private TextView txtResultados;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtMoeda = findViewById(R.id.edtMoeda);
        edtValor = findViewById(R.id.edtValor);
        btnConsultar = findViewById(R.id.btnConsultar);
        txtResultados = findViewById(R.id.txtResultados);

        queue = Volley.newRequestQueue(this);

        btnConsultar.setOnClickListener(v -> consultarCotacao());
    }

    private void consultarCotacao() {
        String moedaBase = edtMoeda.getText().toString().trim().toUpperCase(Locale.ROOT);
        String valorStr = edtValor.getText().toString().trim().replace(",", ".");

        if (moedaBase.isEmpty() || valorStr.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (moedaBase.length() != 3) {
            Toast.makeText(this, "A moeda deve ter 3 letras (Ex: BRL, USD, EUR).", Toast.LENGTH_SHORT).show();
            return;
        }

        double valorInformado;
        try {
            valorInformado = Double.parseDouble(valorStr);
            if (valorInformado <= 0) {
                Toast.makeText(this, "Informe um valor maior que zero.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Valor numérico inválido.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://api.exchangerate-api.com/v4/latest/" + moedaBase;
        txtResultados.setText("Consultando...");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject rates = response.getJSONObject("rates");
                        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
                        
                        double rateBRL = rates.optDouble("BRL", 0);
                        double rateUSD = rates.optDouble("USD", 0);
                        double rateEUR = rates.optDouble("EUR", 0);

                        StringBuilder sb = new StringBuilder();
                        sb.append("Moeda Base: ").append(moedaBase).append("\n\n");
                        
                        // 1) Exibir cotações de 1 unidade
                        sb.append("Cotação para 1 ").append(moedaBase).append(":\n");
                        sb.append("• Real (BRL): ").append(df.format(rateBRL)).append("\n");
                        sb.append("• Dólar (USD): ").append(df.format(rateUSD)).append("\n");
                        sb.append("• Euro (EUR): ").append(df.format(rateEUR)).append("\n\n");

                        // 2) Exibir valor nas outras duas moedas (do trio BRL, USD, EUR)
                        sb.append("Conversão de ").append(df.format(valorInformado)).append(" ").append(moedaBase).append(":\n");
                        
                        if (!moedaBase.equals("BRL")) 
                            sb.append("→ R$ ").append(df.format(valorInformado * rateBRL)).append(" (BRL)\n");
                        if (!moedaBase.equals("USD")) 
                            sb.append("→ $ ").append(df.format(valorInformado * rateUSD)).append(" (USD)\n");
                        if (!moedaBase.equals("EUR")) 
                            sb.append("→ € ").append(df.format(valorInformado * rateEUR)).append(" (EUR)\n");

                        txtResultados.setText(sb.toString());

                    } catch (Exception e) {
                        txtResultados.setText("Erro ao processar dados. A moeda informada é válida?");
                    }
                },
                error -> txtResultados.setText("Falha na conexão ou moeda não encontrada.")
        );

        queue.add(request);
    }
}
