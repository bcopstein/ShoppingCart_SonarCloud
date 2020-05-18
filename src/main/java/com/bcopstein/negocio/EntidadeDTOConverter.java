package com.bcopstein.negocio;

import com.bcopstein.persistencia.ProdutoDTO;
import com.bcopstein.persistencia.VendaDTO;

// Usa singleton para servir de exemplo
// Neste caso métodos static simples podem ser suficientes
public class EntidadeDTOConverter {
    private static EntidadeDTOConverter converter = null;

    private EntidadeDTOConverter() {
    }

    public static EntidadeDTOConverter getInstance(){
        if (converter == null){
            converter = new EntidadeDTOConverter();
        }
        return converter;
    }

    public ProdutoDTO prod2dto(final Produto p) {
        final int codigo = p.getCodigo();
        final String descricao = p.getDescricao();
        final float valor = p.getPrecoUnitario().getValorReferencia();
        final String moeda = p.getPrecoUnitario().getMoeda().name();
        return new ProdutoDTO(codigo,descricao,valor,moeda);
    }

    public Produto dto2prod(final ProdutoDTO p){
        final int codigo = p.getCodigo();
        final String descricao = p.getDescricao();
        final float valorRef = p.getPrecoUnitario();
        final String nomeMoeda = p.getMoeda();
        return Produto.criaProduto(codigo,descricao,new Moeda(valorRef,TipoMoeda.valueOf(nomeMoeda)));
    }

    public VendaDTO cart2DTO(Cart c){
        return null;
    }
}