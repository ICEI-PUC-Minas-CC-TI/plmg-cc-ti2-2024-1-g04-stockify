package service;

import dao.PratoDAO;
import model.Prato;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.List;

public class PratoService {
    private PratoDAO pratoDAO;
    private Gson gson;

    public PratoService() {
        this.pratoDAO = new PratoDAO();
        this.gson = new Gson();
    }

    public String adicionarPrato(Request request, Response response) {
        Prato prato = gson.fromJson(request.body(), Prato.class);
        System.out.println("Dados recebidos para adicionar prato:");
        System.out.println("ID: " + prato.getId());
        System.out.println("Nome: " + prato.getNome());
        System.out.println("Ingredientes: " + prato.getIngredientes());
        System.out.println("Quantidade: " + prato.getQuantidade());
        pratoDAO.adicionarPrato(prato);
        response.status(201);
        return "Prato adicionado com sucesso!";
    }

    public String obterTodosPratos(Request request, Response response) {
        List<Prato> pratos = pratoDAO.obterTodosPratos();
        response.type("application/json");
        
        if (pratos.isEmpty()) {
            response.status(404);
            return "Nenhum prato cadastrado";
        } else {
            return gson.toJson(pratos);
        }
    }

    public String obterPratoPorId(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Prato prato = pratoDAO.obterPratoPorId(id);
        if (prato != null) {
            response.type("application/json");
            return gson.toJson(prato);
        } else {
            response.status(404);
            return "Prato não encontrado";
        }
    }

    public String atualizarPrato(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Prato pratoAtualizado = gson.fromJson(request.body(), Prato.class);
        pratoAtualizado.setId(id);
        pratoDAO.atualizarPrato(pratoAtualizado);
        return "Prato atualizado com sucesso!";
    }

    public String excluirPrato(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        pratoDAO.removerPrato(id);
        return "Prato removido com sucesso!";
    }
    
    // Método para atualizar as quantidades de um prato
    public String atualizarQuantidadesDoPrato(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        int[] quantidades = gson.fromJson(request.body(), int[].class);
        pratoDAO.atualizarQuantidadesDoPrato(id, quantidades);
        return "Quantidades do prato atualizadas com sucesso!";
    }
}
