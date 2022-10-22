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

import java.util.Calendar;

public class SignUpPage extends AppCompatActivity {
    static String firstnamee;
    static String lastnamee;
    static String emaile;
    static String pwde;
    private EditText firstName, lastName, email, password;
    private TextView sign;
    private Button SignUpButton;
    private FirebaseAuth mFirebaseAuth;


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



        

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                String mFirst= firstName.getText().toString().trim();
                String mLast= lastName.getText().toString().trim();
                if(TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(mFirst) || TextUtils.isEmpty(mLast) || pwd.length()<=6 ) {
                    if(TextUtils.isEmpty(mEmail) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(mFirst) && TextUtils.isEmpty(mLast)) {
                        email.setError("Email is required");
                        password.setError("Password is required");
                        firstName.setError("FirstName is required");
                        lastName.setError("LastName is required");
                   }
                    else if(TextUtils.isEmpty(mEmail) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(mFirst) ) {
                        email.setError("Email is required");
                        password.setError("Password is required");
                        firstName.setError("FirstName is required");
                    }
                    else if(TextUtils.isEmpty(mEmail) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(mFirst) && TextUtils.isEmpty(mLast)) {
                        email.setError("Email is required");
                        password.setError("Password is required");
                        lastName.setError("LastName is required");
                    }
                    else if(TextUtils.isEmpty(mEmail) && TextUtils.isEmpty(mFirst) && TextUtils.isEmpty(mLast)) {
                        email.setError("Email is required");
                        firstName.setError("FirstName is required");
                        lastName.setError("LastName is required");
                    }
                    else if(TextUtils.isEmpty(pwd) && TextUtils.isEmpty(mFirst) && TextUtils.isEmpty(mLast)) {
                        password.setError("Password is required");
                        firstName.setError("FirstName is required");
                        lastName.setError("LastName is required");
                    }
                    else if(TextUtils.isEmpty(mEmail) && TextUtils.isEmpty(pwd)) {
                        email.setError("Email is required");
                        password.setError("Password is required");

                    }
                    else if( TextUtils.isEmpty(mFirst) && TextUtils.isEmpty(mLast)) {
                        firstName.setError("FirstName is required");
                        lastName.setError("LastName is required");
                    }
                    else if(TextUtils.isEmpty(mEmail) && TextUtils.isEmpty(mLast)) {
                        email.setError("Email is required");
                        lastName.setError("LastName is required");
                    }
                    else if(TextUtils.isEmpty(pwd) && TextUtils.isEmpty(mLast)) {
                        password.setError("Password is required");
                        lastName.setError("LastName is required");
                    }
                    else if(TextUtils.isEmpty(mEmail) && TextUtils.isEmpty(mFirst) ) {
                        email.setError("Email is required");
                        firstName.setError("FirstName is required");
                    }
                    else if(TextUtils.isEmpty(pwd) && TextUtils.isEmpty(mFirst)) {
                        password.setError("Password is required");
                        firstName.setError("FirstName is required");
                    }

                    else if(TextUtils.isEmpty(mEmail)){
                email.setError("Email is required");
                return;
            }
              else if(TextUtils.isEmpty(pwd)){
                password.setError("Password is required");
                return;
            }
               else if(TextUtils.isEmpty(mFirst)){
                    firstName.setError("FirstName is required");
                    return;
                }
               else if(TextUtils.isEmpty(mLast)){
                    lastName.setError("LastName is required");
                    return;
                }
           else if(pwd.length()<=6){
                password.setError("Password must be 6 or more characters");
                return;
            }
                return;}
            mFirebaseAuth.createUserWithEmailAndPassword(mEmail,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        if(UserType.usertype.equals("ChefUser")){
                            firstnamee= mFirst;
                            lastnamee= mLast;
                            emaile= mEmail;
                            pwde= pwd;
                            Toast.makeText(SignUpPage.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), InfoCuisinier.class));
                        }
                        else if(UserType.usertype.equals("ClientUser")){
                            firstnamee= mFirst;
                            lastnamee= mLast;
                            emaile= mEmail;
                            pwde= pwd;
                            Toast.makeText(SignUpPage.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), InfoClient.class));
                        }
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
            Intent i = new Intent(SignUpPage.this, LoginPage.class);
            startActivity(i);

        }
    });
}
    }

