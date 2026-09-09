package com.example.aula06_pets;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aula06_pets.Pet.Pet;

public class MainActivity extends AppCompatActivity {

    Pet caes1, caes2, caes3, gato1, gato2, gato3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        caes1 = new Pet("Julio", "pintado", "vira-lata", 6,  "23042007");
        caes2 = new Pet("Gutin", "manchado", "lulu da panamera", 3,  "23/01/2024");
        caes3 = new Pet("Layla", "preto", "Poodle", 1,  "11/09/2003");

        gato1 = new Pet("Felicia", "Mestiço", "Persa", 6,  "23/04/2007");
        gato2 = new Pet("Gutin", "manchado", "lulu da panamera", 3,  "23/01/2024");
        gato3 = new Pet("Layla", "preto", "Poodle", 1,  "11/09/2003");

        getText() = findViewById(R.id.textInfo);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);

        button1.setOnClickListener(v -> textInfo.setText(caes1.getInfo()));
        button2.setOnClickListener(v -> textInfo.setText(caes2.getInfo()));
        button3.setOnClickListener(v -> textInfo.setText(caes3.getInfo()));
        button4.setOnClickListener(v -> textInfo.setText(gato1.getInfo()));
        button5.setOnClickListener(v -> textInfo.setText(gato2.getInfo()));
        button6.setOnClickListener(v -> textInfo.setText(gato3.getInfo()));



    });
    }
