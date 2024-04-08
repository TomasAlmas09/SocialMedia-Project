package com.example.socialmedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity {

    // Request code for selecting a photo
    public static final int CANAL_FOTO = 1;
    // Adapter for the RecyclerView
    RecyclePost adpt;
    // RecyclerView to display posts
    RecyclerView recyclerView;
    // Position of the selected post
    public int pos;
    // Request code for inserting a new post
    public static final int CANALINSERT = 2;
    // Tag for logging
    private static final String TAG = "MainActivity";
    // Instantiate BottomBar
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up BottomBar
        bottomBar = new BottomBar();
        bottomBar.setupBottomBar(this); // Pass context to set up BottomBar

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recycle_post_main);
        adpt = new RecyclePost(MainActivity.this, App.stand);
        adpt.setOnSacaFotoListener(new IOnSacaFoto() {
            @Override
            public void SacaFoto(int posicao) {
                pos = posicao;
                // Start activity for selecting an image
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it, CANAL_FOTO);
                Log.d(TAG, "Started activity for selecting image.");
            }
        });
        recyclerView.setAdapter(adpt);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If a new post is inserted, refresh the activity
        if (requestCode == CANALINSERT) {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
            Log.d(TAG, "Inserted new post and refreshed activity.");
        }

        // If a photo is selected, set it to the selected post
        if (requestCode == CANAL_FOTO && resultCode == RESULT_OK) {
            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                App.stand.get(pos).setFoto(Post.bitmapToArray(bmp));
                adpt.notifyDataSetChanged();
                Log.d(TAG, "Selected image set to post and adapter notified.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
