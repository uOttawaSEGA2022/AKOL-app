package com.example.akolapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPage extends AppCompatActivity {
    EditText FirstName, LastName, Email, Password;
    TextView sign;
    Button SignUpButton;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        mFirebaseAuth=FirebaseAuth.getInstance();
        Email=findViewById(R.id.Email);
        Password=findViewById(R.id.Password);
        FirstName=findViewById(R.id.FirstName);
        LastName=findViewById(R.id.LastName);
        SignUpButton=findViewById(R.id.SignUpButton);
        sign=findViewById(R.id.Sign);
        SignUpButton.setOnClickListener((v) ->{
                String email = Email.getText().toString();
                String pwd = Password.getText().toString();
                String firstname = FirstName.getText().toString();
                String lastname = LastName.getText().toString();

                if(!(firstname.isEmpty() && lastname.isEmpty() && email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignUpPage.this, InfoCuisinier.class));
                            } else{
                                Toast.makeText(SignUpPage.this, "SignUp Unsuccessful, Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                }
                else if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || pwd.isEmpty()){
                    Toast.makeText(SignUpPage.this,"Some Fields is Empty",Toast.LENGTH_SHORT).show();
                    if (firstname.isEmpty()){
                        FirstName.setError("Please enter a First Name");
                        FirstName.requestFocus();
                    }
                    if(lastname.isEmpty()){
                        LastName.setError("Please enter a Last Name");
                        LastName.requestFocus();
                    }
                    if(email.isEmpty()){
                        Email.setError("Please enter a Email Name");
                        Email.requestFocus();
                    }
                    if(pwd.isEmpty()){
                        Password.setError("Please enter a Password Name");
                        Email.requestFocus();
                    }
                }

                    else{
                        Toast.makeText(SignUpPage.this,"Error Occurred!!",Toast.LENGTH_SHORT).show();
                    }
                });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpPage.this,LoginPage.class);
                startActivity(i);

            }
        });
        }
    }

