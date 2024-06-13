package service;

import dao.EventoDAO;
import model.Evento;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EventoService {
    private EventoDAO eventoDAO;

    public EventoService() {
        this.eventoDAO = new EventoDAO();
    }

    public boolean criarEvento(Request request, Response response) {
        try {
            String dataStr = request.queryParams("data");
            String nomeEvento = request.queryParams("nomeEvento"); // Altere para "nome"
    
            System.out.println("Dados recebidos para criar evento:");
            System.out.println("Data: " + dataStr);
            System.out.println("Nome do Evento: " + nomeEvento);
    
            if (dataStr == null || nomeEvento == null) {
                response.status(400); // Bad Request
                return false;
            }
    
            LocalDate data = LocalDate.parse(dataStr);
    
            // Altere o construtor para usar "nome" em vez de "nomeEvento"
            Evento evento = new Evento(data, nomeEvento);
            boolean inserido = eventoDAO.inserir(evento);
    
            if (inserido) {
                System.out.println("Evento inserido com sucesso.");
            } else {
                System.err.println("Falha ao inserir o evento no banco de dados.");
            }
    
            return inserido;
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao fazer parsing da data:");
            e.printStackTrace(); // ou logar o erro
    
            response.status(400); // Bad Request
            return false;
        }
    }    

    public Evento buscarEventoPorId(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        System.out.println("Buscando evento com ID: " + id);
        
        Evento evento = eventoDAO.buscarPorId(id);
        if (evento != null) {
            response.type("application/json");
            return evento;
        } else {
            response.status(404); // Not Found
            return null;
        }
    }

    public List<Evento> listarEventos(Request request, Response response) {
        System.out.println("Listando todos os eventos.");
        
        List<Evento> eventos = eventoDAO.buscarTodos();
        response.type("application/json");
        return eventos;
    }

    public boolean atualizarEvento(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        LocalDate novaData = LocalDate.parse(request.queryParams("novaData"));
        String novoNomeEvento = request.queryParams("novoNomeEvento");
        Evento evento = new Evento(id, novaData, novoNomeEvento);

        System.out.println("Dados recebidos para atualizar evento:");
        System.out.println("ID: " + id);
        System.out.println("Nova Data: " + novaData);
        System.out.println("Novo Nome do Evento: " + novoNomeEvento);
        
        boolean sucesso = eventoDAO.atualizar(evento);
        if (sucesso) {
            response.status(200); // OK
        } else {
            response.status(500); // Internal Server Error
        }
        return sucesso;
    }

    public boolean excluirEvento(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        System.out.println("Excluindo evento com ID: " + id);
        
        boolean sucesso = eventoDAO.excluir(id);
        if (sucesso) {
            response.status(200); // OK
        } else {
            response.status(500); // Internal Server Error
        }
        return sucesso;
    }
}
