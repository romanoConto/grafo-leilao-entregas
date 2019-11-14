package com.grafo.leiaoEntregas.entregas;

import com.grafo.leiaoEntregas.Distancia;
import com.grafo.leiaoEntregas.Entradas;
import com.grafo.leiaoEntregas.PontoGrafo;
import com.grafo.leiaoEntregas.PontoEntrega;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Entregas {
    private Entradas entradas;
    private List<Rota> rotas = new ArrayList<>();
	private List<String> pontosPassados = new ArrayList<>();

    public Entregas(Entradas entradas) {
        this.entradas = entradas;
    }

    public List<Rota> processarEntregas() {
        List<PontoEntrega> pontoEntregas = entradas.getPontosEntrega();

        PontoGrafo pontoAtual = entradas.getPontosGrafo().get(0);

        for (PontoEntrega pontoEntrega : pontoEntregas) {
            Rota rota = new Rota();
            rota.setDistancia(0);
            rota.setRecompensa(0);
            rota.setDestino(pontoEntrega.getDestino());
            rota.addPonto(pontoAtual.getNome());

            rota = distanciaRota(pontoAtual, rota);

            if (rota != null) {
                rotas.add(rota);
            }
            pontoAtual = getPonto(pontoEntrega.getDestino());
        }

        return rotas;
    }

    private Rota distanciaRota(PontoGrafo pontoAtual, Rota rota) {
        List<Rota> rotasPonto = new ArrayList<>();

        for (Distancia dist : pontoAtual.getDistancias()) {
            if (pontosPassados.stream().anyMatch(x -> x.equals(dist.getNome())))
                continue;

            if (dist.getNome().equals(rota.getDestino()) && dist.getDistancia() != 0) {
                Rota r = new Rota();
                r.setDestino(rota.getDestino());
                r.setPontos(new ArrayList<>(rota.getPontos()));
                r.setRecompensa(rota.getRecompensa());
                r.setDistancia(rota.getDistancia());

                r.addPonto(dist.getNome());
                r.addDistancia(dist.getDistancia());
                r.setRecompensa(getBonus(dist.getNome()));
                rotasPonto.add(r);
                continue;
            }

			pontosPassados.add(dist.getNome());

            if (dist.getDistancia() != 0) {
                rota.addPonto(dist.getNome());
                rota.addDistancia(dist.getDistancia());

                PontoGrafo ponto = getPonto(dist.getNome());
                Rota r = distanciaRota(ponto, rota);
                if (r != null) {
                    rotasPonto.add(r);
                }
            }
        }

        rotasPonto.sort((x, y) -> Integer.compare(x.getDistancia(), y.getDistancia()));
        Rota rotaMenor = rotasPonto.size() > 0 ? rotasPonto.get(0) : null;

        return rotaMenor;
    }

    private PontoGrafo getPonto(String name) {
        for (PontoGrafo p : entradas.getPontosGrafo()) {
            if (p.getNome().equals(name)) {
                return p;
            }
        }
        return null;
    }

    private int getBonus(String pontodist) {
        return getEntrega(pontodist).getBonus();
    }

    private PontoEntrega getEntrega(String name) {
        for (PontoEntrega p : entradas.getPontosEntrega()) {
            if (p.getDestino().equals(name)) {
                return p;
            }
        }
        return null;
    }
}
