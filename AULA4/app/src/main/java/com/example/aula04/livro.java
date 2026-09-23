package com.example.aula04;

public class livro {
    private String titulo;

    private String autor;

    private int anoPublicacao;

    public livro(String titulo, String autor, int anoPublicacao){
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;

    }

    public String getInfo() {
        return "Titulo: " + titulo + "\nAutor:"+ autor + "\nAno" + anoPublicacao;
    }
}
