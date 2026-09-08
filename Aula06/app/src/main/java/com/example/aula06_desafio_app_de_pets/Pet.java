package com.example.aula06_desafio_app_de_pets;

import java.util.Date;

public class Pet {
    private String nome;
    private String cor;
    private String raca;
    private int idade;
    private String aniversario;

    public Pet (String nome, String cor, String raca, int idade, String aniversário ) {
        this.nome = nome;
        this.cor = cor;
        this.raca = raca;
        this.idade = idade;
        this.aniversario = aniversario;
    }
    public String getInfo() {
        return "Nome" + nome +"\nCor" + cor + "\nRaca" + raca + "\nIdade" + idade + "\nAniversario" + aniversario;
    }
}
