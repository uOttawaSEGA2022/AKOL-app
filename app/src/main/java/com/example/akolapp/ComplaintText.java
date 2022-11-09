package com.example.akolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ComplaintText extends AppCompatActivity {
    TextView complaintTxt;
    TextView complNum;
    Button back;
    chefComplaintBlock chefcomplaints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_text);
        String complaint = getIntent().getStringExtra("complaint");
        String num = getIntent().getStringExtra("num");
        num = Integer.toString(Integer.parseInt(num)+1);
        chefcomplaints =(chefComplaintBlock) getIntent().getParcelableExtra("chef");
        complaintTxt = (TextView) findViewById(R.id.compltxt);
        complaintTxt.setText(complaint);
        complNum = (TextView) findViewById(R.id.compl);
        complNum.setText(((String)"Complaint "+num));
        back = (Button) findViewById(R.id.goBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComplaintText.this,ComplaintsList.class);
                intent.putExtra("chef",chefcomplaints);
                startActivity(intent);
            }
        });


    }
}