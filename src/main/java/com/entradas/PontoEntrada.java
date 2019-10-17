package com.entradas;

import java.util.ArrayList;
import java.util.List;

public class PontoEntrada {

    private String nome;
    private List<Integer> distancias = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Integer> getDistancias() {
        return distancias;
    }

    public void setDistancias(List<Integer> distancias) {
        this.distancias = distancias;
    }
}
