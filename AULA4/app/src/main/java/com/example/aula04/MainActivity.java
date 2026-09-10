package com.example.aula04;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    livro Livro1, Livro2, Livro3;

    TextView textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Livro1 = new livro("Primeiro Livro", "Primeiro Autor", 2026);
        Livro2 = new livro("Segundo Livro", "Segundo Autor", 2020);
        Livro3 = new livro("Terceiro livro", "Terceiro Autor", 2020);

       textInfo = findViewById(R.id.textInfo);

       Button button1 = findViewById(R.id.button);
       Button button2 = findViewById(R.id.button2);
       Button button3 = findViewById(R.id.button3);

       button1.setOnClickListener(  v -> textInfo.setText(Livro1.getInfo()));
       button2.setOnClickListener(  v -> textInfo.setText(Livro2.getInfo()));
       button3.setOnClickListener( v -> textInfo.setText(Livro3.getInfo()));

    }
    }
