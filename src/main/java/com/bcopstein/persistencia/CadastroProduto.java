package com.bcopstein.persistencia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CadastroProduto implements Iterable<ProdutoDTO> {
    private List<ProdutoDTO> produtos;
    private ArqProdutos arqProds;

    public CadastroProduto(ArqProdutos arqProds) {
        this.arqProds = arqProds;
        this.produtos = new ArrayList<>();
        this.arqProds.carregaProdutos(produtos);
    }

    public ProdutoDTO recuperaPorCodigo(int codigo) {
        return produtos.stream().filter(prod -> prod.getCodigo() == codigo).findAny().get();
    }

    @Override
    public Iterator<ProdutoDTO> iterator() {
        return produtos.iterator();
    }
}