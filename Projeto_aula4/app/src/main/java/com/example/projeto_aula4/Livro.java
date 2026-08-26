package com.example.projeto_aula4;

public class Livro {

    private String titulo;

    private String autor;

    private int anoPublicacao;

    public Livro(String titulo, String autor, int anoPublicacao ){
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
    }

    public String getinfo() {
        return "Titulo: " + titulo +"\nAutor: " + autor +"\nAno: " + anoPublicacao;
    }
}
