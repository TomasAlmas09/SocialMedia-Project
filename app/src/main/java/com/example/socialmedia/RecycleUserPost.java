package com.example.socialmedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleUserPost extends RecyclerView.Adapter<RecycleUserPost.ViewHolder> {
    private static final String TAG = "RecyclePost"; // Tag for logging

    private Context context;
    private List<Post> posts;
    private OnItemClickListener listener; // Change type to OnItemClickListener
    private String username;

    // Constructor to initialize RecyclePost adapter with a list of posts
    public RecycleUserPost(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    // Method to set the username
    public void setUsername(String username) {
        this.username = username;
    }

    // ViewHolder class to hold the views
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
            editUser = itemView.findViewById(R.id.edit_username);
            btnDelete = itemView.findViewById(R.id.bt_delete_item); // Assuming the delete button ID is btnDelete
        }
    }

    // Method to set the list of posts
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // Inflate the useritempost layout and return a new ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.useritempost, parent, false);
        return new ViewHolder(itemView);
    }

    // Bind data to the views
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.editId.setText(String.valueOf(post.getTitle()));
        holder.editModelo.setText(post.getModelo());
        holder.editUser.setText(post.getUsername());

        // Set the photo of the post if available
        if (post.getFoto() != null && post.getFoto().length > 0) {
            holder.imgFoto.setImageBitmap(Post.arrayToBitmap(post.getFoto()));
        } else {
            holder.imgFoto.setImageBitmap(null);
        }

        // Delete post when the button is clicked
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteClick(position);
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    // Return the number of posts
    @Override
    public int getItemCount() {
        return posts.size();
    }
}


