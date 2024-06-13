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
        String sql = "INSERT INTO receitas (nome, ingrediente, quantidade) VALUES (?, ?, ?)";
        boolean sucesso = false;

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
        String sql = "UPDATE receitas SET nome = ?, ingrediente = ?, quantidade = ? WHERE id = ?";
        boolean sucesso = false;

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
}
