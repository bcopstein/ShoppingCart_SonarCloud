package com.bcopstein;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bcopstein.negocio.Moeda;
import com.bcopstein.negocio.Produto;
import com.bcopstein.negocio.TipoMoeda;

import org.junit.jupiter.api.Test;

public class ProdutoTest {
    @Test
    public void testaValorOriginalEmReais(){
        Produto p = Produto.criaProdutoEmReais(10,"Eca",100);
        float rEsp = 100F;
        float rObs = p.getPrecoUnitarioEmReais();
        assertEquals(rEsp,rObs);
    }
    @Test
    public void testaValorOriginalEmDolar(){
        Produto p = Produto.criaProduto(10,"Eca",new Moeda(100,TipoMoeda.Dolar));
        float rEsp = 500F;
        float rObs = p.getPrecoUnitarioEmReais();
        assertEquals(rEsp,rObs);
    }
}