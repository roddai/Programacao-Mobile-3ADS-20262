package com.example.aula07;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtResultado;

    ImageView imgMaquina;
    ImageView imgInterrogacao;
    ImageView imgPessoa;
    ImageView imgInterrogacao2;

    ImageView imgPedra;
    ImageView imgPapel;
    ImageView imgTesoura;

    ImageView imgPessoaF;
    ImageView imgPessoaM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        // TextView
        txtResultado = findViewById(R.id.txtResultado);

        // Imagens principais
        imgMaquina = findViewById(R.id.imgMaquina);
        imgInterrogacao = findViewById(R.id.imgInterrogacao);

        imgPessoa = findViewById(R.id.imgPessoa);
        imgInterrogacao2 = findViewById(R.id.imgInterrogacao2);

        // Opções do JokenPo
        imgPedra = findViewById(R.id.imgPedra);
        imgPapel = findViewById(R.id.imgPapel);
        imgTesoura = findViewById(R.id.imgTesoura);

        // Personagens
        imgPessoaF = findViewById(R.id.imgPessoaF);
        imgPessoaM = findViewById(R.id.imgPessoaM);


        // -------------------------
        // CLIQUES DO JOKENPO
        // -------------------------

        imgPedra.setOnClickListener(v -> {
            jogar("pedra");
        });

        imgPapel.setOnClickListener(v -> {
            jogar("papel");
        });

        imgTesoura.setOnClickListener(v -> {
            jogar("tesoura");
        });


        // -------------------------
        // ESCOLHA DO PERSONAGEM
        // -------------------------

        imgPessoaF.setOnClickListener(v -> {
            imgPessoa.setImageResource(R.drawable.usuario_fem);
        });

        imgPessoaM.setOnClickListener(v -> {
            imgPessoa.setImageResource(R.drawable.usuario_masc);
        });
    }


    // ========================================
    // MÉTODO PRINCIPAL DO JOGO
    // ========================================

    public void jogar(String escolhaDoUsuario) {

        String[] opcoes = {
                "pedra",
                "papel",
                "tesoura"
        };

        // Gera um número entre 0 e 2
        int numero = new Random().nextInt(3);

        // Obtém a escolha da máquina
        String escolhaMaquina = opcoes[numero];


        // ========================================
        // SWITCH DA MÁQUINA
        // ========================================

        switch (escolhaMaquina) {

            case "pedra":
                imgInterrogacao.setImageResource(R.drawable.pedra);
                break;

            case "papel":
                imgInterrogacao.setImageResource(R.drawable.papel);
                break;

            case "tesoura":
                imgInterrogacao.setImageResource(R.drawable.tesoura);
                break;
        }


        // ========================================
        // SWITCH DO USUÁRIO
        // ========================================

        switch (escolhaDoUsuario) {

            case "pedra":
                imgInterrogacao2.setImageResource(R.drawable.pedra);
                break;

            case "papel":
                imgInterrogacao2.setImageResource(R.drawable.papel);
                break;

            case "tesoura":
                imgInterrogacao2.setImageResource(R.drawable.tesoura);
                break;
        }


        // ========================================
        // VERIFICA QUEM GANHOU
        // ========================================

        if (escolhaDoUsuario.equals(escolhaMaquina)) {

            txtResultado.setText("Empate");

        } else if (
                (escolhaDoUsuario.equals("pedra")
                        && escolhaMaquina.equals("tesoura"))

                        ||

                        (escolhaDoUsuario.equals("papel")
                                && escolhaMaquina.equals("pedra"))

                        ||

                        (escolhaDoUsuario.equals("tesoura")
                                && escolhaMaquina.equals("papel"))
        ) {

            txtResultado.setText("Você ganhou!! :)");

        } else {

            txtResultado.setText("Você perdeu!! :(");
        }
    }
}