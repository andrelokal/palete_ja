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
import com.paleteja.br.paleteja.utils.UserLogged;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    public Button login_btn;
    public Button recover_btn;
    public EditText user;
    public EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //recover_password

        login_btn = findViewById(R.id.login);
        recover_btn = findViewById(R.id.recover);
        user = findViewById(R.id.edit_user);
        pass = findViewById(R.id.edit_pass);

        setLoginClick();

    }

    private void setLoginClick(){

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoad();

                String user_text = user.getText().toString();
                String user_pass = pass.getText().toString();
                checkLogin(user_text, user_pass);

            }
        });

        recover_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoad();

                String user_text = user.getText().toString();

                if(user_text.isEmpty()){
                    hideLoad();
                    showMessage("Digite seu email no campo usuário.");
                } else {
                    checkRecover(user_text);
                }


            }
        });

    }

    private void checkRecover(String email){

        JSONObject object = new JSONObject();

        try {
            object.put("Email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        api.recover_password(object,this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                hideLoad();
                try {

                    boolean success = response.getBoolean("Sucesso");
                    String msg = response.getString("Mensagem");

                    if (success) {
                        showMessage(msg);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });

    }

    private void checkLogin(String user, String pass){

            JSONObject object = new JSONObject();

            try {
                object.put("Email", user);
                object.put("Senha", pass);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            api.login(object,this, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    hideLoad();
                    try {

                        boolean success = response.getBoolean("Sucesso");

                        if(success) {

                            JSONObject json = response.getJSONObject("Autenticacao");

                            UserModel user = UserLogged.RegisterUser( json );

                            UserLogged.getInstance().setUsuario(user);
                            //UserLogged.getInstance.getUsuario.getNome

                            // 1 é vendedor
                            // 2 é comprador
                            if(user.getTipoUsuario() == 1){

                                Intent intent = new Intent(LoginActivity.this, HomeVendedorActivity.class);
                                startActivity(intent);

                            } else {

                                Intent intent = new Intent(LoginActivity.this, HomeCompradorActivity.class);
                                startActivity(intent);
                            }


                        } else {
                            showMessage("Usuário não encontrado.");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            });

    }


}