package com.paleteja.br.paleteja.repository;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.paleteja.br.paleteja.api.Api;
import com.paleteja.br.paleteja.model.PaletsVendedorModel;
import java8.util.concurrent.CompletableFuture;
import java8.util.function.Function;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaletsVendedorRepository {
    public CompletableFuture<List<PaletsVendedorModel>> meus() {
        return Api.make(Request.Method.GET, "/api/Palete/MeusPaletes").thenApply(new Function<JSONObject, List<PaletsVendedorModel>>() {
            @Override
            public List<PaletsVendedorModel> apply(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("Sucesso") == false) {
                        throw new RuntimeException("Não foi possível obter lista de paletes.");
                    }

                    List<PaletsVendedorModel> resultado = new ArrayList<>();
                    Gson gson = new Gson();
                    JSONArray paletesJson = jsonObject.getJSONArray("Paletes");

                    for(int i  = 0; i < paletesJson.length(); i ++) {
                        resultado.add(gson.fromJson(paletesJson.getJSONObject(i).toString(), PaletsVendedorModel.class));
                    }

                    return resultado;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public CompletableFuture<JSONObject> cadastrar(PaletsVendedorModel paletsVendedorModel) {
        paletsVendedorModel.setPaleteId(null);

        return atualizar(paletsVendedorModel);
    }

    public CompletableFuture<JSONObject> atualizar(PaletsVendedorModel paletsVendedorModel) {
        return Api.make(Request.Method.POST, "/api/Palete/Salvar", new Gson().toJson(paletsVendedorModel));
    }
}
