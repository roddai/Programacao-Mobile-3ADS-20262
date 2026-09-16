package com.example.aula07;

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
    ImageView imgMaquina, imgPedra, imgPapel, imgTesoura;

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
        }
    public void jogar(String escolhaDoUsuario){
        String[] opcoes = {"pedra", "papel", "tesoura"};

        int numero = new Random().nextInt(3);
        String escolhaMaquina = opcoes[numero];

        switch (){
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

        if (escolhaDoUsuario.equals(escolhaMaquina)){
            txtResultado.setText("Empate");
        } else if ((escolhaDoUsuario.equals("pedra") && escolhaMaquina.equals("tesoura")) ||
                (escolhaDoUsuario.equals("papel") && escolhaMaquina.equals("pedra")) ||
                (escolhaDoUsuario.equals("tesoura") && escolhaMaquina.equals("papel"))) {
            txtResultado.setText("Você ganhou!! :)");
        } else{
            txtResultado.setText("Você perdeu!! :(");
        }

    }
}