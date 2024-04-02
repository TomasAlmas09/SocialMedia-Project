package com.example.socialmedia;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    public static List<Post>stand = new ArrayList<>();
    public  static Context ctx;
    public  static  final String TAG="Xpto";
    @Override
    public void onCreate() {
        super.onCreate();
        ctx=getApplicationContext();
        Log.i(TAG,"App Created");
        carregalista();
        for(Post c : stand)Log.i(TAG,c.toString());
    }
    public  static  void carregalista(){
        MyBD myBD = new MyBD(ctx,1);
        stand = myBD.carregaLista();
    }


    void criarlista(){
        stand.add(new Post(1,"Ford Fiesta","Utilitário", new byte[]{}));
        stand.add(new Post(2,"Ferrari","Desportivo", new byte[]{}));
        stand.add(new Post(3,"Volkswagen","Luxo", new byte[]{}));
        stand.add(new Post(4,"Mustang","Desportivo", new byte[]{}));
        stand.add(new Post(5,"BMW","Utilitário", new byte[]{}));
    }


}

