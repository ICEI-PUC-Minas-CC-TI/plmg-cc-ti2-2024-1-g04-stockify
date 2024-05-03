package model;

public class Produto {
    private int id;
    private String nome;
    private String categoria;
    private int quantidade;
    private String fornecedor;
    private String lote;
    private String datavalidade;

    public Produto() {
        id = 0;
        nome = "";
        categoria = "";
        quantidade = 0;
        fornecedor = "";
        lote = "";
        datavalidade = ""; // validade padrão de 6 meses
    }

    public Produto(String nome, String categoria, int quantidade, String fornecedor, String lote, String datavalidade) {
        this.nome = nome;
        this.categoria = categoria;
        this.quantidade = quantidade;
        this.fornecedor = fornecedor;
        this.lote = lote;
        this.datavalidade = datavalidade;
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

    public String getDatavalidade() {
        return datavalidade;
    }

    public void setDatavalidade(String datavalidade) {
        this.datavalidade = datavalidade;
    }

    // Outros métodos

    @Override
    public String toString() {
        return "Produto: " + nome + "   Categoria: " + categoria + "   Quantidade: " + quantidade +
                "   Fornecedor: " + fornecedor + "   Lote: " + lote + "   Data de Validade: " + datavalidade;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getId() == ((Produto) obj).getId());
    }
}
