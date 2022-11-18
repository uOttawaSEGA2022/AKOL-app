package com.example.akolapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class AddmealActivity extends AppCompatActivity {
    static String ID;
    FirebaseFirestore db;
    static FirebaseAuth Auth;
    static EditText Name,MealDescription,MealRecipe,price,typeCui,mealtype,allergens;
    static Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmeal);
        Auth=FirebaseAuth.getInstance();
        Name=findViewById(R.id.Mealname);
        MealDescription=findViewById(R.id.description);
        MealRecipe=findViewById(R.id.Recipe);
        price=findViewById(R.id.Price);
        typeCui=findViewById(R.id.Cuisine);
        mealtype=findViewById(R.id.MealType);
        allergens=findViewById(R.id.Allergens);
        add=findViewById(R.id.Add);
        db= FirebaseFirestore.getInstance();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mName= Name.getText().toString().trim();
                String Description = MealDescription.getText().toString().trim();
                String recipe = MealRecipe.getText().toString().trim();
                String prix = price.getText().toString().trim();
                String cuis= typeCui.getText().toString().trim();
                String mealtyp= mealtype.getText().toString().trim();
                String allerg= allergens.getText().toString().trim();
                int n=0;
                DocumentReference user = db.collection("cuisinier").document(ID);
                user.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                    }
                });
                    Map<String, Object> chef = new HashMap<>();
                    chef.put("Name", mName);
                    chef.put("Ingredients", recipe);
                    chef.put("Description", Description);
                    chef.put("Meal type",mealtyp);
                    chef.put("Prix",prix);
                    chef.put("Cuisine type",cuis);
                    chef.put("Allergens",allerg);

                    Map<String, Object> Meals= new HashMap<>();
                    Meals.put("Recipe",chef);
                    Meals.put("number of recipe",n+1)
                    ID = "52OzAPfiECWHTGp7i4aHbf44akM2";/*Auth.getUid();*/



                    user.update(Meals).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddmealActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddmealActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        });
    }
}