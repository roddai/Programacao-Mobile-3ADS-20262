package com.example.n2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JogoActivity extends AppCompatActivity {

    private static final int TOTAL_PERGUNTAS = 10;
    private static final int PONTOS_POR_ACERTO = 10;
    private static final String[] LETRAS = {"A", "B", "C", "D"};

    private TextView tvNumeroPergunta, tvPontuacao, tvFeedback, tvCarregando;
    private ImageView ivBandeira;
    private ProgressBar pbBandeira, pbCarregando;
    private Button btnProxima, btnTentarNovamente;
    private Button[] botoesAlternativas;
    private Group gpJogo;

    private ColorStateList corOriginalBotao;
    private ColorStateList corOriginalTexto;

    private UsuarioRepositorio repositorio;
    private JSONObject usuario;
    private final Random sorteio = new Random();

    private List<Pais> todosPaises;   // lista carregada da API uma única vez
    private List<Pais> perguntas;     // os 10 países sorteados da partida
    private Pais paisCorreto;
    private int perguntaAtual = 0;
    private int acertos = 0;
    private int erros = 0;
    private int pontos = 0;
    private boolean jaRespondeu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repositorio = new UsuarioRepositorio(this);
        usuario = repositorio.usuarioLogado();

        // Usuário não autenticado não pode jogar
        if (usuario == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_jogo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(barras.left, barras.top, barras.right, barras.bottom);
            return insets;
        });

        tvNumeroPergunta = findViewById(R.id.tvNumeroPergunta);
        tvPontuacao = findViewById(R.id.tvPontuacao);
        tvFeedback = findViewById(R.id.tvFeedback);
        tvCarregando = findViewById(R.id.tvCarregando);
        ivBandeira = findViewById(R.id.ivBandeira);
        pbBandeira = findViewById(R.id.pbBandeira);
        pbCarregando = findViewById(R.id.pbCarregando);
        btnProxima = findViewById(R.id.btnProxima);
        btnTentarNovamente = findViewById(R.id.btnTentarNovamente);
        gpJogo = findViewById(R.id.gpJogo);

        botoesAlternativas = new Button[]{
                findViewById(R.id.btnAlternativaA),
                findViewById(R.id.btnAlternativaB),
                findViewById(R.id.btnAlternativaC),
                findViewById(R.id.btnAlternativaD)
        };

        // Guarda as cores padrão para "limpar" os botões a cada pergunta
        corOriginalBotao = botoesAlternativas[0].getBackgroundTintList();
        corOriginalTexto = botoesAlternativas[0].getTextColors();

        for (Button botao : botoesAlternativas) {
            botao.setOnClickListener(v -> verificarResposta(botao));
        }
        btnProxima.setOnClickListener(v -> proximaPergunta());
        btnTentarNovamente.setOnClickListener(v -> carregarPaises());

        carregarPaises();
    }

    // Carrega a lista de países da API uma única vez, no começo da partida
    private void carregarPaises() {
        gpJogo.setVisibility(View.INVISIBLE);
        pbCarregando.setVisibility(View.VISIBLE);
        tvCarregando.setVisibility(View.VISIBLE);
        tvCarregando.setText(R.string.carregando_paises);
        btnTentarNovamente.setVisibility(View.GONE);

        new Thread(() -> {
            try {
                List<Pais> paises = PaisApi.buscarTodos();
                runOnUiThread(() -> iniciarPartida(paises));
            } catch (IOException e) {
                Log.e("JogoActivity", "Erro ao buscar países", e);
                runOnUiThread(() -> mostrarErro(getString(R.string.erro_carregar_paises) + "\n" + e.getMessage()));
            } catch (Exception e) {
                Log.e("JogoActivity", "Erro ao ler os países", e);
                runOnUiThread(() -> mostrarErro(getString(R.string.erro_carregar_paises)));
            }
        }).start();
    }

    private void mostrarErro(String mensagem) {
        pbCarregando.setVisibility(View.GONE);
        tvCarregando.setText(mensagem);
        btnTentarNovamente.setVisibility(View.VISIBLE);
    }

    // Sorteia os 10 países da partida e mostra a primeira pergunta
    private void iniciarPartida(List<Pais> paises) {
        if (paises.size() < TOTAL_PERGUNTAS) {
            mostrarErro(getString(R.string.erro_poucos_paises));
            return;
        }

        todosPaises = paises;

        // Embaralha uma cópia e pega os 10 primeiros: assim nenhum país se repete na partida
        List<Pais> embaralhados = new ArrayList<>(paises);
        Collections.shuffle(embaralhados, sorteio);
        perguntas = new ArrayList<>(embaralhados.subList(0, TOTAL_PERGUNTAS));

        perguntaAtual = 0;
        acertos = 0;
        erros = 0;
        pontos = 0;

        pbCarregando.setVisibility(View.GONE);
        tvCarregando.setVisibility(View.GONE);
        gpJogo.setVisibility(View.VISIBLE);

        mostrarPergunta();
    }

    private void mostrarPergunta() {
        jaRespondeu = false;
        paisCorreto = perguntas.get(perguntaAtual);

        tvNumeroPergunta.setText(getString(R.string.numero_pergunta, perguntaAtual + 1, TOTAL_PERGUNTAS));
        tvPontuacao.setText(getString(R.string.pontuacao, pontos));
        tvFeedback.setVisibility(View.INVISIBLE);
        btnProxima.setVisibility(View.INVISIBLE);

        List<Pais> alternativas = montarAlternativas(paisCorreto);
        for (int i = 0; i < botoesAlternativas.length; i++) {
            Button botao = botoesAlternativas[i];
            Pais pais = alternativas.get(i);
            botao.setText(LETRAS[i] + ") " + pais.getNome());
            botao.setTag(pais);
            botao.setBackgroundTintList(corOriginalBotao);
            botao.setTextColor(corOriginalTexto);
            botao.setClickable(true);
        }

        baixarBandeira(paisCorreto);
    }

    // Sorteia 3 países errados (sem repetir) e mistura com o país correto
    private List<Pais> montarAlternativas(Pais correto) {
        List<Pais> alternativas = new ArrayList<>();
        alternativas.add(correto);

        while (alternativas.size() < LETRAS.length) {
            Pais sorteado = todosPaises.get(sorteio.nextInt(todosPaises.size()));

            boolean repetido = false;
            for (Pais pais : alternativas) {
                if (pais.getNome().equals(sorteado.getNome())) {
                    repetido = true;
                    break;
                }
            }
            if (!repetido) {
                alternativas.add(sorteado);
            }
        }

        Collections.shuffle(alternativas, sorteio);
        return alternativas;
    }

    // Baixa a imagem da bandeira em segundo plano
    private void baixarBandeira(Pais pais) {
        ivBandeira.setImageDrawable(null);
        pbBandeira.setVisibility(View.VISIBLE);

        new Thread(() -> {
            Bitmap bandeira = ConexaoHttp.baixarImagem(pais.getUrlBandeira());
            runOnUiThread(() -> {
                // Se o jogador já passou para outra pergunta, ignora esta imagem
                if (pais != paisCorreto) return;

                pbBandeira.setVisibility(View.GONE);
                if (bandeira != null) {
                    ivBandeira.setImageBitmap(bandeira);
                } else {
                    Toast.makeText(this, R.string.erro_bandeira, Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void verificarResposta(Button botaoEscolhido) {
        // Não deixa responder duas vezes a mesma pergunta
        if (jaRespondeu) return;
        jaRespondeu = true;

        for (Button botao : botoesAlternativas) {
            botao.setClickable(false);
        }

        int corAcerto = ContextCompat.getColor(this, R.color.cor_acerto);
        int corErro = ContextCompat.getColor(this, R.color.cor_erro);
        int corBranca = ContextCompat.getColor(this, R.color.white);

        // Sempre mostra em verde qual era a resposta certa
        for (Button botao : botoesAlternativas) {
            if (botao.getTag() == paisCorreto) {
                botao.setBackgroundTintList(ColorStateList.valueOf(corAcerto));
                botao.setTextColor(corBranca);
            }
        }

        Pais escolhido = (Pais) botaoEscolhido.getTag();
        String mensagem;

        if (escolhido == paisCorreto) {
            acertos++;
            pontos += PONTOS_POR_ACERTO;
            mensagem = getString(R.string.resposta_correta, PONTOS_POR_ACERTO);
            tvFeedback.setTextColor(corAcerto);
        } else {
            erros++;
            botaoEscolhido.setBackgroundTintList(ColorStateList.valueOf(corErro));
            botaoEscolhido.setTextColor(corBranca);
            mensagem = getString(R.string.resposta_errada, paisCorreto.getNome());
            tvFeedback.setTextColor(corErro);
        }

        mensagem += "\n" + getString(R.string.capital_do_pais, paisCorreto.getCapital());
        tvFeedback.setText(mensagem);
        tvFeedback.setVisibility(View.VISIBLE);
        tvPontuacao.setText(getString(R.string.pontuacao, pontos));

        boolean ultimaPergunta = perguntaAtual == TOTAL_PERGUNTAS - 1;
        btnProxima.setText(ultimaPergunta ? R.string.botao_ver_resultado : R.string.botao_proxima);
        btnProxima.setVisibility(View.VISIBLE);
    }

    private void proximaPergunta() {
        perguntaAtual++;
        if (perguntaAtual < TOTAL_PERGUNTAS) {
            mostrarPergunta();
        } else {
            finalizarPartida();
        }
    }

    // Salva o desempenho e abre a tela de resultado
    private void finalizarPartida() {
        boolean novoRecorde = repositorio.salvarPartida(usuario.optString("email"), pontos, acertos, erros);

        Intent intent = new Intent(this, ResultadoActivity.class);
        intent.putExtra(ResultadoActivity.EXTRA_ACERTOS, acertos);
        intent.putExtra(ResultadoActivity.EXTRA_ERROS, erros);
        intent.putExtra(ResultadoActivity.EXTRA_PONTOS, pontos);
        intent.putExtra(ResultadoActivity.EXTRA_NOVO_RECORDE, novoRecorde);
        startActivity(intent);
        finish();
    }
}
