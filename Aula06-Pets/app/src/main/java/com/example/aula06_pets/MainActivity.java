package com.example.aula06_pets;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Caes cao1, cao2, cao3;
    Gatos gato1, gato2, gato3;
    TextView textInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        /* Instancias */
        cao1 = new Caes("Max", "Preto e Branco", "Boder Colllie", 4, "01/04/2022");
        cao2 = new Caes("Thor", "Marrom e branco", "Bagle", 2, "07/11/2023");
        cao3 = new Caes("Amora", "Branco e Marrom", "Shih Tzu", 4, "10/04/2026");

        gato1 = new Gatos("Marcelo", "Preto e Marrom", "Siames", 4, "01/04/2022");
        gato2 = new Gatos("Tigresa", "Marrom e branco Zebra", "Exotico", 2, "07/11/2023");
        gato3 = new Gatos("Bartholomeu", "Branco e preto", "Persa", 4, "10/04/2026");

        textInfo = findViewById(R.id.textInfo);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);


        button1.setOnClickListener(v -> textInfo.setText(cao1.getInfo()));
        button2.setOnClickListener(v -> textInfo.setText(cao2.getInfo()));
        button3.setOnClickListener(v -> textInfo.setText(cao3.getInfo()));
        button4.setOnClickListener(v -> textInfo.setText(gato1.getInfo()));
        button5.setOnClickListener(v -> textInfo.setText(gato2.getInfo()));
        button6.setOnClickListener(v -> textInfo.setText(gato3.getInfo()));
    }
}