package com.example.aula_07;

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
    ImageView imgPedra, imgPapel, imgTesoura, imgMaquina;

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
        imgPedra = findViewById(R.id.imgPedra);
        imgPapel = findViewById(R.id.imgPapel);
        imgTesoura = findViewById(R.id.imgTesoura);

        imgPedra.setOnClickListener(v -> Jogar("Pedra"));
        imgPapel.setOnClickListener(v -> Jogar("papel"));
        imgTesoura.setOnClickListener(v -> Jogar("tesoura"));

    }

    public void Jogar(String escolhaUser) {
        String[] opcoes ={"pedra", "papel", "tesoura"};

        int num = new Random().nextInt(3);
        String escolhaBot = opcoes[num];

        switch(escolhaBot) {
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

        if (escolhaUser.equals(escolhaBot)){
            txtResultado.setText("Empate");
        } else if ((escolhaUser.equals("pedra") && escolhaBot.equals("tesoura")) ||
                (escolhaUser.equals("papel") && escolhaBot.equals("pedra")) ||
                (escolhaUser.equals("tesoura") && escolhaBot.equals("papel"))) {
            txtResultado.setText("Você Venceu!");
        } else {
            txtResultado.setText("Você Perdeu!");
        }

    }
}