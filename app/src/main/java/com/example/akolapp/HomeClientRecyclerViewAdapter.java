package com.example.akolapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class HomeClientRecyclerViewAdapter extends RecyclerView.Adapter<HomeClientRecyclerViewAdapter.MyViewHolder> {
    Context context;
    List<Recipe> recipesList;
    private final RecyclerInterface recyclerInterface;
    public HomeClientRecyclerViewAdapter(Context context, List<Recipe> recipesList, RecyclerInterface recyclerInterface){
        this.recipesList=recipesList;
        this.context = context;
        this.recyclerInterface = recyclerInterface;

    }
    public void setList(List<Recipe> rec){
        this.recipesList = rec;
    }
    @NonNull
    @Override
    public HomeClientRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row,parent,false);
        return new MyViewHolder(view,recyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeClientRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.ChefName.setText(recipesList.get(position).getRecipeName());
        holder.NumOfComplaints.setText(recipesList.get(position).getChefName());
    }

    @Override
    public int  getItemCount() {
        return recipesList.size();
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
