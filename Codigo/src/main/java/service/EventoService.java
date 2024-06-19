package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.EventoDAO;
import model.Evento;
import spark.Request;
import spark.Response;
import utils.LocalDateAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class EventoService {
    private EventoDAO eventoDAO;
    private Gson gson;

    public EventoService() {
        this.eventoDAO = new EventoDAO();

        // Configuração do Gson com o adaptador LocalDateAdapter
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        this.gson = gsonBuilder.create();
    }

    public boolean criarEvento(Evento evento) {
        if (evento.getData() == null || evento.getNomeEvento() == null) {
            return false;
        }

        boolean inserido = eventoDAO.inserir(evento);
        return inserido;
    }

    public boolean criarEvento(Request request, Response response) {
        try {
            Evento evento = gson.fromJson(request.body(), Evento.class);
            System.out.println("Dados recebidos para criar evento:");
            System.out.println("Data: " + evento.getData());
            System.out.println("Nome do Evento: " + evento.getNomeEvento());

            if (evento.getData() == null || evento.getNomeEvento() == null) {
                response.status(400); // Bad Request
                return false;
            }

            boolean inserido = eventoDAO.inserir(evento);
            if (inserido) {
                response.status(201); // Created
                return true;
            } else {
                response.status(500); // Internal Server Error
                return false;
            }
        } catch (DateTimeParseException e) {
            response.status(400); // Bad Request
            return false;
        }
    }

    public List<Evento> listarEventos(Request request, Response response) {
        List<Evento> eventos = eventoDAO.buscarTodos();
        response.type("application/json");
        return eventos;
    }

    public Evento buscarEventoPorId(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Evento evento = eventoDAO.buscarPorId(id);
        if (evento != null) {
            response.type("application/json");
            return evento;
        } else {
            response.status(404); // Not Found
            return null;
        }
    }

    public boolean atualizarEvento(Request request, Response response) {
        try {
            Evento evento = gson.fromJson(request.body(), Evento.class);
            int id = Integer.parseInt(request.params(":id"));
            evento.setId(id);
            boolean sucesso = eventoDAO.atualizar(evento);
            if (sucesso) {
                response.status(200); // OK
                return true;
            } else {
                response.status(500); // Internal Server Error
                return false;
            }
        } catch (NumberFormatException e) {
            response.status(400); // Bad Request
            return false;
        }
    }

    public boolean excluirEvento(Request request, Response response) {
        try {
            int id = Integer.parseInt(request.params(":id"));
            boolean sucesso = eventoDAO.excluir(id);
            if (sucesso) {
                response.status(200); // OK
                return true;
            } else {
                response.status(500); // Internal Server Error
                return false;
            }
        } catch (NumberFormatException e) {
            response.status(400); // Bad Request
            return false;
        }
    }

    public List<Evento> listarEventosPorData(LocalDate data) {
        return eventoDAO.buscarPorData(data);
    }

    public Evento buscarPorDataENome(LocalDate data, String nome) {
        return eventoDAO.buscarPorDataENome(data, nome);
    }

    public boolean excluirEventoPorDataENome(LocalDate data, String nome) {
        Evento evento = eventoDAO.buscarPorDataENome(data, nome);
        if (evento != null) {
            return eventoDAO.excluir(evento.getId());
        }
        return false;
    }
}
