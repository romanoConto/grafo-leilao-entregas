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
	private List<RotasEntrega> rotas = new ArrayList<>();

	public Entregas(Entradas entradas)
	{
		this.entradas = entradas;
	}

	public List<RotasEntrega> processarEntregas() throws CloneNotSupportedException
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

			RotasEntrega re = retornaRotas(pontoAtual, rota, null);

			if (re != null)
			{
				rotas.add(re);
			}
			pontoAtual = getPonto("A");
		}

		return rotas;
	}

	private RotasEntrega retornaRotas(PontoGrafo pontoAtual, Rota rota, List<String> pontosVerificados)
		throws CloneNotSupportedException
	{
		List<Rota> possiveisRotas = getPossiveisRotas(pontoAtual, rota, pontosVerificados);

		possiveisRotas.sort((x, y) -> Integer.compare(x.getDistancia(), y.getDistancia()));
		Rota rotaMenor = possiveisRotas.get(0);
		possiveisRotas.remove(rotaMenor);

		RotasEntrega re = new RotasEntrega();
		re.setRotaMenor(rotaMenor);
		re.setRotas(possiveisRotas);

		return re;
	}

	private List<Rota> getPossiveisRotas(PontoGrafo pontoAtual, Rota rota,
		List<String> pontosVerificados) throws CloneNotSupportedException
	{
		return getPossiveisRotas(pontoAtual, rota, pontosVerificados, null);
	}

	private List<Rota> getPossiveisRotas(PontoGrafo pontoAtual, Rota rotaAnt,
		List<String> pontosVerificados, Distancia distAnt) throws CloneNotSupportedException
	{
		Rota rota = new Rota();
		rota.setRecompensa(rotaAnt.getRecompensa());
		rota.setPontos(new ArrayList<>(rotaAnt.getPontos()));
		rota.setDistancia(rotaAnt.getDistancia());
		rota.setDestino(rotaAnt.getDestino());

		if (distAnt != null && !rota.getPontos().contains(distAnt.getNome()))
		{
			rota.addPonto(distAnt.getNome());
			rota.addDistancia(distAnt.getDistancia());
		}

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

			if (dist.getNome().equals(rota.getDestino()))
			{
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

			List<String> pontosVerfi = new ArrayList<>();
			for (String p : rota.getPontos())
				pontosVerfi.add(p);

			PontoGrafo ponto = getPonto(dist.getNome());
			List<Rota> r = getPossiveisRotas(ponto, rota, pontosVerfi, dist);
			if (r != null)
			{
				rotasPonto.addAll(r);
			}
		}
		return rotasPonto;
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
