package com.example.aula06_pets;

public class Pet {
    private String nome;
    private String cor;
    private String raca;
    private int idade;
    private String data;

    public Pet(String nome, String cor, String raca, int idade, String data){
        this.nome = nome;
        this.cor = cor;
        this.raca = raca;
        this.idade = idade;
        this.data = data;
    }
    public String getInfo(){
        return "Nome: " + nome + "\nCor: " + cor + "\nRaça: " + raca + "\nIdade: " + idade + "\nData: " + data;
    }
}
