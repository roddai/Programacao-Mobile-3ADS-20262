package com.example.aula6pets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Pet cachorro1, cachorro2, cachorro3;
    Pet gato1, gato2, gato3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cachorro1 = new Pet("Rex", "dourado", "Vira-lata Premium", 2, new Date(124, 8, 12));
        cachorro2 = new Pet("Thor", "Preto", "Vira-lata Premium", 3, new Date(123, 6, 25));
        cachorro3 = new Pet("Luna", "dourado", "Vira-lata Premium", 5, new Date(121, 8, 10));

        gato1 = new Pet("Juju", "preto", "Vira-lata Premium", 2, new Date(124, 8, 12));
        gato2 = new Pet("Raul", "laranja", "Vira-lata Premium", 3, new Date(123, 6, 25));
        gato3 = new Pet("Luis", "branco", "Vira-lata Premium", 5, new Date(121, 8, 10));

        Button btnCachorro1 = findViewById(R.id.btnCachorro1);
        Button btnCachorro2 = findViewById(R.id.btnCachorro2);
        Button btnCachorro3 = findViewById(R.id.btnCachorro3);

        Button btnGato1 = findViewById(R.id.btnGato1);
        Button btnGato2 = findViewById(R.id.btnGato2);
        Button btnGato3 = findViewById(R.id.btnGato3);

        btnCachorro1.setOnClickListener(v -> abrirDetalhes(cachorro1));
        btnCachorro2.setOnClickListener(v -> abrirDetalhes(cachorro2));
        btnCachorro3.setOnClickListener(v -> abrirDetalhes(cachorro3));

        btnGato1.setOnClickListener(v -> abrirDetalhes(gato1));
        btnGato2.setOnClickListener(v -> abrirDetalhes(gato2));
        btnGato3.setOnClickListener(v -> abrirDetalhes(gato3));
    }

    private void abrirDetalhes(Pet pet) {
        Intent intent = new Intent(MainActivity.this, Detalhes.class);

        intent.putExtra("nome", pet.nome);
        intent.putExtra("cor", pet.cor);
        intent.putExtra("raca", pet.raca);
        intent.putExtra("idade", pet.idade);
        intent.putExtra("aniversario", pet.aniversario.toString());

        startActivity(intent);
    }
}