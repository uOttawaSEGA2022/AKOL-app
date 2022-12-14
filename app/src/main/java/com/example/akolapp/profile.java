package com.example.akolapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profile extends AppCompatActivity {
    static String ID;
    FirebaseFirestore db;
    static FirebaseAuth Auth;
    static TextView UserName, UserAdress, UserRating;
    static ImageView UserPic;
    static Button changeImage,logoutButton ;
    static Integer Rating;
    static String FName,LName,Name,Adress,Email;
    static ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profilefrag);

        Auth=FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        ID = Auth.getUid();

        UserName=findViewById(R.id.username);
        UserAdress=findViewById(R.id.userAddress);
        UserRating=findViewById(R.id.userRating);
        UserPic=findViewById(R.id.userPic);
        logoutButton =findViewById(R.id.logoutButton);

        if(ID==null){
            Toast.makeText(getApplicationContext(), "Error, Please contact the Support", Toast.LENGTH_LONG).show();
        }
        else{
            showUserProfile();
        }


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Auth.signOut();
                    finish();
                    Intent intent = new Intent(profile.this, LoginPage.class);
                    startActivity(intent);
            }
        });
        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent();
                    i.setType("image/*");
                    i.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(i, "Select Picture"), 100);

            }
        });}

    public void showUserProfile(){
        DocumentReference user = db.collection("cuisinier").document(ID);
        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Rating=Integer.valueOf(documentSnapshot.getString("rating"));
                    FName=documentSnapshot.getString("First name");
                    LName=documentSnapshot.getString("Last name");
                    Name=FName+LName;
                    Adress=documentSnapshot.getString("address");
                    Email=documentSnapshot.getString("Email");
                    UserName.setText(Name);
                    UserRating.setText(Rating);
                    UserAdress.setText(Adress);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Error, Please contact the Support", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
