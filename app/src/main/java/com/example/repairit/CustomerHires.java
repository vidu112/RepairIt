package com.example.repairit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CustomerHires extends AppCompatActivity {
    ArrayList<HiresClass> customerHireslist;
    CustomerHiresAdapter customerHiresAdapter;
    ListView CustomerHiresListView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_hires);
        CustomerHiresListView = findViewById(R.id.customer_hires_listview);
        customerHireslist = new ArrayList<>();
        customerHiresAdapter = new CustomerHiresAdapter(this, R.layout.activity_customer_hire_list, customerHireslist);
        HiresClass test1 = new HiresClass("Vidu", "100", "2/6/2020", "uDhvHwMo8dqfE7q1VKeG", "Dinuga", "dinuga@gmail.com"
                , "Carpenter", "i like to work on toilets", "Accepted", "0.0", "0.0");
        HiresClass test2 = new HiresClass("Vidu", "100", "2/6/2020", "uDhvHwMo8dqfE7q1VKeG", "Dinuga", "dinuga@gmail.com"
                , "Carpenter", "i like to work on toilets", "NotAccepted", "0.0", "0.0");
        HiresClass test3 = new HiresClass("Vidu", "100", "2/6/2020", "uDhvHwMo8dqfE7q1VKeG", "Dinuga", "dinuga@gmail.com"
                , "Carpenter", "i like to work on toilets", "Completed", "0.0", "0.0");
        customerHireslist.add(test1);
        customerHireslist.add(test2);
        customerHireslist.add(test3);
        CustomerHiresListView.setAdapter(customerHiresAdapter);

    }

    private void GetHiresDB() {
        customerHireslist.clear();
        db.collection("Hires")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("repairType").toString().equals(repairmanType) || (repairmanType.equals("All"))) {
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
