package com.paleteja.br.paleteja.api;

import com.android.volley.toolbox.Volley;
import com.paleteja.br.paleteja.App;
import com.paleteja.br.paleteja.utils.UserLogged;

import org.json.JSONObject;

import java.util.Map;

import java8.util.concurrent.CompletableFuture;

import static com.paleteja.br.paleteja.utils.StringUtils.strOrNull;

public class Api {
    private static final String hostname = "http://paleteja.web6202.kinghost.net";

    public static CompletableFuture<JSONObject> make(int method, String url) {
        return make(method, url, (String) null);
    }

    public static CompletableFuture<JSONObject> make(int method, String url, Map<String, String> params) {
        JSONObject jsonRequestBody = null;

        if (params != null) {
            jsonRequestBody = new JSONObject(params);
        }

        return make(method, url, jsonRequestBody);
    }

    public static CompletableFuture<JSONObject> make(int method, String url, JSONObject jsonRequestBody) {
        return make(method, url, strOrNull(jsonRequestBody));
    }

    public static CompletableFuture<JSONObject> make(int method, String url, String requestBody) {
        CompletableJsonObjectRequest request = new CompletableJsonObjectRequest(method, hostname + url, requestBody);
        request.getHeaders().put("UserToken", UserLogged.getInstance().getUsuario().getToken());
        Volley.newRequestQueue(App.getContext()).add(request.getRequest());

        return request;
    }
}
