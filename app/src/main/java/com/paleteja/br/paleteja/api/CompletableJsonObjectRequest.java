package com.paleteja.br.paleteja.api;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class CompletableJsonObjectRequest extends CompletableJsonRequest<JSONObject> {
    public CompletableJsonObjectRequest(String url, String requestBody) {
        super(url, requestBody);
    }

    public CompletableJsonObjectRequest(int method, String url, String requestBody) {
        super(method, url, requestBody);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, getBodyContentType()));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }
}
