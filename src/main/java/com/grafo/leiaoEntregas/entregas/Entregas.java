package com.grafo.leiaoEntregas.entregas;

import com.grafo.leiaoEntregas.Distancia;
import com.grafo.leiaoEntregas.Entradas;
import com.grafo.leiaoEntregas.PontoGrafo;
import com.grafo.leiaoEntregas.PontoEntrega;

import java.util.ArrayList;
import java.util.List;

public class Entregas {
    private Entradas entradas;
    private List<Rota> rotas = new ArrayList<>();

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
            List<String> pontos = rota.getPontos();
            pontos.add(pontoAtual.getNome());
            rota.setPontos(pontos);

            rota = distanciaRota(pontoAtual, rota);

            if (rota != null)
			{
				rotas.add(rota);
			}
            pontoAtual = getPonto(pontoEntrega.getDestino());
        }

        return rotas;
    }

    private Rota distanciaRota(PontoGrafo pontoAtual, Rota rota) {
        for (Distancia dist : pontoAtual.getDistancias()) {
            if (dist.getNome().equals(rota.getDestino()) && dist.getDistancia() != 0) {
                List<String> pontos = rota.getPontos();
                pontos.add(dist.getNome());
                rota.setPontos(pontos);

                rota.setDistancia(rota.getDistancia() + dist.getDistancia());

                rota.setRecompensa(getBonus(dist.getNome(), rota.getDestino(), rota));
                return rota;
            }
            if (dist.getDistancia() != 0 && (rota.getPontos().size() < 1
                    || !rota.getPontos().contains(dist.getNome()))) {

                List<String> pontos = rota.getPontos();
                pontos.add(dist.getNome());
                rota.setPontos(pontos);

                rota.setDistancia(rota.getDistancia() + dist.getDistancia());
                PontoGrafo ponto = getPonto(dist.getNome());

                return distanciaRota(ponto, rota);
            }
        }

        return null;
    }

    private PontoGrafo getPonto(String name) {
        for (PontoGrafo p : entradas.getPontosGrafo()) {
            if (p.getNome().equals(name)) {
                return p;
            }
        }
        return null;
    }

    private int getBonus(String pontodist, String pontoRota, Rota rota) {
    	if(rota.getPontos().size() > 2)
		{
			return getEntrega(pontodist).getBonus() + getEntrega(pontoRota).getBonus();
		}
    	else
		{
			return getEntrega(pontodist).getBonus();
		}

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
