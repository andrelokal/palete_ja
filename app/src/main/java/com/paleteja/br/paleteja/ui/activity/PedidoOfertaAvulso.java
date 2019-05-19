package com.paleteja.br.paleteja.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Response;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.model.UserModel;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.UserLogged;

import org.json.JSONException;
import org.json.JSONObject;

public class PedidoOfertaAvulso extends BaseActivity {


    public Button buttonSend;
    public EditText Quantidade;
    public EditText Valor;
    public RadioGroup TipoEntrega;
    public RadioGroup TipoProduto;
    public EditText Endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_oferta_avulso);

        buttonSend = findViewById( R.id.btn_send );
        Quantidade = findViewById( R.id.quantidade );
        Valor = findViewById( R.id.valor );
        TipoEntrega = findViewById( R.id.tipo_entrega );
        TipoProduto = findViewById( R.id.tipo_produto );
        Endereco = findViewById( R.id.endereco );

        BindClickButtonSend();

    }

    private void BindClickButtonSend(){

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoad();

                String Quantidade_text = Quantidade.getText().toString();
                String Valor_text = Valor.getText().toString();
                String Endereco_text = Endereco.getText().toString();
                String TipoEntrega = "";
                String TipoProduto = "";

                Boolean TipoEntregachecked = ((RadioButton) v).isChecked();
                switch(v.getId()) {
                    case R.id.radio_retirar:
                        if (TipoEntregachecked)
                                TipoEntrega = "1";
                            break;
                    case R.id.radio_entregar:
                        if (TipoEntregachecked)
                                TipoEntrega = "2";
                            break;
                }

                Boolean TipoProdutochecked = ((RadioButton) v).isChecked();
                switch(v.getId()) {
                    case R.id.radio_novo:
                        if (TipoEntregachecked)
                            TipoProduto = "1";
                        break;
                    case R.id.radio_usado:
                        if (TipoEntregachecked)
                            TipoProduto = "2";
                        break;
                }

                Send(  Quantidade_text,
                       Valor_text,
                       Endereco_text,
                       TipoEntrega,
                       TipoProduto);
            }
        });
    }

    private void Send( String Quantidade,
                       String Valor,
                       String Endereco,
                       String TipoEntrega,
                       String TipoProduto ){

        JSONObject object = new JSONObject();
        try {
            object.put("Quantidade", Quantidade);
            object.put("Valor", Valor);
            object.put("Endereco", Endereco);
            object.put("TipoEntrega", TipoEntrega);
            object.put("TipoProduto", TipoProduto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
        api.PedidoAvulso(object,this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                hideLoad();

            }
        });
        */

    }
}
