package dao;

import model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date; // Importe Date
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDAO extends DAO {

    public ProdutoDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public List<Produto> getAll() {
        List<Produto> produtos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM produto";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Itera sobre o resultado e adiciona os produtos à lista
            while (rs.next()) {
                String nome = rs.getString("nome");
                String categoria = rs.getString("categoria");
                int quantidade = rs.getInt("quantidade");
                String fornecedor = rs.getString("fornecedor");
                String lote = rs.getString("lote");
                String dataValidade = rs.getString("datavalidade");

                // Cria um objeto Produto com os dados do banco de dados
                Produto produto = new Produto(nome, categoria, quantidade, fornecedor, lote, dataValidade);
                produto.setId(rs.getInt("id")); // Atribui o ID retornado ao objeto Produto
                produtos.add(produto);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    public boolean insert(Produto produto) {
        boolean status = false;
        try {
            String sql = "INSERT INTO produto (nome, categoria, quantidade, fornecedor, lote, datavalidade) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conexao.prepareStatement(sql);

            st.setString(1, produto.getNome());
            st.setString(2, produto.getCategoria());
            st.setInt(3, produto.getQuantidade());
            st.setString(4, produto.getFornecedor());
            st.setString(5, produto.getLote());
            st.setDate(6, Date.valueOf(produto.getDatavalidade()));

            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public Optional<Produto> get(int id) {
        Produto produto = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM produto WHERE id=" + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String nome = rs.getString("nome");
                String categoria = rs.getString("categoria");
                int quantidade = rs.getInt("quantidade");
                String fornecedor = rs.getString("fornecedor");
                String lote = rs.getString("lote");
                String dataValidade = rs.getString("datavalidade");

                // Cria um objeto Produto com os dados do banco de dados
                produto = new Produto(nome, categoria, quantidade, fornecedor, lote, dataValidade);
                produto.setId(rs.getInt("id")); // Atribui o ID retornado ao objeto Produto
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Optional.ofNullable(produto);
    }

    public List<Produto> getOrderByID() {
        return get("id");
    }

    public List<Produto> getOrderByNome() {
        return get("nome");
    }

    public List<Produto> getOrderByCategoria() {
        return get("categoria");
    }

    private List<Produto> get(String orderBy) {
        List<Produto> produtos = new ArrayList<>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM produto" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Produto p = new Produto(rs.getString("nome"), rs.getString("categoria"),
                        rs.getInt("quantidade"), rs.getString("fornecedor"), rs.getString("lote"),
                        rs.getString("datavalidade"));
                p.setId(rs.getInt("id")); // Atribui o ID retornado ao objeto Produto
                produtos.add(p);
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return produtos;
    }

    public boolean update(Produto produto) {
        boolean status = false;
        try {
            String sql = "UPDATE produto SET nome = ?, categoria = ?, quantidade = ?, fornecedor = ?, lote = ?, "
                    + "datavalidade = ? WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, produto.getNome());
            st.setString(2, produto.getCategoria());
            st.setInt(3, produto.getQuantidade());
            st.setString(4, produto.getFornecedor());
            st.setString(5, produto.getLote());
            st.setDate(6, Date.valueOf(produto.getDatavalidade()));
            st.setInt(7, produto.getId());
            st.executeUpdate();
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM produto WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public List<String> getAllFornecedores() {
        List<String> fornecedores = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT fornecedor FROM produto"; // Consulta para obter fornecedores distintos
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            // Itera sobre o resultado e adiciona os fornecedores à lista
            while (rs.next()) {
                String fornecedor = rs.getString("fornecedor");
                fornecedores.add(fornecedor);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fornecedores;
    }
}
