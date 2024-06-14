package dao;

import model.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO extends DAO {

    public EventoDAO() {
        super();
        conectar();
    }

    public void finalize() {
        close();
    }

    public EventoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Método para inserir um novo evento no banco de dados
    public boolean inserir(Evento evento) {
        try {
            if (conexao == null) {
                throw new SQLException("Conexão com o banco de dados não inicializada corretamente.");
            }
            String sql = "INSERT INTO evento (data, nome) VALUES (?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(evento.getData()));
            stmt.setString(2, evento.getNomeEvento());
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para buscar um evento pelo ID
    public Evento buscarPorId(int id) {
        try {
            String sql = "SELECT * FROM evento WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Evento(
                        rs.getInt("id"),
                        rs.getDate("data").toLocalDate(),
                        rs.getString("nome")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para buscar todos os eventos
    public List<Evento> buscarTodos() {
        List<Evento> eventos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM evento";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Evento evento = new Evento(
                        rs.getInt("id"),
                        rs.getDate("data").toLocalDate(),
                        rs.getString("nome")
                );
                eventos.add(evento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    // Método para atualizar um evento
    public boolean atualizar(Evento evento) {
        try {
            String sql = "UPDATE evento SET data = ?, nome = ? WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(evento.getData()));
            stmt.setString(2, evento.getNomeEvento());
            stmt.setInt(3, evento.getId());
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para excluir um evento
    public boolean excluir(int id) {
        try {
            String sql = "DELETE FROM evento WHERE id = ?";
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