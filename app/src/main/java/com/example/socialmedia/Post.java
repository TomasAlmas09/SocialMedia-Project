package com.example.socialmedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;

public class Post implements Parcelable {
    //region campos
    private  int id;
    private String  modelo;
    private  String categoria;

    private  byte[] foto;
    // endregion

    //region Getters & Setters

    protected Post(Parcel in) {
        id = in.readInt();
        modelo = in.readString();
        categoria = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            foto = in.readBlob();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(modelo);
        dest.writeString(categoria);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            dest.writeBlob(foto);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    //endregion


    @NonNull
    @Override
    public String toString() {
        return String.format("%d %s  %s", id,modelo,categoria);
    }

    public Post(int id, String modelo, String categoria, byte[] foto) {
        this.id = id;
        this.modelo = modelo;
        this.categoria = categoria;
        this.foto = foto;
    }
    public Post(int id, String modelo, String categoria, Bitmap bmp) {
        this.id = id;
        this.modelo = modelo;
        this.categoria = categoria;
        this.foto = bitmaptoarray(bmp);
    }

    public  static byte[] bitmaptoarray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        return stream.toByteArray();
    }

    public  static  Bitmap arraytobitmap(byte[] foto){
        return BitmapFactory.decodeByteArray(foto,0,foto.length);
    }
}
