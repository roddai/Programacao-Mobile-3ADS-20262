package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Livro livro1, livro2, livro3;
    TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        livro1 = new Livro("Primeiro Livro", "Primeiro Autor", 2024);
        livro2 = new Livro("Segundo Livro", "Segundo Autor", 2012);
        livro3 = new Livro("Terceiro Livro", "Terceiro Autor", 2017);

        textInfo = findViewById(R.id.textInfo);

        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button1.setOnClickListener(v -> textInfo.setText(livro1.getinfo()));
        button2.setOnClickListener(v -> textInfo.setText(livro2.getinfo()));
        button3.setOnClickListener(v -> textInfo.setText(livro3.getinfo()));


    }
}