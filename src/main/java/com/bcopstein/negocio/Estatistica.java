package com.bcopstein.negocio;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Estatistica implements Observer {
    public enum Operacao {
        INS, REM, END
    }

    private int contIns;
    private int contRem;
    private List<Integer[]> estatisticas;

    public Estatistica() {
        contIns = 0;
        contRem = 0;
        estatisticas = new LinkedList<>();
    }

    @Override
    public void update(Observable o, Object arg) {
        Operacao op = (Operacao) arg;
        switch (op) {
            case INS:
                contIns++;
                break;
            case REM:
                contRem++;
                break;
            case END:
                Integer[] tupla = new Integer[2];
                tupla[0] = contIns;
                tupla[1] = contRem;
                estatisticas.add(tupla);
                contIns = 0;
                contRem = 0;
                break;
            default:
                break;
        }
    }

    // O indice 0 retorna a quantidade de vendas considerada
    // O indice 1 retorna a média de inserções
    // O indice 2 retorna a média de remoções
    public float[] getEstatisticas() {
        if (estatisticas.size() > 0) {
            float resp[] = new float[3];
            float somaIns = estatisticas.stream().mapToInt(tupla -> tupla[0]).sum();
            float somaRem = estatisticas.stream().mapToInt(tupla -> tupla[1]).sum();
            resp[0] = estatisticas.size();
            resp[1] = somaIns / estatisticas.size();
            resp[2] = somaRem / estatisticas.size();
            return resp;
        } else {
            float resp[] = { 0, 0 };
            return resp;
        }
    }
}