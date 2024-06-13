package model;

import java.time.LocalDate;

public class Evento {
    private int id;
    private LocalDate data;
    private String nomeEvento;

    public Evento(LocalDate data, String nomeEvento) {
        this.data = data;
        this.nomeEvento = nomeEvento;
    }

    public Evento(int id, LocalDate data, String nomeEvento) {
        this.id = id;
        this.data = data;
        this.nomeEvento = nomeEvento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", data=" + data +
                ", nomeEvento='" + nomeEvento + '\'' +
                '}';
    }
}
