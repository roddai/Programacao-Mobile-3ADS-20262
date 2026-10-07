package com.example.aula_06;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pet extends AppCompatActivity {
    public String nome;

    private String cor;

    private String raca;

    private int idade;

    private String niver;


    public Pet(String nome, String cor, String raca, int idade, String niver){
        this.nome = nome;
        this.cor =  cor;
        this.raca = raca;
        this.idade = idade;
        this.niver =  niver;
    }

    public String getInfo() {
        return "nome: " + nome + "\ncor: " + cor + "\nraca: " + raca + "\nidade: " + idade + "\nniver: " + niver;
    }
}





