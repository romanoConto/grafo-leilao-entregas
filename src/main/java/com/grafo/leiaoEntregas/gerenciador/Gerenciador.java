package com.grafo.leiaoEntregas.gerenciador;

import com.grafo.leiaoEntregas.Entradas;
import com.grafo.leiaoEntregas.entradas.LerEntradas;

import java.util.Scanner;

public class Gerenciador {

    private static Entradas entradas = new Entradas();

    private static String path = "src\\files\\entradas.txt";

	public Gerenciador() throws Exception {

		Scanner ler = new Scanner(System.in);
		int iniciar;

		while(true)
		{
			System.out.println("\n============== LEILÃO DE ENTREGAS =============");
			System.out.println("1 - Carregar Entradas ");
			System.out.println("2 - Entregas ");
			System.out.println("0 - Sair ");
			iniciar = ler.nextInt();

			switch (iniciar)
			{
				case 1:
					ReadFile();
					break;
				case 2:
					break;
				case 0:
					break;
			}
		}
	}

	private static void ReadFile()
	{
		try {
			LerEntradas read = new LerEntradas();
			entradas = read.readFile(path);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Formato de arquivo inválido!");
		}
	}

	private static void calcRoute()
	{
		
	}

}


