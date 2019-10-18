package com.grafo.leiaoEntregas.entregas;

import com.grafo.leiaoEntregas.Distancia;
import com.grafo.leiaoEntregas.Entradas;
import com.grafo.leiaoEntregas.PontoGrafo;
import com.grafo.leiaoEntregas.PontoEntrega;

import java.util.ArrayList;
import java.util.List;

public class Entregas
{
	private Entradas entradas;
	private List<Rota> rotas = new ArrayList<>();

	public Entregas(Entradas entradas)
	{
		this.entradas = entradas;
	}

	public List<Rota> processarEntregas()
	{
		List<PontoEntrega> pontoEntregas = entradas.getPontosEntrega();

		PontoGrafo pontoAtual = entradas.getPontosGrafo().get(0);

		for (PontoEntrega pontoEntrega : pontoEntregas)
		{
			Rota rota = new Rota();
			rota.setDistancia(0);
			rota.setPontos(new ArrayList<>());
			rota.setRecompensa(0);
			rota.setDestino(pontoEntrega.getDestino());

			rota = distanciaRota(pontoAtual, rota);

			rotas.add(rota);
			pontoAtual = getPonto(pontoEntrega.getDestino());
		}

		return rotas;
	}

	private Rota distanciaRota(PontoGrafo pontoAtual, Rota rota)
	{
		for (Distancia dist : pontoAtual.getDistancias())
		{
			if (dist.getNome().equals(rota.getDestino()) && dist.getDistancia() != 0)
			{
				List<String> pontos = rota.getPontos();
				pontos.add(dist.getNome());
				rota.setPontos(pontos);

				rota.setDistancia(rota.getDistancia() + dist.getDistancia());

				rota.setRecompensa(getBonus(dist.getNome()));
				return rota;
			}
			if (dist.getDistancia() != 0)
			{
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

	private PontoGrafo getPonto(String name)
	{
		for (PontoGrafo p : entradas.getPontosGrafo())
		{
			if (p.getNome().equals(name))
			{
				return p;
			}
		}
		return null;
	}

	private int getBonus(String name)
	{
		return getEntrega(name).getBonus();
	}

	private PontoEntrega getEntrega(String name)
	{
		for (PontoEntrega p : entradas.getPontosEntrega())
		{
			if (p.getDestino().equals(name))
			{
				return p;
			}
		}
		return null;
	}
}
