package com.paleteja.br.paleteja.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class OfertasAvulsaModel implements Serializable {
    public static final long serialVersionUID = 1L;

    public Integer IdOferta;
    public Integer TipoProduto;
    public Integer Quantidade;
    public float PrecoPago;
    public String TipoProdutoDescricao;
    public String TipoEntrega;
    public String EnderecoEntrega;
    public OfertasAvulsaCompradorModel Usuario;

    public float getPrecoPago() {
        return PrecoPago;
    }

    public void setPrecoPago(float precoPago) {
        PrecoPago = precoPago;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getIdOferta() {
        return IdOferta;
    }

    public void setIdOferta(Integer idOferta) {
        IdOferta = idOferta;
    }

    public Integer getTipoProduto() {
        return TipoProduto;
    }

    public void setTipoProduto(Integer tipoProduto) {
        TipoProduto = tipoProduto;
    }

    public Integer getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        Quantidade = quantidade;
    }

    public String getTipoProdutoDescricao() {
        return TipoProdutoDescricao;
    }

    public void setTipoProdutoDescricao(String tipoProdutoDescricao) {
        TipoProdutoDescricao = tipoProdutoDescricao;
    }

    public String getTipoEntrega() {
        return TipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        TipoEntrega = tipoEntrega;
    }

    public String getEnderecoEntrega() {
        return EnderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        EnderecoEntrega = enderecoEntrega;
    }

    public OfertasAvulsaCompradorModel getUsuario() {
        return Usuario;
    }

    public void setUsuario(OfertasAvulsaCompradorModel Usuario) {
        this.Usuario = Usuario;
    }


}
