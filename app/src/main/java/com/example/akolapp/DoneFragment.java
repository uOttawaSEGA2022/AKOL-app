package com.example.akolapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoneFragment extends Fragment implements RecyclerInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerViewPublished;
    HomeClientRecyclerViewAdapter myAdapterPublished;
    FirebaseFirestore db;
    private String currID;
    ArrayList<Recipe> recipes;
    private FirebaseAuth auth;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoneFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoneFragment newInstance(String param1, String param2) {
        DoneFragment fragment = new DoneFragment();
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
        return inflater.inflate(R.layout.fragment_done, container, false);
    }    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        recyclerViewPublished = getView().findViewById(R.id.RecyclerOrders);
        recyclerViewPublished.setHasFixedSize(true);
        recyclerViewPublished.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        db = FirebaseFirestore.getInstance();
        recipes = new ArrayList<>();
        myAdapterPublished = new HomeClientRecyclerViewAdapter(this.getActivity(),recipes,this);
        recyclerViewPublished.setAdapter(myAdapterPublished);
        myAdapterPublished.notifyDataSetChanged();
        EventChangeListener();
    }

    private void EventChangeListener() {
        currID = auth.getUid();
        DocumentReference docIdRef = db.collection("ClientUser").document(currID);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String,Object> ClientInfo = document.getData();
                        for (Map.Entry<String,Object> mapElement : ClientInfo.entrySet()) {
                            String key = mapElement.getKey();
                            if (key.startsWith("Recipe")) {
                                Map<String,String> recipeDb = (Map<String,String>)mapElement.getValue();
                                Log.d(TAG,"allo hada id: " + currID);
                                Recipe recipe = new Recipe(recipeDb.get("Name"),recipeDb.get("Allergens"),recipeDb.get("Cuisine type"),recipeDb.get("Description"),recipeDb.get("Ingredients"),recipeDb.get("Meal type"),recipeDb.get("Prix"),currID, (String) recipeDb.get("ChefName"));//to add parameters after taha creates them using mapElement.getValue().get("Recipe Name" or "type of cuisine"...);
                                //recipes.add(new Recipe((String) curRecipe.get("Name"), (String) curRecipe.get("Allergens"), (String) curRecipe.get("Cuisine type"), (String) curRecipe.get("Description"), (String) curRecipe.get("Ingredients"), (String) curRecipe.get("Meal type"), (String) curRecipe.get("Prix"), (String) curRecipe.get("id"),(String) elementsDoc.get("First name")+" "+elementsDoc.get("Last name")));

                                if(recipeDb.get("isDelivered").equals("yes")) {
                                    recipes.add(recipe);
                                }

                            }
                        }
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
        Intent intent = new Intent(this.getActivity(),LoginPage.class);
        startActivity(intent);
    }

    @Override
    public void clicked(int pos) {
        /*Intent intent = new Intent(CuisinierMenusPage.this,PublishedRecipes.class);//publishedRecipe is hiba's page name
        intent.putExtra("Recipe", (Parcelable) recipes.get(pos));//we pass to the published recipe's page a recipe which will be shown afterwards

        startActivity(intent);*/
    }
}