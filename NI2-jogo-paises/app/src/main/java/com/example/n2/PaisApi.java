package com.example.n2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Busca a lista de países na REST Countries (v5)
public class PaisApi {

    private static final String URL_API = "https://api.restcountries.com/countries/v5";
    private static final String CAMPOS = "names.common,names.translations,flag.url_png,capitals";
    private static final int LIMITE_POR_PAGINA = 100; // máximo do plano gratuito
    private static final int OFFSET_MAXIMO = 1000;    // trava de segurança para o laço

    // Busca todos os países, de 100 em 100, e devolve só os que têm nome e bandeira
    public static List<Pais> buscarTodos() throws IOException, JSONException {
        List<Pais> paises = new ArrayList<>();
        Set<String> nomesUsados = new HashSet<>();
        int offset = 0;
        boolean temMais = true;

        while (temMais && offset < OFFSET_MAXIMO) {
            String url = URL_API + "?limit=" + LIMITE_POR_PAGINA + "&offset=" + offset
                    + "&response_fields=" + CAMPOS;
            String resposta = ConexaoHttp.buscarTexto(url, BuildConfig.REST_COUNTRIES_KEY);

            JSONObject data = new JSONObject(resposta).getJSONObject("data");
            JSONArray objetos = data.getJSONArray("objects");

            for (int i = 0; i < objetos.length(); i++) {
                Pais pais = lerPais(objetos.getJSONObject(i));
                // Ignora países sem bandeira e nomes repetidos
                if (pais != null && nomesUsados.add(pais.getNome())) {
                    paises.add(pais);
                }
            }

            JSONObject meta = data.optJSONObject("meta");
            temMais = meta != null && meta.optBoolean("more", false) && objetos.length() > 0;
            offset += LIMITE_POR_PAGINA;
        }

        return paises;
    }

    // Converte o JSON de um país no objeto Pais
    private static Pais lerPais(JSONObject json) {
        JSONObject names = json.optJSONObject("names");
        if (names == null) return null;

        String nome = nomeEmPortugues(names);

        JSONObject flag = json.optJSONObject("flag");
        String urlBandeira = flag != null ? flag.optString("url_png", "") : "";

        String capital = "Não possui";
        JSONArray capitais = json.optJSONArray("capitals");
        if (capitais != null && capitais.length() > 0) {
            JSONObject primeira = capitais.optJSONObject(0);
            if (primeira != null) capital = primeira.optString("name", capital);
        }

        if (nome.isEmpty() || urlBandeira.isEmpty()) return null;
        return new Pais(nome, urlBandeira, capital);
    }

    // Usa a tradução em português; se não tiver, usa o nome em inglês (ex.: Germany -> Alemanha)
    private static String nomeEmPortugues(JSONObject names) {
        JSONObject traducoes = names.optJSONObject("translations");
        if (traducoes != null) {
            JSONObject por = traducoes.optJSONObject("por");
            if (por != null && !por.optString("common", "").isEmpty()) {
                return por.optString("common");
            }
        }
        return names.optString("common", "");
    }
}
