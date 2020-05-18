package com.bcopstein.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import com.bcopstein.persistencia.CadastroProduto;

public class Cart extends Observable implements Iterable<ItemCart> {
    private List<ItemCart> itens;
    private CadastroProduto cadProds;
    private Desconto desconto;

    public Cart(CadastroProduto cadProds) {
        this.cadProds = cadProds;
        this.itens = new ArrayList<>();
        this.desconto = new DescontoPadrao(this);
    }

    public ItemCart addItem(int codigo, int quantidade) {
        Produto prod = EntidadeDTOConverter.getInstance().dto2prod(cadProds.recuperaPorCodigo(codigo)); // Dependência
                                                                                                        // implícita por
                                                                                                        // singleton
        ItemCart item = new ItemCart(prod, quantidade);
        itens.add(item);

        // Notifica observadores
        this.setChanged();
        this.notifyObservers(Estatistica.Operacao.INS);

        return item;
    }

    public ItemCart getLastItem() {
        if (itens.size() == 0) {
            return null;
        } else {
            return itens.get(itens.size() - 1);
        }
    }

    public ItemCart removeLast() {
        if (itens.size() == 0) {
            return null;
        } else {
            // Notifica observadores
            this.setChanged();
            this.notifyObservers(Estatistica.Operacao.REM);
            return itens.remove(itens.size() - 1);
        }
    }

    public void closeCart() {
            // Notifica observadores
            this.setChanged();
            this.notifyObservers(Estatistica.Operacao.END);
    }

    public int getQtdadeItens() {
        return itens.size();
    }

    public Collection<Produto> produtosDiferentes() {
        Set<Produto> diferentes = new HashSet<>();
        itens.forEach(item -> diferentes.add(item.getProduto()));
        return diferentes;
    }

    @Override
    public Iterator<ItemCart> iterator() {
        return itens.iterator();
    }

    public float getSubTotal() {
        return (float) (itens.stream().mapToDouble(it -> it.getProduto().getPrecoUnitarioEmReais()).sum());
    }

    public float getValorDesconto() {
        return desconto.desconto();
    }

    public void setDesconto(Desconto desconto) {
        this.desconto = desconto;
    }

    public float getTotal() {
        return getSubTotal() - getValorDesconto();
    }
}