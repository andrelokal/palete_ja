package com.paleteja.br.paleteja.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.model.PaleteModel;
import com.paleteja.br.paleteja.model.PaletsVendedorModel;
import com.paleteja.br.paleteja.model.TipoProduto;
import com.paleteja.br.paleteja.repository.PaletsVendedorRepository;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.CurrencyUtils;
import com.paleteja.br.paleteja.utils.MaskMoney;

import java8.util.function.Consumer;
import java8.util.function.Function;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.List;

public class EditarPaletesVendedorActivity extends BaseActivity {

    PaletsVendedorModel palete;
    PaletsVendedorRepository repository;

    RadioGroup tipoProdutoGroup;
    EditText precoAlvo;
    EditText precoPromocao;
    RadioGroup notaFiscalGroup;
    EditText saldo;
    EditText tabelaA;
    EditText tabelaB;
    EditText tabelaC;
    EditText quantidadeA;
    EditText quantidadeB;
    EditText quantidadeC;
    EditText descricao;
    Button salvar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor_paletes_editar);
        setMenuToolbar(false);

        palete = (PaletsVendedorModel) getIntent().getSerializableExtra("palete");

        if(palete == null){
            setTitle(R.string.title_activity_vendedor_paletes_novo);
        } else {
            setTitle(R.string.title_activity_vendedor_paletes_editar);
        }


        repository = new PaletsVendedorRepository();


        // Binding
        tipoProdutoGroup = findViewById(R.id.vendedor_paletes_editar_tipo_produto);
        precoAlvo = findViewById(R.id.vendedor_paletes_editar_preco_alvo);
        precoAlvo.addTextChangedListener(new MaskMoney(precoAlvo));

        precoPromocao = findViewById(R.id.vendedor_paletes_editar_preco_promocao);
        precoPromocao.addTextChangedListener(new MaskMoney(precoPromocao));
        //notaFiscalGroup = findViewById(R.id.vendedor_paletes_editar_nota_fiscal);
        saldo = findViewById(R.id.vendedor_paletes_editar_saldo);
        tabelaA = findViewById(R.id.vendedor_paletes_editar_tabela_a);
        tabelaB = findViewById(R.id.vendedor_paletes_editar_tabela_b);
        tabelaC = findViewById(R.id.vendedor_paletes_editar_tabela_c);
        quantidadeA = findViewById(R.id.vendedor_paletes_editar_quantidade_a);
        quantidadeB = findViewById(R.id.vendedor_paletes_editar_quantidade_b);
        quantidadeC = findViewById(R.id.vendedor_paletes_editar_quantidade_c);
        descricao = findViewById(R.id.descricao);
        salvar = findViewById(R.id.vendedor_paletes_editar_salvar);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                palete.setTipoProduto(getTipoProduto());
                palete.setPrecoAlvo(getPrecoAlvo());
                palete.setPrecoPromocao(getPrecoPromocao());
                palete.setNotaFiscal(false); //getNotaFiscal()
                palete.setSaldo(getSaldo());
                palete.setTabelaA(getTabelaA());
                palete.setTabelaB(getTabelaB());
                palete.setTabelaC(getTabelaC());
                palete.setQuantidadeA(getQuantidadeA());
                palete.setQuantidadeB(getQuantidadeB());
                palete.setQuantidadeC(getQuantidadeC());
                palete.setDescricao(descricao.getText().toString());

                repository.atualizar(palete).thenAccept(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) {
                        try {
                            showMessage(jsonObject.getString("Mensagem"));
                            finish();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).exceptionally(new Function<Throwable, Void>() {
                    @Override
                    public Void apply(Throwable throwable) {
                        throwable.getCause().printStackTrace();
                        showMessage("Ops., algo de errado.");

                        return null;
                    }
                });
            }
        });

        if(palete != null) {
            carregar();
        } else {
            palete = new PaletsVendedorModel();
        }

    }

    private void carregar() {
        DecimalFormat currencyInstance = CurrencyUtils.getInstance();
        TipoProduto tipoProduto = TipoProduto.parseInt(palete.getTipoProduto());

        if(tipoProduto != null && tipoProduto.equals(TipoProduto.USADO)) {
            tipoProdutoGroup.check(R.id.vendedor_paletes_editar_tipo_produto_usado);
        } else {
            tipoProdutoGroup.check(R.id.vendedor_paletes_editar_tipo_produto_novo);
        }

        precoAlvo.setText(currencyInstance.format(palete.getPrecoAlvo()));
        precoPromocao.setText(currencyInstance.format(palete.getPrecoPromocao()));

//        if(palete.isNotaFiscal()) {
//            notaFiscalGroup.check(R.id.vendedor_paletes_editar_nota_fiscal_sim);
//        } else {
//            notaFiscalGroup.check(R.id.vendedor_paletes_editar_nota_fiscal_nao);
//        }

        saldo.setText(String.valueOf(palete.getSaldo()));
        tabelaA.setText(String.valueOf(palete.getTabelaA()));
        tabelaB.setText(String.valueOf(palete.getTabelaB()));
        tabelaC.setText(String.valueOf(palete.getTabelaC()));
        quantidadeA.setText(String.valueOf(palete.getQuantidadeA()));
        quantidadeB.setText(String.valueOf(palete.getQuantidadeB()));
        quantidadeC.setText(String.valueOf(palete.getQuantidadeC()));
        descricao.setText(String.valueOf(palete.getDescricao()));
    }

    private TipoProduto getTipoProduto() {
        RadioButton tipoProduto = findViewById(tipoProdutoGroup.getCheckedRadioButtonId());

        return TipoProduto.parseStr(tipoProduto.getText());
    }

    private Float getPrecoAlvo() {
        try {
            return CurrencyUtils.getInstance().parse(precoAlvo.getText().toString()).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }
    }

    private Float getPrecoPromocao() {
        try {
            return CurrencyUtils.getInstance().parse(precoPromocao.getText().toString()).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }
    }

    private Boolean getNotaFiscal() {
        RadioButton notaFiscal = findViewById(notaFiscalGroup.getCheckedRadioButtonId());

        if(notaFiscal != null && notaFiscal.getText().toString().equals("Não")) {
            return false;
        }

        return true;
    }

    private Integer getSaldo() {
        return Integer.parseInt(saldo.getText().toString());
    }

    private Integer getTabelaA() {

        String a = tabelaA.getText().toString();

        if(!a.isEmpty()) {
            return Integer.parseInt(a);
        } else {
            return 0;
        }

    }

    private Integer getTabelaB() {

        String b = tabelaB.getText().toString();

        if(!b.isEmpty()) {
            return Integer.parseInt(b);
        } else {
            return 0;
        }

    }

    private Integer getTabelaC() {

        String c = tabelaC.getText().toString();

        if(!c.isEmpty()) {
            return Integer.parseInt(c);
        } else {
            return 0;
        }

    }

    private Integer getQuantidadeA() {

        String a = quantidadeA.getText().toString();

        if(!a.isEmpty()) {
            return Integer.parseInt(a);
        } else {
            return 0;
        }

    }

    private Integer getQuantidadeB() {

        String a = quantidadeB.getText().toString();

        if(!a.isEmpty()) {
            return Integer.parseInt(a);
        } else {
            return 0;
        }

    }

    private Integer getQuantidadeC() {

        String a = quantidadeC.getText().toString();

        if(!a.isEmpty()) {
            return Integer.parseInt(a);
        } else {
            return 0;
        }

    }
}
