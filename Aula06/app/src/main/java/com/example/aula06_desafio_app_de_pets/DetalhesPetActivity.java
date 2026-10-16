package com.example.aula06_desafio_app_de_pets;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesPetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        String nome = getIntent().getStringExtra("nome");
        String cor = getIntent().getStringExtra("cor");
        String raca = getIntent().getStringExtra("raca");
        int idade = getIntent().getIntExtra("idade", 0);
        String aniversario = getIntent().getStringExtra("aniversario");

        TextView txtNome = findViewById(R.id.txtNome);
        TextView txtRaca = findViewById(R.id.txtRaca);
        TextView txtCor = findViewById(R.id.txtCor);
        TextView txtIdade = findViewById(R.id.txtIdade);
        TextView txtAniversario = findViewById(R.id.txtAniversario);

        txtNome.setText(nome);
        txtRaca.setText("Raça: " + raca);
        txtCor.setText("Cor: " + cor);
        txtIdade.setText("Idade: " + idade + " anos");
        txtAniversario.setText("Aniversário: " + aniversario);
    }
}
