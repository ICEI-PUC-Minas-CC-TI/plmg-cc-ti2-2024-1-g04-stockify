package app;

import static spark.Spark.*;
import service.ProdutoService;
import service.UsuarioService;

public class Aplicacao {
    
    private static ProdutoService produtoService = new ProdutoService();
    private static UsuarioService usuarioService = new UsuarioService(); 

    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");

        // Rotas para lidar com operações de produtos
        get("/produto/insert", (request, response) -> produtoService.insert(request, response));
        post("/produto/insert", (request, response) -> produtoService.insert(request, response));
        get("/produto/:id", (request, response) -> produtoService.get(request, response));
        get("/produto/list/:orderby", (request, response) -> produtoService.getAll(request, response));
        get("/produto/update/:id", (request, response) -> produtoService.getToUpdate(request, response));
        post("/produto/update/:id", (request, response) -> produtoService.update(request, response));
        get("/produto/delete/:id", (request, response) -> produtoService.delete(request, response));

        // Rota para servir o arquivo form.html como página inicial
        get("/", (request, response) -> {
            response.redirect("/index.html");
            return null;
        });

        // Rota para lidar com o login do usuário
        get("/login", (request, response) -> usuarioService.login(request, response));
        post("/login", (request, response) -> usuarioService.criarUsuario(request, response));

    }
}
