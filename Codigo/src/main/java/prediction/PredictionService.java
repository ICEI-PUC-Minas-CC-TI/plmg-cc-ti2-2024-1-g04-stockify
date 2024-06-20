package prediction;

import dao.VendaDAO;
import model.Evento;
import service.EventoService;
import service.ProdutoService;

import java.time.LocalDate;
import java.util.Map;

public class PredictionService {
    private PredictionModel predictionModel;
    private ProdutoService produtoService;
    private EventoService eventoService;

    public PredictionService(VendaDAO vendaDAO, ProdutoService produtoService, EventoService eventoService) {
        this.predictionModel = new PredictionModel(vendaDAO);
        this.produtoService = produtoService;
        this.eventoService = eventoService;
    }

    public Map<String, Map<String, Double>> getAverageConsumptionPerDayOfWeek() {
        return predictionModel.calculateAverageConsumptionPerDayOfWeek();
    }

    public Map<String, Integer> getDaysUntilStockOut(Map<String, Integer> currentStock) {
        return predictionModel.estimateDaysUntilStockOut(currentStock);
    }

    public void verificarEstoqueECriarEventos() {
        Map<String, Integer> currentStock = produtoService.getCurrentStock();
        Map<String, Integer> previsao = getDaysUntilStockOut(currentStock);
        LocalDate today = LocalDate.now();
    
        for (String produto : previsao.keySet()) {
            int diasRestantes = previsao.get(produto);
            int estoqueAtual = currentStock.getOrDefault(produto, 0); // Garante que o estoque atual seja inicializado corretamente
    
            if (estoqueAtual <= 25) { // Verifica se o estoque está abaixo ou igual a 25
                String nomeEvento = "Reabastecer Estoque de " + produto;
    
                Evento eventoExistente = eventoService.buscarPorDataENome(today, nomeEvento);
                if (eventoExistente == null) {
                    Evento evento = new Evento(today, nomeEvento);
                    eventoService.criarEvento(evento);
                }
            } else {
                Evento eventoExistente = eventoService.buscarPorDataENome(today, "Reabastecer Estoque de " + produto);
                if (eventoExistente != null) {
                    eventoService.excluirEventoPorDataENome(today, "Reabastecer Estoque de " + produto);
                }
            }
    
            if (diasRestantes <= 3) {
                System.out.println("Alerta: O estoque do produto " + produto + " acabou!");
            }
        }
    }
}    
