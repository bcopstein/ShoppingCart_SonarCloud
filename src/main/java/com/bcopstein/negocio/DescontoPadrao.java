package com.bcopstein.negocio;

public class DescontoPadrao implements Desconto {
    public static final int QTDADE_PARA_DESCONTO = 5;
    public static final float TX_DESCONTO = 0.05F;
    private Cart cart;

    public DescontoPadrao(Cart cart){
        this.cart = cart;
    }
    
    @Override
    public float desconto() {
        if (cart.produtosDiferentes().size() > QTDADE_PARA_DESCONTO){
            return cart.getSubTotal() * TX_DESCONTO;
        }else{
            return 0F;
        }
    }    
}