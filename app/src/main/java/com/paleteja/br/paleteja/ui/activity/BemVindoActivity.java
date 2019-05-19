package com.paleteja.br.paleteja.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.ui.BaseActivity;

public class BemVindoActivity extends BaseActivity {

    public Button login_btn;
    public Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);

        checkPermission();

        login_btn = findViewById(R.id.login);
        register_btn = findViewById(R.id.register);

        setClicks();

    }

    private boolean checkPermission(){
        if (
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION }, 123);
            return false;
        } else {
            return true;
        }
    }

    private void setClicks(){

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BemVindoActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BemVindoActivity.this, Cadastro1Activity.class);
                startActivity(intent);
            }
        });

    }

}
