package br.com.eventosflorianpolis2020.modelo;

import java.io.Serializable;

public class Locais implements Serializable {

    private int id;
    private String descricao;
    private String bairro;
    private String cidade;
    private int capacidade;

    public Locais(int id, String descricao, String bairro, String cidade, int capacidade) {
        this.id = id;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricaoe(String descricao) {
        this.descricao = descricao;
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

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return "Nome: " + descricao +
                "\nBairro: " + bairro +
                "\nCidade: " + cidade +
                "\nCapacidade: " + capacidade;
    }
}
