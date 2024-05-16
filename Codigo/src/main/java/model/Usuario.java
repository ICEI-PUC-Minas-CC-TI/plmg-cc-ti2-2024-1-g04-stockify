package model;

public class Usuario {
    private int id;
    private String email;
    private String senha;
    private String username;
    private double salario;
    private String cpf;
    private int idade;

    public Usuario(int id, String username, String email, String senha, double salario, String cpf, int idade) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.salario = salario;
        this.cpf = cpf;
        this.idade = idade;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return this.username;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
