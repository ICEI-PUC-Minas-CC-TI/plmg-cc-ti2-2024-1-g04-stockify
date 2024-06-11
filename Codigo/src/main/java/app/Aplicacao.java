package app;

import static spark.Spark.*;
import service.ProdutoService;
import service.UsuarioService;
import service.FornecedorService;
import service.PratoService;

public class Aplicacao {
    
    private static ProdutoService produtoService = new ProdutoService();
    private static UsuarioService usuarioService = new UsuarioService(); 
    private static FornecedorService fornecedorService = new FornecedorService();
    private static PratoService pratoService = new PratoService();

    public static void main(String[] args) {
        port(6789);
        
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

        // Rotas para lidar com os produtos
        post("/produto/insere", (request, response) -> produtoService.insert(request, response));
        get("/produto/getAll", (request,response) -> produtoService.getAll(request, response));
        get("/produto/fornecedores", (request, response) -> produtoService.getAllFornecedores(request, response));
        get("/produto/:id", (request, response) -> produtoService.getById(request, response));
        put("/produto/atualizar/:id", (request, response) -> produtoService.atualizarProduto(request, response));
        delete("/produto/excluir/:id", (request, response) -> produtoService.excluirProduto(request, response)); 

        // Rotas fornecedores
        post("/fornecedor/insere", (request, response) -> fornecedorService.inserirFornecedor(request, response)); 
        get("/fornecedor/getAll", (request, response) -> fornecedorService.getAll(request, response));
        get("/fornecedor/:id", (request, response) -> fornecedorService.getById(request, response));
        put("/fornecedor/atualizar/:id", (request, response) -> fornecedorService.atualizarFornecedor(request, response));
        delete("/fornecedor/excluir/:id", (request, response) -> fornecedorService.excluirFornecedor(request, response)); 
        
        //Rotas pratos
        post("/prato/insere", (request, response) -> pratoService.adicionarPrato(request, response));
        get("/prato/getAll", (request,response) -> pratoService.obterTodosPratos(request, response));
        get("/prato/:id", (request, response) -> pratoService.obterPratoPorId(request, response));
        put("/prato/atualizar/:id", (request, response) -> pratoService.atualizarPrato(request, response));
        delete("/prato/excluir/:id", (request, response) -> pratoService.excluirPrato(request, response)); 
    }
}
