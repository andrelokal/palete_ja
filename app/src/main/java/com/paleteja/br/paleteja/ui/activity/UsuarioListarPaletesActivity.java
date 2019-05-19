package com.paleteja.br.paleteja.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.adapter.PaleteAdapter;
import com.paleteja.br.paleteja.model.PaleteModel;
import com.paleteja.br.paleteja.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class UsuarioListarPaletesActivity extends BaseActivity {
    private ListView listaPaletes;
    private List<PaleteModel> paletes = new ArrayList<>();
    private String vendedor;
    private TextView nomeVendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_listar_paletes);

        //Toolbar
        //setMenuToolbar();
        //toolbar.setTitle("Ofertar Palete");

        paletes = (List<PaleteModel>) getIntent().getSerializableExtra("paletes");
        vendedor = getIntent().getStringExtra("vendedor");

        listaPaletes = findViewById(R.id.usuario_lista_paletes);
        nomeVendedor = findViewById(R.id.nome_vendedor);

        PaleteAdapter adapter = new PaleteAdapter(this, R.layout.activity_usuario_listar_paletes, paletes);
        listaPaletes.setAdapter(adapter);

        nomeVendedor.setText(vendedor);

        adapter.notifyDataSetChanged();

        listaPaletes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int paleteID = paletes.get(position).getPaleteId();
                String produto = paletes.get(position).getTipoProdutoDesc();
                Float preco = paletes.get(position).getPrecoPromocao();

                Intent intent = new Intent(UsuarioListarPaletesActivity.this, CompradorOfertarActivity.class);
                intent.putExtra("id", paleteID );
                intent.putExtra("produto", produto );
                intent.putExtra("preco", preco );
                startActivity(intent);

            }
        });

    }
}
