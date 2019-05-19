package com.paleteja.br.paleteja.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.model.OfertasVendedorModel;

import java.util.List;

public class OfertasVendedorAdapter extends ArrayAdapter<OfertasVendedorModel> {
    public OfertasVendedorAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<OfertasVendedorModel> list) {
        super(context, textViewResourceId, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        OfertasVendedorModel item = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_oferta_vendedor, null);

            holder.qtda = convertView.findViewById(R.id.quantidade);
            holder.id = convertView.findViewById(R.id.id_pedido);
            holder.tipo_produto_descricao = convertView.findViewById(R.id.tipo_produto_descricao);
            holder.preco_pago = convertView.findViewById(R.id.preco_pago);
            holder.tipo_entrega = convertView.findViewById(R.id.tipo_entrega);
            holder.endereco = convertView.findViewById(R.id.endereco);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.qtda.setText(Integer.toString(item.getQuantidade()));
        holder.id.setText(Integer.toString(item.getIdOferta()));
        holder.tipo_produto_descricao.setText(item.getTipoProdutoDescricao());
        holder.preco_pago.setText(Float.toString(item.getPrecoPago()));
        holder.tipo_entrega.setText(item.getTipoEntrega());
        if(item.getEnderecoEntrega() != null){
            holder.endereco.setText(item.getEnderecoEntrega());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView qtda;
        TextView id;
        TextView tipo_produto_descricao;
        TextView preco_pago;
        TextView tipo_entrega;
        TextView endereco;
    }
}
