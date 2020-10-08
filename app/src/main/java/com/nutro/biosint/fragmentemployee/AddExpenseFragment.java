package com.nutro.biosint.fragmentemployee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutro.biosint.R;

public class AddExpenseFragment extends Fragment {

    static Button showDate;
    static Button add_image;
    static EditText cost_Purpose;
    static EditText phone_No;
    static EditText email_Id;
    static CalendarView calendarView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_expense__deatails_fragment, container, false);

        cost_Purpose = (EditText) view.findViewById(R.id.cost_Purpose);
        phone_No = (EditText) view.findViewById(R.id.phone_No);
        email_Id = (EditText) view.findViewById(R.id.email_Id);
        showDate = (Button) view.findViewById(R.id.showDate);
        add_image = (Button) view.findViewById(R.id.add_image);
        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setVisibility(View.GONE);

        showDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Check", "Check");
                cost_Purpose.setVisibility(View.GONE);
                phone_No.setVisibility(View.GONE);
                email_Id.setVisibility(View.GONE);
                add_image.setVisibility(View.GONE);
                calendarView.setVisibility(View.VISIBLE);

            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String msg = i2 + "/" + (i1 + 1) + " / " + i;
                showDate.setText(msg);

                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                calendarView.setVisibility(View.GONE);
                cost_Purpose.setVisibility(View.VISIBLE);
                phone_No.setVisibility(View.VISIBLE);
                email_Id.setVisibility(View.VISIBLE);
                add_image.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }
}
