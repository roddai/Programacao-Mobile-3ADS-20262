package com.example.aula7;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtResultado;

    ImageView imgMaquina, imgPedra, imgPapel, imgTesoura, imgMulher, imgHomem, imgMulher2, imgInterrogacao1, imgInterrogacao2;

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
        imgMulher = findViewById(R.id.imgMulher);
        imgHomem = findViewById(R.id.imgHomem);
        imgMulher2 = findViewById(R.id.imgMulher2);
        imgInterrogacao1 = findViewById(R.id.imgInterrogacao1);
        imgInterrogacao2 = findViewById(R.id.imgInterrogacao2);


        imgPedra.setOnClickListener(v -> jogar ("pedra"));
        imgPapel.setOnClickListener(v -> jogar ("papel"));
        imgTesoura.setOnClickListener(v -> jogar ("tesoura"));
        imgMulher.setOnClickListener(v -> imgMulher2.setImageResource(R.drawable.usuario_fem));
        imgHomem.setOnClickListener(v -> imgMulher2.setImageResource(R.drawable.usuario_masc));

    }

    public void jogar(String escolhaDoUsuario) {
        String[] opcoes = {"pedra", "papel", "tesoura"};

        int numero = new Random().nextInt(3);
        String escolhaDaMaquina = opcoes[numero];

        switch (escolhaDaMaquina) {
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
