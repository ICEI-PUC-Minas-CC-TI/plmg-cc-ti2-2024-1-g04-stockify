package model;

public class Fornecedor {
    private int id;
    private String nome;
    private String endereco;
    private String celular;
    private String categoria;

    public Fornecedor() {
        this(0, "", "", "", "");
    }

    public Fornecedor(int id, String nome, String endereco, String celular, String categoria) {
        super();
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.celular = celular;
        this.categoria = categoria;
    }

    // Getters and Setters

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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
