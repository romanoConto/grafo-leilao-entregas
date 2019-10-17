package com.gerenciador;

import com.entradas.Entradas;
import com.entradas.PontoEntrada;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        int i = 0;
        while ((linha = lerArq.readLine()) != null) {

            boolean valores = false;
            List<String> partes = Arrays.asList(linha.split(","));

            if (partes.size() == 1 && entradas.getTamanhoMatrizEntrada() == 0) {
                entradas.setTamanhoMatrizEntrada(Integer.parseInt(partes.get(0)));
                continue;
            }

            if (partes.size() == 1 && entradas.getTamanhoMatrizEntrega() == 0) {
                entradas.setTamanhoMatrizEntrega(Integer.parseInt(partes.get(0)));
                continue;
            }

            int j = 0;

            for (String s : partes) {

                if (s.contains("'")) {

                    PontoEntrada ponto = new PontoEntrada();
                    ponto.setNome(s.replaceAll("'", ""));

                    pontoEntradas.add(ponto);
                    continue;
                }

                if (!isRoute(partes)) {
                    PontoEntrada ponto = pontoEntradas.get(j);

                    ponto.setDistancias();
                    continue;
                }

                if (isRoute(partes)) {
                    continue;
                }
            }

            if (!isRoute(partes)) {
                i++;
            }
        }
    }

    private static boolean isRoute(List<String> partes) {
        for (String parte : partes) {
            char[] ps = parte.toCharArray();
            for (int i = 0; i < ps.length; i++) {
                if (Character.isAlphabetic(ps[i]))
                    return true;
            }
        }
        return false;
    }
}


