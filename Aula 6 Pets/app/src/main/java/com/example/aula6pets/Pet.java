package com.example.aula6pets;

import java.util.Date;

public class Pet {

    public String nome;
    public String cor;
    public String raca;

    public int idade;
    public Date aniversario;

    public Pet(String nome, String cor, String raca, int idade, Date aniversario) {

        this.nome = nome;
        this.cor = cor;
        this.raca = raca;
        this.idade = idade;
        this.aniversario = aniversario;
    }

    public String getInfo() {
        return "Nome: " + nome + "\nCor: " + cor + "\nRaça: " + raca + "\nIdade: " + idade + "\nAniversário: " + aniversario;
    }
}