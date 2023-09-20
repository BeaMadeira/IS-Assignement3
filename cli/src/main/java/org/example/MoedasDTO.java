package org.example;


public class MoedasDTO {
    private static final long serialVersionUID = 1L;

    private int idMoeda;


    private String nome;
    private Double exchangeRate;

    public MoedasDTO(String nome, Double exchangeRate) {
        this.nome = nome;
        this.exchangeRate = exchangeRate;
    }

    public MoedasDTO() {
    }

    public int getIdMoeda() {
        return idMoeda;
    }

    public void setIdMoeda(int idMoeda) {
        this.idMoeda = idMoeda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}