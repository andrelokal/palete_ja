package com.paleteja.br.paleteja.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.Constants;
import com.paleteja.br.paleteja.utils.UserLogged;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BaseApi {

    private String api_url = Constants.BASE_URL;
    public String api_url_custom = "";
    private RequestQueue queue;
    //private GsonRequest request;
    private Gson gson;

    public boolean custom_api = false;

    Context context;
    RequestQueue mRequestQueue;

    Boolean hasUrl = true;

    public BaseApi() {
        //this.url = UrlHelper.BASE_URL;
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    }


    public void setContext(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    //Request.Method.GET
    @SuppressLint("LongLogTag")
    protected void execute(int method, String action,
                           final JSONObject jsonRequest,
                           final Response.Listener listener,
                           Response.ErrorListener errorListener) {

        if( custom_api ){
            api_url = api_url_custom;
            custom_api = false;
        } else {
            api_url = Constants.BASE_URL;
        }

        String novaUrl = "";

        if(hasUrl) {
            //novaUrl = api_url + action + Constants.ACCESS_TOKEN;
        } else {
            //novaUrl = action;
        }

        novaUrl = api_url + action;

        final Map<String, String> headers = new HashMap<String, String>();

        //token do usuario.
        final String token = UserLogged.getInstance().getUsuario().getToken();
        //token = "fb8be620-36ed-41fd-b3be-519aa67a6148";

        headers.put("Content-Type", "application/json");

        if(jsonRequest != null) {
            Log.d("Debug request json", jsonRequest.toString());
        }
        Log.d("Debug request Method", method+"");
        Log.d("Debug URL", novaUrl+"");
        Log.d("Debug Token", "Debug TOKEN : "+token);


        JsonObjectRequest request = new JsonObjectRequest(
                method,
                novaUrl,
                jsonRequest,
                listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(context, "Erro, por favor tente novamente.", Toast.LENGTH_LONG);
                        toast.show();
                        BaseActivity.hideLoad();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                if(token != null) {
                    headers.put("UserToken", token);
                }
                return headers;
            }
            @Override
            protected void deliverResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.d("Debug response json", response.toString());
                listener.onResponse(response);
            }
        };

        mRequestQueue = Volley.newRequestQueue(this.context);
        mRequestQueue.add(request);

    }


    private Response.ErrorListener ErrorRequest() {


        Log.d("Opa", "Deu Erro.");
        return null;
    }



}
