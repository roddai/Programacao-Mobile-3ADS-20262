package com.example.aula07jokenpo;

import android.media.Image;
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

        textResultado = findViewById(R.id.txtResultado);   // AQUI PEGA O OUTRO TEXT QUE CIE NO ACTIVITY
        imgMaquina = findViewById(R.id.imgMaquina); // ou seja estamos atrelando, depois de criar as variaveis la em cima
        imgPedra = findViewById(R.id.imgPedra); // vamos agora atrelar as variaveis com os elementos que temos no activity_main
        imgPapel = findViewById(R.id.imgPapel);
        imgTesoura = findViewById(R.id.imgTesoura);

        imgTesoura.setOnClickListener(v -> jogar("tesoura"));
        imgPapel.setOnClickListener(v -> jogar("papel"));
        imgPedra.setOnClickListener(v -> jogar("pedra"));
    }

    // DEPOIS DE ATRELAR PRECISAMOS QUE O CODIGO TENHA QUAL A ESCOLHA DA MAQUINA E QUAL A NOSSA ESCOLHA

    public void jogar (String escolhadDousuario) {
        String[] opcoes = {"pedra", "papel", "tesoura"};

        int numero = new Random().nextInt(3);
        // void é que ele nao vai levar nada dentro dela, apenas processar algo. no paremetro () ele precisa receber a escolha do usuário
        // para criar a lsta array/vetor String []
        // int numero = new Random().nextInt(3) é o que vai criar a escolha randomica.
        // nextInt(3) o 3 significa que ele vai escolher entre os 3 elementos entro do array/vetor. ou seja, 3 pulos.

        String escolhaDaMquina = opcoes [numero]; // ele vai ver em opções o que tem lá e ver o numero que tem no random. 0,1 ou 2.

        switch (escolhaDaMquina) {
            case "pedra":
                imgMaquina.setImageResource(R.drawable.pedra); // o Set é para mudar a imagem
                break; // sempre que usamos um switch case devemos colocar o break

            case "papel":
                imgMaquina.setImageResource(R.drawable.papel); // o Set é para mudar a imagem
                break; // sempre que usamos um switch case devemos colocar o break

            case "tesoura":
                imgMaquina.setImageResource(R.drawable.tesoura); // o Set é para mudar a imagem
                break; // sempre que usamos um switch case devemos colocar o break

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
// cria as variaveis e depois os elementos e depois dinamicas
// dinamicas as 4 imagens (maquina + pedra papel e tesoura e o txt do resultado que vai mudar
