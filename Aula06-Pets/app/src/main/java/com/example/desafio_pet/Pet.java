package com.example.desafio_pet;

public class Pet {
    private String nome;
    private String cor;
    private String raca;
    private String idade;
    private String aniversario;
    private int imagem;
    public Pet(String nome, String cor, String raca, String idade, String aniversario, int imagem) {

        this.nome = nome;
        this.cor = cor;
        this.raca = raca;
        this.idade = idade;
        this.aniversario = aniversario;
        this.imagem = imagem;
    }
    public String getNome() {
        return nome;
    }
    public String getCor() {
        return cor;
    }
    public String getRaca() {
        return raca;
    }
    public String getIdade() {
        return idade;
    }
    public String getAniversario() {
        return aniversario;
    }
    public int getImagem() {
        return imagem;
    }
}