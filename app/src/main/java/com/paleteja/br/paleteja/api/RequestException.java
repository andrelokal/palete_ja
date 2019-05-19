package com.paleteja.br.paleteja.api;

import com.android.volley.VolleyError;

import java.util.Arrays;
import java.util.Map;

public class RequestException extends RuntimeException {
    CompletableJsonRequest<?> request;
    VolleyError cause;

    public RequestException(CompletableJsonRequest<?> request, VolleyError cause) {
        this.cause = cause;
        this.request = request;
    }

    @Override
    public void printStackTrace() {
        printRequest();
        super.printStackTrace();
    }

    public void printRequest() {
        request.printRequest();

        System.err.println("======= Response =======");
        System.err.println("Status Code: " + cause.networkResponse.statusCode);
        System.err.println("Time (ms): " + cause.networkResponse.networkTimeMs);
        System.err.println("Headers: ");

        for(Map.Entry<String, String> i: cause.networkResponse.headers.entrySet()) {
            System.err.println("    " + i.getKey() + ": " + i.getValue());
        }

        System.err.println("Data: ");
        System.err.println("    " + new String(cause.networkResponse.data));

        System.err.println("========================");
    }
}
