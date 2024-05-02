package model;

import java.time.LocalDate;

public class Produto {
    private int id;
    private String nome;
    private String categoria;
    private int quantidade;
    private String fornecedor;
    private String lote;
    private LocalDate dataValidade;

    public Produto() {
        id = -1;
        nome = "";
        categoria = "";
        quantidade = 0;
        fornecedor = "";
        lote = "";
        dataValidade = LocalDate.now().plusMonths(6); // validade padrão de 6 meses
    }

    public Produto(String nome, String categoria, int quantidade, String fornecedor, String lote, LocalDate dataValidade) {
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.fornecedor = fornecedor;
        this.lote = lote;
        this.dataValidade = dataValidade;
    }

    // Métodos getters e setters

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    // Outros métodos

    @Override
    public String toString() {
        return "Produto: " + nome + "   Categoria: " + categoria + "   Quantidade: " + quantidade +
                "   Fornecedor: " + fornecedor + "   Lote: " + lote + "   Data de Validade: " + dataValidade;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId() == ((Produto) obj).getId());
    }
}
