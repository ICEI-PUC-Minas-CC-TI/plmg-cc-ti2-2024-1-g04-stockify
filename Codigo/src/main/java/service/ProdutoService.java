package service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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
        List<Produto> produtos = getAllProducts();
        Gson gson = new Gson();
        return gson.toJson(produtos);
    }

    private List<Produto> getAllProducts() {
        return produtoDAO.getAll();
    }

    public boolean insert(Request request, Response response) {
        Gson gson = new Gson();
        Produto produto = gson.fromJson(request.body(), Produto.class);

        try {
            ProdutoDAO prDAO = new ProdutoDAO();
            return prDAO.insert(produto);
        } catch (Exception e) {
            System.out.println("Erro ao inserir no service: " + e.getMessage());
            return false;
        }

    }

    public Object get(Request request, Response response) {
        // Lógica para obter um produto do banco de dados
        return null; // Você pode retornar algo útil aqui, se necessário
    }

    public Object getToUpdate(Request request, Response response) {
        // Lógica para obter um produto para atualização do banco de dados
        return null; // Você pode retornar algo útil aqui, se necessário
    }

    public Object update(Request request, Response response) {
        // Lógica para atualizar um produto no banco de dados
        return null; // Você pode retornar algo útil aqui, se necessário
    }

    public Object delete(Request request, Response response) {
        // Lógica para excluir um produto do banco de dados
        return null; // Você pode retornar algo útil aqui, se necessário
    }
}