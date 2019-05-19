package com.paleteja.br.paleteja.model;

import java.io.Serializable;

public class OfertasAvulsaCompradorModel implements Serializable {
    public static final long serialVersionUID = 1L;

    public int UsuarioId;
    public String Nome;
    public Float PrecoMedio;
    public String Endereco;
    public String Telefone;
    public String Celular;
    public String Email;

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

    public Float getPrecoMedio() {
        return PrecoMedio;
    }

    public void setPrecoMedio(Float precoMedio) {
        PrecoMedio = precoMedio;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

}
