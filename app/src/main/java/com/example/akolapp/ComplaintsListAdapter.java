package com.example.akolapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComplaintsListAdapter extends RecyclerView.Adapter<ComplaintsListAdapter.MyViewHolder>{
    private final RecyclerInterface recyclerInterface;
    Context context;
    ArrayList<String> complaints;

    public ComplaintsListAdapter(Context context, ArrayList<String> complaints,RecyclerInterface recyclerInterface) {
        this.context = context;
        this.complaints = complaints;
        this.recyclerInterface = recyclerInterface;
    }

    @NonNull
    @Override
    public ComplaintsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row2,parent,false);
        return new MyViewHolder(view,recyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintsListAdapter.MyViewHolder holder, int position) {
        holder.ComplaintTitle.setText(complaints.get(position));
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ComplaintTitle;
        public MyViewHolder(@NonNull View itemView,RecyclerInterface recyclerInterface) {
            super(itemView);
            ComplaintTitle = itemView.findViewById(R.id.ComplaintTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerInterface!=null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerInterface.clicked(pos);
                        }

                    }
                }
            });
        }
    }
}
