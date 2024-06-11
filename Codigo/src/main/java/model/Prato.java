package model;

import java.util.ArrayList;
import java.util.List;

public class Prato {
    private int id;
    private String nome;
    private List<String> ingredientes;
    private List<Integer> quantidades;

    public Prato(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.ingredientes = new ArrayList<>();
        this.quantidades = new ArrayList<>();
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

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<Integer> getQuantidade() {
        return quantidades;
    }

    public void setQuantidade(List<Integer> quantidades) {
        this.quantidades = quantidades;
    }

    public void adicionarIngrediente(String ingrediente, int quantidade) {
        this.ingredientes.add(ingrediente);
        this.quantidades.add(quantidade);
    }

    public void removerIngrediente(String ingrediente) {
        int index = ingredientes.indexOf(ingrediente);
        if (index != -1) {
            ingredientes.remove(index);
            quantidades.remove(index);
        }
    }

    public void setQuantidades(int[] quantidadesArray) {
        this.quantidades.clear();
        for (int quantidade : quantidadesArray) {
            this.quantidades.add(quantidade);
        }
    }

    public void exibirInformacoes() {
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Ingredientes:");
        for (int i = 0; i < ingredientes.size(); i++) {
            System.out.println(ingredientes.get(i) + ": " + quantidades.get(i));
        }
    }
}
