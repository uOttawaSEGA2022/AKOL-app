package com.example.akolapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homefrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homefrag extends Fragment implements RecyclerInterfaceRecipes {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerViewPublished;
    RecyclerView recyclerViewMenu;
    RecipePublished_RecyclerView_adapter myAdapterPublished;
    RecipeLists_RecyclerView_adapter myAdapterMenu;
    private Button SignOut;
    FirebaseFirestore db;
    private String currID;
    ArrayList<Recipe> arrPublished;
    ArrayList<Recipe> arrMenu;
    private FirebaseAuth auth;
    private TextView welcomeChef;

    public Homefrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Homefrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Homefrag newInstance(String param1, String param2) {
        Homefrag fragment = new Homefrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homefrag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        SignOut = (Button) getView().findViewById(R.id.SignOutButton);

        welcomeChef = (TextView) getView().findViewById(R.id.Welcoming);
        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add a recipe", Snackbar.LENGTH_LONG)
                        .setAction("Add", null).show();
                //this will be completed when the newRecipe page will be created
                Intent intent = new Intent(getActivity(),AddmealActivity.class);
                startActivity(intent);
            }
        });
        recyclerViewPublished =getView().findViewById(R.id.PublishedRecipesList);
        recyclerViewPublished.setHasFixedSize(true);
        recyclerViewPublished.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMenu=getView().findViewById(R.id.SavedRecipesList);
        recyclerViewMenu.setHasFixedSize(true);
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        db = FirebaseFirestore.getInstance();
        arrPublished = new ArrayList<Recipe>();
        arrMenu = new ArrayList<Recipe>();
        myAdapterPublished = new RecipePublished_RecyclerView_adapter(getActivity(),arrPublished,this,true);
        myAdapterMenu = new RecipeLists_RecyclerView_adapter(getActivity(),arrMenu,this,false);
        recyclerViewPublished.setAdapter(myAdapterPublished);
        recyclerViewMenu.setAdapter(myAdapterMenu);
        myAdapterMenu.notifyDataSetChanged();
        myAdapterPublished.notifyDataSetChanged();
        EventChangeListener();
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LogOut();

            }
        });

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
                                Recipe recipe = new Recipe(recipeDb.get("Name"),recipeDb.get("Allergens"),recipeDb.get("Cuisine type"),recipeDb.get("Description"),recipeDb.get("Ingredients"),recipeDb.get("Meal type"),recipeDb.get("Prix"),currID, (String) CuisinierInfo.get("First name")+" "+CuisinierInfo.get("Last name"));//to add parameters after taha creates them using mapElement.getValue().get("Recipe Name" or "type of cuisine"...);
                                if(recipeDb.get("published").equals("yes")) {
                                    arrPublished.add(recipe);
                                }
                                else {
                                    arrMenu.add(recipe);
                                }
                            }
                        }
                        myAdapterPublished = new RecipePublished_RecyclerView_adapter(getActivity(),arrPublished,Homefrag.this,true);
                        myAdapterMenu = new RecipeLists_RecyclerView_adapter(getActivity(),arrMenu,Homefrag.this,false);
                        recyclerViewPublished.setAdapter(myAdapterPublished);
                        recyclerViewMenu.setAdapter(myAdapterMenu);
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
    public void LogOut(){
        Intent intent = new Intent(getActivity(),LoginPage.class);
        startActivity(intent);
    }
    @Override
    public void clicked(int pos, boolean published) {
        if(published){
            Intent intent = new Intent(getActivity(),PublishedRecipes.class);//publishedRecipe is hiba's page name
            intent.putExtra("Recipe", (Parcelable) arrPublished.get(pos));//we pass to the published recipe's page a recipe which will be shown afterwards

            startActivity(intent);
        }
        else  {
            Intent intent = new Intent(getActivity(),UnpublishedRecipes.class);//MenuRecipe is hiba's page name
            intent.putExtra("Recipe",  arrMenu.get(pos));//we pass to the published recipe's page a recipe which will be shown afterwards

            startActivity(intent);
        }


    }

}