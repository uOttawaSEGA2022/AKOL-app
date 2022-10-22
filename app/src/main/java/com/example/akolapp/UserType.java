package com.example.akolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class UserType extends AppCompatActivity {
    static String usertype;
    private Button chefuser, clientuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        chefuser=findViewById(R.id.ChefUser);
        clientuser=findViewById(R.id.ClientUser);

        clientuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity1();

            }
        });

        chefuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity2();
            }
        });
    }
    public void OpenActivity1(){
        usertype="ClientUser";
        Intent intent= new Intent(this,SignUpPage.class);
        startActivity(intent);
    }
    public void OpenActivity2(){
        usertype="ChefUser";
        Intent intent= new Intent(this,SignUpPage.class);
        startActivity(intent);
    }
}