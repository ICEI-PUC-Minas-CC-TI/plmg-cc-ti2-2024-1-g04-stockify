package dao;

import model.Venda;
import model.Receita;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO extends DAO {

    private ReceitaDAO receitaDAO;

    public VendaDAO() {
        super();
        receitaDAO = new ReceitaDAO(); // Inicializa o ReceitaDAO
    }

    public boolean registrarVenda(Venda venda) {
        conectar();
        String sql = "INSERT INTO vendas (nome_prato, quantidade, data_venda) VALUES (?, ?, NOW())";
        boolean sucesso = false;

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, venda.getNomePrato());
            stmt.setInt(2, venda.getQuantidade());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Venda registrada com sucesso.");
                sucesso = true;

                // Após registrar a venda, atualiza o estoque
                sucesso = sucesso && atualizarEstoque(venda.getNomePrato(), venda.getQuantidade());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao registrar a venda: " + e.getMessage());
        } finally {
            close();
        }

        return sucesso;
    }

    public List<Venda> getVendasPendentesPorPrato(String nomePrato) {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT nome_prato, quantidade FROM vendas WHERE nome_prato = ? AND data_venda IS NULL";

        conectar();
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nomePrato);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int quantidade = rs.getInt("quantidade");

                Venda venda = new Venda(nomePrato, quantidade);
                vendas.add(venda);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar vendas pendentes para o prato " + nomePrato + ": " + e.getMessage());
        } finally {
            close();
        }

        return vendas;
    }

    public boolean atualizarEstoque(String nomePrato, int quantidadeVendida) {
        conectar();
        boolean sucesso = true;

        // Obter as receitas (ingredientes e quantidades) do prato vendido
        List<Receita> receitas = receitaDAO.getReceitasPorNome(nomePrato);

        // Atualizar a quantidade de cada ingrediente no estoque
        for (Receita receita : receitas) {
            String nomeIngrediente = receita.getNomeIngrediente();
            int quantidadeIngrediente = receita.getQuantidadeIngrediente();

            // Calcular a quantidade total a ser descontada do estoque para este ingrediente
            int quantidadeDescontar = quantidadeIngrediente * quantidadeVendida;

            // Preparar e executar o SQL para atualizar o estoque deste ingrediente
            String sql = "UPDATE produto SET quantidade = quantidade - ? WHERE nome = ?";

            try {
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setInt(1, quantidadeDescontar);
                stmt.setString(2, nomeIngrediente);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated == 0) {
                    sucesso = false;
                }
            } catch (SQLException e) {
                System.out.println("Erro ao atualizar o estoque para o ingrediente " + nomeIngrediente + ": " + e.getMessage());
                sucesso = false;
            }
        }

        close();
        return sucesso;
    }

    public List<Venda> getAllVendas() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT nome_prato, quantidade, data_venda FROM vendas";

        conectar();
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nomePrato = rs.getString("nome_prato");
                int quantidade = rs.getInt("quantidade");
                java.sql.Timestamp dataVenda = rs.getTimestamp("data_venda");

                Venda venda = new Venda(nomePrato, quantidade);
                venda.setDataVenda(dataVenda);
                vendas.add(venda);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar todas as vendas: " + e.getMessage());
        } finally {
            close();
        }

        return vendas;
    }
}
