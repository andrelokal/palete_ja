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
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.ui.BaseActivity;

/**
 * Created by caiomarques on 03/05/2018.
 */

public class FiltrarUsuariosPaletesActivity extends BaseActivity {

    public SeekBar raio;
    public TextView raioAtual;
    public String currentUF = "SP";
    TextView selectEstado;
    Button buscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar);

        //Toolbar
        setMenuToolbar(true);
        setTitle("Buscar vendedores");

        raio = findViewById(R.id.raio);
        raioAtual = findViewById(R.id.raio_atual);
        selectEstado = findViewById(R.id.select_estado);
        buscar = findViewById(R.id.buscar);

        int maxValue = raio.getMax();
        int minValue = 10;
        int seekBarValue = raio.getProgress();
        raioAtual.setText(Integer.toString(minValue));

        clicks();
    }


    public void clicks(){

        selectEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectEstado();
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUF == "Todos Estados"){
                    currentUF = "";
                }

                Intent intent = new Intent(FiltrarUsuariosPaletesActivity.this, HomeCompradorActivity.class);
                intent.putExtra("uf", currentUF);
                intent.putExtra("raio", raio.getProgress());
                //intent.putExtra("raio", raio.getProgress());
                startActivity(intent);
            }
        });

        raio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                int v = seekBar.getProgress();
                raioAtual.setText(Integer.toString(v));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void selectEstado(){

        final String[] states = getStates();

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(FiltrarUsuariosPaletesActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.list_custom, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle("Selecione o estado");
        ListView lv = convertView.findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, states);
        lv.setAdapter(adapter);
        final AlertDialog mDialog = alertDialog.show();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentUF = states[i];
                selectEstado.setText(currentUF);
                mDialog.dismiss();
            }
        });

    }

    public String[] getStates() {

        String states[] ={"Todos Estados", "AC","AL","AP", "AM","BA", "CE", "DF", "ES", "GO", "MA", "MT",
                "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};

        return states;

    }

}
