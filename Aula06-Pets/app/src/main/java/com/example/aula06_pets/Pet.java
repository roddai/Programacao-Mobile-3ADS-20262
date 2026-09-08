package com.example.aula06_pets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Pet extends AppCompatActivity{

    TextView cor, raca, idade, niver;
    Button detalhes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cor = findViewById(R.id.cor);
        raca = findViewById(R.id.raca);
        idade = findViewById(R.id.idade);
        niver = findviewById(R.id.niver);
        detalhes = findViewById(R.id.detalhes);

        detalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pet.this, activity_detalhes.class);
                startActivity(intent);
            }
        });
    }
}

