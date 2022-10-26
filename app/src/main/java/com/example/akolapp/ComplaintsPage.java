package com.example.akolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ComplaintsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_page);
        String ChefName="";
        String Complaints="";
        List<ComplaintsGp> items=new ArrayList<ComplaintsGp>();
        for(int i=0;i<PageHiba.number;i++) {
            items.add(new ComplaintsGp(ChefName,Complaints,i));
        }
        RecyclerView recyclerView=findViewById(R.id.ComplaintsRecycled);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ComAdapter(getApplicationContext(),items));

    }
}