package com.paleteja.br.paleteja.model;

import java.io.Serializable;

public class PaleteModel implements Serializable {
    public static final long serialVersionUID = 1L;

    public int PaleteId;
    public int TipoProduto;
    public String TipoProdutoDesc;
    public Float PrecoAlvo;
    public Float PrecoPromocao;
    public boolean NotaFiscal;
    public Float PrecoMedio;
    public int Saldo;
    public String Descricao;
    public int TabelaA;
    public int TabelaB;
    public int TabelaC;
    public int QuantidadeA;
    public int QuantidadeB;
    public int QuantidadeC;

    public int getPaleteId() {
        return PaleteId;
    }

    public void setPaleteId(int paleteId) {
        PaleteId = paleteId;
    }

    public int getTipoProduto() {
        return TipoProduto;
    }

    public void setTipoProduto(int tipoProduto) {
        TipoProduto = tipoProduto;
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

    public boolean isNotaFiscal() {
        return NotaFiscal;
    }

    public void setNotaFiscal(boolean notaFiscal) {
        NotaFiscal = notaFiscal;
    }

    public Float getPrecoMedio() {
        return PrecoMedio;
    }

    public void setPrecoMedio(Float precoMedio) {
        PrecoMedio = precoMedio;
    }

    public int getSaldo() {
        return Saldo;
    }

    public void setSaldo(int saldo) {
        Saldo = saldo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public int getTabelaA() {
        return TabelaA;
    }

    public void setTabelaA(int tabelaA) {
        TabelaA = tabelaA;
    }

    public int getTabelaB() {
        return TabelaB;
    }

    public void setTabelaB(int tabelaB) {
        TabelaB = tabelaB;
    }

    public int getTabelaC() {
        return TabelaC;
    }

    public void setTabelaC(int tabelaC) {
        TabelaC = tabelaC;
    }

    public int getQuantidadeA() {
        return QuantidadeA;
    }

    public void setQuantidadeA(int quantidadeA) {
        QuantidadeA = quantidadeA;
    }

    public int getQuantidadeB() {
        return QuantidadeB;
    }

    public void setQuantidadeB(int quantidadeB) {
        QuantidadeB = quantidadeB;
    }

    public int getQuantidadeC() {
        return QuantidadeC;
    }

    public void setQuantidadeC(int quantidadeC) {
        QuantidadeC = quantidadeC;
    }


}
