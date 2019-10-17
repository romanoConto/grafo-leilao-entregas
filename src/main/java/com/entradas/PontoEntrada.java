package com.entradas;

import java.util.List;

public class PontoEntrada {

    private String nome;
    private List<Integer> distancias;

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
