package com.example.akolapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    private String clientName;
    private String ClientID;
    private String recipeName;

    public Order(String clientName, String clientID, String recipeName) {
        this.clientName = clientName;
        ClientID = clientID;
        this.recipeName = recipeName;
    }

    protected Order(Parcel in) {
        clientName = in.readString();
        ClientID = in.readString();
        recipeName = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(clientName);
        parcel.writeString(ClientID);
        parcel.writeString(recipeName);
    }
}
