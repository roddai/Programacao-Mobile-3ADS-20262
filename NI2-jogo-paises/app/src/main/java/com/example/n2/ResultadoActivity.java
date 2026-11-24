package com.example.n2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

public class ResultadoActivity extends AppCompatActivity {

    public static final String EXTRA_ACERTOS = "acertos";
    public static final String EXTRA_ERROS = "erros";
    public static final String EXTRA_PONTOS = "pontos";
    public static final String EXTRA_NOVO_RECORDE = "novo_recorde";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UsuarioRepositorio repositorio = new UsuarioRepositorio(this);
        JSONObject usuario = repositorio.usuarioLogado();

        // Usuário não autenticado volta para o login
        if (usuario == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_resultado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        TextView tvNomeJogador = findViewById(R.id.tvNomeJogador);
        TextView tvPontuacaoFinal = findViewById(R.id.tvPontuacaoFinal);
        TextView tvNovoRecorde = findViewById(R.id.tvNovoRecorde);
        TextView tvAcertos = findViewById(R.id.tvAcertos);
        TextView tvErros = findViewById(R.id.tvErros);
        TextView tvMelhorPontuacao = findViewById(R.id.tvMelhorPontuacao);
        Button btnJogarNovamente = findViewById(R.id.btnJogarNovamente);
        Button btnVoltarMenu = findViewById(R.id.btnVoltarMenu);

        int acertos = getIntent().getIntExtra(EXTRA_ACERTOS, 0);
        int erros = getIntent().getIntExtra(EXTRA_ERROS, 0);
        int pontos = getIntent().getIntExtra(EXTRA_PONTOS, 0);
        boolean novoRecorde = getIntent().getBooleanExtra(EXTRA_NOVO_RECORDE, false);

        tvNomeJogador.setText(getString(R.string.nome_jogador, usuario.optString("nome")));
        tvPontuacaoFinal.setText(getString(R.string.pontuacao, pontos));
        tvAcertos.setText(getString(R.string.quantidade_acertos, acertos));
        tvErros.setText(getString(R.string.quantidade_erros, erros));
        tvMelhorPontuacao.setText(getString(R.string.melhor_pontuacao, usuario.optInt("melhorPontuacao", 0)));
        tvNovoRecorde.setVisibility(novoRecorde ? View.VISIBLE : View.GONE);

        btnJogarNovamente.setOnClickListener(v -> {
            startActivity(new Intent(this, JogoActivity.class));
            finish();
        });

        // O menu continua aberto embaixo, então é só fechar esta tela
        btnVoltarMenu.setOnClickListener(v -> finish());
    }
}
