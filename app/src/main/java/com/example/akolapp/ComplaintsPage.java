package com.example.akolapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.channels.ProduceKt;

public class ComplaintsPage extends AppCompatActivity {
    private FirebaseFirestore db;
     List<ComplaintsGp> items;
    Integer n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_page);
        String ChefName="";
        String Complaints="";
        db = FirebaseFirestore.getInstance();
        currID=hibapage.currID;
        DocumentReference docIdRef = db.collection("complaints").document(currID);
        docIdRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    n= Integer.valueOf(documentSnapshot.getString("Number"));
                    for(int i=1; i<n;i++){
                        items.add(new ComplaintsGp((documentSnapshot.getString("Complaint"+i)),i));
                    }
                }
            }
        });

        RecyclerView recyclerView=findViewById(R.id.ComplaintsRecycled);





        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ComAdapter(getApplicationContext(),items));

    }

}
