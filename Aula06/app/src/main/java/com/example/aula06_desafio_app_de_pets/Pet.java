package com.example.aula06_desafio_app_de_pets;

import java.util.Date;

import java.io.Serializable;

public class Pet implements Serializable {
    private String nome;
    private String cor;
    private String raca;
    private int idade;
    private String aniversario;
    private int imagemId;

    public Pet(String nome, String cor, String raca, int idade, String aniversario, int imagemId) {
        this.nome = nome;
        this.cor = cor;
        this.raca = raca;
        this.idade = idade;
        this.aniversario = aniversario;
        this.imagemId = imagemId;}

    public String getNome() { return nome; }
    public String getCor() { return cor; }
    public String getRaca() { return raca; }
    public int getIdade() { return idade; }
    public String getAniversario() { return aniversario; }
    public int getImagemId() { return imagemId; }
    }

