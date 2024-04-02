package com.example.socialmedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class InsertPost extends AppCompatActivity {
    private static final int CANALFOTO = 3;
    EditText editid,editmodelo;
    Button btinsert, btcancel;

    ImageView imgfoto;
    Spinner spincat;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CANALFOTO && resultCode==RESULT_OK){
            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                imgfoto.setImageBitmap(bmp);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        editid =findViewById(R.id.edit_idcar_insert);
        editmodelo=findViewById(R.id.edit_modelo_insert);
        imgfoto = findViewById(R.id.img_foto_insert);
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto,CANALFOTO);
            }
        });

        spincat =findViewById(R.id.spin_categoria_insert);
        ArrayAdapter<String>adpt = new ArrayAdapter<>(
                InsertPost.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item

        );
        adpt.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spincat.setAdapter(adpt);
        spincat.setSelection(0);
        btinsert= findViewById(R.id.bt_insert_insert);
        btinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idcar = Integer.parseInt(editid.getText().toString());
                String modelo = editmodelo.getText().toString();
                String categoria = spincat.getSelectedItem().toString();
                BitmapDrawable drw =(BitmapDrawable) imgfoto.getDrawable();
                Bitmap bmp = drw.getBitmap();
                Post novo = new Post(idcar,modelo,categoria,bmp);
                MyBD myBD = new MyBD(InsertPost.this,1);
                myBD.inserirCarro(novo);
                App.carregalista();
                finish();
            }
        });
        btcancel =findViewById(R.id.bt_cancel_insert);
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}