package com.paleteja.br.paleteja.model;

import java.io.Serializable;

public class PaletsVendedorModel implements Serializable {
    private Integer PaleteId;
    private Integer TipoProduto;
    private String TipoProdutoDesc;
    private Float PrecoAlvo;
    private Float PrecoPromocao;
    private Boolean NotaFiscal;
    private Float PrecoMedio;
    private Integer Saldo;
    private String Descricao;
    private Integer TabelaA;
    private Integer TabelaB;
    private Integer TabelaC;
    private Integer QuantidadeA;
    private Integer QuantidadeB;
    private Integer QuantidadeC;

    public Integer getPaleteId() {
        return PaleteId;
    }

    public void setPaleteId(Integer paleteId) {
        PaleteId = paleteId;
    }

    public Integer getTipoProduto() {
        return TipoProduto;
    }

    public void setTipoProduto(Integer tipoProduto) {
        TipoProduto = tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        TipoProduto = tipoProduto.getValor();
    }

    public String getTipoProdutoDesc() {
        return TipoProdutoDesc;
    }

    public void setTipoProdutoDesc(String tipoProdutoDesc) {
        TipoProdutoDesc = tipoProdutoDesc;
    }

    public Float getPrecoAlvo() {
        return PrecoAlvo;
    }

    public void setPrecoAlvo(Float precoAlvo) {
        PrecoAlvo = precoAlvo;
    }

    public Float getPrecoPromocao() {
        return PrecoPromocao;
    }

    public void setPrecoPromocao(Float precoPromocao) {
        PrecoPromocao = precoPromocao;
    }

    public Boolean isNotaFiscal() {
        return NotaFiscal;
    }

    public void setNotaFiscal(Boolean notaFiscal) {
        NotaFiscal = notaFiscal;
    }

    public Float getPrecoMedio() {
        return PrecoMedio;
    }

    public void setPrecoMedio(Float precoMedio) {
        PrecoMedio = precoMedio;
    }

    public Integer getSaldo() {
        return Saldo;
    }

    public void setSaldo(Integer saldo) {
        Saldo = saldo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Integer getTabelaA() {
        return TabelaA;
    }

    public void setTabelaA(Integer tabelaA) {
        TabelaA = tabelaA;
    }

    public Integer getTabelaB() {
        return TabelaB;
    }

    public void setTabelaB(Integer tabelaB) {
        TabelaB = tabelaB;
    }

    public Integer getTabelaC() {
        return TabelaC;
    }

    public void setTabelaC(Integer tabelaC) {
        TabelaC = tabelaC;
    }

    public Integer getQuantidadeA() {
        return QuantidadeA;
    }

    public void setQuantidadeA(Integer quantidadeA) {
        QuantidadeA = quantidadeA;
    }

    public Integer getQuantidadeB() {
        return QuantidadeB;
    }

    public void setQuantidadeB(Integer quantidadeB) {
        QuantidadeB = quantidadeB;
    }

    public Integer getQuantidadeC() {
        return QuantidadeC;
    }

    public void setQuantidadeC(Integer quantidadeC) {
        QuantidadeC = quantidadeC;
    }

}