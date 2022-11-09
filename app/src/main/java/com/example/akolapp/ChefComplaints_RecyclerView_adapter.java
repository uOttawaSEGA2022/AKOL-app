package com.example.akolapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChefComplaints_RecyclerView_adapter extends RecyclerView.Adapter<ChefComplaints_RecyclerView_adapter.MyViewHolder> {
    Context context;
    ArrayList<chefComplaintBlock> chefComplaints;
    private final RecyclerInterface recyclerInterface;
    public ChefComplaints_RecyclerView_adapter(Context context, ArrayList<chefComplaintBlock> chefComplaints, RecyclerInterface recyclerInterface){
        this.chefComplaints=chefComplaints;
        this.context = context;
        this.recyclerInterface = recyclerInterface;

    }
    @NonNull
    @Override
    public ChefComplaints_RecyclerView_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row,parent,false);
        return new MyViewHolder(view,recyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ChefComplaints_RecyclerView_adapter.MyViewHolder holder, int position) {
        holder.ChefName.setText(chefComplaints.get(position).getChefName());
        holder.NumOfComplaints.setText(chefComplaints.get(position).getComplaintsNumber());
    }

    @Override
    public int  getItemCount() {
        return chefComplaints.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ChefName;
        TextView NumOfComplaints;
        public MyViewHolder(@NonNull View itemView, RecyclerInterface recyclerInterface) {
            super(itemView);
            ChefName = itemView.findViewById(R.id.chefName);
            NumOfComplaints = itemView.findViewById(R.id.complaintsNumber);
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
