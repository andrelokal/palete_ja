package com.paleteja.br.paleteja.api;

/**
 * Created by caio.mmartins on 16/05/2017.
 */

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CustomRequest extends Request<JSONObject> {

    private Listener<JSONObject> listener;
    private Map<String, String> params;
    private Map<String, String> headers_params;


    public CustomRequest(int method, String url, Map<String, String> header,  Map<String, String> params,
                         Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        this.headers_params = header;
    }

    protected Map<String, String> getParams() {
        return params;
    };

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() {

        HashMap<String, String> headers = new HashMap<>();
        //headers.put("Content-Type", "application/json;");
        //headers.put("Charset", "UTF-8");
        headers.putAll(headers_params);
        return headers;

    }

    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub
        Log.d("Debug response json", response.toString());
        listener.onResponse(response);
    }
}