package com.paleteja.br.paleteja.repository;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.paleteja.br.paleteja.api.Api;
import com.paleteja.br.paleteja.model.PaletsUserModel;
import java8.util.concurrent.CompletableFuture;
import java8.util.function.Function;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.paleteja.br.paleteja.utils.StringUtils.strOrNull;

public class PaletsUserRepository {
    public CompletableFuture<List<PaletsUserModel>> todos(String estado, String cidade, Integer raio, Double latitude, Double longitude) {

        JSONObject params = new JSONObject();

        try {
            params.put("estado", estado);
            params.put("cidade", cidade);
            params.put("raio", raio);
            params.put("latitude", latitude);
            params.put("longitude", longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Api.make(Request.Method.POST, "/api/Palete/ObterUsuariosPaletes", params).thenApply(new Function<JSONObject, List<PaletsUserModel>>() {
            @Override
            public List<PaletsUserModel> apply(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("Sucesso") == false) {
                        throw new RuntimeException("Não foi possível obter lista de paletes por usuário.");
                    }

                    List<PaletsUserModel> resultado = new ArrayList<>();
                    Gson gson = new Gson();
                    JSONArray usuariosJson = jsonObject.getJSONArray("Usuarios");

                    for(int i  = 0; i < usuariosJson.length(); i ++) {
                        resultado.add(gson.fromJson(usuariosJson.getJSONObject(i).toString(), PaletsUserModel.class));
                    }

                    return resultado;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
