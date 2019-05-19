package com.paleteja.br.paleteja.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.model.PaletsUserModel;

import java.util.ArrayList;
import java.util.List;

public class PaletesUsuarioAdapter  extends ArrayAdapter<PaletsUserModel> {

    Context context;
    private List<PaletsUserModel> paletsUserList;

    public PaletesUsuarioAdapter(Context context, int textViewResourceId, List<PaletsUserModel> paletsUserList) {
        super(context, textViewResourceId, paletsUserList);
        this.context = context;
        this.paletsUserList = new ArrayList<>();
        this.paletsUserList.addAll(paletsUserList);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView nomeVendedor;
        TextView precoMedioVendedor;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder = new ViewHolder();
        PaletsUserModel paletsUserModel = getItem(i);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_palets_user, null);

            holder.nomeVendedor = convertView.findViewById(R.id.nome_vendedor);
            holder.precoMedioVendedor = convertView.findViewById(R.id.preco_medio_vendedor);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nomeVendedor.setText(paletsUserModel.getNome());
        holder.precoMedioVendedor.setText(paletsUserModel.getPrecoMedio());

        return convertView;

    }
}
