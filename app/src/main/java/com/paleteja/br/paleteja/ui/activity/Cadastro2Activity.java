package com.paleteja.br.paleteja.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.model.UserModel;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.Constants;
import com.paleteja.br.paleteja.utils.UserLogged;

import org.json.JSONException;
import org.json.JSONObject;

public class Cadastro2Activity extends BaseActivity {

    public Button buttonSend;

    public EditText name;
    public EditText email;
    public EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);

        buttonSend = findViewById(R.id.btn_send);

        name = findViewById(R.id.name_register );
        email = findViewById(R.id.email_register );
        pass = findViewById(R.id.pass_register );

        setClick();
    }

    private void setClick(){
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showLoad();

            String name_text = name.getText().toString();
            String email_text = email.getText().toString();
            String pass_text = pass.getText().toString();

            SendAccount(name_text, email_text, pass_text);
            }
        });
    }

    private void SendAccount( String name, String email, String pass ){

        JSONObject object = new JSONObject();

        try {
            object.put("Nome", name);
            object.put("Email", email);
            object.put("Senha", pass);
            object.put("TipoUsuario", Constants.UserLoginData.REGISTER_TYPE);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        api.account(object,this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                hideLoad();
                try {

                    boolean success = response.getBoolean("Sucesso");

                    if(success) {

                        JSONObject json = response.getJSONObject("Autenticacao");
                        //System.out.println( " --------- " +json.getString("Token") );
                        UserModel user = UserLogged.RegisterUser( json );
                        UserLogged.getInstance().setUsuario(user);

                        Intent intent = new Intent( Cadastro2Activity.this , Cadastro3Activity.class);
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
