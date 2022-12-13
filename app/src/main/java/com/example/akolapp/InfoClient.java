package com.example.akolapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class InfoClient extends AppCompatActivity {
    FirebaseFirestore db;
    private String UserID;
    private FirebaseAuth Authent;
    private EditText CardNumber,ExpiryDate,CVV,PostalCode;
    private Button addbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_client);
        CardNumber = findViewById(R.id.CardNumberField);
        ExpiryDate = findViewById(R.id.ExpiryDateField);
        CVV = findViewById(R.id.CVVField);
        PostalCode = findViewById(R.id.PostalCodeField);
        addbutton=findViewById(R.id.AddButton);
        db=FirebaseFirestore.getInstance();
        Authent=FirebaseAuth.getInstance();


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardnum = CardNumber.getText().toString().trim();
                String Expirydate = ExpiryDate.getText().toString().trim();
                String cvv = CVV.getText().toString().trim();
                String postalcode = PostalCode.getText().toString().trim();
                if (TextUtils.isEmpty(cardnum) || TextUtils.isEmpty(Expirydate) || TextUtils.isEmpty(cvv) || TextUtils.isEmpty(postalcode)) {
                    if (TextUtils.isEmpty(cardnum) && TextUtils.isEmpty(Expirydate) && TextUtils.isEmpty(cvv) && TextUtils.isEmpty(postalcode)) {
                        CardNumber.setError("CardNumber is required");
                        ExpiryDate.setError("Expirydate is required");
                        CVV.setError("CVV is required");
                        PostalCode.setError("PostalCode is required");
                    } else if (TextUtils.isEmpty(cardnum) && TextUtils.isEmpty(Expirydate) && TextUtils.isEmpty(cvv)) {
                        CardNumber.setError("CardNumber is required");
                        ExpiryDate.setError("Expirydate is required");
                        CVV.setError("CVV is required");
                    } else if (TextUtils.isEmpty(cardnum) && TextUtils.isEmpty(Expirydate) && TextUtils.isEmpty(cvv) && TextUtils.isEmpty(postalcode)) {
                        CardNumber.setError("CardNumber is required");
                        ExpiryDate.setError("Expirydate is required");
                        PostalCode.setError("PostalCode is required");
                    } else if (TextUtils.isEmpty(cardnum) && TextUtils.isEmpty(cvv) && TextUtils.isEmpty(postalcode)) {
                        CardNumber.setError("CardNumber is required");
                        CVV.setError("CVV is required");
                        PostalCode.setError("PostalCode is required");
                    } else if (TextUtils.isEmpty(cardnum) && TextUtils.isEmpty(cvv) && TextUtils.isEmpty(postalcode)) {
                        ExpiryDate.setError("Expirydate is required");
                        CVV.setError("CVV is required");
                        PostalCode.setError("PostalCode is required");
                    } else if (TextUtils.isEmpty(cardnum) && TextUtils.isEmpty(Expirydate)) {
                        CardNumber.setError("CardNumber is required");
                        ExpiryDate.setError("Expirydate is required");

                    } else if (TextUtils.isEmpty(cvv) && TextUtils.isEmpty(postalcode)) {
                        CVV.setError("CVV is required");
                        PostalCode.setError("PostalCode is required");
                    } else if (TextUtils.isEmpty(cardnum) && TextUtils.isEmpty(postalcode)) {
                        CardNumber.setError("CardNumber is required");
                        PostalCode.setError("PostalCode is required");
                    } else if (TextUtils.isEmpty(Expirydate) && TextUtils.isEmpty(postalcode)) {
                        ExpiryDate.setError("Expirydate is required");
                        PostalCode.setError("PostalCode is required");
                    } else if (TextUtils.isEmpty(cardnum) && TextUtils.isEmpty(cvv)) {
                        CardNumber.setError("CardNumber is required");
                        CVV.setError("CVV is required");
                    } else if (TextUtils.isEmpty(Expirydate) && TextUtils.isEmpty(cvv)) {
                        ExpiryDate.setError("Expirydate is required");
                        CVV.setError("CVV is required");
                    } else if (TextUtils.isEmpty(cardnum)) {
                        CardNumber.setError("CardNumber is required");
                        return;
                    } else if (TextUtils.isEmpty(Expirydate)) {
                        ExpiryDate.setError("Expirydate is required");
                        return;
                    } else if (TextUtils.isEmpty(cvv)) {
                        CVV.setError("CVV is required");
                        return;
                    } else if (TextUtils.isEmpty(postalcode)) {
                        PostalCode.setError("PostalCode is required");
                        return;
                    }

                }
                else{
                    String email = SignUpPage.emaile;        //email is the id
                    String Usertype = UserType.usertype;
                    String Firstname = SignUpPage.firstnamee;
                    String Lastname = SignUpPage.lastnamee;
                    String Card = CardNumber.getText().toString().trim();
                    String Exp = ExpiryDate.getText().toString().trim();
                    String Cvv = CVV.getText().toString().trim();
                    String Postalcode = PostalCode.getText().toString().trim();

                    Map<String, Object> user = new HashMap<>();

                    user.put("Email", email);
                    user.put("User_Type", Usertype);
                    user.put("FirstName", Firstname);
                    user.put("LastName", Lastname);
                    user.put("CardNum", Card);
                    user.put("CVV", Cvv);
                    user.put("Expiration", Exp);
                    user.put("PostalCode", Postalcode);
                    user.put("NumberOfOrders","0");
                    UserID=Authent.getUid();

                    DocumentReference UserF=db.collection("ClientUser").document(UserID);
                            UserF.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(InfoClient.this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), FirstPage.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(InfoClient.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

        });
    }


}