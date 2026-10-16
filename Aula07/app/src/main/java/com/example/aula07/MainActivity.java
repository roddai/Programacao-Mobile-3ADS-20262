package com.example.aula07;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView resultado;

    ImageView imageMaquina, imagePedra, imagePapel, imageTesoura, imagePlayer1, imagePlayer2, imagePlayer, imageEscPlayer, imageEscMaquina;

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

        resultado = findViewById(R.id.resultado);

        imageMaquina = findViewById(R.id.imageMaquina);
        imagePedra = findViewById(R.id.imagePedra);
        imagePapel = findViewById(R.id.imagePapel);
        imageTesoura = findViewById(R.id.imageTesoura);
        imagePlayer1 = findViewById(R.id.imagePlayer1);
        imagePlayer2 = findViewById(R.id.imagePlayer2);
        imageEscPlayer = findViewById(R.id.imageEscPlayer);
        imagePlayer = findViewById(R.id.imagePlayer);
        imageEscMaquina = findViewById(R.id.imageEscMaquina);

        imagePlayer1.setOnClickListener(v-> imagePlayer.setImageResource(R.drawable.usuario_fem));
        imagePlayer2.setOnClickListener(v-> imagePlayer.setImageResource(R.drawable.usuario_masc));

        imagePedra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageEscPlayer.setImageResource(R.drawable.pedra);
                String escolhaDoUsuario = "pedra";
                jogar(escolhaDoUsuario);
            }});

        imagePapel.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                imageEscPlayer.setImageResource(R.drawable.papel);
                String escolhaDoUsuario = "papel";
                jogar(escolhaDoUsuario);
            }});

        imageTesoura.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageEscPlayer.setImageResource(R.drawable.tesoura);
                    String escolhaDoUsuario = "tesoura";
                    jogar(escolhaDoUsuario);
                }});
    }

    public void jogar(String escolhaDoUsuario){
        String[] opcoes = {"pedra","papel","tesoura"};

        int num = new Random().nextInt(3);
        String escolhaDaMaquina = opcoes[num];

        switch (escolhaDaMaquina){
            case "pedra":
                imageEscMaquina.setImageResource(R.drawable.pedra);
                break;
            case "papel":
                imageEscMaquina.setImageResource(R.drawable.papel);
                break;
            case "tesoura":
                imageEscMaquina.setImageResource(R.drawable.tesoura);
                break;
        }

        if (escolhaDoUsuario.equals(escolhaDaMaquina)){
            resultado.setText("Você Empatou!");
        }else if(escolhaDoUsuario.equals("pedra") && escolhaDaMaquina.equals("papel") || escolhaDoUsuario.equals("papel") && escolhaDaMaquina.equals("tesoura") || escolhaDoUsuario.equals("tesoura") && escolhaDaMaquina.equals("pedra")){
            resultado.setText("Você Perdeu!!!");
        }else{
            resultado.setText("Você Ganhou!!!");
        }
    }


}