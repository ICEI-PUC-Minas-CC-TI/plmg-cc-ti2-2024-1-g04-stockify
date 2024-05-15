package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Fornecedor;

public class FornecedorDAO extends DAO {

    public FornecedorDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public FornecedorDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para inserir um novo fornecedor no banco de dados
    public boolean inserir(Fornecedor fornecedor) {
        try {
            if (conexao == null) {
                throw new SQLException("Objeto de conexão não foi inicializado corretamente.");
            }
            String sql = "INSERT INTO fornecedor (nome, endereco, celular, categoria) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getEndereco());
            stmt.setString(3, fornecedor.getCelular());
            stmt.setString(4, fornecedor.getCategoria());
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para buscar um fornecedor pelo ID
    public Fornecedor buscarPorId(int id) {
        try {
            String sql = "SELECT * FROM fornecedor WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Fornecedor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("celular"),
                    rs.getString("categoria")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para buscar todos os fornecedores
    public List<Fornecedor> buscarTodos() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        try {
            String sql = "SELECT * FROM fornecedor";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("endereco"),
                    rs.getString("celular"),
                    rs.getString("categoria")
                );
                fornecedores.add(fornecedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fornecedores;
    }

    // Método para atualizar um fornecedor
    public boolean atualizar(Fornecedor fornecedor) {
        try {
            String sql = "UPDATE fornecedor SET nome = ?, endereco = ?, celular = ?, categoria = ? WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getEndereco());
            stmt.setString(3, fornecedor.getCelular());
            stmt.setString(4, fornecedor.getCategoria());
            stmt.setInt(5, fornecedor.getId());
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para excluir um fornecedor
    public boolean excluir(int id) {
        try {
            String sql = "DELETE FROM fornecedor WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
