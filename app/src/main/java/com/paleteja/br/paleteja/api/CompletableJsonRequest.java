package com.paleteja.br.paleteja.api;

import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.JsonRequest;
import java8.util.concurrent.CompletableFuture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class CompletableJsonRequest<T> extends CompletableFuture<T> {
    private JsonRequest<T> request;
    private Map<String, String> headers;

    {
        headers = new HashMap<>();
    }

    public CompletableJsonRequest(String url, String requestBody) {
        request = new MyJsonRequest(url, requestBody, null, null);
    }

    public CompletableJsonRequest(int method, String url, String requestBody) {
        request = new MyJsonRequest(method, url, requestBody, null, null);
    }

    protected abstract Response<T> parseNetworkResponse(NetworkResponse response);

    public void deliverError(VolleyError error) {
        completeExceptionally(new RequestException(this, error));
    }

    protected void deliverResponse(T response) {
        complete(response);
    }

    public JsonRequest<T> getRequest() {
        return request;
    }

    public void printRequest() {
        System.err.println("======= Request =======");
        System.err.println("Método: " + String.valueOf(getMethod()));
        System.err.println("URL: " + getUrl());
        System.err.println("Headers: ");
        System.err.println("    ContentType: " + getBodyContentType());

        for(Map.Entry<String, String> i: getHeaders().entrySet()) {
            System.err.println("    " + i.getKey() + ": " + i.getValue());
        }

        System.err.println("Body: ");
        System.err.println("    " + Arrays.toString(getBody()));
    }

    /*
     * Itens delegados ao JsonRequest
     */
    public int getMethod() {
        return request.getMethod();
    }

    public Request<?> setTag(Object tag) {
        return request.setTag(tag);
    }

    public Object getTag() {
        return request.getTag();
    }

    public Response.ErrorListener getErrorListener() {
        return request.getErrorListener();
    }

    public int getTrafficStatsTag() {
        return request.getTrafficStatsTag();
    }

    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        return request.setRetryPolicy(retryPolicy);
    }

    public void addMarker(String tag) {
        request.addMarker(tag);
    }

    public Request<?> setRequestQueue(RequestQueue requestQueue) {
        return request.setRequestQueue(requestQueue);
    }

    public Request<?> setSequence(int sequence) {
        return request.setSequence(sequence);
    }

    public int getSequence() {
        return request.getSequence();
    }

    public String getUrl() {
        return request.getUrl();
    }

    public String getOriginUrl() {
        return request.getOriginUrl();
    }

    public void setRedirectUrl(String redirectUrl) {
        request.setRedirectUrl(redirectUrl);
    }

    public String getCacheKey() {
        return request.getCacheKey();
    }

    public Request<?> setCacheEntry(Cache.Entry entry) {
        return request.setCacheEntry(entry);
    }

    public Cache.Entry getCacheEntry() {
        return request.getCacheEntry();
    }

    public void cancel() {
        request.cancel();
    }

    public boolean isCanceled() {
        return request.isCanceled();
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBodyContentType() {
        return request.getBodyContentType();
    }

    public Request<?> setShouldCache(boolean shouldCache) {
        return request.setShouldCache(shouldCache);
    }

    public boolean shouldCache() {
        return request.shouldCache();
    }

    public Request.Priority getPriority() {
        return request.getPriority();
    }

    public int getTimeoutMs() {
        return request.getTimeoutMs();
    }

    public RetryPolicy getRetryPolicy() {
        return request.getRetryPolicy();
    }

    public void markDelivered() {
        request.markDelivered();
    }

    public boolean hasHadResponseDelivered() {
        return request.hasHadResponseDelivered();
    }

    public int compareTo(Request<T> other) {
        return request.compareTo(other);
    }

    public String toString() {
        return request.toString();
    }

    public String getPostBodyContentType() {
        return request.getPostBodyContentType();
    }

    public byte[] getPostBody() {
        return request.getPostBody();
    }

    public byte[] getBody() {
        return request.getBody();
    }

    private class MyJsonRequest extends JsonRequest<T> {
        private MyJsonRequest(String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
            super(url, requestBody, listener, errorListener);
        }

        private MyJsonRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
            super(method, url, requestBody, listener, errorListener);
        }

        @Override
        protected Response<T> parseNetworkResponse(NetworkResponse response) {
            return CompletableJsonRequest.this.parseNetworkResponse(response);
        }

        @Override
        public void deliverError(VolleyError error) {
            CompletableJsonRequest.this.deliverError(error);
        }

        @Override
        protected void deliverResponse(T response) {
            CompletableJsonRequest.this.deliverResponse(response);
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            return CompletableJsonRequest.this.getHeaders();
        }
    }
}
