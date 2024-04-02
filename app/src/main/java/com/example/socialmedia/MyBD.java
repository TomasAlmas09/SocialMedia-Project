package com.example.socialmedia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyBD extends SQLiteOpenHelper {
    public static final String DATABASENAME = "socialmedia.db";
    public static final String TB_POST = "tb_post";
    public static final String TITLE = "tittle";
    public static final String DESCRIPTION = "description";
    public static final String FOTO = "foto";

    private static final String TAG = "Xpto";

    public MyBD(@Nullable Context context, int version) {
        super(context, DATABASENAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TB_POST + " ( " +
                TITLE + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                FOTO + " BLOB " +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_POST);
        onCreate(db);
    }

    public long insertPost(Post novo, Bitmap bmp) {
        long resp = 0;
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(TITLE, novo.getTitle());
            cv.put(DESCRIPTION, novo.getModelo());
            cv.put(FOTO, Post.bitmapToArray(bmp));
            resp = db.insert(TB_POST, null, cv);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e(TAG, "Error inserting data into database: " + e.getMessage());
        } finally {
            db.endTransaction();
            db.close();
        }
        return resp;
    }


    List<Post> carregaLista(){
        List<Post> lista = new ArrayList<>();
        SQLiteDatabase db =getReadableDatabase();
        String sql="select * from "+ TB_POST +" ;";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.getCount()>0){
            cur.moveToFirst();
            do{
                Post c = new Post(
                        cur.getString(0),
                        cur.getString(1),
                        cur.getBlob(2)
                );
                lista.add(c);
            }while (cur.moveToNext());
        }
        return  lista;
    }

}
