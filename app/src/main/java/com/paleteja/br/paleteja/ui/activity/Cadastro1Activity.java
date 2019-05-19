package com.paleteja.br.paleteja.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.Constants;

public class Cadastro1Activity extends BaseActivity {

    public Button comprador;
    public Button vendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro1);


        comprador = findViewById(R.id.comprador);
        vendedor = findViewById(R.id.vendedor);


        setClicks();
    }

    public void setClicks(){


        comprador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.UserLoginData.REGISTER_TYPE = 1;
                Intent intent = new Intent(Cadastro1Activity.this, Cadastro2Activity.class);
                startActivity(intent);
            }
        });

        vendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.UserLoginData.REGISTER_TYPE = 2;
                Intent intent = new Intent(Cadastro1Activity.this, Cadastro2Activity.class);
                startActivity(intent);
            }
        });


    }

}
