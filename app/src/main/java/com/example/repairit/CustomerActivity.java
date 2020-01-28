package com.example.repairit;

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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    private static final String TAG = "CustomerActivity";
    private TextView mTextMessage;
    ArrayList<Repairmen> repairmenList;
    RepairmenAdapter repairmenAdapter;

    private SharedPreferences mpreferences;
    private ListView RepairmenListView;
    private String userEmail;
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
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        repairmenList = new ArrayList<>();
        repairmenAdapter = new RepairmenAdapter(this, R.layout.repairmen_list,repairmenList);
//        Repairmen test =new  Repairmen("vidu","Boob Job","Best in the world","10 star","100000 USD");
//        repairmenList.add(test);
        RepairmenListView = (ListView) findViewById(R.id.repairmenList);

        db.collection("repairmen")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.get("fullName").toString());
                                Repairmen test =new  Repairmen(document.get("fullName").toString(),document.get("repairType").toString(),document.get("description").toString(),document.get("rating").toString(),document.get("costPerDay").toString());
                                repairmenList.add(test);
                            }
                            RepairmenListView.setAdapter(repairmenAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


//        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
//        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot document : task.getResult()) {
//                        Repairmen repairmen = document.toObject(Repairmen.class);
//                        repairmenList.add(repairmen);
//                    }
//                    ListView RepairmenListView = (ListView) findViewById(R.id.repairmenList);
//
//                    RepairmenListView.setAdapter(repairmenAdapter);
//                } else {
//                    Log.d("MissionActivity", "Error getting documents: ", task.getException());
//                }
//            }
//        });
    }

}
