package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HireRepairman extends AppCompatActivity {
    private static final String TAG = "HireRepairman";
    private DatePicker datePicker;
    String RepairmanID, RepairmanEmail, RepairmanNameText, RepairmanTypeText, RepairmanPriceText, RepairmanStarText, RepairmanEmailText;
    FirebaseFirestore db;
    EditText descriptionTextField;
    String userID, hireID;
    private TextView NameTextView, TypeTextView, PriceTextView, StarTextView, EmailTextView;
    private SharedPreferences mpreferences;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_repairman);

        RepairmanEmail = getIntent().getStringExtra("RepairmanEmail");
        RepairmanID = getIntent().getStringExtra("RepairmanID");
        Log.i("RepairmanID", RepairmanID);
        TextView IDText = findViewById(R.id.textView1);
        datePicker = findViewById(R.id.datePicker1);
        descriptionTextField = findViewById(R.id.description);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        NameTextView = findViewById(R.id.name);
        TypeTextView = findViewById(R.id.type);
        PriceTextView = findViewById(R.id.price);
        StarTextView = findViewById(R.id.star);
        EmailTextView = findViewById(R.id.email);
        mpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userID = mpreferences.getString(getString(R.string.user_id), "");
        IDText.setText(RepairmanID);
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("repairmen").document(RepairmanID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        RepairmanNameText = document.get("fullName").toString();
                        RepairmanTypeText = document.get("repairType").toString();
                        RepairmanPriceText = document.get("costPerDay").toString();
                        RepairmanStarText = document.get("rating").toString();
                        RepairmanEmailText = document.get("email").toString();
                        NameTextView.setText("fullName " + RepairmanNameText);
                        TypeTextView.setText("repairType " + RepairmanTypeText);
                        PriceTextView.setText("costPerDay " + RepairmanPriceText);
                        StarTextView.setText("rating " + RepairmanStarText);
                        EmailTextView.setText("email " + RepairmanEmailText);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
    public void HireButton(View view){
        CreateHire();
    }

    private void CreateHire() {
        HiresClass hiresClass = new HiresClass("Vidu testing", RepairmanPriceText, datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear(), RepairmanID, RepairmanNameText, RepairmanEmailText, RepairmanTypeText
                , descriptionTextField.getText().toString(), "NotAccepted", "0.0", "0.0", userID);
        hireID = getID();
        db.collection("Hires").document(hireID)
                .set(hiresClass)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "created Hire in Hires");
                        HiresClass hiresClass = new HiresClass("Vidu", RepairmanPriceText, datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear(), RepairmanID, RepairmanNameText, RepairmanEmailText, RepairmanTypeText
                                , descriptionTextField.getText().toString(), "NotAccepted", "0.0", "0.0", "");
                        hireID = getID();
                        db.collection("Hires").document(hireID)
                                .set(hiresClass)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "created Hire in Hires");
                                        AddTaskToRepairman();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });
                    }
                });


    }

    private void AddTaskToRepairman() {
        Map<String, Object> task = new HashMap<>();
        task.put("HireID", hireID);
        db.collection("repairmen/" + RepairmanID + "/task").document()
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Task Added to repairmen");
                        AddHireToCustomer();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void AddHireToCustomer() {
        Map<String, Object> task = new HashMap<>();
        task.put("HireID", hireID);
        db.collection("Customer/" + userID + "/task").document()
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Hire Added to customer tasks branch");
                        Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
//                                intent.putExtra("",)
                        startActivity(intent);
                        //AddTaskToRepairman();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public String getID() { //creating a unique a id
        Date dte = new Date();
        long milliSeconds = dte.getTime();
        String strLong = Long.toString(milliSeconds);
        return strLong;

    }

}

