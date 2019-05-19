package com.paleteja.br.paleteja.model;

public enum TipoProduto {
    NOVO(1, "Novo"),
    USADO(2, "Usado");

    private Integer valor;
    private String label;

    TipoProduto(Integer valor, String label) {
        this.valor = valor;
        this.label = label;
    }

    public static TipoProduto parseInt(Integer valor) {
        for(TipoProduto tipoProduto : values()) {
            if(tipoProduto.valor.equals(valor)) {
                return tipoProduto;
            }
        }

        return null;
    }

    public static TipoProduto parseStr(CharSequence label) {
        return parseStr(label.toString());
    }

    public static TipoProduto parseStr(String label) {
        for(TipoProduto tipoProduto : values()) {
            if(tipoProduto.label.equals(label)) {
                return tipoProduto;
            }
        }

        return null;
    }

    public Integer getValor() {
        return valor;
    }

    public String getLabel() {
        return label;
    }
}
