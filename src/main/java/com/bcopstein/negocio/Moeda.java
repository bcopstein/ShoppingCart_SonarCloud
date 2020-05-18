package com.bcopstein.negocio;

public class Moeda {
    private float valor;
    private TipoMoeda moeda;

    public Moeda(float valor, TipoMoeda moeda) {
        this.valor = valor;
        this.moeda = moeda;
    }

    public TipoMoeda getMoeda(){
        return moeda;
    }

    public float getValorReferencia(){
        return valor;
    }

    public float valorEmReais(){
        return valor * moeda.valor;
    }

    public String toString(){
        return "R$ "+valorEmReais();
    }
}