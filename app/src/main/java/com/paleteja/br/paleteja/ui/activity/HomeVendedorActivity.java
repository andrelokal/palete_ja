package com.paleteja.br.paleteja.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.adapter.PaletesVendedorAdapter;
import com.paleteja.br.paleteja.model.OfertasAvulsaModel;
import com.paleteja.br.paleteja.model.PaletsVendedorModel;
import com.paleteja.br.paleteja.repository.PaletsVendedorRepository;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.CurrencyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import java8.util.function.Consumer;
import java8.util.function.Function;

public class HomeVendedorActivity extends BaseActivity {
    ListView listaPalets;
    PaletesVendedorAdapter adapter;
    ArrayList<PaletsVendedorModel> paletsVendedorModel;
    PaletsVendedorRepository paleteRepository;

    int total_paletes = 0;
    int total_ofertas = 0;
    double total_venda_valor = 0.0;
    int total_venda_quantidade = 0;

    TextView totalPaletes;
    TextView totalOfertas;
    TextView totalValorVenda;
    TextView totalQuantidade;
    TextView totalPrecoMedio;
    TextView totalOfertasAceitas;
    LinearLayout meusPaletes;
    LinearLayout ofertasAceitas;
    LinearLayout ofertasVenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_vendedor);

        //Toolbar
        setMenuToolbar(true);
        setTitle("Home Vendedor");

        final DecimalFormat currencyInstance = CurrencyUtils.getInstance();


        totalPaletes = findViewById(R.id.paletes_cadastrados);
        totalOfertas = findViewById(R.id.ofertas_venda);
        totalOfertasAceitas = findViewById(R.id.ofertas_aceitas);
        totalValorVenda = findViewById(R.id.vendidos_total);
        totalQuantidade = findViewById(R.id.vendidos_quantidade);
        totalPrecoMedio = findViewById(R.id.preco_medio);
        meusPaletes = findViewById(R.id.paletes_cadastrados_wrapper);
        ofertasAceitas = findViewById(R.id.ofertas_aceitas_wrapper);
        ofertasVenda = findViewById(R.id.ofertas_venda_wrapper);


        meusPaletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVendedorActivity.this, PaletesVendedorActivity.class);
                startActivity(intent);
            }
        });

        ofertasAceitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVendedorActivity.this, VendedorOfertasActivity.class);
                startActivity(intent);
            }
        });

        ofertasVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVendedorActivity.this, OfertasAvulsaActivity.class);
                startActivity(intent);
            }
        });

        //total paletes
        api.getMeusPaletes(this,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    boolean success = response.getBoolean("Sucesso");

                    if(success) {

                        Double precoMedio = response.getDouble("PrecoMedio");

                        JSONArray data = response.getJSONArray("Paletes");

                        total_paletes = data.length();
                        totalPaletes.setText(Integer.toString(total_paletes));
                        totalPrecoMedio.setText("Preço Médio: R$ "+currencyInstance.format(precoMedio));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //total ofertas
        api.getOffers(this,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray data = response.getJSONArray("Ofertas");

                            total_ofertas = data.length();
                            totalOfertas.setText(Integer.toString(total_ofertas));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, null);


        //total Venda
        api.getTotalVenda(this,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            total_venda_valor = response.getDouble("Preco");
                            total_venda_quantidade = response.getInt("Quantidade");

                            totalValorVenda.setText("R$ "+currencyInstance.format(total_venda_valor));
                            totalQuantidade.setText(Integer.toString(total_venda_quantidade));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


        api.getBuyerOffers(null, HomeVendedorActivity.this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideLoad();

                try {
                    JSONArray data = response.getJSONArray("Ofertas");

                    int total = data.length();
                    totalOfertasAceitas.setText(Integer.toString(total));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }





}
