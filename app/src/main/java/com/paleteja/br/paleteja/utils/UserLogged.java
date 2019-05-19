package com.paleteja.br.paleteja.utils;

import com.paleteja.br.paleteja.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by caiomarques on 10/04/2018.
 */

public class UserLogged {

    private static UserLogged instance = null;
    private static UserModel usuario = null;

    public static UserLogged getInstance() {
        if (instance == null) {
            usuario = new UserModel();
            return instance = new UserLogged();
        } else {
            return instance;
        }
    }

    public void setUsuario(UserModel usuario) {
        UserLogged.usuario = usuario;
    }

    public UserModel getUsuario() {
        return UserLogged.usuario;
    }

    public static UserModel RegisterUser( JSONObject json ) throws JSONException {
        String token = json.getString("Token");
        String nome = json.getString("Nome");
        int TipoUsuario = json.getInt("TipoUsuario");
        int Id = json.getInt("Id");

        UserModel user = new UserModel();
        user.setNome(nome);
        user.setId(Id);
        user.setTipoUsuario(TipoUsuario);
        user.setToken(token);
        return user;
    }

}
