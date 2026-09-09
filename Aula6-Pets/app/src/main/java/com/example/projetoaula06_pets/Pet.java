package com.example.projetoaula06_pets;

public class Pet {
    private String nome;
    private String cor;
    private String raca;
    private int idade;
    private String niver;

    public Pet(String nome,String cor,String raca, int idade, String niver)
    {
        this.nome = nome;
        this.cor = cor;
        this.raca = raca;
        this.idade = idade;
        this.niver = niver;
    }

    public String getInfo()
    {
        return "HELL YEAH";
    }
}


