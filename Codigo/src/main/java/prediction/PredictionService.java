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
            if (diasRestantes <= 7) { // Por exemplo, se restarem 7 dias ou menos
                String nomeEvento = "Reabastecer Estoque de " + produto; // Nome do evento com o nome do produto

                // Verificar se já existe um evento com o mesmo nome na data de hoje
                Evento eventoExistente = eventoService.buscarPorDataENome(today, nomeEvento);
                if (eventoExistente == null) {
                    Evento evento = new Evento(today, nomeEvento);
                    // Criar evento
                    eventoService.criarEvento(evento);
                }
            } else {
                // Verificar se existe um evento de reabastecimento para este produto na data de hoje
                Evento eventoExistente = eventoService.buscarPorDataENome(today, "Reabastecer Estoque de " + produto);
                if (eventoExistente != null) {
                    // Remover evento (opcional, se necessário)
                    eventoService.excluirEventoPorDataENome(today, "Reabastecer Estoque de " + produto);
                }
            }

            if (diasRestantes <= 3) {
                System.out.println("Alerta: O estoque do produto " + produto + " está acabando!");
            }
        }
    }
}
