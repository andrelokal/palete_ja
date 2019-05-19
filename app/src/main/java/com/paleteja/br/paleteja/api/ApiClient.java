package com.paleteja.br.paleteja.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.paleteja.br.paleteja.utils.Constants;

import org.json.JSONObject;

public class ApiClient extends BaseApi {

    private String GMapsApiKey = "AIzaSyAq9xx3E8HSlIhifnsPYjZ8lLhUkkwMbMQ";
    private static final String gmpsByAddress = "https://maps.googleapis.com/maps/api/geocode/json?address=";

    private static final String CADASTRAR = "Usuario/Cadastrar";
    private static final String CADASTRAR_MAIS_COMPRADOR = "Usuario/CadastrarDadosComprador";
    private static final String CADASTRAR_MAIS_VENDEDOR = "Usuario/CadastrarDadosVendedor";
    private static final String LOGIN = "Usuario/Autenticar";
    private static final String GET_USER_PALETES = "Palete/ObterUsuariosPaletes";
    private static final String GET_PRECO_MEDIO = "Palete/PrecoMedioHoje";
    private static final String GET_MEUS_PALETES = "Palete/MeusPaletes";
    private static final String POST_PEDIDO_AVULSO = "Palete/RealizarOfertaAvulsa";
    private static final String FAZER_OFERTA = "Palete/RealizarOferta";
    private static final String FAZER_OFERTA_AVULSA = "Palete/RealizarOfertaAvulsa";
    private static final String GET_OFERTAS_COMPRADOR = "Palete/ObterOfertasComprador";
    private static final String GET_OFERTAS_AVULSOS = "Palete/ObterOfertasAvulsas";
    private static final String ACCEPT_OFFER = "Palete/AceitarOferta";
    private static final String RECOVER_PASSWORD = "Usuario/EsqueciSenha";
    private static final String MINHAS_OFERTAS = "Palete/ObterMinhasOfertas";
    private static final String APAGAR_OFERTA_COMPRADOR = "Palete/ExcluirOferta";
    private static final String TOTAL_VENDIDO = "Palete/ObterPaletesVendidos";
    private static final String PERFIL_USUARIO = "Usuario/Obter";
    private static final String UPDATE_USUARIO = "Usuario/Atualizar";

    public void getLatLgtByAddress(String address, Response.Listener listener){
        this.setContext(context);
        this.api_url_custom = gmpsByAddress;
        this.custom_api = true;
        this.execute(Request.Method.GET, address+"&key="+GMapsApiKey, null, listener,null);
    }

    public void getBuyerOffers(JSONObject request, Context context, Response.Listener listener) {

        this.setContext(context);
        this.execute(Request.Method.GET, GET_OFERTAS_COMPRADOR, request, listener,null);
    }

    public void getUser(Context context, Response.Listener listener) {

        this.setContext(context);
        this.execute(Request.Method.GET, PERFIL_USUARIO, null, listener,null);
    }

    public void newOffer(boolean ofertaAvulsa, JSONObject request, Context context, Response.Listener listener) {

        this.setContext(context);

        if(ofertaAvulsa){
            this.execute(Request.Method.POST, FAZER_OFERTA_AVULSA, request, listener,null);
        } else {
            this.execute(Request.Method.POST, FAZER_OFERTA, request, listener,null);
        }

    }

    public void updateUser(JSONObject request, Context context, Response.Listener listener) {

        this.setContext(context);
        this.execute(Request.Method.POST, UPDATE_USUARIO, request, listener,null);
    }

    public void deleteOffer(int idOferta, Context context, Response.Listener listener) {

        this.setContext(context);
        this.execute(Request.Method.POST, APAGAR_OFERTA_COMPRADOR+"/idOferta="+idOferta, null, listener,null);
    }

    public void login(JSONObject request, Context context, Response.Listener listener) {

        this.setContext(context);
        this.execute(Request.Method.POST, LOGIN, request, listener,null);
    }

    public void recover_password(JSONObject request, Context context, Response.Listener listener) {

        this.setContext(context);
        this.execute(Request.Method.POST, RECOVER_PASSWORD, request, listener,null);
    }

    public void account(JSONObject request, Context context, Response.Listener listener) {
        this.setContext(context);
        this.execute(Request.Method.POST, CADASTRAR, request, listener,null);
    }

    public void accountPhoto(JSONObject request, Context context, Response.Listener listener) {
        this.setContext(context);
        this.execute(Request.Method.POST, CADASTRAR_MAIS_COMPRADOR, request, listener,null);
    }

    public void accountCompl(JSONObject request, Context context, Response.Listener listener) {

        this.setContext(context);

        if(Constants.UserLoginData.REGISTER_TYPE == 1){
            this.execute(Request.Method.POST, CADASTRAR_MAIS_VENDEDOR, request, listener,null);
        } else {
            this.execute(Request.Method.POST, CADASTRAR_MAIS_COMPRADOR, request, listener,null);
        }
    }

    public void getUserPalets(JSONObject request, Context context, Response.Listener listener, Response.ErrorListener jsonError){
        this.setContext(context);
        this.execute(Request.Method.POST, GET_USER_PALETES, request, listener, jsonError);
    }

    public void getMeusPaletes(Context context, Response.Listener listener){
        this.setContext(context);
        this.execute(Request.Method.GET, GET_MEUS_PALETES, null, listener, null);
    }

    public void getPrecoMedio(Context context, Response.Listener listener) {
        this.setContext(context);
        this.execute(Request.Method.GET, GET_PRECO_MEDIO, null, listener, null);
    }

    public void PedidoAvulso(Context context, Response.Listener listener, Response.ErrorListener jsonError){
        this.setContext(context);
        this.execute(Request.Method.POST, POST_PEDIDO_AVULSO, null, listener, jsonError);
    }

    public void getOffers(Context context, Response.Listener listener, Response.ErrorListener jsonError){
        this.setContext(context);
        this.execute(Request.Method.GET, GET_OFERTAS_AVULSOS, null, listener, jsonError);
    }

    public void acceptOffer(JSONObject request, Context context, Response.Listener listener, Response.ErrorListener jsonError){
        this.setContext(context);
        this.execute(Request.Method.PUT, ACCEPT_OFFER, request, listener, jsonError);
    }

    public void getTotalVenda(Context context, Response.Listener listener){
        this.setContext(context);
        this.execute(Request.Method.GET, TOTAL_VENDIDO, null, listener, null);
    }

}
