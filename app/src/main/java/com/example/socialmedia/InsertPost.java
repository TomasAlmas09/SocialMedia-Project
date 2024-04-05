package com.example.socialmedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;

public class InsertPost extends AppCompatActivity {

    private static final int CANALFOTO = 3;
    EditText editTitle, editDesc;
    Button btinsert, btcancel;
    ImageView imgfoto;

    private static final String TAG = "InsertPost";
    private BottomBar bottomBar; // Instantiate BottomBar

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CANALFOTO && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgfoto.setImageBitmap(bmp);
                Log.d(TAG, "Image set successfully from onActivityResult.");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error setting image from onActivityResult: " + e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        // Instantiate BottomBar and setup bottom navigation bar
        bottomBar = new BottomBar();
        bottomBar.setupBottomBar(this); // Pass context to set up BottomBar

        editTitle = findViewById(R.id.edit_tittle_insert);
        editDesc = findViewById(R.id.edit_desc_insert);
        imgfoto = findViewById(R.id.img_foto_insert);
        imgfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itfoto = new Intent(Intent.ACTION_GET_CONTENT);
                itfoto.setType("image/*");
                startActivityForResult(itfoto, CANALFOTO);
                Log.d(TAG, "Started activity for image selection.");
            }
        });

        btinsert = findViewById(R.id.bt_insert_insert);
        btinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString();
                String desc = editDesc.getText().toString();
                BitmapDrawable drw = (BitmapDrawable) imgfoto.getDrawable();
                Bitmap bmp = drw.getBitmap();
                Post novo = new Post(title, desc, bmp);
                MyBD myBD = new MyBD(InsertPost.this, 2);
                myBD.insertPost(novo, bmp);
                App.loadList();
                Intent intent = new Intent(InsertPost.this, MainActivity.class);
                startActivity(intent);
                Log.d(TAG, "Inserted new post and finished activity.");
            }
        });

        btcancel = findViewById(R.id.bt_cancel_insert);
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertPost.this, MainActivity.class);
                startActivity(intent);
                Log.d(TAG, "Cancelled post insertion and finished activity.");
            }
        });


    }
}
