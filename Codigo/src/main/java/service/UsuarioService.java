package service;

import com.google.gson.Gson;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Método para criar um usuário
    public Object criarUsuario(Request request, Response response) {
        Gson gson = new Gson();
        Usuario usuario = gson.fromJson(request.body(), Usuario.class);

        // Imprimir os valores dos parâmetros email e password
        System.out.println("Email recebido: " + usuario.getEmail());
        System.out.println("Senha recebida: " + usuario.getSenha()); // olhar senha
        System.out.println("Nome recebido: " + usuario.getusername()); // olhar senha

        // Verificar se o usuário já existe
        Usuario usuarioExistente = usuarioDAO.buscarUsuarioPorEmail(usuario.getEmail());
        if (usuarioExistente != null) {
            response.status(400); // Bad Request
            return "Já existe um usuário cadastrado com este email.";
        }

        // Criptografar a senha antes de salvar o usuário
        String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        usuario.setSenha(senhaCriptografada);

        usuarioDAO.salvarUsuario(usuario);
        response.status(201); // Created
        return gson.toJson(usuario);
    }

    // Método para recuperar um usuário por ID
    public Object buscarUsuario(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Usuario usuario = usuarioDAO.buscarUsuarioPorId(id);
        if (usuario != null) {
            return usuario;
        } else {
            response.status(404); // Not Found
            return "Usuário não encontrado.";
        }
    }

    // Método para atualizar um usuário
    public Object atualizarUsuario(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Gson gson = new Gson();
        Usuario usuarioAtualizado = gson.fromJson(request.body(), Usuario.class);

        Usuario usuarioExistente = usuarioDAO.buscarUsuarioPorId(id);
        if (usuarioExistente != null) {
            usuarioAtualizado.setId(id);
            usuarioDAO.atualizarUsuario(usuarioAtualizado);
            return usuarioAtualizado;
        } else {
            response.status(404); // Not Found
            return "Usuário não encontrado.";
        }
    }

    // Método para excluir um usuário
    public Object excluirUsuario(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Usuario usuarioExistente = usuarioDAO.buscarUsuarioPorId(id);
        if (usuarioExistente != null) {
            usuarioDAO.excluirUsuario(id);
            response.status(200); // OK
            return "Usuário excluído com sucesso.";
        } else {
            response.status(404); // Not Found
            return "Usuário não encontrado.";
        }
    }

    public boolean login(Request request, Response response) {
        try {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(request.body(), Usuario.class);
            Usuario auxiliar = usuarioDAO.autenticarUsuario(usuario.getEmail());
    
            if (auxiliar != null && BCrypt.checkpw(usuario.getSenha(), auxiliar.getSenha())) {
                // Se o usuário for autenticado com sucesso
                response.status(200); // OK
                response.type("application/json");
                return true;
            } else {
                // Se as credenciais estiverem incorretas
                response.status(401); // Unauthorized
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro durante o login: " + e.getMessage());
            response.status(500); // Internal Server Error
            return false;
        }
    }

    public Object getAllUsuarios(Request request, Response response) {
        List<Usuario> usuarios = usuarioDAO.getAllUsuarios();
        if (!usuarios.isEmpty()) {
            return usuarios;
        } else {
            response.status(404); // Not Found
            return "Nenhum usuário encontrado.";
        }
    }
}
