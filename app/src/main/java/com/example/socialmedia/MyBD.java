package com.example.socialmedia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyBD extends SQLiteOpenHelper {
    public  static  final String DATABASENAME ="carros.db";
    public static final String TB_CARROS = "tbCarros";
    public static final String ID = "id";
    public static final String MODELO = "modelo";
    public static final String CATEGORIA = "categoria";
    public static final String FOTO = "foto";

    Context ctx;
    public MyBD(@Nullable Context context,
                int version) {
        super(context, DATABASENAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TB_CARROS + " ( " +
                "    " + ID + "        INT  PRIMARY KEY, " +
                "    " + MODELO + "    TEXT, " +
                "    " + CATEGORIA + " TEXT, " +
                "    " + FOTO + "      BLOB " +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql ="drop table if exists   " + TB_CARROS + ";";
        db.execSQL(sql);
        onCreate(db);
    }
    public static  final String TAG ="Xpto";
    long  inserirCarro(Post novo){
        long resp=0;
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(ID, novo.getId());
            cv.put(MODELO, novo.getModelo());
            cv.put(CATEGORIA, novo.getCategoria());
            cv.put(FOTO,novo.getFoto());
            resp=db.insert(TB_CARROS,null,cv);
            db.setTransactionSuccessful();
        }catch(SQLException erro){
            Log.i(TAG,erro.getMessage());
        }
        db.endTransaction();
        db.close();
        return  resp;
    }

    List<Post> carregaLista(){
        List<Post> lista = new ArrayList<>();
        SQLiteDatabase db =getReadableDatabase();
        String sql="select * from "+ TB_CARROS +" ;";
        Cursor cur = db.rawQuery(sql,null);
        if(cur.getCount()>0){
            cur.moveToFirst();
            do{
                Post c = new Post(
                        cur.getInt(0),
                        cur.getString(1),
                        cur.getString(2),
                        cur.getBlob(3)
                );
                lista.add(c);
            }while (cur.moveToNext());
        }
        return  lista;
    }
}
