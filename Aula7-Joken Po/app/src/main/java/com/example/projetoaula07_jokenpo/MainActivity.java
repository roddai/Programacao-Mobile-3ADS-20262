package com.example.projetoaula07_jokenpo;

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
    ImageView imgPlayer, jogadaPlayer, jogadaMaquina,imgPedra,imgPapel,imgTesoura,
            imgHomi, imgMule;

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

        imgPlayer = findViewById(R.id.imagePlayer);
        jogadaPlayer = findViewById(R.id.escolhaPlayer);
        jogadaMaquina = findViewById(R.id.escolhaMaquina);
        imgPedra = findViewById(R.id.imagePedra);
        imgPapel = findViewById(R.id.imagePapel);
        imgTesoura = findViewById(R.id.imageTesoura);

        imgHomi = findViewById(R.id.imageHomi);
        imgMule = findViewById(R.id.imageMulher);

        imgHomi.setOnClickListener(V -> imgPlayer.setImageResource(R.drawable.usuario_masc));
        imgMule.setOnClickListener(V -> imgPlayer.setImageResource(R.drawable.usuario_fem));

        imgPedra.setOnClickListener(v -> jogar("pedra"));
        imgPapel.setOnClickListener(v -> jogar("papel"));
        imgTesoura.setOnClickListener(v -> jogar("tesoura"));
    }

    public void jogar(String escolhaPlayer)
    {
        String[] opcoes = {"pedra","papel","tesoura"};

        int r = new Random().nextInt(3);
        String escolhaMaquina = opcoes[r];

        switch (escolhaMaquina)
        {
            case "pedra": jogadaMaquina.setImageResource(R.drawable.pedra);
                break;
            case "papel": jogadaMaquina.setImageResource(R.drawable.papel);
                break;
            case "tesoura": jogadaMaquina.setImageResource(R.drawable.tesoura);
                break;
        }

        switch (escolhaPlayer)
        {
            case "pedra": jogadaPlayer.setImageResource(R.drawable.pedra);
                break;
            case "papel": jogadaPlayer.setImageResource(R.drawable.papel);
                break;
            case "tesoura": jogadaPlayer.setImageResource(R.drawable.tesoura);
                break;
        }

        if (escolhaPlayer.equals(escolhaMaquina)) {
            txtResultado.setText("Empate, bah que pena");
        } else if ((escolhaPlayer.equals("pedra") && escolhaMaquina.equals("tesoura")) ||
                    (escolhaPlayer.equals("papel") && escolhaMaquina.equals("pedra")) ||
                    (escolhaPlayer.equals("tesoura") && escolhaMaquina.equals("papel"))){
            txtResultado.setText("Você ganhou, yepiii!!!");
        } else {
            txtResultado.setText("Você perdeu, otário!!!");
        }
    }
}