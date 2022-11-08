package com.example.akolapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ComAdapter extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<ComplaintsGp> items;

    public ComAdapter(Context context, List<ComplaintsGp> items) {
        this.context = context;
        this.items = this.items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.complaintitems,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TitleView.setText(items.get(position).getComplaintTitle());
        holder.NumberView.setText(items.get(position).getComplaintsNum());
    }

    @Override
    public int getItemCount() {
        return ComplaintsPage.n;
    }
}
