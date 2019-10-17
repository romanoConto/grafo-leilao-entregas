package com.gerenciador;

import com.entradas.Entradas;
import com.entradas.PontoEntrada;
import com.entradas.PontoEntrega;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gerenciador {
    private static Entradas entradas = new Entradas();

    public void addNo() throws Exception {

        String path = "src\\files\\entradas.txt";

        FileReader arquivo = new FileReader(path);
        BufferedReader lerArq = new BufferedReader(arquivo);
        String linha = null;

        List<PontoEntrada> pontoEntradas = new ArrayList<>();
        List<PontoEntrega> pontosEntregas = new ArrayList<>();

        int linhaMatriz = 0;

        while ((linha = lerArq.readLine()) != null) {

            int colunaMatriz = 0;

            List<String> colunas = Arrays.asList(linha.split(","));

            //Verifica se é o tamanho da matriz distancia
            if (colunas.size() == 1 && entradas.getTamanhoMatrizEntrada() == 0) {
                entradas.setTamanhoMatrizEntrada(Integer.parseInt(colunas.get(0)));
                continue;
            }

            //Verifica se é o tamanho da matriz de entregas
            if (colunas.size() == 1 && entradas.getTamanhoMatrizEntrega() == 0) {
                entradas.setTamanhoMatrizEntrega(Integer.parseInt(colunas.get(0)));
                continue;
            }

            if (!linha.contains("'") && isRoute(colunas)) {
                PontoEntrega caminho = new PontoEntrega();

                caminho.setPartida(Integer.parseInt(colunas.get(0)));
                caminho.setDestino(colunas.get(1));
                caminho.setBonus(Integer.parseInt(colunas.get(2)));

                pontosEntregas.add(caminho);
                continue;
            }
            for (String col : colunas) {

                //Verifica se é o cabeçalho da matriz com o nome dos pontos
                if (col.contains("'")) {

                    PontoEntrada ponto = new PontoEntrada();
                    ponto.setNome(col.replaceAll("'", ""));

                    pontoEntradas.add(ponto);
                    continue;
                }

                //Verifica se é uma linha da matriz distancia
                if (!isRoute(colunas)) {
                    PontoEntrada ponto = pontoEntradas.get(colunaMatriz);
                    List<Integer> distancias = ponto.getDistancias();

                    distancias.add(Integer.valueOf(col));

                    ponto.setDistancias(distancias);

                    colunaMatriz++;
                    continue;
                }

                //Verifica se é uma linha da matriz entregas
            }
        }
        entradas.setPontosEntrada(pontoEntradas);
        entradas.setPontosEntrega(pontosEntregas);
        int i = 0;
    }

    private static boolean isRoute(List<String> partes) {
        for (String parte : partes) {
            char[] ps = parte.toCharArray();
            for (int i = 0; i < ps.length; i++) {
                if (Character.isAlphabetic(ps[i])) {
                    return true;
                }
            }
        }
        return false;
    }
}


