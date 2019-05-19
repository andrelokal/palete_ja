package com.paleteja.br.paleteja.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.adapter.OfertasAvulsasAdapter;
import com.paleteja.br.paleteja.adapter.PaletesVendedorAdapter;
import com.paleteja.br.paleteja.model.OfertasAvulsaCompradorModel;
import com.paleteja.br.paleteja.model.OfertasAvulsaModel;
import com.paleteja.br.paleteja.model.PaletsVendedorModel;
import com.paleteja.br.paleteja.ui.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OfertasAvulsaActivity extends BaseActivity {

    ListView list;
    OfertasAvulsasAdapter adapter;
    ArrayList<OfertasAvulsaModel> arrayList;

    int palete_id;
    int oferta_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas_avulsas);

        palete_id = getIntent().getIntExtra("id_palete", 0);
        oferta_id = getIntent().getIntExtra("id_oferta", 0);

        //foi aceita uma oferta
        if(palete_id > 0 && oferta_id > 0){
            acceptOffer();
        }

        //Toolbar
        setMenuToolbar(false);
        setTitle("Ofertas Avulsas");

        list = findViewById(R.id.ofertas_avulsa);

        arrayList = new ArrayList<>();

        adapter = new OfertasAvulsasAdapter(OfertasAvulsaActivity.this, R.layout.activity_ofertas_avulsas, arrayList);
        list.setAdapter(adapter);

        getUniqueOffers();
        setClicks();

    }

    public void acceptOffer(){

        JSONObject object = new JSONObject();

        try {

            object.put("IdPedido", oferta_id);
            object.put("IdPalete", palete_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        api.acceptOffer(object, OfertasAvulsaActivity.this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                showMessage("Oferta aceita com sucesso.");
            }
        }, null);

    }

    private void setClicks() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(OfertasAvulsaActivity.this, OfertasAvulsaDetalhesActivity.class);
                intent.putExtra("data", arrayList.get(position));
                startActivity(intent);
            }
        });
    }

    private void getUniqueOffers(){

        api.getOffers(this,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();

                        try {
                            JSONArray data = response.getJSONArray("Ofertas");

                            int total = data.length();

                            if(total > 0) {
                                for (int i = 0; i < total; i++) {

                                    String item = data.getJSONObject(i).toString();

                                    OfertasAvulsaModel object = gson.fromJson(item, OfertasAvulsaModel.class);
                                    arrayList.add(object);

                                }

                            } else {
                                showMessage("Nenhum resultado encontrado.");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                    }
        }, null);
    }

}
