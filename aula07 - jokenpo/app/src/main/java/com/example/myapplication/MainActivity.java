package com.example.myapplication;

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
    ImageView imgMaquina, imgInterrogacao1, imgUsuario, imgInterrogacao2, imgPedra, imgPapel, imgTesoura, imgPersonagem1, imgPersonagem2;
    int personagemEscolhido = R.drawable.usuario_masc;

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

        txtResultado = findViewById(R.id.txtResultado);

        imgMaquina = findViewById(R.id.imgMaquina);
        imgInterrogacao1 = findViewById(R.id.imgInterrogacao1);
        imgUsuario = findViewById(R.id.imgUsuario);
        imgInterrogacao2 = findViewById(R.id.imgInterrogacao2);
        imgPedra = findViewById(R.id.imgPedra);
        imgPapel = findViewById(R.id.imgPapel);
        imgTesoura = findViewById(R.id.imgTesoura);
        imgPersonagem1 = findViewById(R.id.imgPersonagem1);
        imgPersonagem2 = findViewById(R.id.imgPersonagem2);

        imgUsuario.setImageResource(personagemEscolhido);

        imgPedra.setOnClickListener(v -> jogar("pedra"));
        imgPapel.setOnClickListener(v -> jogar("papel"));
        imgTesoura.setOnClickListener(v -> jogar("tesoura"));

        imgPersonagem1.setOnClickListener(v -> {
            personagemEscolhido = R.drawable.usuario_fem;
            imgUsuario.setImageResource(personagemEscolhido);
        });

        imgPersonagem2.setOnClickListener(v -> {
            personagemEscolhido = R.drawable.usuario_masc;
            imgUsuario.setImageResource(personagemEscolhido);
        });
    }

    public void jogar(String escolheOUsuario) {
        String[] opcoes = {"pedra", "papel", "tesoura"};

        int numero = new Random().nextInt(3);
        String escolhaMaquina = opcoes[numero];

        switch (escolhaMaquina) {
            case "pedra":
                imgInterrogacao1.setImageResource(R.drawable.pedra);
                break;
            case "papel":
                imgInterrogacao1.setImageResource(R.drawable.papel);
                break;
            case "tesoura":
                imgInterrogacao1.setImageResource(R.drawable.tesoura);
                break;
        }

        switch (escolheOUsuario) {
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

        if (escolheOUsuario.equals(escolhaMaquina)) {
            txtResultado.setText(R.string.resultado_empate);
        } else if ((escolheOUsuario.equals("pedra") && escolhaMaquina.equals("tesoura")) ||
                (escolheOUsuario.equals("papel") && escolhaMaquina.equals("pedra")) ||
                (escolheOUsuario.equals("tesoura") && escolhaMaquina.equals("papel"))) {
            txtResultado.setText(R.string.resultado_ganhou);
        } else {
            txtResultado.setText(R.string.resultado_perdeu);
        }
    }
}
