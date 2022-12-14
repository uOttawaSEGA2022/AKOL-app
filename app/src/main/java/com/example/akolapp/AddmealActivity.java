package com.example.akolapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddmealActivity extends AppCompatActivity {
    static String ID;
    FirebaseFirestore db;
    static FirebaseAuth Auth;
    static EditText Name,MealDescription,MealRecipe,price,typeCui,mealtype,allergens;
    static Button add;
    int n;
    CheckBox publ;
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
        publ=findViewById(R.id.check);


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
                ID = Auth.getUid();
                if(TextUtils.isEmpty(mName) || TextUtils.isEmpty(Description) || TextUtils.isEmpty(recipe) || TextUtils.isEmpty(prix) || TextUtils.isEmpty(cuis) || TextUtils.isEmpty(mealtyp) || TextUtils.isEmpty(allerg)){
                   if(TextUtils.isEmpty(mName)) {
                       Name.setError("Meal Name is required!!");
                   }
                    if(TextUtils.isEmpty(Description)) {
                        MealDescription.setError("Meal Description is required!!");
                    }
                    if(TextUtils.isEmpty(recipe)) {
                        MealRecipe.setError("Meal Recipe is required!!");
                    }
                    if(TextUtils.isEmpty(prix)) {
                        price.setError("Meal Price is required!!");
                    }
                    if(TextUtils.isEmpty(cuis)) {
                        typeCui.setError("Cuisine Type is required!!");
                    }
                    if(TextUtils.isEmpty(mealtyp)) {
                        mealtype.setError("Meal Type is required!!");
                    }
                    if(TextUtils.isEmpty(allerg)) {
                        allergens.setError("Allergens is required!!");
                    }
                    return;
                }
                else {
                    DocumentReference user = db.collection("cuisinier").document(ID);
                    user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                n = Integer.valueOf(documentSnapshot.getString("number of recipe"));

                                Map<String, Object> chef = new HashMap<>();

                                chef.put("Name", mName);
                                chef.put("Ingredients", recipe);
                                chef.put("Description", Description);
                                chef.put("Meal type", mealtyp);
                                chef.put("Prix", prix);
                                chef.put("Cuisine type", cuis);
                                chef.put("Allergens", allerg);
                                if (publ.isChecked()) {
                                    chef.put("published", "yes");
                                } else if (!publ.isChecked()) {
                                    chef.put("published", "no");
                                }


                                Map<String, Object> Meals = new HashMap<>();
                                Meals.put("Recipe" + n, chef);
                                Meals.put("number of recipe", String.valueOf(n + 1));


                                user.update(Meals).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {


                                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.addMealLayout,new Homefrag()).commit();
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddmealActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Error, Please contact the Support", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                }
        });
    }
}