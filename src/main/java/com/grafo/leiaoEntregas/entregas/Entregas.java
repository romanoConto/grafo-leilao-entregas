package com.grafo.leiaoEntregas.entregas;

import com.grafo.leiaoEntregas.Distancia;
import com.grafo.leiaoEntregas.Entradas;
import com.grafo.leiaoEntregas.PontoGrafo;
import com.grafo.leiaoEntregas.PontoEntrega;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Entregas
{
	private Entradas entradas;
	private List<Rota> rotas = new ArrayList<>();

	public Entregas(Entradas entradas)
	{
		this.entradas = entradas;
	}

	public List<Rota> processarEntregas() throws CloneNotSupportedException
	{
		List<PontoEntrega> pontoEntregas = entradas.getPontosEntrega();

		PontoGrafo pontoAtual = entradas.getPontosGrafo().get(0);

		for (PontoEntrega pontoEntrega : pontoEntregas)
		{
			Rota rota = new Rota();
			rota.setDistancia(0);
			rota.setRecompensa(0);
			rota.setDestino(pontoEntrega.getDestino());
			rota.addPonto(pontoAtual.getNome());

			rota = distanciaRota(pontoAtual, rota, null);

			if (rota != null)
			{
				rotas.add(rota);
			}
			pontoAtual = getPonto("A");
		}

		return rotas;
	}

	private Rota distanciaRota(PontoGrafo pontoAtual, Rota rota, List<String> pontosVerificados)
		throws CloneNotSupportedException
	{
		Rota rotaAux = new Rota();
		rotaAux.setDestino(new String(rota.getDestino()));
		rotaAux.setPontos(new ArrayList<>(rota.getPontos()));
		rotaAux.setRecompensa(rota.getRecompensa());
		rotaAux.setDistancia(rota.getDistancia());

		List<Rota> rotasPonto = new ArrayList<>();

		if (pontosVerificados == null)
		{
			pontosVerificados = new ArrayList<>();
		}

		for (Distancia dist : pontoAtual.getDistancias())
		{
			if (pontosVerificados.stream().anyMatch(x -> x.equals(dist.getNome()))
				|| dist.getDistancia() == 0)
			{
				continue;
			}

			if (dist.getNome().equals(rotaAux.getDestino()))
			{
				Rota r = new Rota();
				r.setDestino(rotaAux.getDestino());
				r.setPontos(new ArrayList<>(rotaAux.getPontos()));
				r.setRecompensa(rotaAux.getRecompensa());
				r.setDistancia(rotaAux.getDistancia());

				r.addPonto(dist.getNome());
				r.addDistancia(dist.getDistancia());
				r.setRecompensa(getBonus(dist.getNome()));
				rotasPonto.add(r);

				continue;
			}

			rotaAux.addPonto(dist.getNome());
			rotaAux.addDistancia(dist.getDistancia());

			List<String> pontosVerfi = new ArrayList<>();
			for (String p : rotaAux.getPontos())
				pontosVerfi.add(p);

			PontoGrafo ponto = getPonto(dist.getNome());
			Rota r = distanciaRota(ponto, rotaAux, pontosVerfi);
			if (r != null)
			{
				rotasPonto.add(r);
			}

			rotaAux.setDestino(new String(rota.getDestino()));
			rotaAux.setPontos(new ArrayList<>(rota.getPontos()));
			rotaAux.setRecompensa(rota.getRecompensa());
			rotaAux.setDistancia(rota.getDistancia());

		}

		rotasPonto.sort((x, y) -> Integer.compare(x.getDistancia(), y.getDistancia()));
		Rota rotaMenor = rotasPonto.size() > 0 ? rotasPonto.get(0) : null;

		return rotaMenor;
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

	private int getBonus(String pontodist)
	{
		return getEntrega(pontodist).getBonus();
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
