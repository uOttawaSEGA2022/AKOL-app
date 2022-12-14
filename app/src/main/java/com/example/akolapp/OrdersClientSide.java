package com.example.akolapp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class OrdersClientSide extends AppCompatActivity {
    TextView recipeName;
    TextView ClientName;
    Button back;
    Button confirm;
    Button reject;
    FirebaseFirestore db;
    FirebaseAuth Auth;
    String ID;
    String name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_client_side);
        Order order = getIntent().getParcelableExtra("Order");

        recipeName =findViewById( R.id.RecipeName);
        ClientName =findViewById( R.id.ClientName);
        back = findViewById(R.id.BackButton);
        confirm = findViewById(R.id.buyButton);
        reject = findViewById(R.id.rejectButton);
        recipeName.setText(recipeName.getText().toString() +order.getRecipeName());
        ClientName.setText(ClientName.getText().toString() + order.getClientName());
        Auth=FirebaseAuth.getInstance();
        ID= Auth.getUid();
        db = FirebaseFirestore.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.orderCuisinier,new OrdersFrag()).commit();
                finish();

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //starts here


                DocumentReference docIdRef = db.collection("ClientUser").document(order.getClientID());
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String,Object> ClientInfo = document.getData();
                                for (Map.Entry<String,Object> mapElement : ClientInfo.entrySet()) {
                                    String key = mapElement.getKey();
                                    if (key.startsWith("Recipe")) {
                                        Map<String,String> recipeDb = (Map<String,String>)mapElement.getValue();
                                       // Log.d(TAG,"allo hada id: " + ID);
                                        if(recipeDb.get("ChefID").equals(ID) && recipeDb.get("Name").equals(order.getRecipeName())) {
                                            recipeDb.put("isDelivered", "yes");
                                            ClientInfo.put(key,recipeDb);
                                            docIdRef.set(ClientInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "Recipe is unpublished now");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Recipe is still published, some error occured ");
                                                }
                                            });
                                            break;
                                        }

                                    }
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


                //ends here


                DocumentReference docIdRef2 = db.collection("cuisinier").document(ID);
                docIdRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String,Object> CuisinierInfo = document.getData();
                                for (Map.Entry<String,Object> mapElement : CuisinierInfo.entrySet()) {
                                    String key = mapElement.getKey();
                                    if (key.startsWith("Order")) {
                                        Map<String,String> recipeDb = (Map<String,String>)mapElement.getValue();
                                        // Log.d(TAG,"allo hada id: " + ID);
                                        if(recipeDb.get("Client name").equals(order.getClientName()) && recipeDb.get("recipe name").equals(order.getRecipeName()) && recipeDb.get("isDone").equals("no")) {
                                            recipeDb.put("isDone", "yes");
                                            CuisinierInfo.put(key,recipeDb);
                                            docIdRef2.set(CuisinierInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "Recipe is unpublished now");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Recipe is still published, some error occured ");
                                                }
                                            });
                                            break;
                                        }

                                    }
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.orderCuisinier,new OrdersFrag()).commit();
                finish();

            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //starts here


                DocumentReference docIdRef = db.collection("ClientUser").document(order.getClientID());
                docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String,Object> ClientInfo = document.getData();
                                for (Map.Entry<String,Object> mapElement : ClientInfo.entrySet()) {
                                    String key = mapElement.getKey();
                                    if (key.startsWith("Recipe")) {
                                        Map<String,String> recipeDb = (Map<String,String>)mapElement.getValue();
                                        // Log.d(TAG,"allo hada id: " + ID);
                                        if(recipeDb.get("ChefID").equals(ID) && recipeDb.get("Name").equals(order.getRecipeName())) {
                                            recipeDb.put("isDelivered", "rejected");
                                            ClientInfo.put(key,recipeDb);
                                            docIdRef.set(ClientInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "Recipe is unpublished now");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Recipe is still published, some error occured ");
                                                }
                                            });
                                            break;
                                        }

                                    }
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });


                //ends here



                DocumentReference docIdRef2 = db.collection("cuisinier").document(ID);
                docIdRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Map<String,Object> CuisinierInfo = document.getData();
                                for (Map.Entry<String,Object> mapElement : CuisinierInfo.entrySet()) {
                                    String key = mapElement.getKey();
                                    if (key.startsWith("Order")) {
                                        Map<String,String> recipeDb = (Map<String,String>)mapElement.getValue();
                                        // Log.d(TAG,"allo hada id: " + ID);
                                        if(recipeDb.get("Client name").equals(order.getClientName()) && recipeDb.get("recipe name").equals(order.getRecipeName()) && recipeDb.get("isDone").equals("no")) {
                                            recipeDb.put("isDone", "rejected");
                                            CuisinierInfo.put(key,recipeDb);
                                            docIdRef2.set(CuisinierInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG, "Recipe is unpublished now");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d(TAG, "Recipe is still published, some error occured ");
                                                }
                                            });
                                            break;
                                        }

                                    }
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.orderCuisinier,new OrdersFrag()).commit();
                finish();

            }
        });





    }
}