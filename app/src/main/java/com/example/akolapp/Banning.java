package com.example.akolapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Date;
import java.util.Map;

public class Banning extends AppCompatActivity {
    EditText days;
    EditText hours;
    Button ban;
    FirebaseFirestore db;
    Map<String,Object> curChef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banning);
        db = FirebaseFirestore.getInstance();

        days = (EditText) findViewById(R.id.editDays);
        hours = (EditText) findViewById(R.id.editHours);
        ban = (Button)findViewById(R.id.bann);
        ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currID = getIntent().getStringExtra("id");
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




                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                                String start_date = LocalDateTime.now().format(formatter);
                                //start_date.
                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                Date d1;
                                d1 = new Date();
                                try {
                                    d1 = sdf.parse(start_date);
                                    //Log.d(TAG,banHours+" "+banDays);
                                    Long a = (Long)Long.parseLong(hours.getText().toString())*60*60*1000;
                                    Long b = (Long)Long.parseLong(days.getText().toString())*24*60*60*1000;
                                    d1.setTime(d1.getTime()+a+b);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String banDate = sdf.format(d1);
                                curChef.put("Ban period",banDate);
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
    public void goBack(){
        Toast.makeText(this,"Cuisinier has been banned",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(Banning.this,ComplaintsChef.class);

        startActivity(intent);

    }
}