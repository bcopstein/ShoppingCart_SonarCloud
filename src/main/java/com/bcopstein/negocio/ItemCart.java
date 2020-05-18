package com.bcopstein.negocio;

public class ItemCart{
    private Produto produto;
    private int quantidade;

    ItemCart(Produto produto, int quantidade){
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto(){
        return this.produto;
    }

    public int getQuantidade(){
        return this.quantidade;
    }

    public float getValorDoItem(){
        return this.getProduto().getPrecoUnitario().valorEmReais() * this.getQuantidade();
    }
}