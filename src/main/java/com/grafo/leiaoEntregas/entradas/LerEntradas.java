package com.grafo.leiaoEntregas.entradas;
import com.grafo.leiaoEntregas.Distancia;
import com.grafo.leiaoEntregas.Entradas;
import com.grafo.leiaoEntregas.PontoGrafo;
import com.grafo.leiaoEntregas.PontoEntrega;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LerEntradas {
    private static Entradas entradas;

    /**
     * Faz a leitura do arquivo, quebrando em linhas e colunas
     */

    public Entradas readFile(String path) throws Exception {

        entradas = new Entradas();

        FileReader arquivo = new FileReader(path);
        BufferedReader lerArq = new BufferedReader(arquivo);
        String linha = null;

        List<PontoGrafo> pontoGrafos = new ArrayList<>();
        List<PontoEntrega> pontosEntregas = new ArrayList<>();

        int linhaMatriz = 0;
        boolean matriz = false;

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
                    if(colunas.size() != entradas.getTamanhoMatrizEntrada())
                        throw new Exception();

                    PontoGrafo ponto = new PontoGrafo();
                    ponto.setNome(col.replaceAll("'", ""));

                    pontoGrafos.add(ponto);
                    continue;
                }

                //Verifica se é uma linha da matriz distancia
                if (!isRoute(colunas)) {
                    PontoGrafo ponto = pontoGrafos.get(colunaMatriz);
                    List<Distancia> distancias = ponto.getDistancias();

                    Integer distancia = Integer.valueOf(col);

                    Distancia dist = new Distancia();
                    try{
                        if(linhaMatriz == colunaMatriz && distancia != 0)
                        {
                            continue;
                        }
                    } catch (Exception e)
                    {
                        System.out.println("A distancia do ponto " + ponto.getNome() + " é diferente de zero!");
                    }

                    dist.setNome(pontoGrafos.get(linhaMatriz).getNome());
                    dist.setDistancia(distancia);

                    distancias.add(dist);

                    ponto.setDistancias(distancias);

                    matriz = true;
                    colunaMatriz++;
                    continue;
                }

                //Verifica se é uma linha da matriz entregas
            }

            if (matriz) {
                linhaMatriz++;
            }
        }
        if(entradas.getTamanhoMatrizEntrada() != linhaMatriz)
            throw new Exception();

        entradas.setPontosEntrada(pontoGrafos);
        entradas.setPontosEntrega(pontosEntregas);

        return entradas;
    }

    /**
     * Verifica se parte analisada é um caracter alfabetico,
     * caso seja, sinifica que é um ponto do grafo
     */

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
