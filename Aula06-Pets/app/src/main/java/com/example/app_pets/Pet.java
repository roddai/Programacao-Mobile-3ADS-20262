package com.example.app_pets;

import java.util.Date;

public class Pet {
    private String nome;
    private String cor;
    private String raca;
    private int idade;
    private String aniversario;

    public Pet(String nome,String cor, String raca, int idade, String aniversario) {
        this.nome= nome;
        this.cor = cor;
        this.raca = raca;
        this.idade = idade;
        this.aniversario = aniversario;

    }
    public String getInfo() {
        return "Nome: " + nome +"\nCor: " + cor + "\nRaça: " + raca + "\nIdade: " + idade + "\nAniversário: " + aniversario;
    }
}
