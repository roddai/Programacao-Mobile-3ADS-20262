package com.example.aula07jokempo;

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

    TextView textResultado;
    ImageView imgMaquina, imgPapel, imgPedra, imgTesoura;

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

        textResultado = findViewById(R.id.txtResultado);
        imgMaquina = findViewById(R.id.imgMaquina);
        imgPedra = findViewById(R.id.imgPedra);
        imgPapel = findViewById(R.id.imgPapel);
        imgTesoura = findViewById(R.id.imgTesoura);

        imgTesoura.setOnClickListener(v -> jogar("tesoura"));
        imgPapel.setOnClickListener(v -> jogar("papel"));
        imgPedra.setOnClickListener(v -> jogar("pedra"));
    }


    public void jogar (String escolhadDousuario) {
        String[] opcoes = {"pedra", "papel", "tesoura"};

        int numero = new Random().nextInt(3);

        String escolhaDaMquina = opcoes [numero]; // ele vai ver em opções o que tem lá e ver o numero que tem no random. 0,1 ou 2.

        switch (escolhaDaMquina) {
            case "pedra":
                imgMaquina.setImageResource(R.drawable.pedra);
                break;

            case "papel":
                imgMaquina.setImageResource(R.drawable.papel);
                break;

            case "tesoura":
                imgMaquina.setImageResource(R.drawable.tesoura);
                break;

        }

        if (escolhadDousuario.equals(escolhaDaMquina)) {
            textResultado.setText("Empate!");
        } else if ((escolhadDousuario.equals("pedra") && escolhaDaMquina.equals("tesoura")) ||
                (escolhadDousuario.equals("papel") && escolhaDaMquina.equals("pedra")) ||
                ((escolhadDousuario.equals("tesoura") && escolhaDaMquina.equals("papel")))) {
            textResultado.setText("Você Ganhou");
        } else {
            textResultado.setText("Você Perdeu");

        }
    }
}
