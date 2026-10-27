package com.example.aula11_paises_api;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etNomePais;
    private MaterialButton btnBuscar;
    private ProgressBar progressBar;
    private CardView cvResultado;
    private ImageView ivBandeira;
    private TextView tvNomeOficial, tvCapital, tvRegiao, tvPopulacao;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar componentes com os IDs do XML
        etNomePais = findViewById(R.id.etNomePais);
        btnBuscar = findViewById(R.id.btnBuscar);
        progressBar = findViewById(R.id.progressBar);
        cvResultado = findViewById(R.id.cvResultado);
        ivBandeira = findViewById(R.id.ivBandeira);
        tvNomeOficial = findViewById(R.id.tvNomeOficial);
        tvCapital = findViewById(R.id.tvCapital);
        tvRegiao = findViewById(R.id.tvRegiao);
        tvPopulacao = findViewById(R.id.tvPopulacao);

        requestQueue = Volley.newRequestQueue(this);

        btnBuscar.setOnClickListener(v -> {
            String nome = etNomePais.getText().toString().trim();
            if (nome.isEmpty()) {
                etNomePais.setError("Por favor, digite o nome de um país");
            } else {
                fecharTeclado();
                buscarPais(nome);
            }
        });
    }

    private void buscarPais(String nome) {
        // Montar URL dinamicamente e tratar espaços/caracteres especiais
        String url = "https://restcountries.com/v3.1/name/" + Uri.encode(nome);

        progressBar.setVisibility(View.VISIBLE);
        cvResultado.setVisibility(View.GONE);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        if (response.length() > 0) {
                            // Pega o primeiro país retornado pela busca
                            JSONObject pais = response.getJSONObject(0);
                            exibirDados(pais);
                        } else {
                            mostrarErro("Nenhum país encontrado.");
                        }
                    } catch (JSONException e) {
                        mostrarErro("Erro ao ler os dados do país.");
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    if (error.networkResponse != null && error.networkResponse.statusCode == 404) {
                        mostrarErro("País não encontrado. Tente o nome em inglês (ex: Brazil).");
                    } else {
                        mostrarErro("Erro de rede. Verifique sua conexão com a internet.");
                    }
                });

        requestQueue.add(request);
    }

    private void exibirDados(JSONObject pais) throws JSONException {
        // Nome Oficial
        JSONObject nameObj = pais.getJSONObject("name");
        String nomeOficial = nameObj.optString("official", "N/A");
        
        // Capital (pode ser um array na API)
        String capital = "N/A";
        if (pais.has("capital") && !pais.isNull("capital")) {
            JSONArray capitals = pais.getJSONArray("capital");
            if (capitals.length() > 0) {
                capital = capitals.getString(0);
            }
        }
        
        // Região
        String regiao = pais.optString("region", "N/A");
        
        // População
        long populacao = pais.optLong("population", 0);
        String populacaoFormatada = NumberFormat.getInstance(new Locale("pt", "BR")).format(populacao);
        
        // Bandeira
        String bandeiraUrl = "";
        if (pais.has("flags")) {
            bandeiraUrl = pais.getJSONObject("flags").optString("png", "");
        }

        // Atualizar interface com os dados recebidos
        tvNomeOficial.setText(nomeOficial);
        tvCapital.setText("Capital: " + capital);
        tvRegiao.setText("Região: " + regiao);
        tvPopulacao.setText("População: " + populacaoFormatada);

        // Carregar a imagem da bandeira usando Glide
        Glide.with(this)
                .load(bandeiraUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.stat_notify_error)
                .into(ivBandeira);

        cvResultado.setVisibility(View.VISIBLE);
    }

    private void mostrarErro(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void fecharTeclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
