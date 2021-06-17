package com.example.encrypto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.encrypto.R;
import com.example.encrypto.listeners.UserClickListener;
import com.example.encrypto.models.User;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private Context context;
    private List<User> list;
    private UserClickListener listener;

    public UsersAdapter(Context context, List<User> list, UserClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UsersViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_user, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {

        User currentItem = list.get(position);

        holder.userName.setText(currentItem.getUsername());
        holder.userStatus.setText(currentItem.getStatus());

        Glide.with(context).load(currentItem.getProfile_image()).into(holder.userImage);

        holder.root.setOnClickListener(v -> listener.onUserItemClicked(currentItem));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName, userStatus;
        MaterialCardView root;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.itemChatUserImage);
            userName = itemView.findViewById(R.id.itemChatUserName);
            userStatus = itemView.findViewById(R.id.itemChatLastMessage);
            root = itemView.findViewById(R.id.chatRoot);
        }
    }
}
