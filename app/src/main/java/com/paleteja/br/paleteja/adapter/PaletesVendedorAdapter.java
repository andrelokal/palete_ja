package com.paleteja.br.paleteja.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.model.PaletsUserModel;
import com.paleteja.br.paleteja.model.PaletsVendedorModel;

import java.util.ArrayList;

public class PaletesVendedorAdapter  extends ArrayAdapter<PaletsVendedorModel> {

    Context context;
    private ArrayList<PaletsVendedorModel> paletsUserList;

    public PaletesVendedorAdapter(Context context, int textViewResourceId, ArrayList<PaletsVendedorModel> paletsUserList) {
        super(context, textViewResourceId, paletsUserList);
        this.context = context;
        this.paletsUserList = new ArrayList<>();
        this.paletsUserList.addAll(paletsUserList);
    }

    // View lookup cache
    private static class ViewHolder {

        TextView TipoProdutoDesc;
        TextView PrecoAlvo;
        TextView PrecoPromocao;
        TextView NotaFiscal;
        TextView Saldo;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        PaletesVendedorAdapter.ViewHolder holder = new PaletesVendedorAdapter.ViewHolder();
        PaletsVendedorModel paletsVendedorModel = getItem(i);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_palets_vendedor, null);

            holder.TipoProdutoDesc = convertView.findViewById(R.id.nome_produto);
            holder.PrecoAlvo = convertView.findViewById(R.id.preco_produto);
            holder.Saldo = convertView.findViewById(R.id.saldo_produto);

            convertView.setTag(holder);

        } else {
            holder = (PaletesVendedorAdapter.ViewHolder) convertView.getTag();
        }

        holder.TipoProdutoDesc.setText(paletsVendedorModel.getTipoProdutoDesc());
        holder.PrecoAlvo.setText(Float.toString(paletsVendedorModel.getPrecoAlvo()));
        holder.Saldo.setText(Integer.toString(paletsVendedorModel.getSaldo()));

        return convertView;

    }
}
