package com.example.akolapp;


import static android.content.ContentValues.TAG;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;


public class chefComplaintBlock implements Parcelable {
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

    protected chefComplaintBlock(Parcel in) {
        chefName = in.readString();
        ComplaintsNumber = in.readString();
        id = in.readString();
        complaints = in.createStringArrayList();
    }

    public static final Creator<chefComplaintBlock> CREATOR = new Creator<chefComplaintBlock>() {
        @Override
        public chefComplaintBlock createFromParcel(Parcel in) {
            return new chefComplaintBlock(in);
        }

        @Override
        public chefComplaintBlock[] newArray(int size) {
            return new chefComplaintBlock[size];
        }
    };

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

    public String getIds() {
        return id;
    }

    public ArrayList<String> getComplaints() {
        return this.complaints;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(chefName);
        parcel.writeString(ComplaintsNumber);
        parcel.writeString(id);
        parcel.writeStringList(complaints);
    }
}