package service;

import java.time.LocalDate;
import java.util.List;
import model.Produto;
import dao.ProdutoDAO;
import spark.Request;
import spark.Response;

public class ProdutoService {

    private ProdutoDAO produtoDAO;

    public ProdutoService() {
        this.produtoDAO = new ProdutoDAO();
    }

    public List<Produto> getAllProducts() {
        return produtoDAO.getAll();
    }

    public Object getAll(Request request, Response response) {
        List<Produto> produtos = getAllProducts();
        return produtos;
    }

    public Object insert(Request request, Response response) {
        String nome = request.queryParams("nome");
        String categoria = request.queryParams("categoria");
        String quantidadeParam = request.queryParams("quantidade");
        int quantidade = 0; // Valor padrão, caso a quantidade esteja vazia ou nula
        if (quantidadeParam != null && !quantidadeParam.isEmpty()) {
            quantidade = Integer.parseInt(quantidadeParam);
        }
        String fornecedor = request.queryParams("fornecedor");
        String lote = request.queryParams("lote");
        LocalDate dataValidade = LocalDate.parse(request.queryParams("data_validade"));
    
        Produto produto = new Produto(nome, categoria, quantidade, fornecedor, lote, dataValidade);
        boolean sucesso = produtoDAO.insert(produto);
    
        if (sucesso) {
            return "Produto inserido com sucesso!";
        } else {
            response.status(500);
            return "Erro ao inserir o produto.";
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