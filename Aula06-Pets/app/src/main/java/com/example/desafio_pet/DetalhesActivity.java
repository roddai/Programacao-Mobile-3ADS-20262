package com.example.desafio_pet;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalhesActivity extends AppCompatActivity {

    private ImageView imgPet;

    private TextView txtNome;
    private TextView txtCor;
    private TextView txtRaca;
    private TextView txtIdade;
    private TextView txtAniversario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhesactivity);

        imgPet = findViewById(R.id.imgPet);

        txtNome = findViewById(R.id.txtNome);
        txtCor = findViewById(R.id.txtCor);
        txtRaca = findViewById(R.id.txtRaca);
        txtIdade = findViewById(R.id.txtIdade);
        txtAniversario = findViewById(R.id.txtAniversario);


        String nome = getIntent().getStringExtra("nome");
        String cor = getIntent().getStringExtra("cor");
        String raca = getIntent().getStringExtra("raca");
        String idade = getIntent().getStringExtra("idade");
        String aniversario = getIntent().getStringExtra("aniversario");

        int imagem = getIntent().getIntExtra("imagem", 0);


        txtNome.setText("Nome: " + nome);
        txtCor.setText("Cor: " + cor);
        txtRaca.setText("Raça: " + raca);
        txtIdade.setText("Idade: " + idade);
        txtAniversario.setText("Aniversário: " + aniversario);

        imgPet.setImageResource(imagem);
    }
}