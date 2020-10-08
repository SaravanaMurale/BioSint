package com.nutro.biosint.fragmentemployee;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutro.biosint.R;
import com.nutro.biosint.adapteremployee.ViewCheckInAdapter;
import com.nutro.biosint.modelresponse.ViewCheckInResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewCheckInFragment extends Fragment implements ViewCheckInAdapter.CheckInClickListener {

    DatePickerDialog.OnDateSetListener setListenerDate;
    TextView selectDate;
    String userSelectedDate;

    RecyclerView viewCheckInRecyclerView;
    ViewCheckInAdapter viewCheckInAdapter;
    List<ViewCheckInResponse> viewCheckInResponseList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_view_checkin_fragment, container, false);

        selectDate = (TextView) view.findViewById(R.id.selectDate);
        viewCheckInRecyclerView = (RecyclerView) view.findViewById(R.id.viewUserRecyclerView);
        viewCheckInRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewCheckInRecyclerView.setHasFixedSize(true);

        viewCheckInResponseList = new ArrayList<>();

        viewCheckInAdapter = new ViewCheckInAdapter(getActivity(), viewCheckInResponseList, ViewCheckInFragment.this);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        final Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        System.out.println("Today: " + dayOfTheWeek);
        selectDate.setText(dayOfTheWeek);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        int monthOfYear = month + 1;
                        String date = dayOfMonth + "/" + monthOfYear + "/" + year;
                        userSelectedDate = date;
                        selectDate.setText("" + date);
                        
                        getMyCheckInDataBasedOnDate(userSelectedDate);


                    }
                }, year, month, day);


            }
        });


        return view;
    }

    private void getMyCheckInDataBasedOnDate(String userSelectedDate) {
    }

    @Override
    public void onCheckInClick() {

    }
}