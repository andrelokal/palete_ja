package com.paleteja.br.paleteja.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.adapter.PaletesUsuarioAdapter;
import com.paleteja.br.paleteja.model.PaletsUserModel;
import com.paleteja.br.paleteja.repository.PaletsUserRepository;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.Utils;

import java8.util.function.Consumer;
import java8.util.function.Function;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeCompradorActivity extends BaseActivity {
    ListView listaPalets;
    PaletesUsuarioAdapter adapter;
    List<PaletsUserModel> paletsUserModel;
    TextView precoMedio;
    TextView precoMedio2;
    PaletsUserRepository paletsUserRepository;
    LinearLayout fazerOferta;

    int filter_raio;
    String filter_UF;

    Double[] latLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paletsUserRepository = new PaletsUserRepository();

        setContentView(R.layout.activity_home_comprador);

        filter_raio = getIntent().getIntExtra("raio", 100);
        filter_UF = getIntent().getStringExtra("UF");

        //Toolbar
        setMenuToolbar(true);
        toolbar.setTitle("PaleteJa");
        //searchToolbar.setVisibility(View.VISIBLE);
        //toolbar.setOverflowIcon(android.R.drawable.ic_search_category_default);

        precoMedio = findViewById(R.id.preco_medio);
        precoMedio2 = findViewById(R.id.preco_medio2);
        fazerOferta = findViewById(R.id.fazer_oferta);

        //pega a lista do layout.
        listaPalets = findViewById(R.id.comprador_paletes);

        //Cria um novo array
        paletsUserModel = new ArrayList<>();

        //seta o adapter;
        adapter = new PaletesUsuarioAdapter(
                HomeCompradorActivity.this, R.layout.activity_home_comprador, paletsUserModel);
        listaPalets.setAdapter(adapter);

        latLong = new Double[] {null, null};

        //latLong = Utils.getLatLong(HomeCompradorActivity.this);

        getPrecoMedio();
        getListPalets();
        setClickItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getPrecoMedio() {
        api.getPrecoMedio(this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean success = response.getBoolean("Sucesso");

                    if (success) {

                        String valorAtual = response.getString("PrecoMedio");
                        precoMedio.setText(valorAtual);
                        precoMedio2.setText(valorAtual);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setClickItem() {
        listaPalets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeCompradorActivity.this, UsuarioListarPaletesActivity.class);
                intent.putExtra("paletes", paletsUserModel.get(position).getPaletes());
                intent.putExtra("vendedor", paletsUserModel.get(position).getNome());
                startActivity(intent);
            }
        });

        fazerOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeCompradorActivity.this, CompradorOfertarActivity.class);
                intent.putExtra("avulsa", true);
                startActivity(intent);
            }
        });
    }

    private void getListPalets() {

        if (filter_UF == "" || filter_UF == null) {
            filter_UF = "SP";
        }

        JSONObject params = new JSONObject();

        try {
            params.put("estado", filter_UF);
            params.put("cidade", null);
            params.put("raio", filter_raio);
            params.put("latitude", latLong[0]);
            params.put("longitude", latLong[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        api.getUserPalets(params, HomeCompradorActivity.this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                List<PaletsUserModel> resultado = new ArrayList<>();
                Gson gson = new Gson();


                try {
                    JSONArray usuariosJson = response.getJSONArray("Usuarios");
                    for(int i  = 0; i < usuariosJson.length(); i ++) {
                        resultado.add(gson.fromJson(usuariosJson.getJSONObject(i).toString(), PaletsUserModel.class));
                        paletsUserModel.addAll(resultado);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, null);

//        paletsUserRepository.todos(filter_UF, null, filter_raio, latitude, longitude).thenAccept(new Consumer<List<PaletsUserModel>>() {
//            @Override
//            public void accept(List<PaletsUserModel> resultado) {
//                paletsUserModel.addAll(resultado);
//                adapter.notifyDataSetChanged();
//            }
//        }).exceptionally(new Function<Throwable, Void>() {
//            @Override
//            public Void apply(Throwable throwable) {
//                Log.e("-->", "Ops., algo deu errado.", throwable);
//
//                return null;
//            }
//        });
    }






}
