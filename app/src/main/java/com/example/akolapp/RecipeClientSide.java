package com.example.akolapp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class RecipeClientSide extends AppCompatActivity {
    TextView recipeName;
    TextView allergens;
    TextView cuisineType;
    TextView description;
    TextView ingredients;
    TextView mealType;
    TextView price;
    Button back;
    Button buyRecipe;
    FirebaseFirestore db;
    FirebaseAuth Auth;
    String ID;
    static String name = "";

    //private String currID;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_client_side);

        Recipe recipe = getIntent().getParcelableExtra("Recipe");
        recipeName = findViewById(R.id.RecipeName);
        cuisineType = findViewById(R.id.CuisineType);
        ingredients = findViewById(R.id.Ingredients);
        mealType = findViewById(R.id.MealType);
        description = findViewById(R.id.description);
        allergens = findViewById(R.id.Allergens);
        price = findViewById(R.id.Prix);
        back = findViewById(R.id.BackButton);
        buyRecipe = findViewById(R.id.buyButton);
        recipeName.setText(recipeName.getText().toString() +recipe.getRecipeName());
        cuisineType.setText(cuisineType.getText().toString() + recipe.getCuisineType());
        ingredients.setText(ingredients.getText().toString() + recipe.getIngredients());
        mealType.setText(mealType.getText().toString() + recipe.getMealType());
        description.setText(description.getText().toString() + recipe.getDescription());
        allergens.setText(allergens.getText().toString() + recipe.getAllergens());
        price.setText(price.getText().toString() + recipe.getPrice());
        Auth=FirebaseAuth.getInstance();
        ID= Auth.getUid();
        db = FirebaseFirestore.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.recipeClient,new HomeFragment()).commit();
                finish();

            }
        });
        buyRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DocumentReference user = db.collection("ClientUser").document(ID);
                user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            int n = Integer.valueOf(documentSnapshot.getString("NumberOfOrders"));
                            name = documentSnapshot.getString("FirstName") + " " + documentSnapshot.getString("LastName");
                            Map<String, Object> client = new HashMap<>();
                            client.put("Name", recipe.getRecipeName());
                            client.put("Ingredients", recipe.getIngredients());
                            client.put("Description", recipe.getDescription());
                            client.put("Meal type", recipe.getMealType());
                            client.put("Prix", recipe.getPrice());
                            client.put("Cuisine type", recipe.getCuisineType());
                            client.put("Allergens", recipe.getAllergens());
                            client.put("ChefID",recipe.getId());
                            client.put("isDelivered","no");
                            client.put("ChefName",recipe.getChefName());
                            name = recipe.getChefName();
                            Log.d(TAG,"allo hada chefname: " + recipe.getChefName());
                            Map<String, Object> Meals = new HashMap<>();
                            Meals.put("Recipe" + n, client);
                            Meals.put("NumberOfOrders", String.valueOf(n + 1));


                            user.update(Meals).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    successfulOperation();
                                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.recipeClient,new HomeFragment()).commit();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Error, Please contact the Support", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                //auth = FirebaseAuth.getInstance();
                String currID = recipe.getId();
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

                                int n = Integer.valueOf((String) CuisinierInfo.get("number of recipe"));

                                Map<String, Object> order = new HashMap<>();
                                order.put("Client name",name);
                                order.put("ClientID",ID);
                                order.put("recipe name", recipe.getRecipeName());
                                CuisinierInfo.put("Order" + n,order);
                                CuisinierInfo.put("number of orders", String.valueOf(n + 1));





                                docIdRef.set(CuisinierInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "Recipe bought");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "Recipe couldn't be bought ");
                                            }
                                        });
                                        //ends here
                                    }
                            else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });
    }
    public void successfulOperation(){
        Toast.makeText(RecipeClientSide.this,"Recipe bought successfully",Toast.LENGTH_LONG).show();
    }
}

