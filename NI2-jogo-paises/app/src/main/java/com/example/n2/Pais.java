package com.example.n2;

// Um país do jogo, com os dados que vêm da REST Countries
public class Pais {

    private final String nome;
    private final String urlBandeira;
    private final String capital;

    public Pais(String nome, String urlBandeira, String capital) {
        this.nome = nome;
        this.urlBandeira = urlBandeira;
        this.capital = capital;
    }

    public String getNome() {
        return nome;
    }

    public String getUrlBandeira() {
        return urlBandeira;
    }

    public String getCapital() {
        return capital;
    }
}
