package com.paleteja.br.paleteja.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.MaskMoney;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CompradorOfertarActivity extends BaseActivity {

    public LinearLayout address;
    public Button fazer_oferta;
    public EditText qtda;
    public EditText valor_pago;
    public EditText endereco;
    public int tipoEntrega = 1;
    public Double[] latLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprador_ofertar);

        //Toolbar
        //setMenuToolbar();
        //toolbar.setTitle("Ofertar Palete");

        latLong = new Double[]{0.0,0.0};

        //avulsa
        final boolean ofertaAvulsa = getIntent().getBooleanExtra("avulsa", false);

        address = findViewById(R.id.address);
        fazer_oferta = findViewById(R.id.fazer_oferta);
        qtda = findViewById(R.id.qtda);
        valor_pago = findViewById(R.id.valor_pago);
        valor_pago.addTextChangedListener(new MaskMoney(valor_pago));
        endereco = findViewById(R.id.endereco);

        //habilita apenas se for oferta avulsa.
        if(ofertaAvulsa){
            valor_pago.setEnabled(true);
        }

        final int paleteId = getIntent().getIntExtra("id", 0);
        String produto = getIntent().getStringExtra("produto");
        final Float preco = getIntent().getFloatExtra("preco", 0);

        TextView produtoTitle = findViewById(R.id.produto_title);
        produtoTitle.setText(produto);
        valor_pago.setText(Float.toString(preco));


        fazer_oferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject object = new JSONObject();

                showLoad();

//                Endereco = endereco_edit.getText().toString();
//
//                api.getLatLgtByAddress(Endereco, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//                            String success = response.getString("status");
//
//                            if (success.equals("OK")){
//
//
//                                JSONArray results = response.getJSONArray("results");
//                                JSONObject result = results.getJSONObject(0);
//                                JSONObject geometry = result.getJSONObject("geometry");
//                                JSONObject location = geometry.getJSONObject("location");
//
//                                Double lat = location.getDouble("lat");
//                                Double lng = location.getDouble("lng");
//
//                                latLong = new Double[]{lat, lng};
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        updateUser();
//
//                    }
//
//                });



                try {

                    if(!ofertaAvulsa){
                        object.put("PaleteId", paleteId);
                    }
                    object.put("QuantidadePaletes", qtda);
                    object.put("TipoEntrega", tipoEntrega);
                    object.put("ValorPago", preco);
                    object.put("Endereco", endereco.getText());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                api.newOffer(ofertaAvulsa, object, CompradorOfertarActivity.this, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        hideLoad();

                        boolean success = false;
                        String msg = "";

                        try {
                            success = response.getBoolean("Sucesso");
                            msg = response.getString("Mensagem");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(success) {
                            showMessage(msg);
                            Intent intent = new Intent(
                                    CompradorOfertarActivity.this, CompradorOfertasActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("EXIT", true);
                            startActivity(intent);
                            finish();
                        }


                    }
                });


            }
        });


    }


    public void sendOffer(){




    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_retirar:
                if (checked)
                    address.setVisibility(View.GONE);
                    tipoEntrega = 1;
                    break;
            case R.id.radio_entregar:
                if (checked)
                    address.setVisibility(View.VISIBLE);
                    tipoEntrega = 2;
                    break;
        }
    }

}
