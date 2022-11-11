package com.example.akolapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ComplaintsList extends AppCompatActivity implements RecyclerInterface  {
    RecyclerView recyclerView;
    ArrayList<String> Complaints;
    ComplaintsListAdapter myAdapter;
    Button back,discard,ban,permaban;
    chefComplaintBlock chefcomplaints;
    TextView txtV1,txtV2;
    FirebaseFirestore db;
    Map<String,Object> curChef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_list);
        db = FirebaseFirestore.getInstance();
        chefcomplaints =(chefComplaintBlock) getIntent().getParcelableExtra("chef");
        Complaints = chefcomplaints.getComplaints();
        recyclerView = findViewById(R.id.ComplaintsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new ComplaintsListAdapter(ComplaintsList.this,Complaints,this);
        recyclerView.setAdapter(myAdapter);
        back =(Button) findViewById(R.id.backToChefs);
        discard = (Button)findViewById(R.id.discard);
        ban = (Button)findViewById(R.id.ban);
        permaban = (Button)findViewById(R.id.permaBan);
        txtV1 = (TextView)findViewById(R.id.textView4);
        txtV2 = (TextView)findViewById(R.id.textView9);
        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currID = chefcomplaints.getIds();
                DocumentReference docRef = db.collection("cuisinier").document(currID);
                Log.d(TAG,"allo "+currID);

                db.collection("complaints").document(currID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

                goBack();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComplaintsList.this,ComplaintsChef.class);
                startActivity(intent);
            }
        });
        ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ComplaintsList.this,Banning.class);


                String currID = chefcomplaints.getIds();
                intent.putExtra("id",currID);
                startActivity(intent);
                }
        });
        permaban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currID = chefcomplaints.getIds();
                DocumentReference docRef = db.collection("cuisinier").document(currID);
                Log.d(TAG,"allo "+currID);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                curChef = document.getData();
                                curChef.put("Ban","Banned");
                                curChef.put("Ban period","permanent");
                                docRef.set(curChef).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error writing document", e);
                                            }
                                        });



                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }

                    }
                });
                db.collection("complaints").document(currID)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

                goBack();
            }
        });


    }


    @Override
    public void clicked(int pos) {
        Intent intent = new Intent(ComplaintsList.this,ComplaintText.class);
        intent.putExtra("complaint",Complaints.get(pos));
        intent.putExtra("num",Integer.toString(pos));
        intent.putExtra("chef",chefcomplaints);
        startActivity(intent);
    }

    public void goBack(){
        Toast.makeText(this,"Cuisinier has been banned",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(ComplaintsList.this,ComplaintsChef.class);

        startActivity(intent);

    }

}