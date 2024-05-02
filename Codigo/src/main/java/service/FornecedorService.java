package service;

import java.util.List;
import dao.FornecedorDAO;
import model.Fornecedor;

public class FornecedorService {
    private FornecedorDAO fornecedorDAO;

    public FornecedorService(FornecedorDAO fornecedorDAO) {
        this.fornecedorDAO = fornecedorDAO;
    }

    // Método para inserir um novo fornecedor
    public boolean inserirFornecedor(Fornecedor fornecedor) {
        return fornecedorDAO.inserir(fornecedor);
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
