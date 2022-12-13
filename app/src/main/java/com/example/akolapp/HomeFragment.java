package com.example.akolapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RecyclerInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchView searchBar;
    private List<Recipe> recipes;
    FirebaseFirestore db;
    private RecyclerView recyclerView;
    HomeClientRecyclerViewAdapter myAdapter;
    boolean flag;
    private Button SignOut;
    List<Recipe> filteredList;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
       // searchBar = (SearchView) getView().findViewById(R.id.searchbar);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment












        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.RecyclerSearch);
        SignOut = (Button) getView().findViewById(R.id.SignOutButton);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), LoginPage.class);
                    startActivity(i);
                    ((Activity) getActivity()).overridePendingTransition(0, 0);



            }
        });
        recyclerView.setHasFixedSize(true);
        recipes = new ArrayList<>();
        flag = false;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        myAdapter = new HomeClientRecyclerViewAdapter(this.getActivity(),recipes,this);
        recyclerView.setAdapter(myAdapter);
        db = FirebaseFirestore.getInstance();
        searchBar = (SearchView) getView().findViewById(R.id.searchbar);
        searchBar.clearFocus();
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });

        db.collection("cuisinier")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error!=null){
                            Log.e("Firestore error",error.getMessage());
                            return;
                        }

                        for(DocumentChange dc:value.getDocumentChanges()){
                            if(dc.getType()==DocumentChange.Type.ADDED){
                                Map<String,Object> elementsDoc= new HashMap<String,Object>();
                                elementsDoc =dc.getDocument().getData();
                                for (Map.Entry<String,Object> mapElement : elementsDoc.entrySet()) {
                                    String key = mapElement.getKey();
                                    if (key.startsWith("Recipe") ) {
                                        Map<String, String> curRecipe = (Map<String, String>) mapElement.getValue();
                                        //if(curRecipe.get("published").equals("yes")) {
                                            recipes.add(new Recipe((String) curRecipe.get("Name"), (String) curRecipe.get("Allergens"), (String) curRecipe.get("Cuisine type"), (String) curRecipe.get("Description"), (String) curRecipe.get("Ingredients"), (String) curRecipe.get("Meal type"), (String) curRecipe.get("Prix"), (String) dc.getDocument().getId(), (String) elementsDoc.get("First name") + " " + elementsDoc.get("Last name")));
                                        //}
                                        //doesn't work because old recipes still don't have a published
                                    }
                                }
                            }
                        }
                        myAdapter.notifyDataSetChanged();
                    }
                });


    }

    private void filterList(String s) {
        filteredList = new ArrayList<>();
        for(Recipe recipe: recipes){
            if(recipe.getRecipeName().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(recipe);

            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this.getActivity(),"Sorry, we don't have any recipe with this name",Toast.LENGTH_SHORT).show();
            myAdapter.setList(filteredList);
            myAdapter.notifyDataSetChanged();
        }
        else{
            flag = true;
            myAdapter.setList(filteredList);
            myAdapter.notifyDataSetChanged();
        }


     }
    @Override
    public void clicked(int pos) {
        Intent intent = new Intent(this.getActivity(),RecipeClientSide.class);//to be determined
        if(flag) intent.putExtra("Recipe",filteredList.get(pos));
        else intent.putExtra("Recipe",recipes.get(pos));
        startActivity(intent);

    }
    public void LogOut(){

    }
}