package com.example.akolapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CuisinierMenusPage extends AppCompatActivity implements RecyclerInterfaceRecipes {
    RecyclerView recyclerViewPublished;
    RecyclerView recyclerViewMenu;
    RecipePublished_RecyclerView_adapter myAdapterPublished;
    RecipeLists_RecyclerView_adapter myAdapterMenu;

    FirebaseFirestore db;
    private String currID;
    ArrayList<Recipe> arrPublished;
    ArrayList<Recipe> arrMenu;
    private FirebaseAuth auth;
    private TextView welcomeChef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_menus_page);
        auth = FirebaseAuth.getInstance();
        welcomeChef = (TextView) findViewById(R.id.Welcoming);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add a recipe", Snackbar.LENGTH_LONG)
                        .setAction("Add", null).show();
                // this will be completed when the newRecipe page will be created
                /*Intent intent = new Intent(CuisinierMain.this,NewRecipe.class);
                startActivity(intent);*/
            }
        });
        recyclerViewPublished = findViewById(R.id.PublishedRecipesList);
        recyclerViewPublished.setHasFixedSize(true);
        recyclerViewPublished.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMenu= findViewById(R.id.SavedRecipesList);
        recyclerViewMenu.setHasFixedSize(true);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        arrPublished = new ArrayList<Recipe>();
        arrMenu = new ArrayList<Recipe>();
        myAdapterPublished = new RecipePublished_RecyclerView_adapter(CuisinierMenusPage.this,arrPublished,this,true);
        myAdapterMenu = new RecipeLists_RecyclerView_adapter(CuisinierMenusPage.this,arrMenu,this,false);
        recyclerViewPublished.setAdapter(myAdapterPublished);
        recyclerViewMenu.setAdapter(myAdapterMenu);

        EventChangeListener();

    }
    private void EventChangeListener() {
        currID = auth.getUid();
        DocumentReference docIdRef = db.collection("cuisinier").document(currID);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                         Map<String,Object> CuisinierInfo = document.getData();
                        for (Map.Entry<String,Object> mapElement : CuisinierInfo.entrySet()) {
                            String key = mapElement.getKey();
                            if(key.equals("First name") || key.equals("Last name") ){
                                welcomeChef.setText(welcomeChef.getText().toString()+" "+(String)mapElement.getValue().toString());
                            }
                            if (key.startsWith("Recipe")) {
                                Map<String,String> recipeDb = (Map<String,String>)mapElement.getValue();
                                Recipe recipe = new Recipe(recipeDb.get("Name"),recipeDb.get("Allergens"),recipeDb.get("Cuisine type"),recipeDb.get("Description"),recipeDb.get("Ingredients"),recipeDb.get("Meal type"),recipeDb.get("Prix"));//to add parameters after taha creates them using mapElement.getValue().get("Recipe Name" or "type of cuisine"...);
                                if(recipeDb.get("published").equals("yes")) {
                                    arrPublished.add(recipe);
                                }
                                else {
                                    arrMenu.add(recipe);
                                }
                            }
                        }
                        myAdapterMenu.notifyDataSetChanged();
                        myAdapterPublished.notifyDataSetChanged();

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



    }
    @Override
    public void clicked(int pos, boolean published) {
        /*if(published){
            Intent intent = new Intent(CuisinierMenusPage.this,PublishedRecipes.class);//publishedRecipe is hiba's page name
            intent.putExtra("Recipe", (Parcelable) arrPublished.get(pos));//we pass to the published recipe's page a recipe which will be shown afterwards
            startActivity(intent);
        }
        else  {
            Intent intent = new Intent(CuisinierMenusPage.this,MyRecipes.class);//MenuRecipe is hiba's page name
            intent.putExtra("Recipe", (Parcelable) arrMenu.get(pos));//we pass to the published recipe's page a recipe which will be shown afterwards
            startActivity(intent);
        }*/


    }
}