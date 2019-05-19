package com.paleteja.br.paleteja.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.adapter.OfertasCompradorAdapter;
import com.paleteja.br.paleteja.adapter.OfertasVendedorAdapter;
import com.paleteja.br.paleteja.model.OfertasCompradorModel;
import com.paleteja.br.paleteja.model.OfertasVendedorModel;
import com.paleteja.br.paleteja.ui.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VendedorOfertasActivity extends BaseActivity {


    ListView listaOfertas;
    OfertasVendedorAdapter adapter;
    List<OfertasVendedorModel> ofertasVendedorModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor_ofertas);

        setMenuToolbar(false);
        setTitle("Minhas ofertas aceitas");

        listaOfertas = findViewById(R.id.vendedor_ofertas);

        ofertasVendedorModel = new ArrayList<>();

        adapter = new OfertasVendedorAdapter(VendedorOfertasActivity.this, R.layout.activity_vendedor_ofertas, ofertasVendedorModel);
        listaOfertas.setAdapter(adapter);

        getOffers();


    }

    public void getOffers(){

        showLoad();

        api.getBuyerOffers(null, VendedorOfertasActivity.this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideLoad();

                Gson gson = new Gson();

                try {
                    JSONArray data = response.getJSONArray("Ofertas");

                    int total = data.length();

                    if(total > 0) {
                        for (int i = 0; i < total; i++) {

                            String item = data.getJSONObject(i).toString();

                            OfertasVendedorModel ofertasGson = gson.fromJson(item, OfertasVendedorModel.class);
                            ofertasVendedorModel.add(ofertasGson);
                        }

                    } else {
                        showMessage("Nenhum resultado encontrado.");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();

            }
        });

    }

}
