package dao;

import model.Prato;
import java.util.List;
import java.util.ArrayList;

public class PratoDAO {
    private List<Prato> pratos;
    private static int contadorId = 1;

    public PratoDAO() {
        this.pratos = new ArrayList<>();
    }

    public void adicionarPrato(Prato prato) {
        prato.setId(contadorId++);
        pratos.add(prato);
    }

    public void removerPrato(int id) {
        pratos.removeIf(prato -> prato.getId() == id);
    }

    public void atualizarPrato(Prato pratoAtualizado) {
        for (int i = 0; i < pratos.size(); i++) {
            Prato prato = pratos.get(i);
            if (prato.getId() == pratoAtualizado.getId()) {
                pratos.set(i, pratoAtualizado);
                break;
            }
        }
    }

    public List<Prato> obterTodosPratos() {
        return pratos;
    }

    public Prato obterPratoPorId(int id) {
        for (Prato prato : pratos) {
            if (prato.getId() == id) {
                return prato;
            }
        }
        return null;
    }
    
    // Método para atualizar as quantidades de um prato
    public void atualizarQuantidadesDoPrato(int id, int[] quantidades) {
        Prato prato = obterPratoPorId(id);
        if (prato != null) {
            prato.setQuantidades(quantidades);
        }
    }
}
