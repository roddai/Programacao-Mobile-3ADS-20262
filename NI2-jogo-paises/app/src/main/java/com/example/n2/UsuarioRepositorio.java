package com.example.n2;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Guarda os usuários cadastrados e quem está logado no momento
public class UsuarioRepositorio {

    private static final String ARQUIVO_USUARIOS = "usuarios";
    private static final String ARQUIVO_SESSAO = "sessao";
    private static final String CHAVE_EMAIL_LOGADO = "email_logado";

    private final SharedPreferences usuarios;
    private final SharedPreferences sessao;

    public UsuarioRepositorio(Context context) {
        usuarios = context.getSharedPreferences(ARQUIVO_USUARIOS, Context.MODE_PRIVATE);
        sessao = context.getSharedPreferences(ARQUIVO_SESSAO, Context.MODE_PRIVATE);
    }

    // ---------- Usuários ----------

    public boolean emailExiste(String email) {
        return usuarios.contains(email.toLowerCase());
    }

    // Cada usuário fica salvo como um JSON, usando o e-mail como chave
    public void salvarUsuario(JSONObject usuario) throws JSONException {
        String email = usuario.getString("email").toLowerCase();
        usuarios.edit().putString(email, usuario.toString()).apply();
    }

    public JSONObject buscarUsuario(String email) {
        String json = usuarios.getString(email.toLowerCase(), null);
        if (json == null) {
            return null;
        }
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            return null;
        }
    }

    public boolean senhaCorreta(String email, String senha) {
        JSONObject usuario = buscarUsuario(email);
        if (usuario == null) {
            return false;
        }
        return usuario.optString("senha").equals(gerarHash(senha));
    }

    // ---------- Desempenho ----------

    // Atualiza as estatísticas do usuário no fim da partida. Devolve true se for um novo recorde
    public boolean salvarPartida(String email, int pontos, int acertos, int erros) {
        JSONObject usuario = buscarUsuario(email);
        if (usuario == null) {
            return false;
        }

        int melhorAnterior = usuario.optInt("melhorPontuacao", 0);
        boolean novoRecorde = pontos > melhorAnterior;

        try {
            usuario.put("partidasJogadas", usuario.optInt("partidasJogadas", 0) + 1);
            usuario.put("totalAcertos", usuario.optInt("totalAcertos", 0) + acertos);
            usuario.put("totalErros", usuario.optInt("totalErros", 0) + erros);
            usuario.put("ultimaPontuacao", pontos);
            usuario.put("melhorPontuacao", Math.max(pontos, melhorAnterior));
            salvarUsuario(usuario);
        } catch (JSONException e) {
            return false;
        }

        return novoRecorde;
    }

    // ---------- Sessão ----------

    public void iniciarSessao(String email) {
        sessao.edit().putString(CHAVE_EMAIL_LOGADO, email.toLowerCase()).apply();
    }

    // Devolve o usuário logado, ou null se ninguém estiver logado
    public JSONObject usuarioLogado() {
        String email = sessao.getString(CHAVE_EMAIL_LOGADO, null);
        if (email == null) {
            return null;
        }
        return buscarUsuario(email);
    }

    public void encerrarSessao() {
        sessao.edit().remove(CHAVE_EMAIL_LOGADO).apply();
    }

    // A senha não é salva em texto puro: guardamos só o hash SHA-256 dela
    public static String gerarHash(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
