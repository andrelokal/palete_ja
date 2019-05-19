package com.paleteja.br.paleteja.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.model.PaleteModel;

import java.text.NumberFormat;
import java.util.List;

public class PaleteAdapter extends ArrayAdapter<PaleteModel> {
    public PaleteAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<PaleteModel> list) {
        super(context, textViewResourceId, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        PaleteModel item = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_usuario_item_palete, null);

            holder.tipoProdutoDescricao = convertView.findViewById(R.id.tipo_produto_descricao);
            holder.unidades = convertView.findViewById(R.id.unidades);
            holder.valor = convertView.findViewById(R.id.valor);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tipoProdutoDescricao.setText(item.getTipoProdutoDesc());
        holder.unidades.setText(String.format("%d unidades(s)", item.getSaldo()));
        holder.valor.setText(NumberFormat.getCurrencyInstance().format(item.getPrecoPromocao()));

        return convertView;
    }

    private static class ViewHolder {
        TextView tipoProdutoDescricao;
        TextView unidades;
        TextView valor;
    }
}
