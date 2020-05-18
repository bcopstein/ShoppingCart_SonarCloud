package com.bcopstein.negocio;

public enum TipoMoeda {
    Real(1.0F), 
    Peso(0.2F),
    Dolar(5.0F),
    Euro(5.2F);

    public final float valor;

    private TipoMoeda(float valor){
        this.valor = valor;
    }
}