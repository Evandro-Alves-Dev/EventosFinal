package br.com.eventosflorianpolis2020.modelo;

import java.io.Serializable;

public class Locais implements Serializable {

    private int id = 0;
    private String nome;
    private String bairro;
    private String cidade;
    private String capacidade;

    public Locais(int id, String nome, String bairro, String cidade, String capacidade) {
        this.id = id;
        this.nome = nome;
        this.bairro = bairro;
        this.cidade = cidade;
        this.capacidade = capacidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(String capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return nome + bairro + cidade + capacidade;
    }
}
