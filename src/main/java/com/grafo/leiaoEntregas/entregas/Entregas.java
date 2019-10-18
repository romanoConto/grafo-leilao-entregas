package com.grafo.leiaoEntregas.entregas;

import com.grafo.leiaoEntregas.Distancia;
import com.grafo.leiaoEntregas.Entradas;
import com.grafo.leiaoEntregas.PontoEntrada;
import com.grafo.leiaoEntregas.PontoEntrega;

import java.util.List;
import java.util.stream.Collectors;

public class Entregas {

    private Entradas entradas;
    private Rota rota;

    public Rota getRota() {
        return rota;
    }

    public Entregas(Entradas entradas) {
        this.entradas = entradas;
    }

    public void processarEntregas() {
        List<PontoEntrega> pontoEntregas = entradas.getPontosEntrega();

        PontoEntrada pontoAtual = entradas.getPontosEntrada().get(0);

        for (PontoEntrega pontoEntrega : pontoEntregas) {

            while (true) {
                String ponto = pontoEntrega.getDestino();

                boolean exist = pontoAtual.getDistancias().stream()
                        .map(Distancia::getNome).anyMatch(ponto::equals);
            }
        }
    }
}
