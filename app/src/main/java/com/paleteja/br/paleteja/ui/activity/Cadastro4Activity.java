package com.paleteja.br.paleteja.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Response;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.model.UserModel;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.Constants;
import com.paleteja.br.paleteja.utils.UserLogged;

import org.json.JSONException;
import org.json.JSONObject;

public class Cadastro4Activity extends BaseActivity {


    public Button buttonSend;
    public EditText Documento;
    public EditText TelefoneFixo;
    public EditText Celular;
    public EditText Endereco;
    public CheckBox Whatsapp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro4);

        buttonSend = findViewById( R.id.btn_send );
        Documento = findViewById( R.id.document_register );
        TelefoneFixo = findViewById( R.id.telefone_register );
        Celular = findViewById( R.id.celular_register );
        Endereco = findViewById( R.id.endereco_register );
        Whatsapp = findViewById( R.id.whatsapp_register );

        BindClickButtonSend();

    }

    private void BindClickButtonSend(){

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoad();

                String Documento_text = Documento.getText().toString();
                String TelefoneFixo_text = TelefoneFixo.getText().toString();
                String Celular_tex = Celular.getText().toString();
                String Endereco_tex = Endereco.getText().toString();
                String Whatsapp_text = Whatsapp.getText().toString();


                SendCompl(  Documento_text,
                            TelefoneFixo_text,
                            Celular_tex,
                            Endereco_tex,
                            Whatsapp_text);
            }
        });
    }

    private void SendCompl( String Documento,
                            String TelefoneFixo,
                            String Celular,
                            String Endereco,
                            String Whatsapp ){

        JSONObject object = new JSONObject();
        try {
            object.put("Documento", Documento);
            object.put("TelefoneFixo", TelefoneFixo);
            object.put("Celular", Celular);
            object.put("Endereco", Endereco);
            object.put("CelularWhatsapp", Whatsapp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        api.accountCompl(object,this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                hideLoad();
                try {

                    boolean success = response.getBoolean("Sucesso");

                    if(success) {

                        JSONObject json = response.getJSONObject("Autenticacao");
                        UserModel user = UserLogged.RegisterUser( json );
                        UserLogged.getInstance().setUsuario(user);

                        Intent intent = new Intent( Cadastro4Activity.this , HomeCompradorActivity.class);
                        startActivity(intent);

                    } else {
                        showMessage(response.getString("Mensagem") );
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

    }




}
