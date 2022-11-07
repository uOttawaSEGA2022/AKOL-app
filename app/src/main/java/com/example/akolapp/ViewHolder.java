package com.example.akolapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView ChefNameView,TitleView,NumberView;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        NumberView=itemView.findViewById(R.id.numberview);

        TitleView=itemView.findViewById(R.id.Title);

    }
}
