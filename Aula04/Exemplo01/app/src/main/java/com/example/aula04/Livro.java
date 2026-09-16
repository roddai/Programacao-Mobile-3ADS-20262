package com.example.aula04;

public class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    public Livro (String titulo, String autor, int anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
// construtor: cria o livro com seus dados
    }
    public String getInfo() {
        return "Titulo" + titulo +"\nAutor" + autor + "\nAno" + anoPublicacao;
// retorna as informações do livro
    }

}
