package service;

import dao.VendaDAO;
import model.Venda;
import spark.Request;
import spark.Response;

import java.sql.Timestamp;

public class VendaService {

    private final VendaDAO vendaDAO;

    public VendaService() {
        this.vendaDAO = new VendaDAO();
    }
    
    public VendaService(VendaDAO vendaDAO) {
        this.vendaDAO = vendaDAO;
    }

    public Object venderPrato(Request request, Response response) {
        String nomePrato = request.queryParams("nomePrato");
        int quantidade = Integer.parseInt(request.queryParams("quantidade"));

        if (nomePrato == null || nomePrato.isEmpty() || quantidade <= 0) {
            response.status(400); // 400 Bad Request
            return "Nome do prato ou quantidade inválida.";
        }

        Venda venda = new Venda(nomePrato, quantidade);
        boolean sucessoVenda = vendaDAO.registrarVenda(venda);
        boolean sucessoEstoque = vendaDAO.atualizarEstoque(nomePrato, quantidade);

        if (sucessoVenda && sucessoEstoque) {
            response.status(200); // 200 OK
            return "Venda registrada e estoque atualizado com sucesso.";
        } else {
            response.status(500); // 500 Internal Server Error
            return "Erro ao registrar a venda ou atualizar o estoque.";
        }
    }
}
