package com.example.akolapp;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ComplaintsChef extends AppCompatActivity implements RecyclerInterface {
    RecyclerView recyclerView;
    ArrayList<chefComplaintBlock> arr;
    ChefComplaints_RecyclerView_adapter myAdapter;
    FirebaseFirestore db;
    private Button SignOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_complaints);
        recyclerView = findViewById(R.id.ChefList);
        SignOut = (Button) findViewById(R.id.SignOutButton);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        arr = new ArrayList<chefComplaintBlock>();
        myAdapter = new ChefComplaints_RecyclerView_adapter(ComplaintsChef.this,arr,this);
        recyclerView.setAdapter(myAdapter);
        EventChangeListener();
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LogOut();

            }
        });

    }
    public void LogOut(){
        Intent intent = new Intent(ComplaintsChef.this,LoginPage.class);
        startActivity(intent);
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

    @Override
    public void clicked(int pos) {
        Intent intent = new Intent(ComplaintsChef.this,ComplaintsList.class);
        intent.putExtra("chef",arr.get(pos));
        startActivity(intent);


    }
}