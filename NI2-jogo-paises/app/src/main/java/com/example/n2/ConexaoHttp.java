package com.example.n2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

// Faz as requisições HTTP usadas pelo app (ViaCEP e REST Countries)
public class ConexaoHttp {

    private static final int TEMPO_LIMITE = 15000;

    // Faz um GET sem autenticação (usado no ViaCEP)
    public static String buscarTexto(String endereco) throws IOException {
        return buscarTexto(endereco, null);
    }

    // Faz um GET e devolve o corpo da resposta como texto
    public static String buscarTexto(String endereco, String chaveApi) throws IOException {
        HttpURLConnection conexao = abrirConexao(endereco, chaveApi);
        try {
            BufferedReader leitor = new BufferedReader(
                    new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder texto = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                texto.append(linha);
            }
            leitor.close();
            return texto.toString();
        } finally {
            conexao.disconnect();
        }
    }

    // Baixa uma imagem (bandeira). Devolve null se der erro
    public static Bitmap baixarImagem(String endereco) {
        HttpURLConnection conexao = null;
        try {
            conexao = abrirConexao(endereco, null);
            InputStream entrada = conexao.getInputStream();
            Bitmap imagem = BitmapFactory.decodeStream(entrada);
            entrada.close();
            return imagem;
        } catch (IOException e) {
            return null;
        } finally {
            if (conexao != null) conexao.disconnect();
        }
    }

    private static HttpURLConnection abrirConexao(String endereco, String chaveApi) throws IOException {
        HttpURLConnection conexao = (HttpURLConnection) URI.create(endereco).toURL().openConnection();
        conexao.setConnectTimeout(TEMPO_LIMITE);
        conexao.setReadTimeout(TEMPO_LIMITE);

        if (chaveApi != null) {
            conexao.setRequestProperty("Authorization", "Bearer " + chaveApi);
        }

        int codigo = conexao.getResponseCode();
        if (codigo != HttpURLConnection.HTTP_OK) {
            conexao.disconnect();
            if (codigo == 401 || codigo == 403) {
                throw new IOException("Chave da API inválida ou sem permissão (HTTP " + codigo + ")");
            }
            if (codigo == 429) {
                throw new IOException("Muitas requisições. Espere alguns segundos e tente de novo.");
            }
            throw new IOException("Erro na resposta da API (HTTP " + codigo + ")");
        }
        return conexao;
    }
}
