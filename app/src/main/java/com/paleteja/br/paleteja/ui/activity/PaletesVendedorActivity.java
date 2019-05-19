package com.paleteja.br.paleteja.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.adapter.PaletesVendedorAdapter;
import com.paleteja.br.paleteja.model.PaletsVendedorModel;
import com.paleteja.br.paleteja.repository.PaletsVendedorRepository;
import com.paleteja.br.paleteja.ui.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java8.util.function.Consumer;
import java8.util.function.Function;

import java.util.ArrayList;
import java.util.List;

public class PaletesVendedorActivity extends BaseActivity {
    ListView listaPalets;
    PaletesVendedorAdapter adapter;
    ArrayList<PaletsVendedorModel> paletsVendedorModel;
    PaletsVendedorRepository paleteRepository;

    boolean loaded = false;
    boolean accept_offer = false;
    int oferta_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paletes_vendedor);


        accept_offer = getIntent().getBooleanExtra("accept_offer", false);
        oferta_id = getIntent().getIntExtra("oferta_id", 0);

        paleteRepository = new PaletsVendedorRepository();

        //Toolbar
        setMenuToolbar(false);
        setTitle("Minhas ofertas");

        if(accept_offer){
            setTitle("Selecione a oferta");
        }

        listaPalets = findViewById(R.id.vendedor_paletes);

        paletsVendedorModel = new ArrayList<>();

        adapter = new PaletesVendedorAdapter(
                PaletesVendedorActivity.this, R.layout.activity_paletes_vendedor, paletsVendedorModel);
        listaPalets.setAdapter(adapter);

        loaded = true;

        getListPalets();
        setClicks();
    }



    @Override
    protected void onResume() {
        super.onResume();

        if(!loaded){
            paletsVendedorModel.clear();
            getListPalets();
        }

    }

    private void setClicks() {

        listaPalets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Se for para aceitar a oferta volta para tela de ofertas avulsas
                if(accept_offer) {
                    confirmOffer(position);
                } else {
                    Intent intent = new Intent(PaletesVendedorActivity.this, EditarPaletesVendedorActivity.class);
                    intent.putExtra("palete", paletsVendedorModel.get(position));
                    startActivity(intent);
                }


            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaletesVendedorActivity.this, EditarPaletesVendedorActivity.class);
                startActivity(intent);
            }
        });
    }

    private void confirmOffer(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setMessage("Tem certeza que quer confirmar a oferta ?")
                .setPositiveButton("Sim",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(PaletesVendedorActivity.this, OfertasAvulsaActivity.class);
                        intent.putExtra("id_palete", paletsVendedorModel.get(position).getPaleteId());
                        intent.putExtra("id_oferta", oferta_id);

                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void getListPalets() {
        paleteRepository.meus().thenAccept(new Consumer<List<PaletsVendedorModel>>() {
            @Override
            public void accept(List<PaletsVendedorModel> list) {
                if (list.size() > 0) {
                    paletsVendedorModel.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    showMessage("Nenhum resultado encontrado.");
                }
            }
        }).exceptionally(new Function<Throwable, Void>() {
            @Override
            public Void apply(Throwable throwable) {
                throwable.getCause().printStackTrace();

                return null;
            }
        });
    }

}
