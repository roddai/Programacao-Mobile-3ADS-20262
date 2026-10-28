package com.example.aula06_pets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Pet cao1, cao2, cao3, gato1, gato2, gato3;
    TextView textInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        /* Instancias */
        cao1 = new Pet("Max", "Preto e Branco", "Boder Colllie", 4, "01/04/2022");
        cao2 = new Pet("Thor", "Marrom e branco", "Bagle", 2, "07/11/2023");
        cao3 = new Pet("Amora", "Branco e Marrom", "Shih Tzu", 4, "10/04/2026");

        gato1 = new Pet("Marcelo", "Preto e Marrom", "Siames", 4, "01/04/2022");
        gato2 = new Pet("Tigresa", "Marrom e branco Zebra", "Exotico", 2, "07/11/2023");
        gato3 = new Pet("Bartholomeu", "Branco e preto", "Persa", 4, "10/04/2026");

        Button button1 = findViewById(R.id.button1);


        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent irParaSegundaTela = new Intent(MainActivity.this, DetalhesPetActivity.class);
                irParaSegundaTela.putExtra("nomePet", cao1.getInfo());
                startActivity(irParaSegundaTela);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent irParaSegundaTela = new Intent(MainActivity.this, DetalhesPetActivity.class);
                irParaSegundaTela.putExtra("nomePet", cao2.getInfo());
                startActivity(irParaSegundaTela);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent irParaSegundaTela = new Intent(MainActivity.this, DetalhesPetActivity.class);
                irParaSegundaTela.putExtra("nomePet", cao3.getInfo());
                startActivity(irParaSegundaTela);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent irParaSegundaTela = new Intent(MainActivity.this, DetalhesPetActivity.class);
                irParaSegundaTela.putExtra("nomePet", gato1.getInfo());
                startActivity(irParaSegundaTela);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent irParaSegundaTela = new Intent(MainActivity.this, DetalhesPetActivity.class);
                irParaSegundaTela.putExtra("nomePet", gato2.getInfo());
                startActivity(irParaSegundaTela);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent irParaSegundaTela = new Intent(MainActivity.this, DetalhesPetActivity.class);
                irParaSegundaTela.putExtra("nomePet", gato3.getInfo());
                startActivity(irParaSegundaTela);
            }
        });
    }
}