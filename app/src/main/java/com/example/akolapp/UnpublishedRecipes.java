package com.example.akolapp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UnpublishedRecipes extends AppCompatActivity {
    TextView recipeName;
    TextView allergens;
    TextView cuisineType;
    TextView description;
    TextView ingredients;
    TextView mealType;
    TextView price;
    Button back;
    Button toMyRecipes;
    FirebaseFirestore db;
    RecipePublished_RecyclerView_adapter myAdapter;

    //private String currID;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unpublished_recipes);


        Recipe recipe = getIntent().getParcelableExtra("Recipe");
        recipeName = findViewById(R.id.RecipeName);
        cuisineType = findViewById(R.id.CuisineType);
        ingredients = findViewById(R.id.Ingredients);
        mealType = findViewById(R.id.MealType);
        description = findViewById(R.id.description);
        allergens = findViewById(R.id.Allergens);
        price = findViewById(R.id.Prix);
        back = findViewById(R.id.BackButton);
        toMyRecipes = findViewById(R.id.MoveToMyRecipesButton);
        recipeName.setText(recipeName.getText().toString() +recipe.getRecipeName());
        cuisineType.setText(cuisineType.getText().toString() + recipe.getCuisineType());
        ingredients.setText(ingredients.getText().toString() + recipe.getIngredients());
        mealType.setText(mealType.getText().toString() + recipe.getMealType());
        description.setText(description.getText().toString() + recipe.getDescription());
        allergens.setText(allergens.getText().toString() + recipe.getAllergens());
        price.setText(price.getText().toString() + recipe.getPrice());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.UnpubLayout,new Homefrag()).commit();
                finish();
            }
        });
        toMyRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                String currID = auth.getUid();
                Log.d(TAG, "hello " + currID);
                db = FirebaseFirestore.getInstance();
                DocumentReference docIdRef = db.collection("cuisinier")
                        .document(currID);
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String,Object> CuisinierInfo = document.getData();
                                for (Map.Entry<String,Object> mapElement : CuisinierInfo.entrySet()) {
                                    String key = mapElement.getKey();
                                    if (key.startsWith("Recipe") && ((Map<String,String>)mapElement.getValue()).get("Name").toString().equals(recipe.getRecipeName())) {
                                        Map<String,String> recipeDb = (Map<String,String>)mapElement.getValue();
                                        recipeDb.put("published","yes");
                                        //starts here
                                        CuisinierInfo.put(key,recipeDb);
                                        docIdRef.set(CuisinierInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                                                             Log.d(TAG, "Recipe is unpublished now");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "Recipe is still published, some error occured ");
                                            }
                                        });
                                        //ends here
                                    }
                                }


                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.UnpubLayout,new Homefrag()).commit();
                finish();
            }
        });



    }
}