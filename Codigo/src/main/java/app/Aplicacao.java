package app;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.VendaDAO;
import service.ProdutoService;
import service.UsuarioService;
import service.FornecedorService;
import service.ReceitaService;
import service.EventoService;
import service.VendaService;
import utils.LocalDateAdapter;
import model.Evento;
import prediction.PredictionService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Aplicacao {

    private static ProdutoService produtoService = new ProdutoService();
    private static UsuarioService usuarioService = new UsuarioService(); 
    private static FornecedorService fornecedorService = new FornecedorService();
    private static ReceitaService receitaService = new ReceitaService();
    private static EventoService eventoService = new EventoService();
    private static VendaService vendaService = new VendaService();
    private static PredictionService predictionService;
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        port(6789);
        
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        staticFiles.location("/public");

        // Rota para servir o arquivo form.html como página inicial
        get("/", (request, response) -> {
            response.redirect("/index.html");
            return null;
        });

        // Rota para lidar com o login do usuário
        put("/login/procura", (request, response) -> usuarioService.login(request, response));
        post("/login/insere", (request, response) -> usuarioService.criarUsuario(request, response));

        //Rota para Administracao de funcionarios
        get("/funcionarios/getAll", (request, response) -> usuarioService.getAllUsuarios(response));
        get("/funcionario/:id", (request, response) -> usuarioService.getById(request,response));
        put("/funcionario/atualizar/:id", (request, response) -> usuarioService.atualizarUsuario(request, response));
        delete("/funcionario/excluir/:id", (request, response) -> usuarioService.excluirUsuario(request, response));

        //Rotas para lidar com os produtos
        post("/produto/insere", (request, response) -> produtoService.insert(request, response));
        get("/produto/getAll", (request,response) -> produtoService.getAll(request, response));
        get("/produto/fornecedores", (request, response) -> produtoService.getAllFornecedores(request, response));
        get("/produto/:id", (request, response) -> produtoService.getById(request, response));
        put("/produto/atualizar/:id", (request, response) -> produtoService.atualizarProduto(request, response));
        delete("/produto/excluir/:id", (request, response) -> produtoService.excluirProduto(request, response)); 

        //Rotas fornecedores
        post("/fornecedor/insere", (request, response) -> fornecedorService.inserirFornecedor(request, response)); 
        get("/fornecedor/getAll", (request, response) -> fornecedorService.getAll(request, response));
        get("/fornecedor/:id", (request, response) -> fornecedorService.getById(request, response));
        put("/fornecedor/atualizar/:id", (request, response) -> fornecedorService.atualizarFornecedor(request, response));
        delete("/fornecedor/excluir/:id", (request, response) -> fornecedorService.excluirFornecedor(request, response)); 
        
        //Rotas Receitas
        post("/receita/insere", (request, response) -> receitaService.inserirReceita(request, response)); 
        get("/receita/getAll", (request, response) -> receitaService.getAll(request, response));
        get("/receita/:id", (request, response) -> receitaService.getById(request, response));
        put("/receita/atualizar/:id", (request, response) -> receitaService.atualizarReceita(request, response));
        delete("/evento/excluir", (request, response) -> receitaService.excluirReceitaPorNome(request, response));

        //Rota Venda
        post("/evento/vender", (request, response) -> vendaService.venderPrato(request, response));

        // Rotas Evento
        post("/evento/insere", (request, response) -> {
            boolean sucesso = eventoService.criarEvento(request, response);
            response.type("application/json");
            return gson.toJson(sucesso ? "Evento criado com sucesso" : "Falha ao criar evento");
        });

        get("/evento/getAll", (request, response) -> {
            List<Evento> eventos = eventoService.listarEventos(request, response);
            response.type("application/json");
            return gson.toJson(eventos);
        });

        get("/evento/:id", (request, response) -> {
            Evento evento = eventoService.buscarEventoPorId(request, response);
            response.type("application/json");
            return gson.toJson(evento != null ? evento : "Evento não encontrado");
        });

        put("/evento/atualizar/:id", (request, response) -> {
            boolean sucesso = eventoService.atualizarEvento(request, response);
            response.type("application/json");
            return gson.toJson(sucesso ? "Evento atualizado com sucesso" : "Falha ao atualizar evento");
        });

        delete("/evento/excluir/:id", (request, response) -> {
            boolean sucesso = eventoService.excluirEvento(request, response);
            response.type("application/json");
            return gson.toJson(sucesso ? "Evento excluído com sucesso" : "Falha ao excluir evento");
        });

        // Rota para atualizar a predição
        get("/atualizarPredicao", (request, response) -> {
            predictionService.verificarEstoqueECriarEventos();
            response.status(200);
            return "";
        });

        // Criação do PredictionService
        VendaDAO vendaDAO = new VendaDAO();
        predictionService = new PredictionService(vendaDAO, produtoService, eventoService);

        // Verificação de estoque e criação de eventos
        predictionService.verificarEstoqueECriarEventos();
    }
}
