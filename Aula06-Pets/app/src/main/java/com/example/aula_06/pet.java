package com.example.aula_06;

public class pet {
    public String nome;

    private String cor;

    private String raca;

    private int idade;

    private String niver;


    public pet(String nome, String cor, String raca, int idade, String niver){
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