package com.example.aula06_pet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Pet cachorro1 = new Pet("Rex", "Dourado", "Golden", "3 anos", "11/05");
        Pet cachorro2 = new Pet("Thor", "Preto", "Pastor Alemão", "2 anos", "29/05");
        Pet cachorro3 = new Pet("Luna", "Preto", "Salsicha", "1 ano", "10/05");
        Pet gato1 = new Pet("Joao", "Cinza", "Vira Lata", "4 anos", "15/05");
        Pet gato2 = new Pet("Marcos", "Mesclado", "Vira Lata", "5 anos", "14/05");
        Pet gato3 = new Pet("Cleber", "Laranja", "Vira Lata", "6 anos", "13/05");

        configurarBotao(R.id.button, cachorro1);
        configurarBotao(R.id.button2, cachorro2);
        configurarBotao(R.id.button3, cachorro3);
        configurarBotao(R.id.button4, gato1);
        configurarBotao(R.id.button5, gato2);
        configurarBotao(R.id.button6, gato3);
    }

    private void configurarBotao(int idBotao, final Pet pet) {
        Button btn = findViewById(idBotao);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Detalhes.class);
                intent.putExtra("nome", pet.nome);
                intent.putExtra("raca", pet.raca);
                intent.putExtra("cor", pet.cor);
                intent.putExtra("idade", pet.idade);
                intent.putExtra("aniversario", pet.aniversario);
                startActivity(intent);
            }
        });
    }
}