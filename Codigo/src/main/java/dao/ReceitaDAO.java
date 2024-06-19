package dao;

import model.Receita;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDAO extends DAO {

    public ReceitaDAO() {
        super();
    }

    public boolean inserirReceita(Receita receita) {
        conectar();
        boolean sucesso = false;

        // Verificar se há estoque suficiente para inserir a nova receita
        boolean estoqueSuficiente = verificarEstoqueSuficienteParaInserir(receita);
        if (!estoqueSuficiente) {
            System.out.println("Erro: Estoque insuficiente para inserir a receita.");
            return false;
        }

        String sql = "INSERT INTO receitas (nome, ingrediente, quantidade) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, receita.getNomePrato());
            stmt.setString(2, receita.getNomeIngrediente());
            stmt.setInt(3, receita.getQuantidadeIngrediente());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A nova receita foi inserida com sucesso.");
                sucesso = true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir a receita: " + e.getMessage());
        } finally {
            close();
        }

        return sucesso;
    }

    public List<Receita> getAll() {
        conectar();
        List<Receita> receitas = new ArrayList<>();
        String sql = "SELECT nome, ingrediente, quantidade FROM receitas";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nomePrato = rs.getString("nome");
                String nomeIngrediente = rs.getString("ingrediente");
                int quantidadeIngrediente = rs.getInt("quantidade");

                Receita receita = new Receita(nomePrato, nomeIngrediente, quantidadeIngrediente);
                receitas.add(receita);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar as receitas: " + e.getMessage());
        } finally {
            close();
        }

        return receitas;
    }

    public Receita getById(int id) {
        conectar();
        Receita receita = null;
        String sql = "SELECT nome, ingrediente, quantidade FROM receitas WHERE id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nomePrato = rs.getString("nome");
                String nomeIngrediente = rs.getString("ingrediente");
                int quantidadeIngrediente = rs.getInt("quantidade");

                receita = new Receita(nomePrato, nomeIngrediente, quantidadeIngrediente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar a receita: " + e.getMessage());
        } finally {
            close();
        }

        return receita;
    }

    public boolean atualizarReceita(Receita receita, int id) {
        conectar();
        boolean sucesso = false;

        // Verificar se há estoque suficiente para atualizar a receita
        boolean estoqueSuficiente = verificarEstoqueSuficienteParaAtualizar(receita, id);
        if (!estoqueSuficiente) {
            System.out.println("Erro: Estoque insuficiente para atualizar a receita.");
            return false;
        }

        String sql = "UPDATE receitas SET nome = ?, ingrediente = ?, quantidade = ? WHERE id = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, receita.getNomePrato());
            stmt.setString(2, receita.getNomeIngrediente());
            stmt.setInt(3, receita.getQuantidadeIngrediente());
            stmt.setInt(4, id);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("A receita foi atualizada com sucesso.");
                sucesso = true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a receita: " + e.getMessage());
        } finally {
            close();
        }

        return sucesso;
    }

    public boolean excluirReceita(int id) {
        conectar();
        String sql = "DELETE FROM receitas WHERE id = ?";
        boolean sucesso = false;

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A receita foi excluída com sucesso.");
                sucesso = true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir a receita: " + e.getMessage());
        } finally {
            close();
        }

        return sucesso;
    }

    public boolean excluirReceitasPorNome(String nomePrato) {
        conectar();
        String sql = "DELETE FROM receitas WHERE nome = ?";
        boolean sucesso = false;

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nomePrato);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("As receitas do prato foram excluídas com sucesso.");
                sucesso = true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir as receitas do prato: " + e.getMessage());
        } finally {
            close();
        }

        return sucesso;
    }

    private boolean verificarEstoqueSuficienteParaInserir(Receita receita) {
        int quantidadeNecessaria = receita.getQuantidadeIngrediente();
        int estoqueAtual = consultarEstoque(receita.getNomeIngrediente());

        return estoqueAtual >= quantidadeNecessaria;
    }

    private boolean verificarEstoqueSuficienteParaAtualizar(Receita receita, int id) {
        int quantidadeNecessaria = receita.getQuantidadeIngrediente();
        int estoqueAtual = consultarEstoque(receita.getNomeIngrediente(), id);

        return estoqueAtual >= quantidadeNecessaria;
    }

    private int consultarEstoque(String nomeProduto) {
        int estoqueAtual = 0;
        String sql = "SELECT quantidade FROM produto WHERE nome = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nomeProduto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                estoqueAtual = rs.getInt("quantidade");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar o estoque: " + e.getMessage());
        }

        return estoqueAtual;
    }

    private int consultarEstoque(String nomeProduto, int id) {
        int estoqueAtual = 0;
        String sql = "SELECT quantidade FROM produto WHERE nome = ? AND id != ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nomeProduto);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                estoqueAtual = rs.getInt("quantidade");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar o estoque: " + e.getMessage());
        }

        return estoqueAtual;
    }

    public List<Receita> getReceitasPorNome(String nomePrato) {
        conectar();
        List<Receita> receitas = new ArrayList<>();
        String sql = "SELECT nome, ingrediente, quantidade FROM receitas WHERE nome = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nomePrato);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                String nomeIngrediente = rs.getString("ingrediente");
                int quantidade = rs.getInt("quantidade");

                Receita receita = new Receita(nome, nomeIngrediente, quantidade);
                receitas.add(receita);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar as receitas pelo nome do prato: " + e.getMessage());
        } finally {
            close();
        }

        return receitas;
    }
}
