package service;

import com.google.gson.Gson;

import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public UsuarioService() {
        makeForm();
    }

    public void makeForm() {

    }

    public Object login(Request request, Response response) {
        String email = "exemplo@email.com";
        String senha = "senha123";
    
        Usuario usuario = usuarioDAO.autenticarUsuario(email, senha);
    
        if (usuario != null) {
            // Se o usuário for autenticado com sucesso, crie um objeto JSON com as informações do usuário
            Gson gson = new Gson();
            String jsonUsuario = gson.toJson(usuario);
            response.status(200); // OK
            response.type("application/json");
            return jsonUsuario;
        } else {
            // Se as credenciais estiverem incorretas, retorne uma mensagem de erro
            response.status(401); // Unauthorized
            return "Email ou senha incorretos. Por favor, tente novamente.";
        }
    }
}
