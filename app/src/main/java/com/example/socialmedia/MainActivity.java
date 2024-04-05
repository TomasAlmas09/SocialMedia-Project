package com.example.socialmedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.socialmedia.BottomBar;

public class MainActivity extends AppCompatActivity {
    public static final int CANAL_FOTO = 1;
    RecyclePost adpt;
    RecyclerView recyclerView;

    public  int pos;
    public  static  final int CANALINSERT=2;

    private static final String TAG = "MainActivity";
    BottomBar bottomBar; // Instantiate BottomBar


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Set up BottomBar
        bottomBar = new BottomBar();
        bottomBar.setupBottomBar(this); // Pass context to set up BottomBar

        recyclerView = findViewById(R.id.recycle_post_main);
        adpt = new RecyclePost(MainActivity.this, App.stand);
        adpt.setOnSacaFotoListener(new IOnSacaFoto() {
            @Override
            public void SacaFoto(int posicao) {
                pos=posicao;
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it,CANAL_FOTO);
                Log.d(TAG, "Started activity for selecting image.");
            }
        });
        recyclerView.setAdapter(adpt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CANALINSERT){
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            Log.d(TAG, "Inserted new post and refreshed activity.");
        }

        if(requestCode==CANAL_FOTO && resultCode==RESULT_OK){
            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                App.stand.get(pos).setFoto(Post.bitmapToArray(bmp));
                adpt.notifyDataSetChanged();
                Log.d(TAG, "Selected image set to post and adapter notified.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
