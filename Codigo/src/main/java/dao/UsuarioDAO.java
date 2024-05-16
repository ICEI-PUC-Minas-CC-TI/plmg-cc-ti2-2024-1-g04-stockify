package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioDAO extends DAO {
    public UsuarioDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    // Método para buscar um usuário por ID
    public Usuario buscarUsuarioPorId(int id) {
        Usuario usuario = null;
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("senha"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID", e);
        }
        return usuario;
    }

    // Método para buscar um usuário por email
    public Usuario buscarUsuarioPorEmail(String email) {
        Usuario usuario = null;
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("senha"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por email", e);
        }
        return usuario;
    }

    // Método para salvar um usuário
    public void salvarUsuario(Usuario usuario) {
        try {
            String sql = "INSERT INTO users (username, email, senha) VALUES (?, ?, ?)";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, usuario.getusername());
            st.setString(2, usuario.getEmail());
            st.setString(3, usuario.getSenha());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    // Método para atualizar um usuário
    public void atualizarUsuario(Usuario usuario) {
        try {
            String sql = "UPDATE users SET email = ?, senha = ? WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, usuario.getEmail());
            st.setString(2, usuario.getSenha());
            st.setInt(3, usuario.getId());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário", e);
        }
    }

    // Método para excluir um usuário
    public void excluirUsuario(int id) {
        try {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário", e);
        }
    }

    // Método para autenticar um usuário
    public Usuario autenticarUsuario(String email) {
        Usuario usuario = null;
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("senha"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
        return usuario;
    }

    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("senha"));
                usuarios.add(usuario);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter todos os usuários", e);
        }
        return usuarios;
    }
    // Método para buscar um usuário por ID
    public Usuario getById(int id) {
        Usuario usuario = null;
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("senha"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID", e);
        }
        return usuario;
    }
}
