package com.example.aula10;

public class CEP {
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private String ddd;
    private boolean erro;

    public String getLogradouro() {
        return logradouro;
    }

    public boolean isErro() {
        return erro;
    }

    @Override
    public String toString() {
        if (erro) return "CEP não encontrado.";
        
        StringBuilder sb = new StringBuilder();
        if (logradouro != null && !logradouro.isEmpty()) sb.append("Logradouro: ").append(logradouro).append("\n");
        if (complemento != null && !complemento.isEmpty()) sb.append("Complemento: ").append(complemento).append("\n");
        if (bairro != null && !bairro.isEmpty()) sb.append("Bairro: ").append(bairro).append("\n");
        if (localidade != null && !localidade.isEmpty()) sb.append("Localidade: ").append(localidade).append("\n");
        if (uf != null && !uf.isEmpty()) sb.append("UF: ").append(uf).append("\n");
        if (estado != null && !estado.isEmpty()) sb.append("Estado: ").append(estado).append("\n");
        if (regiao != null && !regiao.isEmpty()) sb.append("Região: ").append(regiao).append("\n");
        if (ddd != null && !ddd.isEmpty()) sb.append("DDD: ").append(ddd);
        
        return sb.toString();
    }
}