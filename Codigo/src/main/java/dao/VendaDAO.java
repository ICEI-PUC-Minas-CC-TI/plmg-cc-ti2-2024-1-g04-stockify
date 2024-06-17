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
        String sql = "INSERT INTO vendas (nome_prato, quantidade, data_venda) VALUES (?, ?, ?)";
        boolean sucesso = false;
    
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, venda.getNomePrato());
            stmt.setInt(2, venda.getQuantidade());
            stmt.setTimestamp(3, venda.getDataVenda());
    
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

    public boolean atualizarEstoque(String nomePrato, int quantidadeVendida) {
        conectar();
        boolean sucesso = true;
    
        // Obter as receitas (ingredientes e quantidades) do prato vendido
        List<Receita> receitas = getReceitasPorNome(nomePrato);
    
        // Debug: Exibir receitas obtidas para verificação
        System.out.println("Receitas obtidas:");
        for (Receita receita : receitas) {
            System.out.println(receita.getNomeIngrediente() + " - " + receita.getQuantidadeIngrediente());
        }
    
        // Atualizar a quantidade de cada ingrediente no estoque
        for (Receita receita : receitas) {
            String nomeIngrediente = receita.getNomeIngrediente();
            int quantidadeIngrediente = receita.getQuantidadeIngrediente();
    
            // Calcular a quantidade total a ser descontada do estoque para este ingrediente
            int quantidadeDescontar = quantidadeIngrediente * quantidadeVendida;
    
            // Debug: Exibir informações antes de executar o SQL
            
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
            
            System.out.println("Nome Ingrediente: " + nomeIngrediente);
            System.out.println("Quantidade a descontar: " + quantidadeDescontar);
        }
    
        close();
        return sucesso;
    }    
    
    private List<Receita> getReceitasPorNome(String nomePrato) {
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
        }

        return receitas;
    }
}
