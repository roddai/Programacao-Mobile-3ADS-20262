package com.example.myapplication;

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
    ImageView imgPedra;
    ImageView imgPapel;
    ImageView imgTesoura;

    ImageView imgEscolhaCPU;
    ImageView imguser;
    ImageView imgEscolhaUser;
    ImageView imgusermasc;
    ImageView imguserfem;

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
        imgPedra = findViewById(R.id.imgPedra);
        imgPapel = findViewById(R.id.imgPapel);
        imgTesoura = findViewById(R.id.imgTesoura);
        imgEscolhaCPU = findViewById(R.id.imgEscolhaCPU);
        imguser = findViewById(R.id.imguser);
        imgEscolhaUser = findViewById(R.id.imgEscolhaUser);
        imgusermasc = findViewById(R.id.imgusermasc);
        imguserfem = findViewById(R.id.imguserfem);

        imgPedra.setOnClickListener(v -> jogar("Pedra"));
        imgPapel.setOnClickListener(v -> jogar("Papel"));
        imgTesoura.setOnClickListener(v -> jogar("Tesoura"));
        imgusermasc.setOnClickListener(v -> imguser.setImageResource(R.drawable.usuario_masc));
        imguserfem.setOnClickListener(v -> imguser.setImageResource(R.drawable.usuario_fem));


    }

    public void jogar(String EscolhaUser) {
        String[] opcoes = {"Pedra", "Papel", "Tesoura"};

        int numero = new Random().nextInt(3);
        String EscolhaCPU = opcoes[numero];

        if (EscolhaUser.equals("masc")){
            imguser.setImageResource(R.drawable.usuario_masc);
        } else if ((EscolhaUser.equals("fem"))){
            imguser.setImageResource(R.drawable.usuario_fem);
        }

        switch (EscolhaCPU){
            case "Pedra":imgEscolhaCPU.setImageResource(R.drawable.pedra);
            break;
            case "Papel":imgEscolhaCPU.setImageResource(R.drawable.papel);
            break;
            case "Tesoura":imgEscolhaCPU.setImageResource(R.drawable.tesoura);
            break;
        }

        switch (EscolhaUser){
            case "Pedra":imgEscolhaUser.setImageResource(R.drawable.pedra);
            break;
            case "Papel":imgEscolhaUser.setImageResource(R.drawable.papel);
            break;
            case "Tesoura":imgEscolhaUser.setImageResource(R.drawable.tesoura);
            break;
        }

        if (EscolhaUser.equals(EscolhaCPU)){
            txtResultado.setText("Empate!");
        } else if ((EscolhaUser == "Pedra" && EscolhaCPU == "Tesoura")) {
            txtResultado.setText("Vitória!");
        } else if ((EscolhaUser == "Papel" && EscolhaCPU == "Pedra")) {
            txtResultado.setText("Vitória!");
        } else if ((EscolhaUser == "Tesoura" && EscolhaCPU == "Papel")) {
            txtResultado.setText("Vitória!");
        } else if ((EscolhaUser == "Pedra" && EscolhaCPU == "Papel")) {
            txtResultado.setText("Derrota!");
        } else if ((EscolhaUser == "Papel" && EscolhaCPU == "Tesoura")) {
            txtResultado.setText("Derrota!");
        } else if ((EscolhaUser == "Tesoura" && EscolhaCPU == "Pedra")) {
            txtResultado.setText("Derrota!");
        }
    }
}