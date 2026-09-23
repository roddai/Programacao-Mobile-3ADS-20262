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

    ImageView imgMaquina, imgPedra, imgPapel, imgTesoura, imgMulher, imgHomem, imgPersonagem, imgInterrogacao1, imgInterrogacao2;

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

        txtResultado = findViewById(R.id.resultado);

        imgMaquina = findViewById(R.id.logoMaquina);
        imgPedra = findViewById(R.id.ImgPedra);
        imgPapel = findViewById(R.id.ImgPapel);
        imgTesoura = findViewById(R.id.ImgTesoura);
        imgMulher = findViewById(R.id.logousuarioF);
        imgHomem = findViewById(R.id.logousuarioM);
        imgPersonagem = findViewById(R.id.ImageUsuario);
        imgInterrogacao1 = findViewById(R.id.ImageMaquina);
        imgInterrogacao2 = findViewById(R.id.interrogacao);

        imgPedra.setOnClickListener(v -> jogar("pedra"));
        imgPapel.setOnClickListener(v -> jogar("papel"));
        imgTesoura.setOnClickListener(v -> jogar("tesoura"));

        imgMulher.setOnClickListener(v -> escolherPersonagem(R.drawable.usuario_fem));
        imgHomem.setOnClickListener(v -> escolherPersonagem(R.drawable.usuario_masc));
    }

    public void escolherPersonagem(int imagemDoPersonagem) {
        imgPersonagem.setImageResource(imagemDoPersonagem);
    }

    public int imagemDoSimbolo(String simbolo) {
        switch (simbolo) {
            case "pedra":
                return R.drawable.pedra;
            case "papel":
                return R.drawable.papel;
            case "tesoura":
                return R.drawable.tesoura;
        }
        return R.drawable.interrogacao;
    }

    public void jogar(String escolhaDoUsuario) {
        String[] opcoes = {"pedra", "papel", "tesoura"};

        int numero = new Random().nextInt(3);
        String escolhaDaMaquina = opcoes[numero];

        imgInterrogacao1.setImageResource(imagemDoSimbolo(escolhaDaMaquina));
        imgInterrogacao2.setImageResource(imagemDoSimbolo(escolhaDoUsuario));

        if (escolhaDoUsuario.equals(escolhaDaMaquina)) {
            txtResultado.setText("Empate");
        } else if ((escolhaDoUsuario.equals("pedra") && escolhaDaMaquina.equals("tesoura")) ||
                (escolhaDoUsuario.equals("papel") && escolhaDaMaquina.equals("pedra")) ||
                (escolhaDoUsuario.equals("tesoura") && escolhaDaMaquina.equals("papel"))) {
            txtResultado.setText("Você ganhou!!!");
        } else {
            txtResultado.setText("Você perdeu!!!");
        }
    }
}