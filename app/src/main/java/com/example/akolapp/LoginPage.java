package com.example.akolapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity  {
    private FirebaseAuth mAuth;
    private EditText emailText;
    private EditText passwordText;
    private Button logIn;
    private Button signUp;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"Welcome back",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,FirstPage.class));

        }else {
            Toast.makeText(this,"You're not signed in",Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();
        emailText = (EditText) findViewById(R.id.EmailField);
        passwordText = (EditText) findViewById(R.id.PasswordField);
        logIn = (Button)findViewById(R.id.LoginButton);
        signUp = (Button) findViewById(R.id.signUpButtonMain);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogIn();
            }
        });

    }



    public void Register(){
        Intent intent = new Intent(LoginPage.this,UserType.class);
        startActivity(intent);
    }
    public void WelcomeBack(){
        Intent intent = new Intent(LoginPage.this,FirstPage.class);
        startActivity(intent);
    }

    private void checkLogIn(){
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        if(email.isEmpty()){
            emailText.setError("Email is empty");
            emailText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Please enter a valid email");
            emailText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordText.setError("Password is empty");
            passwordText.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    WelcomeBack();
                }
                else {
                    Toast.makeText(LoginPage.this,"Failed to log in, wrong email or password",Toast.LENGTH_LONG).show();
                }

            }
        });




    }
}
