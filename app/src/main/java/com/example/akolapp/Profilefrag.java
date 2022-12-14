package com.example.akolapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profilefrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profilefrag extends Fragment {
    static TextView UserName, UserAdress, UserRating;
    static ImageView UserPic;
    static Button logoutButton ;
    static String Rating;
    static String FName,LName,Name,Adress,Email;
    static String ID;
    FirebaseFirestore db;
    static FirebaseAuth Auth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Profilefrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profilefrag.
     */
    // TODO: Rename and change types and number of parameters
    public static Profilefrag newInstance(String param1, String param2) {
        Profilefrag fragment = new Profilefrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profilefrag, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Auth=FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        ID = Auth.getUid();

        UserName=getView().findViewById(R.id.username);
        UserAdress=getView().findViewById(R.id.userAddress);
        UserRating=getView().findViewById(R.id.userRating);
        UserPic=getView().findViewById(R.id.userPic);
        logoutButton =getView().findViewById(R.id.logoutButton);

        if(ID==null){
            Log.d(TAG, "Error, Please contact the Support");
        }
        else{
            showUserProfile();
        }


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });}
    public void showUserProfile(){
        DocumentReference user = db.collection("cuisinier").document(ID);
        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Rating=documentSnapshot.getString("rating");
                    FName=documentSnapshot.getString("First name");
                    LName=documentSnapshot.getString("Last name");
                    Name=FName+LName;
                    Adress=documentSnapshot.getString("address");
                    Email=documentSnapshot.getString("Email");
                    UserName.setText(Name);
                    UserRating.setText(Rating);
                    UserAdress.setText(Adress);

                }
                else {
                    Log.d(TAG, "Error, Please contact the Support");
                }
            }
        });
    }
    public void Logout(){
        Auth.signOut();
        Intent intent = new Intent(this.getActivity(),LoginPage.class);
        startActivity(intent);
    }

}