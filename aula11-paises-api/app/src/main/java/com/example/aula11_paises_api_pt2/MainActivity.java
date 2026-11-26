package com.example.aula11_paises_api_pt2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String URL_API = "https://api.restcountries.com/countries/v5/";
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

        EditText etPais = findViewById(R.id.etPais);
        Button btnBuscar = findViewById(R.id.btnBuscar);
        ImageView ivBandeira = findViewById(R.id.ivBandeira);
        TextView tvResultado = findViewById(R.id.tvResultado);

        btnBuscar.setOnClickListener(v -> {

            String nome = etPais.getText().toString().trim();

            if (nome.isEmpty()) {
                etPais.setError("Informe o nome de um país");
                return;
            }
            if (!nome.matches("[\\p{L} .'-]+")) {
                etPais.setError("Entrada inválida: use apenas letras");
                return;
            }
            if (BuildConfig.REST_COUNTRIES_KEY.isEmpty()) {
                tvResultado.setText("Chave da API não configurada. Adicione REST_COUNTRIES_KEY no local.properties.");
                tvResultado.setVisibility(View.VISIBLE);
                return;
            }

            tvResultado.setText("Buscando país...");
            tvResultado.setVisibility(View.VISIBLE);
            ivBandeira.setVisibility(View.GONE);

            new Thread(() -> {
                try {
                    JSONObject pais = buscarPais(nome);

                    String resultado = "Nome: " + nomeEmPortugues(pais) +
                            "\nCapital: " + capitais(pais) +
                            "\nRegião: " + traduzirRegiao(pais.optString("region", "-")) +
                            "\nPopulação: " + NumberFormat.getInstance(BRASIL).format(pais.optLong("population")) + " habitantes";

                    Bitmap bandeira = baixarBandeira(pais);

                    runOnUiThread(() -> {
                        tvResultado.setText(resultado);
                        if (bandeira != null) {
                            ivBandeira.setImageBitmap(bandeira);
                            ivBandeira.setVisibility(View.VISIBLE);
                        }
                    });

                } catch (IOException e) {
                    Log.e("MainActivity", "Falha de conexão", e);
                    runOnUiThread(() -> tvResultado.setText("Falha de conexão. Verifique sua internet."));
                } catch (Exception e) {
                    runOnUiThread(() -> tvResultado.setText(e.getMessage()));
                }
            }).start();
        });
    }

    // Procura primeiro pelo nome em português (ex: Alemanha) e depois pelo nome em inglês (ex: Germany)
    private JSONObject buscarPais(String nome) throws Exception {
        String nomeUrl = URLEncoder.encode(nome, "UTF-8").replace("+", "%20");

        JSONArray paises = chamarApi(URL_API + "names.translations/" + nomeUrl);
        if (paises.length() == 0) {
            paises = chamarApi(URL_API + "name?q=" + nomeUrl);
        }
        if (paises.length() == 0) {
            throw new Exception("País \"" + nome + "\" não encontrado.");
        }

        // Se vierem vários, prefere o que tem o nome exatamente igual ao digitado
        for (int i = 0; i < paises.length(); i++) {
            JSONObject pais = paises.getJSONObject(i);
            if (nomeEmPortugues(pais).equalsIgnoreCase(nome)
                    || pais.getJSONObject("names").optString("common").equalsIgnoreCase(nome)) {
                return pais;
            }
        }
        return paises.getJSONObject(0);
    }

    // Monta a requisição com a chave da API e devolve a lista de países encontrados
    private JSONArray chamarApi(String endereco) throws Exception {
        URL url = new URL(endereco);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        conexao.setRequestProperty("Authorization", "Bearer " + BuildConfig.REST_COUNTRIES_KEY);
        conexao.setConnectTimeout(10000);
        conexao.setReadTimeout(10000);

        try {
            int codigo = conexao.getResponseCode();
            if (codigo == HttpURLConnection.HTTP_NOT_FOUND) {
                return new JSONArray();
            }
            if (codigo == HttpURLConnection.HTTP_UNAUTHORIZED || codigo == HttpURLConnection.HTTP_FORBIDDEN) {
                throw new Exception("Chave da API inválida ou sem permissão (código " + codigo + ").");
            }
            if (codigo == 429) {
                throw new Exception("Limite de consultas da API atingido. Tente mais tarde.");
            }
            if (codigo != HttpURLConnection.HTTP_OK) {
                throw new Exception("Erro na API (código " + codigo + ").");
            }

            BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream(), "UTF-8"));
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }
            leitor.close();

            return new JSONObject(resposta.toString()).getJSONObject("data").getJSONArray("objects");
        } finally {
            conexao.disconnect();
        }
    }

    // Baixa a imagem da bandeira usando a URL que vem na resposta da API
    private Bitmap baixarBandeira(JSONObject pais) {
        String urlBandeira = "";
        JSONObject flag = pais.optJSONObject("flag");
        if (flag != null) {
            urlBandeira = flag.optString("url_png", "");
        }
        if (urlBandeira.isEmpty()) {
            return null;
        }

        try {
            HttpURLConnection conexao = (HttpURLConnection) new URL(urlBandeira).openConnection();
            InputStream imagem = conexao.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(imagem);
            imagem.close();
            conexao.disconnect();
            return bitmap;
        } catch (IOException e) {
            return null;
        }
    }

    private String nomeEmPortugues(JSONObject pais) {
        JSONObject names = pais.optJSONObject("names");
        if (names == null) {
            return "-";
        }
        JSONObject traducoes = names.optJSONObject("translations");
        if (traducoes != null && traducoes.optJSONObject("por") != null) {
            return traducoes.optJSONObject("por").optString("common", names.optString("common"));
        }
        return names.optString("common", "-");
    }

    private String capitais(JSONObject pais) {
        JSONArray lista = pais.optJSONArray("capitals");
        if (lista == null || lista.length() == 0) {
            return "Não possui";
        }
        String resultado = "";
        for (int i = 0; i < lista.length(); i++) {
            if (i > 0) resultado += ", ";
            resultado += lista.optJSONObject(i).optString("name");
        }
        return resultado;
    }

    private String traduzirRegiao(String regiao) {
        switch (regiao) {
            case "Africa": return "África";
            case "Americas": return "Américas";
            case "Asia": return "Ásia";
            case "Europe": return "Europa";
            case "Oceania": return "Oceania";
            case "Antarctic": return "Antártida";
            default: return regiao;
        }
    }
}
