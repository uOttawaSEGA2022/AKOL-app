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
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CuisinierMain extends AppCompatActivity {
    TextView welcome;
    FirebaseFirestore db;
    private FirebaseAuth auth;
    private String currID;
    private Button SignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_main);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        SignOut = (Button) findViewById(R.id.SignOutButton);

        currID = auth.getUid();
        welcome = (TextView) findViewById(R.id.Welcome);
        DocumentReference docRef = db.collection("cuisinier").document(currID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        if(document.getData().get("Ban").equals("Not Banned")){
                            welcome.setText("Welcome, you are connected as a cuisinier. Enjoy making recipes!");
                            Intent intent = new Intent(CuisinierMain.this,CuisinierMenusPage.class);
                            startActivity(intent);
                        }
                        else {
                            if(document.getData().get("Ban period").equals("permanent")){
                                welcome.setText("Due to some complaints, you have been banned permanently from AKOL");
                            }
                            else {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                                String start_date = LocalDateTime.now().format(formatter);
                                String end_date = (String) document.getData().get("Ban period");
                                SimpleDateFormat sdf = new SimpleDateFormat(
                                        "dd-MM-yyyy HH:mm:ss");


                                try {
                                    Date d1 = sdf.parse(start_date);
                                    Date d2 = sdf.parse(end_date);
                                    long difference_In_Time
                                            = d2.getTime() - d1.getTime();


                                    long difference_In_Seconds
                                            = (difference_In_Time
                                            / 1000)
                                            % 60;

                                    long difference_In_Minutes
                                            = (difference_In_Time
                                            / (1000 * 60))
                                            % 60;

                                    long difference_In_Hours
                                            = (difference_In_Time
                                            / (1000 * 60 * 60))
                                            % 24;

                                    long difference_In_Years
                                            = (difference_In_Time
                                            / (1000l * 60 * 60 * 24 * 365));

                                    long difference_In_Days
                                            = (difference_In_Time
                                            / (1000 * 60 * 60 * 24))
                                            % 365;
                                    welcome.setText("Due to some complaints, your account is banned. You can get your account back in "+difference_In_Years
                                            + " years, "
                                            + difference_In_Days
                                            + " days, "
                                            + difference_In_Hours
                                            + " hours, "
                                            + difference_In_Minutes
                                            + " minutes, "
                                            + difference_In_Seconds
                                            + " seconds");

                                }

                                // Catch the Exception
                                catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                LogOut();

            }
        });


    }
    public void LogOut(){
        Intent intent = new Intent(CuisinierMain.this,LoginPage.class);
        startActivity(intent);
    }
}