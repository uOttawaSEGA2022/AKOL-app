package com.example.akolapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    EditText firstName, lastName, email, password;
    TextView sign;
    Button SignUpButton;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        mFirebaseAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.Email);
        password=findViewById(R.id.Password);
        firstName=findViewById(R.id.FirstName);
        lastName=findViewById(R.id.LastName);
        SignUpButton=findViewById(R.id.SignUpButton);
        sign=findViewById(R.id.Sign);


        if(mFirebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),LoginPage.class));
            finish();
        }

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                String mFirst= firstName.getText().toString().trim();
                String mLast= lastName.getText().toString().trim();

            if(TextUtils.isEmpty(mEmail)){
                email.setError("Email is required");
                return;
            }
            if(TextUtils.isEmpty(pwd)){
                password.setError("Password is required");
                return;
            }
                if(TextUtils.isEmpty(mFirst)){
                    firstName.setError("FirstName is required");
                    return;
                }
                if(TextUtils.isEmpty(mLast)){
                    lastName.setError("LastName is required");
                    return;
                }
            if(pwd.length()<6){
                password.setError("Password must be 6 or more characters");
                return;
            }
            mFirebaseAuth.createUserWithEmailAndPassword(mEmail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpPage.this,"User Created",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),InfoCuisinier.class));
                    }
                    else{
                        Toast.makeText(SignUpPage.this,"Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
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

