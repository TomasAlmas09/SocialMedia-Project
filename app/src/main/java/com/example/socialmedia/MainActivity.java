package com.example.socialmedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static final int CANAL_FOTO = 1;
    RecyclePost adpt;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    public  int pos;
    public  static  final int CANALINSERT=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.float_insert_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, InsertPost.class);
                startActivityForResult(it,CANALINSERT);
            }
        });
        recyclerView = findViewById(R.id.recycle_carro_main);
        adpt = new RecyclePost(MainActivity.this, App.stand);
        adpt.setOnSacaFotoListener(new IOnSacaFoto() {
            @Override
            public void SacaFoto(int posicao) {
                pos=posicao;
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it,CANAL_FOTO);
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

        }

        if(requestCode==CANAL_FOTO && resultCode==RESULT_OK){
            Uri uri = Uri.parse(data.getData().toString());
            try {
                Bitmap bmp= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                App.stand.get(pos).setFoto(Post.bitmaptoarray(bmp));
                adpt.notifyDataSetChanged();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}