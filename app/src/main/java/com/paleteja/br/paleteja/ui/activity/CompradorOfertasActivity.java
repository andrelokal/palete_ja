package com.paleteja.br.paleteja.ui.activity;

import android.os.Bundle;

import com.android.volley.Response;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.adapter.OfertasCompradorAdapter;
import com.paleteja.br.paleteja.model.OfertasCompradorModel;
import com.paleteja.br.paleteja.ui.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CompradorOfertasActivity extends BaseActivity {


    SwipeMenuListView listaOfertas;
    OfertasCompradorAdapter adapter;
    List<OfertasCompradorModel> ofertasCompradorModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprador_ofertas);

        setMenuToolbar(true);
        setTitle("Minhas ofertas");

        listaOfertas = findViewById(R.id.comprador_ofertas);

        ofertasCompradorModel = new ArrayList<>();

        adapter = new OfertasCompradorAdapter(CompradorOfertasActivity.this, R.layout.activity_comprador_ofertas, ofertasCompradorModel);
        listaOfertas.setAdapter(adapter);

        getOffers();
        actions();



    }

    public void actions(){

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,0xCE)));
//                openItem.setWidth(90);
//                openItem.setTitle("Open");
//                openItem.setTitleSize(18);
//                openItem.setTitleColor(Color.WHITE);
//                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                //deleteItem.setBackground(R.drawable.red_background);
                deleteItem.setWidth(150);
                deleteItem.setIcon(R.drawable.ic_delete_red);
                menu.addMenuItem(deleteItem);
            }
        };

        listaOfertas.setMenuCreator(creator);

        listaOfertas.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        final int idOferta = ofertasCompradorModel.get(position).getIdOferta();

                        api.deleteOffer(idOferta, CompradorOfertasActivity.this, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                showMessage("Oferta apagada com sucesso.");
                                ofertasCompradorModel.remove(idOferta);
                                adapter.notifyDataSetChanged();
                            }
                        });

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

    }

    public void getOffers(){

        showLoad();

        api.getBuyerOffers(null, CompradorOfertasActivity.this, new Response.Listener<JSONObject>() {
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

                            OfertasCompradorModel ofertasGson = gson.fromJson(item, OfertasCompradorModel.class);
                            ofertasCompradorModel.add(ofertasGson);
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
