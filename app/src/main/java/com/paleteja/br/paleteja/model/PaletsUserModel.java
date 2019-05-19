package com.paleteja.br.paleteja.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PaletsUserModel {

    private int UsuarioId;
    private String Nome;
    private String PrecoMedio;
    private ArrayList<PaleteModel> Paletes;

    //Paletes

    //private int PaleteId;
    //private int TipoProduto;
    //private String TipoProdutoDesc;
    //private float PrecoAlvo;
    //private float PrecoPromocao;
    //private boolean NotaFiscal;
    //private int PrecoMedio;
    //private float Saldo;
    //private String Descricao;
    //private int TabelaA;
    //private int TabelaB;
    //private int TabelaC;
    //private int QuantidadeA;
    //private int QuantidadeB;
    //private int QuantidadeC;


    public int getUsuarioId() {
        return UsuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        UsuarioId = usuarioId;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getPrecoMedio() {
        return PrecoMedio;
    }

    public void setPrecoMedio(String precoMedio) {
        PrecoMedio = precoMedio;
    }

    public ArrayList<PaleteModel> getPaletes() {
        return Paletes;
    }

    public void setPaletes(ArrayList<PaleteModel> paletes) {
        Paletes = paletes;
    }
}