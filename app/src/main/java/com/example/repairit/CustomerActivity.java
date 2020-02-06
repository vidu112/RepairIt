package com.example.repairit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "CustomerActivity";
    private TextView mTextMessage;
    private ArrayList<Repairman> repairmanList;
    private RepairmanAdapter repairmanAdapter;
    private String repairmanType = "All";
    private Spinner repairmanDropDown;
    private SharedPreferences mpreferences;
    private ListView RepairmanListView;
    private String userEmail;
    private FirebaseUser currentUser;
    private Button search;
    private List<String> typeofRepairman;
    private SharedPreferences.Editor mEditor;
    private FirebaseAuth mAuth;
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    Intent intent = new Intent(getApplicationContext(), HireRepairman.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_activity);
        mpreferences= PreferenceManager.getDefaultSharedPreferences(this);
        //mEditor=mpreferences.edit();
        mAuth = FirebaseAuth.getInstance();

        userEmail=mpreferences.getString(getString(R.string.user_email),"");
        search = findViewById(R.id.search);
        repairmanDropDown = findViewById(R.id.repairmen_drop_down);
        repairmanDropDown.setOnItemSelectedListener(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        repairmanList = new ArrayList<>();
        typeofRepairman =new ArrayList<>();
        repairmanAdapter = new RepairmanAdapter(this, R.layout.repairman_list, repairmanList);
        RepairmanListView = findViewById(R.id.repairmenList);
        typeofRepairman.add("All");
        ListRepairmen();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListRepairmen();
            }
        });
        db.collection("repairmenType")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                //Log.d(TAG, document.get("name").toString());
                                typeofRepairman.add(document.get("name").toString());
                                UpdateRepairmanDropDown();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        repairmanType = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    private void UpdateRepairmanDropDown() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, typeofRepairman);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repairmanDropDown.setAdapter(dataAdapter);
    }

    public void SignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                                intent.putExtra("",)
        startActivity(intent);
    }

    public void Hires(View view) {
        Intent intent = new Intent(getApplicationContext(), CustomerHires.class);
//                                intent.putExtra("",)
        startActivity(intent);
    }

    private void ListRepairmen()
    {
        repairmanList.clear();
        db.collection("repairmen")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("repairType").toString().equals(repairmanType)||(repairmanType.equals("All"))) {
                                    Log.d(TAG, document.get("fullName").toString());
                                    Repairman test = new Repairman(document.get("fullName").toString(), document.get("repairType").toString()
                                            , document.get("description").toString(), document.get("rating").toString(), document.get("costPerDay").toString()
                                            , document.get("email").toString(), document.getId());
                                    repairmanList.add(test);
                                }
                            }
                            RepairmanListView.setAdapter(repairmanAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
