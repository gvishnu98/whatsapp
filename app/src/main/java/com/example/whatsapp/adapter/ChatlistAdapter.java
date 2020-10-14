package com.example.whatsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsapp.ChatsActivity;
import com.example.whatsapp.DialogViewUser;
import com.example.whatsapp.R;
import com.example.whatsapp.model.Chatlist;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ChatlistAdapter extends RecyclerView.Adapter<ChatlistAdapter.Holder> {
    private List<Chatlist> list;
    private Context context;

    public ChatlistAdapter(List<Chatlist> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatlistAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_chat_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Chatlist chatlist = list.get(position);

        holder.tvName.setText(chatlist.getUserName());
        holder.tvDet.setText(chatlist.getDescription());
        holder.tvDate.setText(chatlist.getDate());

        if (chatlist.getUrlProfile().equals("")) {
            holder.profile.setImageResource(R.drawable.profile);
        } else {
            Glide.with(context).load(chatlist.getUrlProfile()).into(holder.profile);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ChatsActivity.class)
                        .putExtra("userID", chatlist.getUserID())
                        .putExtra("userName", chatlist.getUserName())
                        .putExtra("userProfile", chatlist.getUrlProfile()));
            }
        });

        holder.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogViewUser(context, chatlist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private TextView tvName, tvDet, tvDate;
        private CircularImageView profile;


        public Holder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvDet = itemView.findViewById(R.id.tv_det);
            tvDate = itemView.findViewById(R.id.tv_date);
            profile = itemView.findViewById(R.id.image_profile);
        }
    }
}