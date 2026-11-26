
package com.example.advinheopais;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultadoActivity extends AppCompatActivity {

    private TextView txtPontuacaoFinal, txtAcertos, txtErros;
    private Button btnJogarNovamente, btnVoltarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        txtPontuacaoFinal = findViewById(R.id.txtPontuacaoFinal);
        txtAcertos = findViewById(R.id.txtAcertos);
        txtErros = findViewById(R.id.txtErros);

        btnJogarNovamente = findViewById(R.id.btnJogarNovamente);
        btnVoltarMenu = findViewById(R.id.btnVoltarMenu);

        int pontuacao = getIntent().getIntExtra("pontuacao", 0);
        int acertos = getIntent().getIntExtra("acertos", 0);
        int erros = getIntent().getIntExtra("erros", 0);

        txtPontuacaoFinal.setText(pontuacao + " pontos");
        txtAcertos.setText("Acertos: " + acertos);
        txtErros.setText("Erros: " + erros);

        salvarResultado(pontuacao, acertos);

        btnJogarNovamente.setOnClickListener(v -> {
            startActivity(new Intent(this, JogoActivity.class));
            finish();
        });

        btnVoltarMenu.setOnClickListener(v -> {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
        });
    }

    private void salvarResultado(int pontuacao, int acertos) {

        SharedPreferences sessao = getSharedPreferences(
                "SessaoUsuario", MODE_PRIVATE);

        String email = sessao.getString("email", "");

        if (email.isEmpty()) {
            return;
        }

        SharedPreferences dados = getSharedPreferences(
                "DadosUsuario", MODE_PRIVATE);

        String chaveRecorde = email + "_recorde";
        String chavePartidas = email + "_partidas";
        String chaveTotalAcertos = email + "_totalAcertos";
        String chaveHistorico = email + "_historico";

        int recorde = dados.getInt(chaveRecorde, 0);
        int partidas = dados.getInt(chavePartidas, 0);
        int totalAcertos = dados.getInt(chaveTotalAcertos, 0);
        String historico = dados.getString(chaveHistorico, "");

        if (pontuacao > recorde) {
            recorde = pontuacao;
        }

        partidas++;
        totalAcertos += acertos;

        String novaPartida = "Partida " + partidas
                + ": " + pontuacao + " pontos ("
                + acertos + " acertos)";

        if (historico.isEmpty()) {
            historico = novaPartida;
        } else {
            historico = historico + "\n" + novaPartida;
        }

        dados.edit()
                .putInt(chaveRecorde, recorde)
                .putInt(chavePartidas, partidas)
                .putInt(chaveTotalAcertos, totalAcertos)
                .putString(chaveHistorico, historico)
                .apply();
    }
}
