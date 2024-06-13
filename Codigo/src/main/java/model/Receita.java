package model;

public class Receita {
    private String nomePrato;
    private String nomeIngrediente;
    private int quantidadeIngrediente;

    public Receita(String nomePrato, String nomeIngrediente, int quantidadeIngrediente) {
        this.nomePrato = nomePrato;
        this.nomeIngrediente = nomeIngrediente;
        this.quantidadeIngrediente = quantidadeIngrediente;
    }

    public String getNomePrato() {
        return nomePrato;
    }

    public void setNomePrato(String nomePrato) {
        this.nomePrato = nomePrato;
    }

    public String getNomeIngrediente() {
        return nomeIngrediente;
    }

    public void setNomeIngrediente(String nomeIngrediente) {
        this.nomeIngrediente = nomeIngrediente;
    }

    public int getQuantidadeIngrediente() {
        return quantidadeIngrediente;
    }

    public void setQuantidadeIngrediente(int quantidadeIngrediente) {
        this.quantidadeIngrediente = quantidadeIngrediente;
    }
}
