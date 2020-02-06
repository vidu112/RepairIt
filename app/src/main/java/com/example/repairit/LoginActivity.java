package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private EditText Username, Password;
    private static  String TAG="LoginActivity";
    private String UsernameText, PasswordText;
    private SharedPreferences mpreferences;
    private SharedPreferences.Editor mEditor;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private FirebaseAuth mAuth;
    String customerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        mpreferences= PreferenceManager.getDefaultSharedPreferences(this);

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void Login(View view){
        UsernameText = Username.getText().toString();
        PasswordText = Password.getText().toString();
        Log.i("Username:",UsernameText);
        Log.i("Password:",PasswordText);
        mAuth.signInWithEmailAndPassword(UsernameText, PasswordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void Signup(View view) {
        Intent intent = new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);
    }
    private void updateUI(final FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
            docRef = db.collection("Users").document(user.getEmail());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            mEditor=mpreferences.edit();
                            mEditor.putString(getString(R.string.user_email),user.getEmail());
                            customerID = document.get("customerID").toString();
                            mEditor.putString(getString(R.string.user_id), customerID);
                            //mEditor.putString(getString(R.string.user_name),document.get("fullName").toString());
                            mEditor.apply();
                            mEditor.commit();
                            //getCustomerDetails(document.get("Type").toString());
                            if (document.get("Type").toString().equals("Customer")) {
                                Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                                startActivity(intent);
                            } else if (document.get("Type").toString().equals("Repairman")) {
                                Intent intent = new Intent(getApplicationContext(), HireRepairman.class);
                                startActivity(intent);
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        } else {
        }
    }

    private void getCustomerDetails(final String type) {
        Log.d(TAG, "Customer ID " + customerID);
        docRef = db.collection("Customer").document(customerID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mEditor = mpreferences.edit();
                        //mEditor.putString(getString(R.string.user_name),document.get("fullName").toString());
                        //mEditor.putString(getString(R.string.phone_number),document.get("phoneNo").toString());
                        mEditor.apply();
                        mEditor.commit();
                        //Log.d(TAG, "DocumentSnapshot data: " + document.get("Type"));

                    } else {
                        Log.d(TAG, "No such customer");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}
