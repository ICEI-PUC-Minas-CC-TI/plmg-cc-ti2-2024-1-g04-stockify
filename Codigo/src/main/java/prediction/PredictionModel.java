package prediction;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import dao.VendaDAO;
import model.Venda;

public class PredictionModel {
    private VendaDAO vendaDAO;

    public PredictionModel(VendaDAO vendaDAO) {
        this.vendaDAO = vendaDAO;
    }

    // Calcula a média de consumo por dia da semana para cada produto
    public Map<String, Map<String, Double>> calculateAverageConsumptionPerDayOfWeek() {
        List<Venda> vendas = vendaDAO.getAllVendas();
        Map<String, Map<String, Double>> consumption = new HashMap<>();
        Map<String, Map<String, Integer>> count = new HashMap<>();

        for (Venda venda : vendas) {
            String produto = venda.getNomePrato();
            String diaSemana = venda.getDataVenda().toLocalDateTime().getDayOfWeek().toString();
            double quantidade = venda.getQuantidade();

            consumption.putIfAbsent(produto, new HashMap<>());
            count.putIfAbsent(produto, new HashMap<>());

            consumption.get(produto).put(diaSemana, consumption.get(produto).getOrDefault(diaSemana, 0.0) + quantidade);
            count.get(produto).put(diaSemana, count.get(produto).getOrDefault(diaSemana, 0) + 1);
        }

        for (String produto : consumption.keySet()) {
            for (String diaSemana : consumption.get(produto).keySet()) {
                double totalQuantidade = consumption.get(produto).get(diaSemana);
                int totalCount = count.get(produto).get(diaSemana);
                consumption.get(produto).put(diaSemana, totalQuantidade / totalCount);
            }
        }

        return consumption;
    }

    // Estima quantos dias restam até o estoque acabar para cada produto
    public Map<String, Integer> estimateDaysUntilStockOut(Map<String, Integer> currentStock) {
        Map<String, Map<String, Double>> averageConsumption = calculateAverageConsumptionPerDayOfWeek();
        Map<String, Integer> daysUntilStockOut = new HashMap<>();

        for (String produto : currentStock.keySet()) {
            int estoqueAtual = currentStock.get(produto);
            double consumoDiarioMedio = 0.0;

            for (String diaSemana : averageConsumption.get(produto).keySet()) {
                consumoDiarioMedio += averageConsumption.get(produto).get(diaSemana);
            }

            consumoDiarioMedio /= 7; // Média diária

            int diasRestantes = (int) Math.ceil(estoqueAtual / consumoDiarioMedio);
            daysUntilStockOut.put(produto, diasRestantes);
        }

        return daysUntilStockOut;
    }
}
