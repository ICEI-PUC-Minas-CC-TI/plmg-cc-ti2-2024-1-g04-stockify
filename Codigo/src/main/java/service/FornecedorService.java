package service;

import dao.FornecedorDAO;
import model.Fornecedor;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

import java.util.List;

public class FornecedorService {
    private FornecedorDAO fornecedorDAO;

    public FornecedorService() {
        this.fornecedorDAO = new FornecedorDAO();
    }

    public FornecedorService(FornecedorDAO fornecedorDAO) {
        this.fornecedorDAO = fornecedorDAO;
    }

    public String getById(Request request, Response response) {
        String idParam = request.params(":id");
        int id = Integer.parseInt(idParam);
        Fornecedor fornecedor = buscarFornecedorPorId(id);
        Gson gson = new Gson();
        return gson.toJson(fornecedor);
    }

    public boolean inserirFornecedor(Request request, Response response) {
        Gson gson = new Gson();
        Fornecedor fornecedor = gson.fromJson(request.body(), Fornecedor.class);
        try {
            return fornecedorDAO.inserir(fornecedor);
        } catch (Exception e) {
            System.out.println("Erro ao inserir fornecedor no serviço: " + e.getMessage());
            e.printStackTrace(); // Registrar a pilha de chamadas completa para diagnóstico
            return false;
        }
    }

    public String getAll(Request request, Response response) {
        List<Fornecedor> fornecedores = buscarTodosFornecedores();
        Gson gson = new Gson();
        return gson.toJson(fornecedores);
    }

    public boolean atualizarFornecedor(Request request, Response response) {
        Gson gson = new Gson();
        Fornecedor fornecedor = gson.fromJson(request.body(), Fornecedor.class);
        return fornecedorDAO.atualizar(fornecedor);
    }

    public boolean excluirFornecedor(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        return fornecedorDAO.excluir(id);
    }

    private Fornecedor buscarFornecedorPorId(int id) {
        return fornecedorDAO.buscarPorId(id);
    }

    private List<Fornecedor> buscarTodosFornecedores() {
        return fornecedorDAO.buscarTodos();
    }
}
