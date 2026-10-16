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

    TextView textResultado, txtEscolhaSimbolo, txtEscolhaPersonagem;
    ImageView imgMaquina, imgPapel, imgPedra, imgTesoura, imgInterrogacao, img_usuario_fem, imgInterrogacao2, img_usuario_fem2, img_usuario_masc;

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
        txtEscolhaSimbolo = findViewById(R.id.txtEscolhaSimbolo);
        txtEscolhaPersonagem = findViewById(R.id.txtEscolhaPersonagem);

        imgMaquina = findViewById(R.id.imgMaquina); // ou seja estamos atrelando, depois de criar as variaveis la em cima
        imgPedra = findViewById(R.id.imgPedra); // vamos agora atrelar as variaveis com os elementos que temos no activity_main
        imgPapel = findViewById(R.id.imgPapel);
        imgTesoura = findViewById(R.id.imgTesoura);
        imgInterrogacao = findViewById(R.id.imgInterrogacao);
        img_usuario_fem = findViewById(R.id.img_usuario_fem);
        imgInterrogacao2 = findViewById(R.id.imgInterrogacao2);
        img_usuario_fem2 = findViewById(R.id.img_usuario_fem2);
        img_usuario_masc = findViewById(R.id.img_usuario_masc);

        imgTesoura.setOnClickListener(v -> jogar("tesoura"));
        imgPapel.setOnClickListener(v -> jogar("papel"));
        imgPedra.setOnClickListener(v -> jogar("pedra"));

        img_usuario_fem2.setOnClickListener(v -> img_usuario_fem.setImageResource(R.drawable.usuario_fem));
        img_usuario_masc.setOnClickListener(v -> img_usuario_fem.setImageResource(R.drawable.usuario_masc));
    }

    // DEPOIS DE ATRELAR PRECISAMOS QUE O CODIGO TENHA QUAL A ESCOLHA DA MAQUINA E QUAL A NOSSA ESCOLHA

    public void jogar (String escolhadDousuario) {
        String[] opcoes = {"pedra", "papel", "tesoura"};

        int numero = new Random().nextInt(3);
        // void é que ele nao vai levar nada dentro dela, apenas processar algo. no paremetro () ele precisa receber a escolha do usuário
        // para criar a lsta array/vetor String []
        // int numero = new Random().nextInt(3) é o que vai criar a escolha randomica.
        // nextInt(3) o 3 significa que ele vai escolher entre os 3 elementos entro do array/vetor. ou seja, 3 pulos.

        String escolhaDaMaquina = opcoes [numero]; // ele vai ver em opções o que tem lá e ver o numero que tem no random. 0,1 ou 2.

        switch (escolhaDaMaquina) {
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
        switch (escolhadDousuario) {
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

        if (escolhadDousuario.equals(escolhaDaMaquina)) {
            textResultado.setText("Empate!");
        } else if ((escolhadDousuario.equals("pedra") && escolhaDaMaquina.equals("tesoura")) ||
                (escolhadDousuario.equals("papel") && escolhaDaMaquina.equals("pedra")) ||
                ((escolhadDousuario.equals("tesoura") && escolhaDaMaquina.equals("papel")))) {
            textResultado.setText("Você Ganhou");
        } else {
            textResultado.setText("Você Perdeu");

        }
    }
}
// cria as variaveis e depois os elementos e depois dinamicas
// dinamicas as 4 imagens (maquina + pedra papel e tesoura e o txt do resultado que vai mudar
