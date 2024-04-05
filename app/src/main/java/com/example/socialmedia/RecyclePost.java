package com.example.socialmedia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclePost extends RecyclerView.Adapter<RecyclePost.ViewHolder> {
    private static final String TAG = "RecyclePost"; // Tag for logging

    private Context context;
    private List<Post> posts;
    private IOnSacaFoto listener;

    public RecyclePost(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    public void setOnSacaFotoListener(IOnSacaFoto listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView editId;
        TextView editModelo, editUser;
        ImageView imgFoto, imgUser;
        Button btnDelete;
        Button btnUpdate;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            editId = itemView.findViewById(R.id.edit_idcar_itempost);
            editModelo = itemView.findViewById(R.id.edit_modelo_itempost);
            imgFoto = itemView.findViewById(R.id.img_foto_itempost);
            imgUser = itemView.findViewById(R.id.img_foto_user);
            editUser = itemView.findViewById(R.id.edit_username);

        }
    }



    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempost, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.editId.setText(String.valueOf(post.getTitle()));
        holder.editModelo.setText(post.getModelo());
        holder.editUser.setText(post.getUsername());

        if (post.getFoto() != null && post.getFoto().length > 0) {
            holder.imgFoto.setImageBitmap(Post.arrayToBitmap(post.getFoto()));
        } else {
            // Clear the image if there is no photo for this post
            holder.imgFoto.setImageBitmap(null);
        }



    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
