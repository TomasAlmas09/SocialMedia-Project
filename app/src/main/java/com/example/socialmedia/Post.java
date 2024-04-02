package com.example.socialmedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;

public class Post implements Parcelable {
    //region fields
    private String title;
    private String modelo;
    private byte[] foto;
    // endregion

    //region Getters & Setters

    protected Post(Parcel in) {
        title = in.readString();
        modelo = in.readString();
        foto = in.createByteArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(modelo);
        dest.writeByteArray(foto);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", title, modelo);
    }

    public Post(String title, String modelo, byte[] foto) {
        this.title = title;
        this.modelo = modelo;
        this.foto = foto;
    }

    public Post(String title, String modelo, Bitmap bmp) {
        this.title = title;
        this.modelo = modelo;
        this.foto = bitmapToArray(bmp);
    }

    public static byte[] bitmapToArray(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap arrayToBitmap(byte[] foto) {
        return BitmapFactory.decodeByteArray(foto, 0, foto.length);
    }
}
