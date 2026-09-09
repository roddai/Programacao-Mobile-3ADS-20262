package com.example.aula04exemplo01;

public class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;

    Livro(String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
    }

    public String getInfo() {
        return "Titulo: " + titulo + "\nAutor: " + autor + "\nAno: " + anoPublicacao;
    }
}