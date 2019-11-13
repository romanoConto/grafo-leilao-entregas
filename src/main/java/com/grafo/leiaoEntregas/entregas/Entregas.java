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
			rota.setRecompensa(0);
			rota.setDestino(pontoEntrega.getDestino());
			rota.addPonto(pontoAtual.getNome());

			rota = distanciaRota(pontoAtual, rota);

			if (rota != null)
			{
				rotas.add(rota);
			}
			pontoAtual = getPonto(pontoEntrega.getDestino());
		}

		return rotas;
	}

	private Rota distanciaRota(PontoGrafo pontoAtual, Rota rota)
	{
	    List<Rota> rotasPonto = new ArrayList<>();

	    Rota rotaAux = rota;
		for (Distancia dist : pontoAtual.getDistancias())
		{
			if (dist.getNome().equals(rotaAux.getDestino()) && dist.getDistancia() != 0)
			{
                rotaAux.addPonto(dist.getNome());
                rotaAux.addDistancia(dist.getDistancia());
                rotaAux.setRecompensa(getBonus(dist.getNome()));
                rotasPonto.add(rotaAux);
                rotaAux = rota;
                continue;
			}

			//CONTINUAR DAQUI--->>>>>>>>>>>>>>>
			if (dist.getDistancia() != 0 && (rota.getPontos().size() < 1 || !rota.getPontos()
				.contains(dist.getNome())))
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
