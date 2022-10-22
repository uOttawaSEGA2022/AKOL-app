package com.example.akolapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirstPage extends AppCompatActivity {
    private Button SignOut;
    private TextView welcomeTxt;
    private FirebaseAuth auth;
    private String currID;
    private FirebaseFirestore storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        auth = FirebaseAuth.getInstance();
        storage = FirebaseFirestore.getInstance();
        SignOut = (Button) findViewById(R.id.SignOutButton);
        welcomeTxt = (TextView)findViewById(R.id.WelcomeText);
        currID = auth.getUid();
        DocumentReference docIdRef = storage.collection("cuisinier").document(currID);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        welcomeTxt.setText("Welcome, you are connected as a cuisinier");

                    } else {
                        Log.d(TAG, "Document does not exist!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());

                }
            }
        });
        if(currID .equals("h2OL7WZbSeb63xjcjNhoR8bY7Ps1")){
            welcomeTxt.setText("Welcome you are connected as an admin");
        }
        else {
            DocumentReference docIdRef2 = storage.collection("ClientUser").document(currID);
            docIdRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "Document exists!");
                            welcomeTxt.setText("Welcome, you are connected as a client");

                        } else {
                            Log.d(TAG, "Document does not exist!");
                        }
                    } else {
                        Log.d(TAG, "Failed with: ", task.getException());

                    }
                }
            });

        }



        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LogOut();

            }
        });


    }
    public void LogOut(){
        Intent intent = new Intent(FirstPage.this,LoginPage.class);
        startActivity(intent);


    }
}