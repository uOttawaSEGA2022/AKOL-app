package com.example.akolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class MyRecipes extends AppCompatActivity {
    FirebaseFirestore db;
    Button MoveToPublishedRecipes,Delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);

        db = FirebaseFirestore.getInstance();
        MoveToPublishedRecipes = findViewById(R.id.MoveToPublishedRecipesButton);
        Delete = findViewById(R.id.DeleteButton);





    }
}