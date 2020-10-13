package com.nutro.biosint.fragmentemployee;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nutro.biosint.R;
import com.nutro.biosint.adapteremployee.ViewExpenseAdapter;
import com.nutro.biosint.modelresponse.ViewExpenseResponse;
import com.nutro.biosint.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewExpenseFragment extends Fragment implements ViewExpenseAdapter.ViewExpenseClickListener {

    RecyclerView viewExpenseRecyclerView;
    ViewExpenseAdapter viewExpenseAdapter;
    List<ViewExpenseResponse> viewExpenseResponseList;

    FirebaseFirestore db;
    CollectionReference viewEmployeeExpenseCollection;
    DocumentReference viewEmployeeExpenseDocument;

    private TextView selectViewExpenseDate;
    private String userSelectedDate;

    interface DateWiseExpenseReportListener {

        public void onViewExpenseReport(List<ViewExpenseResponse> viewCheckInResponseList);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_view_expense, container, false);

        initFireBase();
        viewEmployeeExpenseCollection = db.collection("EmployeeExpense");

        viewExpenseRecyclerView = (RecyclerView) view.findViewById(R.id.viewExpenseRecyclerView);
        viewExpenseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewExpenseRecyclerView.setHasFixedSize(true);

        selectViewExpenseDate = (TextView) view.findViewById(R.id.selectViewExpenseDate);

        viewExpenseResponseList = new ArrayList<>();

        viewExpenseAdapter = new ViewExpenseAdapter(getActivity(), viewExpenseResponseList, ViewExpenseFragment.this);

        viewExpenseRecyclerView.setAdapter(viewExpenseAdapter);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        selectViewExpenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        int monthOfYear = month + 1;
                        String date = dayOfMonth + "/" + monthOfYear + "/" + year;
                        userSelectedDate = date;
                        selectViewExpenseDate.setText("" + date);

                        viewExpenseResponseList.clear();
                        viewExpenseAdapter.notifyDataSetChanged();

                        getExpenseReport(userSelectedDate, new DateWiseExpenseReportListener() {
                            @Override
                            public void onViewExpenseReport(List<ViewExpenseResponse> viewExpenseResponseList) {


                                if (viewExpenseResponseList.size() > 0) {
                                    viewExpenseAdapter.setData(viewExpenseResponseList);
                                } else if (viewExpenseResponseList.size() == 0) {

                                    Toast.makeText(getActivity(), "you dont have any data for selected Date", Toast.LENGTH_LONG).show();

                                }


                            }
                        });


                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });


        return view;
    }


    private void getExpenseReport(String userSelectedDate, final DateWiseExpenseReportListener dateWiseExpenseReportListener) {

        viewEmployeeExpenseCollection.whereEqualTo("managerUserId", PreferenceUtil.getManagerId(getContext()))
                .whereEqualTo("empUserId", PreferenceUtil.getEmpUserId(getContext()))
                .whereEqualTo("expenseDate", userSelectedDate)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ViewExpenseResponse viewCheckInResponse = documentSnapshot.toObject(ViewExpenseResponse.class);
                    viewExpenseResponseList.add(viewCheckInResponse);
                }

                dateWiseExpenseReportListener.onViewExpenseReport(viewExpenseResponseList);

            }
        });

    }

    @Override
    public void onViewExpenseClick() {

    }

    private void initFireBase() {
        db = FirebaseFirestore.getInstance();
    }
}
