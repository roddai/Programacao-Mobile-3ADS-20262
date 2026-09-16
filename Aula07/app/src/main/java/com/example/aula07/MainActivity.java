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

    ImageView imageMaquina, imagePedra, imagePapel, imageTesoura;

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

        imagePedra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String escolhaDoUsuario = "pedra";
                jogar(escolhaDoUsuario);
            }});

        imagePapel.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                String escolhaDoUsuario = "papel";
                jogar(escolhaDoUsuario);
            }});

        imageTesoura.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                imageMaquina.setImageResource(R.drawable.pedra);
                break;
            case "papel":
                imageMaquina.setImageResource(R.drawable.papel);
                break;
            case "tesoura":
                imageMaquina.setImageResource(R.drawable.tesoura);
                break;
        }

        if (escolhaDoUsuario.equals(escolhaDaMaquina)){
            resultado.setText("Você Empateou!");
        }else if(escolhaDoUsuario.equals("pedra") && escolhaDaMaquina.equals("papel") || escolhaDoUsuario.equals("papel") && escolhaDaMaquina.equals("tesoura") || escolhaDoUsuario.equals("tesoura") && escolhaDaMaquina.equals("pedra")){
            resultado.setText("Você Perdeu!!!");
        }else{
            resultado.setText("Você Ganhou!!!");
        }
    }


}