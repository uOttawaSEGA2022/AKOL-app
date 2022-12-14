package com.example.akolapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class InfoCuisinier extends AppCompatActivity {
    FirebaseFirestore db;
    private EditText addressText;
    private EditText ZIPText;
    private EditText descriptionText;
    private Button Continue;
    private Button FileFinder;
    private StorageReference firebaseStorage;
    public Uri VoidCheckURL;
    private FirebaseAuth auth;
    private String currID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_cuisinier);

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        currID = auth.getUid();
        //information to send to the cloud

        addressText = (EditText) findViewById(R.id.AddressField);
        ZIPText = (EditText) findViewById(R.id.PostalCodeField);
        descriptionText = (EditText) findViewById(R.id.TellTheClientAbtYourselfField);
        Continue = (Button) findViewById(R.id.ContinueButton);
        Continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInfo();
            }
        });
        FileFinder = (Button) findViewById(R.id.ChooseFileButton);
        FileFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelector();

            }
        });
    }

    private void UploadImageCheck(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Image is being uploaded");
        pd.show();

        final String key = currID;
        StorageReference imgRef = firebaseStorage.child("images/"+key);
        imgRef.putFile(VoidCheckURL);
        //Toast.makeText(InfoCuisinier.this,"allo allo",Toast.LENGTH_SHORT).show();

        imgRef.putFile(VoidCheckURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                Toast.makeText(InfoCuisinier.this, "Image uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(InfoCuisinier.this, "Some error occured, please try again", Toast.LENGTH_SHORT).show();

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercentage = snapshot.getBytesTransferred();
                pd.setMessage("Percentage: "+(int)progressPercentage );
            }
        });

    }
    private void ImageSelector(){
        Intent intent1 = new Intent();
        intent1.setType("image/*");
        intent1.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent1,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data!= null && data.getData()!=null){
            VoidCheckURL= data.getData();
            UploadImageCheck();

        }

    }

    void FirstPage(){
        Intent intent = new Intent(this,FirstPage.class);
        startActivity(intent);
    }

    void checkInfo() {
        String address = addressText.getText().toString().trim();
        String ZIP = ZIPText.getText().toString().trim();
        String description = descriptionText.getText().toString().trim();
        if (address.isEmpty()) {
            addressText.setError("Address is empty");
            addressText.requestFocus();
            return;
        }
        if (ZIP.isEmpty()) {
            ZIPText.setError("Postal code is empty");
            ZIPText.requestFocus();
            return;
        }
        if (description.isEmpty()) {
            descriptionText.setError("Description is empty");
            descriptionText.requestFocus();
            return;
        }
        HashMap<String, Object> cuisinier = new HashMap<>();
        cuisinier.put("Email", SignUpPage.emaile);
        cuisinier.put("First name", SignUpPage.firstnamee);
        cuisinier.put("Last name", SignUpPage.lastnamee);
        cuisinier.put("address", address);
        cuisinier.put("postal code", ZIP);
        cuisinier.put("description", description);
        cuisinier.put("number of recipe",String.valueOf(0));
        cuisinier.put("number of orders",String.valueOf(0));
        cuisinier.put("Ban","Not Banned");
        cuisinier.put("Ban period","Not specified");
        cuisinier.put("rating","0");
        cuisinier.put("nbrrating","0");

        DocumentReference currUser = db.collection("cuisinier").document(currID);

        currUser.set(cuisinier).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(InfoCuisinier.this, "Hello dear cuisinier", Toast.LENGTH_SHORT).show();
                FirstPage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InfoCuisinier.this, "Some error occured, please try again", Toast.LENGTH_SHORT).show();

            }
        });



    }






}