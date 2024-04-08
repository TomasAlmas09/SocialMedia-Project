package com.example.socialmedia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;

public class Post implements Parcelable {
    private static final String TAG = "Post";

    private String title;
    private String modelo;
    private String username;
    private byte[] foto;
    private byte[] userPhoto;

    protected Post(Parcel in) {
        title = in.readString();
        modelo = in.readString();
        username = in.readString();
        foto = in.createByteArray();
        userPhoto = in.createByteArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(modelo);
        dest.writeString(username);
        dest.writeByteArray(foto != null ? foto : new byte[0]);
        dest.writeByteArray(userPhoto != null ? userPhoto : new byte[0]);
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

    public byte[] getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(byte[] userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s %s", title, modelo);
    }

    // Constructor to accept Bitmap objects for photos
    public Post(String title, String username, String description, Bitmap postPhoto, Bitmap userPhoto) {
        this.title = title;
        this.username = username;
        this.modelo = description;

        // Convert postPhoto to byte array if it's not null
        if (postPhoto != null) {
            this.foto = bitmapToArray(postPhoto);
        } else {
            this.foto = new byte[0]; // or handle the null case appropriately
        }

        // Convert userPhoto to byte array if it's not null
        if (userPhoto != null) {
            this.userPhoto = bitmapToArray(userPhoto);
        } else {
            this.userPhoto = new byte[0]; // or handle the null case appropriately
        }
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
