package com.example.akolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PublishedRecipes extends AppCompatActivity {
    FirebaseFirestore db;
    Button MoveToMyRecipes,Modify,Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_published_recipes);

        MoveToMyRecipes =  findViewById(R.id.MoveToMyRecipesButton);
        Modify = findViewById(R.id.ModifyButton);
        Back = findViewById(R.id.BackButton);
        db = FirebaseFirestore.getInstance();













    }
       }
