package model;

import java.sql.Timestamp;

public class Venda {
    private String nomePrato;
    private int quantidade;
    private Timestamp dataVenda;

    public Venda(String nomePrato, int quantidade) {
        this.nomePrato = nomePrato;
        this.quantidade = quantidade;
        this.dataVenda = new Timestamp(System.currentTimeMillis());
    }

    // Getters e Setters
    public String getNomePrato() {
        return nomePrato;
    }

    public void setNomePrato(String nomePrato) {
        this.nomePrato = nomePrato;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Timestamp getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Timestamp dataVenda) {
        this.dataVenda = dataVenda;
    }
}
