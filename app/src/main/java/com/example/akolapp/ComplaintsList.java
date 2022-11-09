package com.example.akolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ComplaintsList extends AppCompatActivity implements RecyclerInterface{
    RecyclerView recyclerView;
    ArrayList<String> Complaints;
    ComplaintsListAdapter myAdapter;
    Button back;
    chefComplaintBlock chefcomplaints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_list);
        chefcomplaints =(chefComplaintBlock) getIntent().getParcelableExtra("chef");
        Complaints = chefcomplaints.getComplaints();
        recyclerView = findViewById(R.id.ComplaintsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new ComplaintsListAdapter(ComplaintsList.this,Complaints,this);
        recyclerView.setAdapter(myAdapter);
        back =(Button) findViewById(R.id.backToChefs);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComplaintsList.this,ComplaintsChef.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public void clicked(int pos) {
        Intent intent = new Intent(ComplaintsList.this,ComplaintText.class);
        intent.putExtra("complaint",Complaints.get(pos));
        intent.putExtra("num",Integer.toString(pos));
        intent.putExtra("chef",chefcomplaints);
        startActivity(intent);
    }
}