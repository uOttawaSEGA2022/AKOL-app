package com.example.akolapp;


import static android.content.ContentValues.TAG;

import android.util.Log;

import java.util.ArrayList;


public class chefComplaintBlock {
    private String chefName;
    private String ComplaintsNumber;
    private String id;
    private ArrayList<String> complaints;


    public chefComplaintBlock(String id, String name,String complaintsNumber,ArrayList<String> complaints) {
        this.id = id;
        this.chefName = name;
        this.ComplaintsNumber = complaintsNumber;
        this.complaints = complaints;
        Log.d(TAG,this.complaints.toString() + this.ComplaintsNumber);

    }

    public void setChefName(String chefName) {
        this.id = chefName;
    }

    public void setComplaintsNumber(String complaintsNumber) {
        ComplaintsNumber = complaintsNumber;
    }

    public String getChefName() {
        return chefName;
    }

    public String getComplaintsNumber() {
        return ComplaintsNumber;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getComplaints() {
        return complaints;
    }
}