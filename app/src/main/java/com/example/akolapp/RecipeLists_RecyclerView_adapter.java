package com.example.akolapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeLists_RecyclerView_adapter extends RecyclerView.Adapter<RecipeLists_RecyclerView_adapter.MyViewHolder> {
    Context context;
    ArrayList<Recipe> Recipes;
    private final RecyclerInterfaceRecipes recyclerInterface;
    public RecipeLists_RecyclerView_adapter(Context context, ArrayList<Recipe> Recipes, RecyclerInterfaceRecipes recyclerInterface,boolean isPublished){
        this.Recipes=Recipes;
        this.context = context;
        this.recyclerInterface = recyclerInterface;

    }
    @NonNull
    @Override
    public RecipeLists_RecyclerView_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row2,parent,false);
        return new RecipeLists_RecyclerView_adapter.MyViewHolder(view,recyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeLists_RecyclerView_adapter.MyViewHolder holder, int position) {
        holder.ChefName.setText(Recipes.get(position).getRecipeName());
    }

    @Override
    public int  getItemCount() {
        return Recipes.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ChefName;
        public MyViewHolder(@NonNull View itemView, RecyclerInterfaceRecipes recyclerInterface) {
            super(itemView);
            ChefName = itemView.findViewById(R.id.ComplaintTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerInterface!=null){
                        int pos = getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerInterface.clicked(pos,false);
                        }

                    }
                }
            });
        }
    }
}
