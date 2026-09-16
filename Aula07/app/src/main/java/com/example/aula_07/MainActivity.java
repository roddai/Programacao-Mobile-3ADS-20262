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
    ImageView imgPedra, imgPapel, imgTesoura, imgMaquina, imgUser, imgBotResultado, imgUserResultado, imgPerson1, imgPerson2;

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

        imgBotResultado = findViewById(R.id.imgBotResultado);
        imgUser = findViewById(R.id.imgUser);
        imgUserResultado = findViewById(R.id.imgUserResultado);
        imgPedra = findViewById(R.id.imgPedra);
        imgPapel = findViewById(R.id.imgPapel);
        imgTesoura = findViewById(R.id.imgTesoura);
        imgPerson1 = findViewById(R.id.imgPerson1);
        imgPerson2 = findViewById(R.id.imgPerson2);

        imgPedra.setOnClickListener(v -> Jogar("Pedra"));
        imgPapel.setOnClickListener(v -> Jogar("papel"));
        imgTesoura.setOnClickListener(v -> Jogar("tesoura"));
        imgPerson1.setOnClickListener(v -> imgUser.setImageResource(R.drawable.usuario_fem));
        imgPerson2.setOnClickListener(v -> imgUser.setImageResource(R.drawable.usuario_masc));
    }

    public void Jogar(String escolhaUser) {
        String[] opcoes ={"pedra", "papel", "tesoura"};

        int num = new Random().nextInt(3);
        String escolhaBot = opcoes[num];

        switch(escolhaBot) {
            case "pedra":
                imgBotResultado.setImageResource(R.drawable.pedra);
                break;

            case "papel":
                imgBotResultado.setImageResource(R.drawable.papel);
                break;

            case "tesoura":
                imgBotResultado.setImageResource(R.drawable.tesoura);
                break;
        }

        switch(escolhaUser) {
            case "pedra":
                imgUserResultado.setImageResource(R.drawable.pedra);
                break;

            case "papel":
                imgUserResultado.setImageResource(R.drawable.papel);
                break;

            case "tesoura":
                imgUserResultado.setImageResource(R.drawable.tesoura);
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