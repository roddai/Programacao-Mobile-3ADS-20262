package com.example.aula07_jokenpo;

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
    ImageView imgInterrogacao, imgInterrogacao2, imgPedra, imgPapel, imgTesoura, imgPersona, imgFeminina, imgMasculina;

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

        imgInterrogacao = findViewById(R.id.imgInterrogacao);
        imgInterrogacao2 = findViewById(R.id.imgInterrogacao2);
        imgPersona = findViewById(R.id.imgPersona);
        imgPapel = findViewById(R.id.imgPapel);
        imgPedra = findViewById(R.id.imgPedra);
        imgTesoura = findViewById(R.id.imgTesoura);
        imgFeminina = findViewById(R.id.imgFeminina);
        imgMasculina = findViewById(R.id.imgMasculina);


        imgPedra.setOnClickListener(v -> jogar("pedra"));
        imgPapel.setOnClickListener(v -> jogar("papel"));
        imgTesoura.setOnClickListener(v -> jogar("tesoura"));
        imgFeminina.setOnClickListener(v -> imgPersona.setImageResource(R.drawable.usuario_fem));
        imgMasculina.setOnClickListener(v -> imgPersona.setImageResource(R.drawable.usuario_masc));
    }

    public void jogar(String escolhaDoUsuario){
        String[] opcoes = {"pedra", "papel", "tesoura"};

        int numero = new Random().nextInt(3);
        String escolhaDaMaquina = opcoes[numero];

        switch (escolhaDaMaquina) {
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
        if (escolhaDoUsuario.equals("pedra")) {
            imgInterrogacao2.setImageResource(R.drawable.pedra);
        } else if (escolhaDoUsuario.equals("papel")) {
            imgInterrogacao2.setImageResource(R.drawable.papel);
        } else if (escolhaDoUsuario.equals("tesoura")) {
            imgInterrogacao2.setImageResource(R.drawable.tesoura);
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