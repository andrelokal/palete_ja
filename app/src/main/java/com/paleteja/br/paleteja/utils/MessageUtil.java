package com.paleteja.br.paleteja.utils;

import android.content.Context;
import android.widget.Toast;

public class MessageUtil {


    public static void showMessage(String message, Context context){

        int duracao = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, message, duracao);
        toast.show();
    }

}
