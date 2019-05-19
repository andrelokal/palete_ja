package com.paleteja.br.paleteja.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class DialogUtil {

    private DialogListener dialogListener;
    Context context;
    AlertDialog.Builder builder;

    public interface DialogListener {
        public void dialogResponse(DialogInterface dialog, int which);
    }

    public DialogUtil(Context context, DialogListener dialogListener) {
        this.context = context;
        this.dialogListener = dialogListener;
    }

    public void makeDialog(String Title, String[] options){

        DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.dialogResponse(dialog, which);
            }
        };

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                actions.show();
            }
        };

        builder = new AlertDialog.Builder(context);
        builder.setTitle(Title);
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancelar", null);

    }


    public void show(){
        builder.show();
    }

    public void hide(){
        //builder.di();
    }


}
