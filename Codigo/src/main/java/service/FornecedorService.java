package service;

import java.sql.SQLException;
import java.util.List;
import dao.FornecedorDAO;
import model.Fornecedor;
import spark.Request;
import spark.Response;


import com.google.gson.Gson;


public class FornecedorService {
    private FornecedorDAO fornecedorDAO;

    public FornecedorService() {
        this.fornecedorDAO = new FornecedorDAO(); // Ou qualquer lógica necessária para inicializar fornecedorDAO
    }
    public FornecedorService(FornecedorDAO fornecedorDAO) {
        this.fornecedorDAO = fornecedorDAO;
    }

    // Método para inserir um novo fornecedor
    public boolean inserirFornecedor(Request request, Response response) {
        Gson gson = new Gson();
        Fornecedor fornecedor = gson.fromJson(request.body(), Fornecedor.class);
    
        try {
            FornecedorDAO fornecedorDAO = new FornecedorDAO(); // ou inicialize conforme a estrutura da sua aplicação
            return fornecedorDAO.inserir(fornecedor);
        } catch (Exception e) {
            System.out.println("Erro ao inserir fornecedor no serviço: " + e.getMessage());
            e.printStackTrace(); // Registrar a pilha de chamadas completa para diagnóstico
            return false;
        }
    }
    
    
    
    

    // Método para buscar um fornecedor pelo ID
    public Fornecedor buscarFornecedorPorId(int id) {
        return fornecedorDAO.buscarPorId(id);
    }

    // Método para buscar todos os fornecedores
    public List<Fornecedor> buscarTodosFornecedores() {
        return fornecedorDAO.buscarTodos();
    }

    // Método para atualizar um fornecedor
    public boolean atualizarFornecedor(Fornecedor fornecedor) {
        return fornecedorDAO.atualizar(fornecedor);
    }

    // Método para excluir um fornecedor
    public boolean excluirFornecedor(int id) {
        return fornecedorDAO.excluir(id);
    }
}
