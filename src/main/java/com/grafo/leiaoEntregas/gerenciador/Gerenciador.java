package com.grafo.leiaoEntregas.gerenciador;

import com.grafo.leiaoEntregas.Entradas;
import com.grafo.leiaoEntregas.entradas.LerEntradas;
import com.grafo.leiaoEntregas.entregas.Entregas;
import com.grafo.leiaoEntregas.entregas.Rota;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Gerenciador {

    private static Entradas entradas = new Entradas();
    private static List<Rota> rotas = new ArrayList<>();

    private static String path = "src\\files\\entradas.txt";

    public Gerenciador() throws Exception {

        Scanner ler = new Scanner(System.in);
        int iniciar;

        while (true) {
            System.out.println("\n============== LEILÃO DE ENTREGAS =============");
            System.out.println("1 - Carregar Entradas ");
            System.out.println("2 - Entregas ");
            System.out.println("3 - Mostrar Rotas ");
            System.out.println("0 - Sair ");
            iniciar = ler.nextInt();

            switch (iniciar) {
                case 1:
                    ReadFile();
                    break;
                case 2:
                    calcRoute();
                    break;
                case 3:
                    showRoute();
                    break;
                case 0:
                    break;
            }
        }
    }

    private void showRoute() {
    	System.out.println(rotas);
    }

    private static void ReadFile() {
        try {
            LerEntradas read = new LerEntradas();
            entradas = read.readFile(path);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Formato de arquivo inválido!");
        }
    }

    private static void calcRoute() {
        Entregas matriz = new Entregas(entradas);

        rotas = matriz.processarEntregas();
    }

}


