package model;

public class Usuario {
    private int id;
    private String email;
    private String senha;
    private String username;

    public Usuario(int id, String username, String email, String senha) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = senha;
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
        this.email = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getusername() {
        return this.username;
    }
}

