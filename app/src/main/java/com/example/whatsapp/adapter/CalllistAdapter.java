package com.example.whatsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsapp.R;
import com.example.whatsapp.model.Calllist;
import com.example.whatsapp.model.Chatlist;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class CalllistAdapter extends RecyclerView.Adapter<CalllistAdapter.Holder>{
    private List<Calllist> list;
    private Context context;

    public CalllistAdapter(List<Calllist> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CalllistAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_call_list,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Calllist calllist=list.get(position);

        holder.tvName.setText(calllist.getUserName());
        holder.tvDate.setText(calllist.getDate());

        if (calllist.getCallType().equals("incoming")){
            holder.arrow.setImageDrawable(context.getDrawable(R.drawable.arrow_down));
            holder.arrow.getDrawable().setTint(context.getResources().getColor(android.R.color.holo_green_dark));
        }else if (calllist.getCallType().equals("missed")){
            holder.arrow.setImageDrawable(context.getDrawable(R.drawable.arrow_down));
            holder.arrow.getDrawable().setTint(context.getResources().getColor(android.R.color.holo_red_dark));
        }else {
            holder.arrow.setImageDrawable(context.getDrawable(R.drawable.arrow_up));
            holder.arrow.getDrawable().setTint(context.getResources().getColor(android.R.color.holo_green_dark));
        }
        Glide.with(context).load(calllist.getUrlProfile()).into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        private TextView tvName, tvDate;
        private CircularImageView profile;
        private ImageView arrow;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tv_name);
            tvDate=itemView.findViewById(R.id.tv_date);
            profile=itemView.findViewById(R.id.image_profile);
            arrow=itemView.findViewById(R.id.image_arrow);
        }
    }
}

