package com.example.akolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class InfoCuisinier extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_cuisinier);
        //information to send to the cloud
        String email= SignUpPage.emaile;        //email is the id
        String Usertype= UserType.usertype;
        String Firstname= SignUpPage.firstnamee;
        String Lastname= SignUpPage.lastnamee;
        String password= SignUpPage.pwde;
    }
}