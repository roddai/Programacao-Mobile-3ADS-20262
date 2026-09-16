package com.example.aula07_jokempo;

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


    TextView txt_resultado;
    ImageView imgMaquina;
    ImageView imgPedra;
    ImageView imgPapel;
    ImageView imgTesoura;
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

        txt_resultado = findViewById(R.id.txt_resultado);
        imgMaquina = findViewById(R.id.img_maquina);
        imgPapel = findViewById(R.id.img_papel);
        imgTesoura = findViewById(R.id.img_tesoura);
        imgPedra = findViewById(R.id.img_pedra);

        imgPedra.setOnClickListener(v -> jogar("pedra"));
        imgPapel.setOnClickListener(v -> jogar("papel"));
        imgTesoura.setOnClickListener(v -> jogar("tesoura"));

    }

    public void jogar(String escolhaUsuario){
        String[] opcoes = {"pedra", "papel", "tesoura"};

        int numero = new Random().nextInt( 3);
        String escolhaMaquina = opcoes[numero];


        switch(escolhaMaquina) {
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

        if(escolhaUsuario.equals(escolhaMaquina)){
            txt_resultado.setText("Empate!!!");

        } else if((escolhaUsuario.equals("pedra") && escolhaMaquina.equals("tesoura")) ||
                (escolhaUsuario.equals("papel") && escolhaMaquina.equals("pedra") ||
                (escolhaUsuario.equals("tesoura") && escolhaMaquina.equals("papel")))) {
            txt_resultado.setText("Você ganhou!!!");
        } else {
            txt_resultado.setText("Você perdeu!!!");


        }

        }
    }
