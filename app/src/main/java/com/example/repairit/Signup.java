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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    private static final String TAG = "Signup";
    EditText Name, Email, Password, PhoneNumber;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private SharedPreferences mpreferences;
    private SharedPreferences.Editor mEditor;
    private String CustomerId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Name = findViewById(R.id.name);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        PhoneNumber = findViewById(R.id.phone);
        mpreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    public void SignUp(View view) {
        CustomerId = getID();
        String emailText = Email.getText().toString();
        String passwordText = Password.getText().toString();
        String nameText = Name.getText().toString();
        String phoneNumberText = PhoneNumber.getText().toString();
        if ((!emailText.equals("")) && (!passwordText.equals("")) && (!nameText.equals("")) && (!phoneNumberText.equals(""))) {
            mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                CreateCustomer();

                            } else {

                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Signup.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        if (Email.getText().toString().equals("")) {
            Email.setError("Email not be empty");
        }
        if (Password.getText().toString().length() < 6) {
            Password.setError("Password contain at least 6 characters");
        }
        if (PhoneNumber.getText().toString().equals("")) {
            PhoneNumber.setError("PhoneNumber not be empty");
        }
        if (Name.getText().toString().equals("")) {
            Name.setError("Name not be empty");
        }
    }

    public void CreateUserAcount() {
        Map<String, Object> customer = new HashMap<>();
        customer.put("type", "Customer");
        customer.put("customerID", CustomerId);
        db.collection("Users").document(Email.getText().toString())
                .set(customer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Added User AccountS!");
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public void CreateCustomer() {

        Customer customer = new Customer(Name.getText().toString(), PhoneNumber.getText().toString(), Email.getText().toString(), "06/11/200");
        db.collection("Customer").document(CustomerId)
                .set(customer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Created Customer Object!");
                        CreateUserAcount();
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
