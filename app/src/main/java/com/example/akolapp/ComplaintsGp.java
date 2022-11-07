package com.example.akolapp;

public class ComplaintsGp {
    String ChefName;
    String ComplaintTitle;
    int ComplaintsNum;

    public ComplaintsGp(String complaintTitle, int complaintsNum) {

        ComplaintTitle = complaintTitle;
        ComplaintsNum = complaintsNum;
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
