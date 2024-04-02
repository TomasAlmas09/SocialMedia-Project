package com.example.socialmedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class InsertPost extends AppCompatActivity {
    private static final int CANALFOTO = 3;
    EditText editTitle, editDesc; // Corrected EditText IDs
    Button btinsert, btcancel;
    ImageView imgfoto;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CANALFOTO && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgfoto.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        editTitle = findViewById(R.id.edit_tittle_insert); // Corrected EditText IDs
        editDesc = findViewById(R.id.edit_desc_insert); // Corrected EditText IDs
        imgfoto = findViewById(R.id.img_foto_insert);
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto, CANALFOTO);
            }
        });

        btinsert = findViewById(R.id.bt_insert_insert);
        btinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString(); // Corrected EditText fields
                String desc = editDesc.getText().toString(); // Corrected EditText fields
                BitmapDrawable drw = (BitmapDrawable) imgfoto.getDrawable();
                Bitmap bmp = drw.getBitmap();
                Post novo = new Post(title, desc, bmp);
                MyBD myBD = new MyBD(InsertPost.this, 1);
                myBD.insertPost(novo, bmp);
                App.loadList();
                finish();
            }
        });

        btcancel = findViewById(R.id.bt_cancel_insert);
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
