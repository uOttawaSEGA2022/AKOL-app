package com.example.akolapp;

import static com.example.akolapp.OdersChefAccepVH.siDone;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrdersAcceptDecline extends AppCompatActivity {
    static String ID;
    FirebaseFirestore db;
    static FirebaseAuth Auth;
    static Integer numb;
    List<String>items;
    String ClientNa;
    OdersChefAccepAdapter adapter;
    public OrdersAcceptDecline(String clientNa) {
        ClientNa = clientNa;
    }
    public String getClientNa() {
        return ClientNa;
    }
    public void setClientNa(String clientNa) {
        ClientNa = clientNa;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_orders);
        Auth=FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        ID = Auth.getUid();
        items=new ArrayList<>();

        if(ID==null){
            Toast.makeText(getApplicationContext(), "Error, Please contact the Support", Toast.LENGTH_LONG).show();
        }
        else{
            info();
        }
        RecyclerView recyclerView=findViewById(R.id.Recyclercuis);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OdersChefAccepAdapter(OrdersAcceptDecline.this,items);
        adapter.notifyDataSetChanged();
    }
    public void info(){
        DocumentReference user = db.collection("cuisinier").document(ID);
        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    int a=1;
                    numb=Integer.valueOf((String) documentSnapshot.get("number of orders"));
                    Map<String,Object> CuisinierInfo = documentSnapshot.getData();
                    for (Map.Entry<String,Object> mapElement : CuisinierInfo.entrySet()) {
                        String key = mapElement.getKey();
                        if(a<=numb){
                        if(key.equals("Order"+a)){
                            Map<String,String> OrderDb = (Map<String,String>)mapElement.getValue();
                            items.add(OrderDb.get("Client name"));
                            List<String> list = new ArrayList<String>(OrderDb.values());
                            if(OrderDb.get("isDone").equals("no")){
                                Map<String, Object> Ordpush = new HashMap<>();
                                if(siDone==true){
                                    Map<String, Object> Ord = new HashMap<>();
                                    Ord.put("Client name",OrderDb.get("Client name"));
                                    Ord.put("ClientID",OrderDb.get("ClientID"));
                                    Ord.put("isDone","yes");
                                    Ord.put("recipe name",OrderDb.get("recipe name"));
                                    Ordpush.put("Order"+a,Ord);

                                    Map<String, Object> Cle = new HashMap<>();
                                    Cle.put("ChefID",ID);
                                    Cle.put("recipe name",OrderDb.get("recipe name"));
                                    Cle.put("isDelivered","yes");
                                    Map<String, Object> Client = new HashMap<>();
                                    Client.put("Recipe"+(a-1),Cle);
                                    user.set(Client).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(OrdersAcceptDecline.this, "Success", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), OrdersAcceptDecline.class));
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(OrdersAcceptDecline.this, "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                                else if(siDone==false){
                                    Map<String, Object> Ord = new HashMap<>();
                                    Ord.put("Client name",OrderDb.get("Client name"));
                                    Ord.put("ClientID",OrderDb.get("ClientID"));
                                    Ord.put("isDone","rejected");
                                    Ord.put("recipe name",OrderDb.get("recipe name"));
                                }
                                else{
                                    items.add(OrderDb.get("Client name"));
                                }
                                user.set(Ordpush).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(OrdersAcceptDecline.this, "Success", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), OrdersAcceptDecline.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(OrdersAcceptDecline.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        }
                        a++;
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error, Please contact the Support", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}