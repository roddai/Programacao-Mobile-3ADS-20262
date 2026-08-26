package com.example.projetoaula04_ex1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        /* Instancias */
        livro1 = new Livro("Desenvolvendo meu primeiro aplicativo mobile", "Ana castela", 2026);
        livro2 = new Livro("Desenvolvendo meu segundo aplicativo mobile", "Gustavo mioto", 2025);
        livro3 = new Livro("Desenvolvendo meu terceiro aplicativo mobile", "Henrique e Juliano", 2024);

        textInfo = findViewById(R.id.textInfo);

        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button1.setOnClickListener(v -> textInfo.setText(livro1.getInfo()));
        button2.setOnClickListener(v -> textInfo.setText(livro2.getInfo()));
        button3.setOnClickListener(v -> textInfo.setText(livro3.getInfo()));
    }
}