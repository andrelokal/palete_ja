package com.paleteja.br.paleteja.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Response;
import com.google.gson.JsonArray;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.DialogUtil;
import com.paleteja.br.paleteja.utils.UserLogged;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PerfilActivity extends BaseActivity {


    String Nome = "";
    String Email = "";
    String Site = "";
    String Endereco = "";
    boolean CelularWhatsapp = false;
    String Celular = "";
    String TelefoneFixo = "";

    //UsuarioConfiguracao
    int TipoProduto = 0;
    int RaioEntrega = 0;
    int QuantidadeMinimaEntrega = 0;
    int TipoEntrega = 0;

    int tipoUser;

    TextView nome_edit;
    EditText endereco_edit;
    EditText telefone_edit;
    EditText celular_edit;
    Switch whatsapp_edit;

    TextView tipo_produto_edit;
    TextView tipo_entrega_edit;
    EditText raio_entrega_edit;
    EditText qtda_minimo_edit;
    EditText site_edit;

    Double[] latLong;

    Button btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        tipoUser = UserLogged.getInstance().getUsuario().getTipoUsuario();
        if(tipoUser == 1) {
            setContentView(R.layout.activity_perfil_vendedor);
        } else {
            setContentView(R.layout.activity_perfil_comprador);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        //getSupportActionBar().setTitle(null);

        latLong = new Double[]{0.0,0.0};

        btn_save = findViewById(R.id.btn_save);

        setViews();
        getUser();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(tipoUser == 1) {

                    Endereco = endereco_edit.getText().toString();

                    api.getLatLgtByAddress(Endereco, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                String success = response.getString("status");

                                if (success.equals("OK")){


                                    JSONArray results = response.getJSONArray("results");
                                    JSONObject result = results.getJSONObject(0);
                                    JSONObject geometry = result.getJSONObject("geometry");
                                    JSONObject location = geometry.getJSONObject("location");

                                    Double lat = location.getDouble("lat");
                                    Double lng = location.getDouble("lng");

                                    latLong = new Double[]{lat, lng};

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            updateUser();

                        }

                    });

                } else {
                    updateUser();
                }



            }
        });


        //apenas se for vendedor.
        if(tipoUser == 1) {

        /*------------------------------------------------------
                    Dialog tipo de produto
        ------------------------------------------------------*/

            tipo_produto_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String[] tipo_produto_arr = {"Novo", "Usado", "Ambos"};
                    DialogUtil dialog_tipo_produto = new DialogUtil(PerfilActivity.this, new DialogUtil.DialogListener() {
                        @Override
                        public void dialogResponse(DialogInterface dialog, int which) {

                            tipo_produto_edit.setText(tipo_produto_arr[which]);
                            TipoProduto = which + 1;

                        }
                    });
                    dialog_tipo_produto.makeDialog("Tipo de produto", tipo_produto_arr);
                    dialog_tipo_produto.show();
                }
            });


        /*------------------------------------------------------
                    Dialog tipo de entrega
        ------------------------------------------------------*/

            tipo_entrega_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String[] tipo_entrega_arr = {"Entregar", "Retirar", "Ambos"};
                    DialogUtil dialog_tipo_entrega = new DialogUtil(PerfilActivity.this, new DialogUtil.DialogListener() {
                        @Override
                        public void dialogResponse(DialogInterface dialog, int which) {

                            tipo_entrega_edit.setText(tipo_entrega_arr[which]);
                            TipoEntrega = which + 1;
                        }
                    });
                    dialog_tipo_entrega.makeDialog("Tipo de produto", tipo_entrega_arr);
                    dialog_tipo_entrega.show();
                }
            });

        }


    }



    public void setEntregaValue(int id){

        if(id == 1){
            tipo_entrega_edit.setText("Novo");
        }

        if(id == 2){
            tipo_entrega_edit.setText("Usado");
        }

        if(id == 3){
            tipo_entrega_edit.setText("Ambos");
        }
    }

    public void setTipoValue(int id){

        if(id == 1){
            tipo_produto_edit.setText("Entregar");
        }

        if(id == 2){
            tipo_produto_edit.setText("Retirar");
        }

        if(id == 3){
            tipo_produto_edit.setText("Ambos");
        }
    }

    public void setViews(){
        nome_edit = findViewById(R.id.nome);
        endereco_edit = findViewById(R.id.endereco);
        telefone_edit = findViewById(R.id.telefone);
        celular_edit = findViewById(R.id.celular);
        whatsapp_edit = findViewById(R.id.whatsapp);
        tipo_produto_edit = findViewById(R.id.tipo_produto);
        tipo_entrega_edit = findViewById(R.id.tipo_entrega);
        raio_entrega_edit = findViewById(R.id.raio_entrega);
        qtda_minimo_edit = findViewById(R.id.qtda_minimo);
        site_edit = findViewById(R.id.site);
    }


    public void setUser(){

        nome_edit.setText(Nome);
        endereco_edit.setText(Endereco);
        telefone_edit.setText(TelefoneFixo);
        celular_edit.setText(Celular);
        whatsapp_edit.setChecked(CelularWhatsapp);

        if(tipoUser == 1) {

            raio_entrega_edit.setText(Integer.toString(RaioEntrega));
            qtda_minimo_edit.setText(Integer.toString(QuantidadeMinimaEntrega));
            site_edit.setText(Site);

        }

    }

    public void updateUser(){


        //simpleSwitch1.isChecked()


        JSONObject object = new JSONObject();
        JSONObject user_data = new JSONObject();

        TelefoneFixo = telefone_edit.getText().toString();
        Celular = celular_edit.getText().toString();
        Endereco = endereco_edit.getText().toString();
        CelularWhatsapp = whatsapp_edit.isChecked();

        try {

            //object.put("UsuarioId", "");
            object.put("TelefoneFixo", TelefoneFixo);
            object.put("Celular", Celular);
            object.put("Endereco", Endereco);
            object.put("CelularWhatsapp", CelularWhatsapp);

            if(tipoUser == 1) {

                Site = site_edit.getText().toString();
                object.put("Site", Site);

                RaioEntrega = Integer.parseInt(raio_entrega_edit.getText().toString());
                QuantidadeMinimaEntrega = Integer.parseInt(qtda_minimo_edit.getText().toString());

                object.put("RaioEntrega", RaioEntrega);
                object.put("latitude", latLong[0]);
                object.put("longitude", latLong[1]);
                object.put("TipoEntrega", TipoEntrega);
                object.put("QuantidadeMinimaEntrega", QuantidadeMinimaEntrega);
                object.put("TipoProduto", TipoProduto);
                object.put("TipoUsuario", 1);
//                object.put("UsuarioConfiguracao", user_data);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        api.updateUser(object, this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    boolean success = response.getBoolean("Sucesso");
                    String msg = response.getString("Sucesso");

                    if(success){
                        showMessage("Dados atualizados com sucesso.");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getUser(){

        api.getUser(this,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONObject data = response.getJSONObject("Usuario");

                            Nome = data.getString("Nome");
                            Email = data.getString("Email");
                            Site = data.getString("Site");
                            Endereco = data.getString("Endereco");
                            Celular = data.getString("Celular");
                            TelefoneFixo = data.getString("TelefoneFixo");
                            CelularWhatsapp = data.getBoolean("CelularWhatsapp");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            if(tipoUser == 1) {

                                JSONObject data = response.getJSONObject("Usuario");

                                JSONObject user_aditional = data.getJSONObject("UsuarioConfiguracao");

                                if(user_aditional.length() > 0){
                                    TipoProduto = user_aditional.getInt("TipoProduto");
                                    RaioEntrega = user_aditional.getInt("RaioEntrega");
                                    QuantidadeMinimaEntrega = user_aditional.getInt("QuantidadeMinimaEntrega");
                                    TipoEntrega = user_aditional.getInt("TipoEntrega");

                                    setEntregaValue(TipoEntrega);
                                    setTipoValue(TipoProduto);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        setUser();
                    }
                });

    }

}
