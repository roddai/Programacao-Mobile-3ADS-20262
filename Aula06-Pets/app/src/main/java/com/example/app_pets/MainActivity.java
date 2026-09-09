package com.example.app_pets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Pet dog1, dog2, dog3, cat1, cat2, cat3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dog1 = new Pet("Lucas","Laranja", "Caramelo", 12, "12 de julho");
        dog2 = new Pet("Thor","Marrom e preto", "Pastor Alemão", 6, "14 de maio");
        dog3 = new Pet("Diana","Laranja", "Pitbull", 5, "15 de março");
        cat1 = new Pet("Cleopatra","Branco", "Siamês", 3, "10 de janeiro");
        cat2 = new Pet("Garfield","Preto", "Persa", 4, "20 de fevereiro");
        cat3 = new Pet("Tigresa","Cinza", "Maine Coon", 2, "5 de abril");




        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);

        button1.setOnClickListener(v -> {
            Intent irParaDetalhes = new Intent(MainActivity.this, Detalhes.class);
            irParaDetalhes.putExtra("infoPet", dog1.getInfo());
            startActivity(irParaDetalhes);
        });
        button2.setOnClickListener(v -> {
            Intent irParaDetalhes = new Intent(MainActivity.this, Detalhes.class);
            irParaDetalhes.putExtra("infoPet", dog2.getInfo());
            startActivity(irParaDetalhes);
        });
        button3.setOnClickListener(v -> {
            Intent irParaDetalhes = new Intent(MainActivity.this, Detalhes.class);
            irParaDetalhes.putExtra("infoPet", dog3.getInfo());
            startActivity(irParaDetalhes);

        });

        button4.setOnClickListener(v -> {

            Intent irParaDetalhes = new Intent(MainActivity.this, Detalhes.class);
            irParaDetalhes.putExtra("infoPet", cat1.getInfo());
            startActivity(irParaDetalhes);
        });
        button5.setOnClickListener(v -> {
            Intent irParaDetalhes = new Intent(MainActivity.this, Detalhes.class);
            irParaDetalhes.putExtra("infoPet", cat2.getInfo());
            startActivity(irParaDetalhes);
        });

        button6.setOnClickListener(v -> {
            Intent irParaDetalhes = new Intent(MainActivity.this, Detalhes.class);
            irParaDetalhes.putExtra("infoPet", cat3.getInfo());
            startActivity(irParaDetalhes);

        });


    }
}