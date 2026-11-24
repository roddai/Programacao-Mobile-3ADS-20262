package com.example.n2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {

    private UsuarioRepositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repositorio = new UsuarioRepositorio(this);
        JSONObject usuario = repositorio.usuarioLogado();

        // Sem usuário logado não pode acessar o menu
        if (usuario == null) {
            voltarParaLogin();
            return;
        }

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        TextView tvBoasVindas = findViewById(R.id.tvBoasVindas);
        Button btnJogar = findViewById(R.id.btnJogar);
        Button btnDesempenho = findViewById(R.id.btnDesempenho);
        Button btnSair = findViewById(R.id.btnSair);

        tvBoasVindas.setText(getString(R.string.boas_vindas, usuario.optString("nome")));

        btnJogar.setOnClickListener(v -> startActivity(new Intent(this, JogoActivity.class)));
        btnDesempenho.setOnClickListener(v -> mostrarDesempenho());
        btnSair.setOnClickListener(v -> sairDaConta());
    }

    // Mostra as estatísticas do usuário (lidas de novo, para pegar a última partida)
    private void mostrarDesempenho() {
        JSONObject usuario = repositorio.usuarioLogado();
        if (usuario == null) {
            voltarParaLogin();
            return;
        }

        int partidas = usuario.optInt("partidasJogadas", 0);
        String mensagem;

        if (partidas == 0) {
            mensagem = getString(R.string.desempenho_sem_partidas);
        } else {
            int acertos = usuario.optInt("totalAcertos", 0);
            int erros = usuario.optInt("totalErros", 0);
            int aproveitamento = acertos * 100 / (acertos + erros);

            mensagem = getString(R.string.desempenho_partidas, partidas) + "\n"
                    + getString(R.string.melhor_pontuacao, usuario.optInt("melhorPontuacao", 0)) + "\n"
                    + getString(R.string.desempenho_ultima, usuario.optInt("ultimaPontuacao", 0)) + "\n"
                    + getString(R.string.desempenho_total_acertos, acertos) + "\n"
                    + getString(R.string.desempenho_total_erros, erros) + "\n"
                    + getString(R.string.desempenho_aproveitamento, aproveitamento);
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.botao_desempenho)
                .setMessage(mensagem)
                .setPositiveButton(R.string.botao_ok, null)
                .show();
    }

    private void sairDaConta() {
        repositorio.encerrarSessao();
        voltarParaLogin();
    }

    private void voltarParaLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
