package com.bcopstein;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bcopstein.negocio.Moeda;
import com.bcopstein.negocio.Produto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProdutoMockitoTest {
    private Moeda moedaReais;
    private Moeda moedaDolar;

    @BeforeEach
    public void setup() {
        moedaReais = mock(Moeda.class);
        moedaDolar = mock(Moeda.class);
        when(moedaReais.valorEmReais()).thenReturn(100F);
        when(moedaDolar.valorEmReais()).thenReturn(500F);
        when(moedaReais.getValorReferencia()).thenReturn(100F);
        when(moedaDolar.getValorReferencia()).thenReturn(100F);
    }

    @Test
    public void testaValorOriginalEmReais(){
        final Produto p = Produto.criaProduto(10, "Eca", moedaReais);
        final float rEsp = 100F;
        final float rObs = p.getPrecoUnitarioEmReais();
        assertEquals(rEsp, rObs);
        verify(moedaReais).valorEmReais();
    }

    @Test
    public void testaValorOriginalEmDolar() {
        final Produto p = Produto.criaProduto(10, "Eca", moedaDolar);
        final float rEsp = 500F;
        final float rObs = p.getPrecoUnitarioEmReais();
        assertEquals(rEsp,rObs);
        verify(moedaDolar).valorEmReais();
    }
}