package com.example.repairit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    ArrayList<Repairmen> repairmenList;
    RepairmenAdapter repairmenAdapter;
    private String repairmenType = "All";
    private Spinner repairmanDropDown;
    private SharedPreferences mpreferences;
    private ListView RepairmenListView;
    private String userEmail;
    private Button search;
    private List<String> typeofRepairman;
    private SharedPreferences.Editor mEditor;
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
                    Intent intent = new Intent(getApplicationContext(), DashBoard.class);
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
        setContentView(R.layout.activity_main);
        mpreferences= PreferenceManager.getDefaultSharedPreferences(this);
        //mEditor=mpreferences.edit();
        userEmail=mpreferences.getString(getString(R.string.user_email),"");
        search = findViewById(R.id.search);
        repairmanDropDown = findViewById(R.id.repairmen_drop_down);
        repairmanDropDown.setOnItemSelectedListener(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        repairmenList = new ArrayList<>();
        typeofRepairman =new ArrayList<>();
        repairmenAdapter = new RepairmenAdapter(this, R.layout.repairmen_list,repairmenList);
        RepairmenListView = (ListView) findViewById(R.id.repairmenList);
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
        repairmenType = parent.getItemAtPosition(pos).toString();
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

    private void ListRepairmen()
    {
        repairmenList.clear();
        db.collection("repairmen")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("repairType").toString().equals(repairmenType)||(repairmenType.equals("All"))) {
                                    //Log.d(TAG, document.get("fullName").toString());
                                    Repairmen test = new Repairmen(document.get("fullName").toString(), document.get("repairType").toString()
                                            , document.get("description").toString(), document.get("rating").toString(), document.get("costPerDay").toString());
                                    repairmenList.add(test);
                                }
                            }
                            RepairmenListView.setAdapter(repairmenAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}
