package com.nutro.biosint.manageractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nutro.biosint.R;
import com.nutro.biosint.modelresponse.ViewExpenseResponse;
import com.nutro.biosint.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class ViewMyEmpExpenseReportActivity extends AppCompatActivity {

    RecyclerView myEmpExpenseRecyclerView;
    Button btnMyEmpExpenseDownload;
    FirebaseFirestore db;

    private CollectionReference viewMyEmpExpenseCollection;

    List<ViewExpenseResponse> viewExpenseResponseList;

    interface MyEmpExpenseReportListener {
        public void onViewMyEmpExpense(List<ViewExpenseResponse> viewExpenseResponseList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_emp_expense_report);

        db = FirebaseFirestore.getInstance();
        viewMyEmpExpenseCollection = db.collection("EmployeeExpense");

        Intent intent = getIntent();
        String fromDate = intent.getStringExtra("FROM_DATE");
        String toDate = intent.getStringExtra("TO_DATE");
        String userId = intent.getStringExtra("USER_ID");

        viewExpenseResponseList=new ArrayList<>();

        myEmpExpenseRecyclerView = (RecyclerView) findViewById(R.id.myEmpExpenseRecyclerView);
        myEmpExpenseRecyclerView.setLayoutManager(new LinearLayoutManager(ViewMyEmpExpenseReportActivity.this));
        myEmpExpenseRecyclerView.setHasFixedSize(true);

        btnMyEmpExpenseDownload = (Button) findViewById(R.id.btnMyEmpExpenseDownload);

        getExpenseReport(fromDate, toDate, userId, new MyEmpExpenseReportListener() {
            @Override
            public void onViewMyEmpExpense(List<ViewExpenseResponse> viewExpenseResponseList) {



            }
        });

    }

    private void getExpenseReport(String fromDate, String toDate, String userId, final MyEmpExpenseReportListener myEmpExpenseReportListener) {

        viewMyEmpExpenseCollection.whereEqualTo("managerUserId", PreferenceUtil.getValueString(ViewMyEmpExpenseReportActivity.this, PreferenceUtil.MY_MANAGER_USER_ID))
                .whereEqualTo("empUserId", PreferenceUtil.getValueString(ViewMyEmpExpenseReportActivity.this, PreferenceUtil.MY_USER_ID))
                .whereLessThan("expenseDate", fromDate)
                .whereGreaterThan("expenseDate", toDate)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ViewExpenseResponse viewCheckInResponse = documentSnapshot.toObject(ViewExpenseResponse.class);

                    viewCheckInResponse.setExpenseDocId(documentSnapshot.getId());

                    viewExpenseResponseList.add(viewCheckInResponse);

                }

                myEmpExpenseReportListener.onViewMyEmpExpense(viewExpenseResponseList);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}