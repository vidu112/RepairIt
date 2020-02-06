package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class CustomerHires extends AppCompatActivity {
    private static final String TAG = "CustomerHires";
    ArrayList<HiresClass> customerHireslist;
    CustomerHiresAdapter customerHiresAdapter;
    String userID, userName;
    private SharedPreferences mpreferences;
    ListView CustomerHiresListView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_hires);
        CustomerHiresListView = findViewById(R.id.customer_hires_listview);
        customerHireslist = new ArrayList<>();
        mpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userID = mpreferences.getString(getString(R.string.user_id), "");
        userName = mpreferences.getString(getString(R.string.user_name), "");
        customerHiresAdapter = new CustomerHiresAdapter(this, R.layout.activity_customer_hire_list, customerHireslist);
        HiresClass test1 = new HiresClass("Vidu", "100", "2/6/2020", "uDhvHwMo8dqfE7q1VKeG", "Dinuga", "dinuga@gmail.com"
                , "Carpenter", "i like to work on toilets", "Accepted", "0.0", "0.0", "1234");
        HiresClass test2 = new HiresClass("Vidu", "100", "2/6/2020", "uDhvHwMo8dqfE7q1VKeG", "Dinuga", "dinuga@gmail.com"
                , "Carpenter", "i like to work on toilets", "NotAccepted", "0.0", "0.0", "12345");
        HiresClass test3 = new HiresClass("Vidu", "100", "2/6/2020", "uDhvHwMo8dqfE7q1VKeG", "Dinuga", "dinuga@gmail.com"
                , "Carpenter", "i like to work on toilets", "Completed", "0.0", "0.0", "123456");
        customerHireslist.add(test1);
        customerHireslist.add(test2);
        customerHireslist.add(test3);
        CustomerHiresListView.setAdapter(customerHiresAdapter);
        GetHiresDB();

    }

    private void GetHiresDB() {
        db.collection("Hires")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.get("customerName").toString());
                                Log.d(TAG, "CustomerID:" + document.get("customerID").toString());
                                Log.d(TAG, "UserID" + userID);
                                if (document.get("customerID").toString().equals(userID)) {
                                    Log.d(TAG, document.get("customerName").toString());
                                    HiresClass hire = new HiresClass(document.get("customerName").toString(), document.get("customerPaidPrice").toString()
                                            , document.get("date").toString(), document.get("repairmanID").toString(), document.get("repairmanName").toString(), document.get("repairmanEmail").toString(),
                                            document.get("repairmanType").toString(), document.get("description").toString(), document.get("status").toString()
                                            , document.get("hireLocLat").toString(), document.get("hireLocLon").toString(), document.get("customerID").toString());
                                    customerHireslist.add(hire);
                                }
                            }
                            ArrayList<HiresClass> sortedList = new ArrayList<>();
                            CustomerHiresListView.setAdapter(customerHiresAdapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
