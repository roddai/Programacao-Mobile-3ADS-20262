package com.example.projeto_aula4;

import android.os.Bundle;
import android.view.View
import  android.widget.Button;

import androidx.activity.enableEdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity : AppCompatActivity() {

    Livro livro1, livro2, livro3;

    TextView textInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        livro1 =  new Livro("Primeiro livro", "primeiro autor", "2020" );
        livro2 =  new Livro("Segundo livro", "Segundo  autor", "2020" );
        livro3 =  new Livro("Terceiro livro", "terceiro autor", "2020" );

        textInfo = findViewById (R.id.textView)

        Button button1 = findViewById (R.id.button);
        Button button2 = findViewById (R.id.button2);
        Button button3 = findViewById (R.id.button3);

        button.setOnClickListener (View v -> textInfo.setText(livro1.setInfo()) );
        button2.setOnClickListener (View v -> textInfo.setText(livro2.setInfo()) );
        button3.setOnClickListener (View v -> textInfo.setText(livro3.setInfo()) );


        }

    }
