package com.paleteja.br.paleteja.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.model.OfertasAvulsaModel;
import com.paleteja.br.paleteja.model.PaletsVendedorModel;
import com.paleteja.br.paleteja.repository.PaletsVendedorRepository;
import com.paleteja.br.paleteja.ui.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import java8.util.function.Consumer;
import java8.util.function.Function;

public class OfertasAvulsaDetalhesActivity extends BaseActivity {

    OfertasAvulsaModel oferta;

    TextView nomeComprador;
    TextView tipoEntrega;
    TextView enderecoEntrega;
    TextView valorDesejado;
    TextView quantidade;
    TextView email;
    TextView celular;
    TextView telefone;
    Button accept;
    LinearLayout contentEnderecoEntrega;

    ListView listView;
    ArrayList<String> paletsNames;

    PaletsVendedorRepository paleteRepository;
    ArrayList<PaletsVendedorModel> paletsVendedorModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aceitar_oferta_avulsa_vendedor);

        setMenuToolbar(false);
        setTitle("Dados da Oferta");

        paletsNames = new ArrayList<String>();
        paleteRepository = new PaletsVendedorRepository();
        paletsVendedorModel = new ArrayList<>();

        oferta = (OfertasAvulsaModel) getIntent().getSerializableExtra("data");

        //nomeComprador = findViewById(R.id.nome_comprador);
        tipoEntrega = findViewById(R.id.tipo_entrega);
        enderecoEntrega = findViewById(R.id.endereco_entrega);
        contentEnderecoEntrega = findViewById(R.id.content_endereco_entrega);
        valorDesejado = findViewById(R.id.valor_desejado);
        quantidade = findViewById(R.id.quantidade);
        //email = findViewById(R.id.email_comprador);
        //telefone = findViewById(R.id.telefone);
        //celular = findViewById(R.id.celular);
        accept = findViewById(R.id.btn_accept);

        //nomeComprador.setText(oferta.getUsuario().getNome());
        tipoEntrega.setText(oferta.getTipoEntrega());
        valorDesejado.setText("R$ "+Float.toString(oferta.getPrecoPago()));
        quantidade.setText(Integer.toString(oferta.getQuantidade()));
        //email.setText(oferta.getUsuario().getEmail());
        //telefone.setText(oferta.getUsuario().getTelefone());
        //celular.setText(oferta.getUsuario().getCelular());


        if(oferta.getEnderecoEntrega() != "") {
            contentEnderecoEntrega.setVisibility(View.VISIBLE);
            enderecoEntrega.setText(oferta.getEnderecoEntrega());
        }

        getListPalets();

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //paleteToChoose();
                Intent intent = new Intent(OfertasAvulsaDetalhesActivity.this, PaletesVendedorActivity.class);
                intent.putExtra("accept_offer", true);
                intent.putExtra("oferta_id", oferta.getIdOferta());
                startActivity(intent);
            }
        });

    }

//    public void paleteToChoose(){
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OfertasAvulsaDetalhesActivity.this);
//        LayoutInflater inflater = getLayoutInflater();
//        View convertView = inflater.inflate(R.layout.list_custom, null);
//        alertDialog.setView(convertView);
//        alertDialog.setTitle("Selecione um palete para essa oferta.");
//        ListView lv = convertView.findViewById(R.id.listView1);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                OfertasAvulsaDetalhesActivity.this, android.R.layout.simple_list_item_1, paletsNames);
//        lv.setAdapter(adapter);
//        alertDialog.show();
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                //showMessage("position: "+i);
//
//                confirmOffer(paletsVendedorModel.get(i).getPaleteId());
//            }
//        });
//
//    }
//
//    public void confirmOffer(final int paleteId){
//        new AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Confirmar Oferta")
//                .setMessage("tem certeza que gostaria de confirmar essa oferta ?")
//                .setPositiveButton("Sim", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        JSONObject object = new JSONObject();
//
//                        try {
//
//                            object.put("IdPedido", oferta.getIdOferta());
//                            object.put("IdPalete", paleteId);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        api.acceptOffer(object, OfertasAvulsaDetalhesActivity.this, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                showMessage("Oferta aceita com sucesso.");
//                                Intent intent = new Intent(OfertasAvulsaDetalhesActivity.this, OfertasAvulsaActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.putExtra("EXIT", true);
//                                startActivity(intent);
//                            }
//                        }, null);
//                    }
//
//                })
//                .setNegativeButton("Não", null)
//                .show();
//    }

    private void getListPalets() {
        paleteRepository.meus().thenAccept(new Consumer<List<PaletsVendedorModel>>() {
            @Override
            public void accept(List<PaletsVendedorModel> list) {

                if (list.size() > 0) {
                    paletsVendedorModel.addAll(list);

                    for (int i = 0; i < list.size(); i++){
                        paletsNames.add(
                                list.get(i).getPaleteId()
                                        +" - "+list.get(i).getTipoProdutoDesc()
                                        +" R$ "+list.get(i).getPrecoAlvo()
                        );
                    }

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
