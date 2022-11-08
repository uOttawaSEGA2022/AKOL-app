package com.example.akolapp;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ComplaintsChef extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<chefComplaintBlock> arr;
    ChefComplaints_RecyclerView_adapter myAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_complaints);
        recyclerView = findViewById(R.id.ChefList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        arr = new ArrayList<chefComplaintBlock>();
        myAdapter = new ChefComplaints_RecyclerView_adapter(ComplaintsChef.this,arr);
        recyclerView.setAdapter(myAdapter);
        EventChangeListener();



    }

    private void EventChangeListener() {
        db.collection("complaints")
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
                                     String num = (String)elementsDoc.get("Number");
                                     String name = (String)elementsDoc.get("Name");
                                     ArrayList<String> lstOfComp = new ArrayList<>();
                                     for (Map.Entry<String,Object> mapElement : elementsDoc.entrySet()) {
                                         String key = mapElement.getKey();
                                         if (key.startsWith("Complaint")) {
                                             lstOfComp.add((String) mapElement.getValue());
                                         }
                                     }
                                     arr.add(new chefComplaintBlock(dc.getDocument().getId(),name,num,lstOfComp));
                                 }
                                 myAdapter.notifyDataSetChanged();
                             }

                    }
                });
    }

}