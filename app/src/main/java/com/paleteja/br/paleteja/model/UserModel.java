package com.paleteja.br.paleteja.model;


public class UserModel {

    public String token;
    public String Nome;
    public int TipoUsuario;
    public int Id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getTipoUsuario() {
        return TipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        TipoUsuario = tipoUsuario;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

}
