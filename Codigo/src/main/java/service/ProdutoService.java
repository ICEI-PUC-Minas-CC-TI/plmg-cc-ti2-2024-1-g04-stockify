package service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import com.google.gson.Gson;
import model.Produto;
import dao.ProdutoDAO;
import spark.Request;
import spark.Response;

public class ProdutoService {

    private ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public String getAll(Request request, Response response) {
        List<Produto> produtos = produtoDAO.getAll();
        Gson gson = new Gson();
        return gson.toJson(produtos);
    }

    public boolean insert(Request request, Response response) {
        Gson gson = new Gson();
        Produto produto = gson.fromJson(request.body(), Produto.class);

        try {
            return produtoDAO.insert(produto);
        } catch (Exception e) {
            System.out.println("Erro ao inserir no service: " + e.getMessage());
            return false;
        }
    }

    public String getById(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Optional<Produto> produto = produtoDAO.get(id);

        if (produto.isPresent()) {
            Gson gson = new Gson();
            return gson.toJson(produto.get());
        } else {
            response.status(404);
            return "Produto não encontrado";
        }
    }

    public boolean atualizarProduto(Request request, Response response) {
        Gson gson = new Gson();
        Produto produto = gson.fromJson(request.body(), Produto.class);

        try {
            return produtoDAO.update(produto);
        } catch (Exception e) {
            System.out.println("Erro ao atualizar no service: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirProduto(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        try {
            return produtoDAO.delete(id);
        } catch (Exception e) {
            System.out.println("Erro ao excluir no service: " + e.getMessage());
            return false;
        }
    }

    public String getAllFornecedores(Request request, Response response) {
        List<String> fornecedores = produtoDAO.getAllFornecedores();
        Gson gson = new Gson();
        return gson.toJson(fornecedores);
    }

    public Map<String, Integer> getCurrentStock() {
        List<Produto> produtos = produtoDAO.getAll();
        Map<String, Integer> currentStock = new HashMap<>();

        for (Produto produto : produtos) {
            currentStock.put(produto.getNome(), produto.getQuantidade());
        }

        return currentStock;
    }
}
