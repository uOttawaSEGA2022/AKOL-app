package com.example.akolapp;

public class ComplaintsGp {
    String ChefName;
    String ComplaintTitle;
    int ComplaintsNum;

    public ComplaintsGp(String chefName, String complaintTitle, int complaintsNum) {
        ChefName = chefName;
        ComplaintTitle = complaintTitle;
        ComplaintsNum = complaintsNum;
    }

    public String getChefName() {
        return ChefName;
    }

    public void setChefName(String chefName) {
        ChefName = chefName;
    }

    public String getComplaintTitle() {
        return ComplaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        ComplaintTitle = complaintTitle;
    }

    public int getComplaintsNum() {
        return ComplaintsNum;
    }

    public void setComplaintsNum(int complaintsNum) {
        ComplaintsNum = complaintsNum;
    }
}
