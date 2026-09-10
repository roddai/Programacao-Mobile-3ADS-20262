package com.example.projetoaula06;

import androidx.appcompat.app.AppCompatActivity;

public class Pet {
    private String nome;

    private String cor;

    private int idade;

    private String raca;

    private String aniversario;

    public Pet(String nome, String cor, int idade, String raca, String aniversario){
        this.nome = nome;
        this.cor = cor;
        this.idade = idade;
        this.raca = raca;
        this.aniversario = aniversario;

    }

    public String getInfo(){
        return "Nome: " + nome + "\nCor:" + cor + "\nRaça:" + raca + "\nIdade: " + idade + "\nAniversário: " + aniversario;
    }

}
