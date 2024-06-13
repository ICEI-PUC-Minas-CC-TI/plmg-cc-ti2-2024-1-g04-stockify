package service;

import dao.ReceitaDAO;
import model.Receita;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

public class ReceitaService {

    private ReceitaDAO receitaDAO;
    private Gson gson;

    public ReceitaService() {
        this.receitaDAO = new ReceitaDAO();
        this.gson = new Gson();
    }

    public Object inserirReceita(Request request, Response response) {
        try {
            // Parse JSON body
            Map<String, Object> data = gson.fromJson(request.body(), Map.class);

            String nomePrato = (String) data.get("nomePrato");
            List<String> ingredientes = (List<String>) data.get("ingredientes");
            List<Double> quantidades = (List<Double>) data.get("quantidades");

            boolean sucesso = false;
            for (int i = 0; i < ingredientes.size(); i++) {
                Receita receita = new Receita(nomePrato, ingredientes.get(i), quantidades.get(i).intValue());
                sucesso = receitaDAO.inserirReceita(receita);
                if (!sucesso) {
                    break;
                }
            }

            if (sucesso) {
                response.status(201); // 201 Created
                return "Receita criada com sucesso.";
            } else {
                response.status(500); // 500 Internal Server Error
                return "Erro ao criar a receita.";
            }
        } catch (Exception e) {
            response.status(500);
            return "Erro ao processar a solicitação: " + e.getMessage();
        }
    }

    public Object getAll(Request request, Response response) {
        List<Receita> receitas = receitaDAO.getAll();
        response.header("Content-Type", "application/json");
        return gson.toJson(receitas);
    }

    public Object getById(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Receita receita = receitaDAO.getById(id);

        if (receita != null) {
            response.header("Content-Type", "application/json");
            return gson.toJson(receita);
        } else {
            response.status(404); // 404 Not Found
            return "Receita não encontrada.";
        }
    }

    public Object atualizarReceita(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        try {
            Map<String, Object> data = gson.fromJson(request.body(), Map.class);
            String nomePrato = (String) data.get("nomePrato");
            String nomeIngrediente = (String) data.get("nomeIngrediente");
            int quantidadeIngrediente = ((Double) data.get("quantidadeIngrediente")).intValue();

            Receita receita = new Receita(nomePrato, nomeIngrediente, quantidadeIngrediente);

            boolean sucesso = receitaDAO.atualizarReceita(receita, id);
            if (sucesso) {
                response.status(200); // 200 OK
                return "Receita atualizada com sucesso.";
            } else {
                response.status(500); // 500 Internal Server Error
                return "Erro ao atualizar a receita.";
            }
        } catch (Exception e) {
            response.status(500);
            return "Erro ao processar a solicitação: " + e.getMessage();
        }
    }

    public Object excluirReceita(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        boolean sucesso = receitaDAO.excluirReceita(id);
        if (sucesso) {
            response.status(200); // 200 OK
            return "Receita excluída com sucesso.";
        } else {
            response.status(500); // 500 Internal Server Error
            return "Erro ao excluir a receita.";
        }
    }
}
