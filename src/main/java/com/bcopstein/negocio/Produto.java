package com.bcopstein.negocio;

// Usa static factory method para facilitar a criação de produtos vendidos
// em reais
public class Produto{
    private final int codigo;
    private final String descricao;
    private Moeda precoUnitario;

    public static Produto criaProdutoEmReais(int codigo,String descricao,float precoUnitario){
        Moeda preco = new Moeda(precoUnitario,TipoMoeda.Real);
        return new Produto(codigo,descricao,preco);
    }

    public static Produto criaProduto(int codigo,String descricao,Moeda moeda){
        return new Produto(codigo,descricao,moeda);
    }

    private Produto(final int codigo, final String descricao, final Moeda precoUnitario) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Moeda getPrecoUnitario() {
        return this.precoUnitario;
    }

    public float getPrecoUnitarioEmReais() {
        return this.precoUnitario.valorEmReais();
    }

    public void setPrecoUnitario(final Moeda preco) {
        this.precoUnitario = preco;
    }

    public String toString(){
        return "Codigo: "+this.codigo+
               ", Descricao: "+this.descricao+
               ", Preco R$ "+this.precoUnitario;
    }
}