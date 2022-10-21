package com.example.akolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Button SignUpButton = findViewById(R.id.signUpButton);
    }
    public void onClick(View v){
        int Element =v.getId();
        switch( Element){
            case R.id.SignUpButton:
                Register();

        }




    }




    public void Register(){
        Intent intent = new Intent(LoginPage.this,SignUpPage.class);
        startActivity(intent);
    }
}
