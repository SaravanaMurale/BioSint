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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nutro.biosint.R;
import com.nutro.biosint.manageractivity.ViewMyEmpExpenseReportActivity;
import com.nutro.biosint.modelresponse.ViewExpenseResponse;
import com.nutro.biosint.utils.PreferenceUtil;

import java.util.Calendar;

public class ViewAttendanceFragment extends Fragment {

    TextView selectViewAttendanceFromDate, selectViewAttendanceToDate;

    FirebaseFirestore db;
    private CollectionReference viewAttendanceCollection;

    private String fromDate = null, toDate = null;

    Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);


    interface ViewAttendanceListener {
        public void viewAttendanceReport();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_view_attendance_fragment, container, false);

        db = FirebaseFirestore.getInstance();
        viewAttendanceCollection = db.collection("EmpAttendance");

        selectViewAttendanceFromDate = (TextView) view.findViewById(R.id.selectViewAttendanceFromDate);
        selectViewAttendanceToDate = (TextView) view.findViewById(R.id.selectViewAttendanceToDate);

        selectViewAttendanceFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(1);
            }
        });

        selectViewAttendanceToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(2);

                if (fromDate != null && toDate != null) {
                    getAttendanceForSelectedDate(new ViewAttendanceListener() {
                        @Override
                        public void viewAttendanceReport() {

                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please select both date", Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

    private void getAttendanceForSelectedDate(ViewAttendanceListener viewAttendanceListener) {

        viewAttendanceCollection.whereEqualTo("empUserId", PreferenceUtil.getValueString(getActivity(), PreferenceUtil.MY_USER_ID))
                .whereEqualTo("empLoginDate",selectViewAttendanceFromDate)
                .whereEqualTo("empLoginDate",selectViewAttendanceToDate)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ViewExpenseResponse viewCheckInResponse = documentSnapshot.toObject(ViewExpenseResponse.class);
                    //viewExpenseResponseList.add(viewCheckInResponse);
                }

            }
        });



    }

    private void selectDate(final int userInput) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthOfYear = month + 1;
                String date = dayOfMonth + "/" + monthOfYear + "/" + year;

                if (userInput == 1) {
                    selectViewAttendanceFromDate.setText(date);
                    fromDate = date;
                } else if (userInput == 2) {
                    selectViewAttendanceToDate.setText(date);
                    toDate = date;
                }

            }
        }, year, month, day);

        datePickerDialog.show();

    }

}
