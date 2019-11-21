package com.grafo.leiaoEntregas.gerenciador;

import com.grafo.leiaoEntregas.Entradas;
import com.grafo.leiaoEntregas.entradas.LerEntradas;
import com.grafo.leiaoEntregas.entregas.Entregas;
import com.grafo.leiaoEntregas.entregas.Rota;

import com.grafo.leiaoEntregas.entregas.RotasEntrega;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gerenciador
{

	private static Entradas entradas = new Entradas();
	private static List<RotasEntrega> rotas = new ArrayList<>();

	private static String path = null;

	public Gerenciador() throws Exception
	{

		Scanner ler = new Scanner(System.in);
		int iniciar;

		while (true)
		{
			System.out.println("\n============== LEILÃO DE ENTREGAS =============");
			System.out.println("1 - Carregar Entradas ");
			System.out.println("2 - Calcular Entregas ");
			System.out.println("3 - Mostrar Rotas ");
			System.out.println("0 - Sair ");
			iniciar = ler.nextInt();

			switch (iniciar)
			{
				case 1:
					System.out.println("\n============== ESCOLHA UMA OPÇÃO ==============");
					System.out.println("1 - Carregar Entradas Enunciado ");
					System.out.println("2 - Carregar Bug Parametro ");
					System.out.println("3 - Carregar Bug Aleatorio ");
					System.out.println("4 - Carregar Bug Complexa ");
					System.out.println("5 - Carregar Entradas 2");
					System.out.println("0 - Voltar ");
					iniciar = ler.nextInt();

					switch (iniciar)
					{
						case 1:
							path = "src\\files\\entradas.txt";
							ReadFile();
							break;

						case 2:
							path = "src\\files\\bug_parametro.txt";
							ReadFile();
							break;

						case 3:
							path = "src\\files\\bug_aleatorio.txt";
							ReadFile();
							break;

						case 4:
							path = "src\\files\\bug_complexa.txt";
							ReadFile();
							break;

						case 5:
							path = "src\\files\\entradas2.txt";
							ReadFile();
							break;

						case 0:
							return;
					}
					break;
				case 2:
					calcRoute();
					break;
				case 3:
					showRoute();
					break;
				case 0:
					System.out.println("Saindo ...");
					System.exit(0);
					break;
			}
		}
	}

	private void showRoute()
	{
		int cont = 1;
		int recompensa = 0;
		System.out.println("\n=================== #Entregas do dia# ===================");
		for (RotasEntrega re : rotas)
		{
			Rota r = re.getRotaMenor();

			System.out
				.print("\n=================== =================== A " + cont + "º rota a ser realizada é de 'A' até '" + r.getDestino() + "' =================== ===================");
			System.out.print("\nAs rotas alternativas são:" + getAlternativeRoutes(re.getRotas()));

			System.out.println("\n\nA rota Principal é: \n" + printRoute(r));

			boolean isTrue = false;
			if (r.getRecompensa() == 1)
			{
				isTrue = true;
			}

			System.out.println(
				"\nCom a chegada estimada de " + r.getDistancia() + " unidades de tempo no destino " + "'"
					+ r.getDestino() + "'" + " e o valor para esta entrega será de " + (isTrue ?
					r.getRecompensa() + " real" : r.getRecompensa() + " reais") + ".");
			recompensa += r.getRecompensa();
			cont++;
		}
		System.out.println("\nO lucro total do dia: " + recompensa + ".");
	}

	private String getAlternativeRoutes(List<Rota> rotas)
	{
		StringBuilder sb = new StringBuilder();

		for (Rota r : rotas)
		{
			sb.append("\n" + printRoute(r));
		}
		return sb.toString();
	}


	private String printRoute(Rota r)
	{
		StringBuilder s = new StringBuilder();

		for (String d : r.getPontos())
		{
			s.append(d + "->");
		}
		s = s.replace(s.length() - 2, s.length(), ".");

		return s.toString();
	}

	private static void ReadFile()
	{
		try
		{
			LerEntradas read = new LerEntradas();
			entradas = read.readFile(path);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Formato de arquivo inválido!");
		}
	}

	private static void calcRoute() throws CloneNotSupportedException
	{
		Entregas matriz = new Entregas(entradas);
		rotas = matriz.processarEntregas();
	}

}


