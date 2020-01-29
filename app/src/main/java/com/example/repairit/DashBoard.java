package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class DashBoard extends AppCompatActivity {

    private static final String TAG = "DashBoard";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        String email = getIntent().getStringExtra("RepairmanEmail");
        String ID = getIntent().getStringExtra("RepairmanID");
        TextView IDText = findViewById(R.id.textView1);
        final TextView NameText = findViewById(R.id.name);
        final TextView TypeText = findViewById(R.id.type);
        final TextView PriceText = findViewById(R.id.price);
        final TextView StarText = findViewById(R.id.star);
        final TextView EmailText = findViewById(R.id.email);
        IDText.setText(ID);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("repairmen").document(ID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        NameText.setText("fullName "+document.get("fullName").toString());
                        TypeText.setText("repairType "+document.get("repairType").toString());
                        PriceText.setText("costPerDay "+document.get("costPerDay").toString());
                        StarText.setText("rating "+document.get("rating").toString());
                        EmailText.setText("email "+document.get("email").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        Map<String, Object> tasks = new HashMap<>();
        tasks.put("customerName", "Vidu Senanayake");
        tasks.put("description", "Need to fix a tap");
        tasks.put("cusLat", "0.0");
        tasks.put("cusLon", "0.0");
        db.collection("repairmen/"+ID+"/tasks").document()
                .set(tasks)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}
