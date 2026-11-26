
package com.example.advinheopais;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class JogoActivity extends AppCompatActivity {

    private TextView txtPergunta, txtPontuacao;
    private ImageView imgBandeira;
    private RadioGroup grupoRespostas;
    private RadioButton radioOpcao1, radioOpcao2;
    private RadioButton radioOpcao3, radioOpcao4;
    private Button btnResponder;

    private RequestQueue fila;

    private final ArrayList<String> nomes = new ArrayList<>();
    private final ArrayList<String> bandeiras = new ArrayList<>();
    private final ArrayList<Integer> ordem = new ArrayList<>();

    private int rodada = 0;
    private int pontuacao = 0;
    private int acertos = 0;
    private int respostaCorreta = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);

        txtPergunta = findViewById(R.id.txtPergunta);
        txtPontuacao = findViewById(R.id.txtPontuacao);
        imgBandeira = findViewById(R.id.imgBandeira);
        grupoRespostas = findViewById(R.id.grupoRespostas);

        radioOpcao1 = findViewById(R.id.radioOpcao1);
        radioOpcao2 = findViewById(R.id.radioOpcao2);
        radioOpcao3 = findViewById(R.id.radioOpcao3);
        radioOpcao4 = findViewById(R.id.radioOpcao4);

        btnResponder = findViewById(R.id.btnResponder);

        fila = Volley.newRequestQueue(this);

        btnResponder.setEnabled(false);

        btnResponder.setOnClickListener(v -> responder());

        carregarPaises();
    }

    private void carregarPaises() {

        String url =
                "https://pw-d20261017a29.plainwork-apps.workers.dev"
                        + "/v3.1/all?fields=name,flags";

        JsonArrayRequest requisicao = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                resposta -> {

                    nomes.clear();
                    bandeiras.clear();
                    ordem.clear();

                    for (int i = 0; i < resposta.length(); i++) {
                        JSONObject pais = resposta.optJSONObject(i);

                        if (pais == null) {
                            continue;
                        }

                        JSONObject nome = pais.optJSONObject("name");
                        JSONObject flags = pais.optJSONObject("flags");

                        if (nome == null || flags == null) {
                            continue;
                        }

                        String nomePais = nome.optString("common");
                        String urlBandeira = flags.optString("png");

                        if (!nomePais.isEmpty()
                                && !urlBandeira.isEmpty()
                                && !nomes.contains(nomePais)) {

                            nomes.add(nomePais);
                            bandeiras.add(urlBandeira);
                        }
                    }

                    if (nomes.size() < 10) {
                        Toast.makeText(this,
                                "Não foi possível carregar os países.",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    for (int i = 0; i < nomes.size(); i++) {
                        ordem.add(i);
                    }

                    Collections.shuffle(ordem);

                    mostrarPergunta();
                },
                erro -> {
                    Toast.makeText(this,
                            "Erro ao carregar os países. Verifique a internet.",
                            Toast.LENGTH_LONG).show();
                }
        );

        fila.add(requisicao);
    }

    private void mostrarPergunta() {

        if (rodada >= 10) {
            finalizarJogo();
            return;
        }

        int indiceCorreto = ordem.get(rodada);

        txtPergunta.setText("Pergunta " + (rodada + 1) + " de 10");
        txtPontuacao.setText("Pontuação: " + pontuacao);

        imgBandeira.setImageDrawable(null);
        grupoRespostas.clearCheck();

        ArrayList<Integer> alternativas = new ArrayList<>();

        alternativas.add(indiceCorreto);

        ArrayList<Integer> outrosPaises = new ArrayList<>();

        for (int i = 0; i < nomes.size(); i++) {
            if (i != indiceCorreto) {
                outrosPaises.add(i);
            }
        }

        Collections.shuffle(outrosPaises);

        for (int i = 0; i < 3; i++) {
            alternativas.add(outrosPaises.get(i));
        }

        Collections.shuffle(alternativas);

        RadioButton[] radios = {
                radioOpcao1,
                radioOpcao2,
                radioOpcao3,
                radioOpcao4
        };

        for (int i = 0; i < 4; i++) {
            int indice = alternativas.get(i);

            radios[i].setText(nomes.get(indice));

            if (indice == indiceCorreto) {
                respostaCorreta = radios[i].getId();
            }
        }

        ImageRequest imagem = new ImageRequest(
                bandeiras.get(indiceCorreto),
                resposta -> imgBandeira.setImageBitmap(resposta),
                0,
                0,
                ImageView.ScaleType.FIT_CENTER,
                null,
                erro -> Toast.makeText(this,
                        "Erro ao carregar a bandeira.",
                        Toast.LENGTH_SHORT).show()
        );

        fila.add(imagem);

        btnResponder.setEnabled(true);
    }

    private void responder() {

        int selecionada = grupoRespostas.getCheckedRadioButtonId();

        if (selecionada == -1) {
            Toast.makeText(this,
                    "Selecione uma alternativa.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (selecionada == respostaCorreta) {
            acertos++;
            pontuacao += 10;

            Toast.makeText(this,
                    "Resposta correta! +10 pontos",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Resposta incorreta!",
                    Toast.LENGTH_SHORT).show();
        }

        rodada++;
        mostrarPergunta();
    }

    private void finalizarJogo() {

        int erros = 10 - acertos;

        Intent intent = new Intent(
                this, ResultadoActivity.class);

        intent.putExtra("pontuacao", pontuacao);
        intent.putExtra("acertos", acertos);
        intent.putExtra("erros", erros);

        startActivity(intent);
        finish();
    }
}
