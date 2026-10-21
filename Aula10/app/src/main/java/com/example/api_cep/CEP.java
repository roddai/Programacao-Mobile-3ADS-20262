package com.example.api_cep;

public class CEP {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    // Getters e Setters adicionais se necessário
    public String getLocalidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }
}