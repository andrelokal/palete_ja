package com.paleteja.br.paleteja.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Response;
import com.paleteja.br.paleteja.R;
import com.paleteja.br.paleteja.model.UserModel;
import com.paleteja.br.paleteja.ui.BaseActivity;
import com.paleteja.br.paleteja.utils.Constants;
import com.paleteja.br.paleteja.utils.UserLogged;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Cadastro3Activity extends BaseActivity {

    public Button buttonSend;
    public ImageView uploadImage;
    public String upFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro3);
        buttonSend = findViewById(R.id.btn_send);
        uploadImage = findViewById(R.id.uploadPicture);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Selecione a ação");

        String[] pictureDialogTexts = {
                Constants.OPTION_IMAGE_UPLOAD1,
                Constants.OPTION_IMAGE_UPLOAD2
                 };

        pictureDialog.setItems(pictureDialogTexts,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                photoFromGallery();
                                break;
                            case 1:
                                photoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    /*
    private void selectImage() {
        final CharSequence[] items = { "Tirar foto", "Escolher da Galeria",
                "Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro3Activity.this);
        builder.setTitle("Add Foto");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


            }
        });
        builder.show();
    }*/

    public void photoFromCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }


    public void photoFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent , 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    onCaptureImageResult(imageReturnedIntent);
                }
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    onSelectFromGalleryResult(imageReturnedIntent);
                }
                break;
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            thumbnail.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            byte[] b = bytes.toByteArray();
            upFile = Base64.encodeToString(b,Base64.DEFAULT);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        uploadImage.setImageBitmap(thumbnail);
        setClick();
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        uploadImage.setImageBitmap(bm);
        uploadImage.setAdjustViewBounds(true);
        uploadImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        setClick();
    }

    private void setClick(){
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             showLoad();
             String upload = upFile;
             SendAccountMore(upload);
            }
        });
    }

    private void SendAccountMore( String picture ){

        //System.out.println( picture );
        JSONObject object = new JSONObject();
        /*
        try {
            object.put("Foto", picture);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */

        System.out.println( object );
        //api.fake = true;
        api.accountPhoto(object,this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                hideLoad();
                try {

                    boolean success = response.getBoolean("Sucesso");

                    if(success) {

                        Intent intent = new Intent( Cadastro3Activity.this , HomeCompradorActivity.class );
                        startActivity(intent);

                    } else {
                        showMessage(response.getString("Mensagem") );
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });

    }


}
