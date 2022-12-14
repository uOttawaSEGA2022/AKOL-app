package com.example.akolapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
 * Use the {@link OrdersFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFrag extends Fragment implements RecyclerInterface {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerViewPublished;
    OrdersRecyclAdapter myAdapterPublished;
    FirebaseFirestore db;
    private String currID;
    ArrayList<Order> recipes;
    private FirebaseAuth auth;
    public OrdersFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFrag newInstance(String param1, String param2) {
        OrdersFrag fragment = new OrdersFrag();
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
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        recyclerViewPublished = getView().findViewById(R.id.Recyclercuis);
        recyclerViewPublished.setHasFixedSize(true);
        recyclerViewPublished.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        db = FirebaseFirestore.getInstance();
        recipes = new ArrayList<>();
        myAdapterPublished = new OrdersRecyclAdapter(this.getActivity(),recipes,this);
        recyclerViewPublished.setAdapter(myAdapterPublished);
        myAdapterPublished.notifyDataSetChanged();
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
                        Map<String,Object> ClientInfo = document.getData();
                        for (Map.Entry<String,Object> mapElement : ClientInfo.entrySet()) {
                            String key = mapElement.getKey();
                            if (key.startsWith("Order")) {
                                Map<String,String> orderN = (Map<String,String>)mapElement.getValue();
                                Log.d(TAG,"allo hada id: " + currID);
                                Order order = new Order(orderN.get("Client name"),orderN.get("ClientID"),orderN.get("recipe name"));
                                        //recipes.add(new Recipe((String) curRecipe.get("Name"), (String) curRecipe.get("Allergens"), (String) curRecipe.get("Cuisine type"), (String) curRecipe.get("Description"), (String) curRecipe.get("Ingredients"), (String) curRecipe.get("Meal type"), (String) curRecipe.get("Prix"), (String) curRecipe.get("id"),(String) elementsDoc.get("First name")+" "+elementsDoc.get("Last name")));

                                if(orderN.get("isDone").equals("no")) {
                                    recipes.add(order);
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

    @Override
    public void clicked(int pos) {
        Intent intent = new Intent(getActivity(),OrdersClientSide.class);//publishedRecipe is hiba's page name
        intent.putExtra("Order", (Parcelable) recipes.get(pos));//we pass to the published recipe's page a recipe which will be shown afterwards
        myAdapterPublished.notifyDataSetChanged();
        startActivity(intent);

    }




    /*public void info(){
        DocumentReference user = db.collection("cuisinier").document(ID);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    int a=1;
                    //Log.d(TAG,(String) document.getData().get("number of orders"));
                    numb=Integer.valueOf((String) document.getData().get("number of orders"));
                    Map<String,Object> CuisinierInfo = document.getData();

                    for (Map.Entry<String,Object> mapElement : CuisinierInfo.entrySet()) {
                        String key = mapElement.getKey();
                        if(a<=numb){
                            if(key.equals("Order"+a)){
                                Map<String,String> OrderDb = (Map<String,String>)mapElement.getValue();
                                items.add(OrderDb.get("Client name"));
                                List<String> list = new ArrayList<String>(OrderDb.values());
                                if(OrderDb.get("isDone").equals("no")){
                                    Map<String, Object> Ordpush = new HashMap<>();
                                    if(siDone==true){
                                        Map<String, Object> Ord = new HashMap<>();
                                        Ord.put("Client name",OrderDb.get("Client name"));
                                        Ord.put("ClientID",OrderDb.get("ClientID"));
                                        Ord.put("isDone","yes");
                                        Ord.put("recipe name",OrderDb.get("recipe name"));
                                        Ordpush.put("Order"+a,Ord);

                                        Map<String, Object> Cle = new HashMap<>();
                                        Cle.put("ChefID",ID);
                                        Cle.put("recipe name",OrderDb.get("recipe name"));
                                        Cle.put("isDelivered","yes");
                                        Map<String, Object> Client = new HashMap<>();
                                        Client.put("Recipe"+(a-1),Cle);
                                        user.set(Client).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "Done");
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "Error, Please contact the Support");
                                            }
                                        });

                                    }
                                    else if(siDone==false){
                                        Map<String, Object> Ord = new HashMap<>();
                                        Ord.put("Client name",OrderDb.get("Client name"));
                                        Ord.put("ClientID",OrderDb.get("ClientID"));
                                        Ord.put("isDone","rejected");
                                        Ord.put("recipe name",OrderDb.get("recipe name"));
                                    }
                                    else{
                                        items.add(OrderDb.get("Client name"));
                                    }
                                    user.set(Ordpush).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG, "Done");
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Error, Please contact the Support");
                                        }
                                    });
                                }
                            }
                        }
                        a++;
                    }
                }
                else {
                    Log.d(TAG, "Error, Please contact the Support");
                }
            }
        });
    }*/
}