package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;

public class UsuarioDAO extends DAO{
    public UsuarioDAO(){
        super();
        conectar();
    }

    public void finalize(){
        close();
    }

    public Usuario autenticarUsuario(String email, String senha) {
        Usuario usuario = null;
        try {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, senha);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                usuario = new Usuario(rs.getInt("id"), rs.getString("email"), rs.getString("password"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuário", e);
        }
        return usuario;
    }
    
    
}
