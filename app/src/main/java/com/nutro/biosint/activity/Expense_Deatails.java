package com.nutro.biosint.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.nutro.biosint.R;

public class Expense_Deatails extends AppCompatActivity {

    static Button showDate;
    static Button add_image;
    static EditText cost_Purpose;
    static EditText phone_No;
    static EditText email_Id;
    static CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense__deatails);

        cost_Purpose = (EditText)findViewById(R.id.cost_Purpose);
        phone_No = (EditText)findViewById(R.id.phone_No);
        email_Id = (EditText)findViewById(R.id.email_Id);
        showDate= (Button)findViewById(R.id.showDate);
        add_image= (Button)findViewById(R.id.add_image);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setVisibility(View.GONE);

        showDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Check","Check");
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

                String msg =  i2 + "/" + (i1 + 1) + " / " + i;
                showDate.setText(msg);

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                calendarView.setVisibility(View.GONE);
                cost_Purpose.setVisibility(View.VISIBLE);
                phone_No.setVisibility(View.VISIBLE);
                email_Id.setVisibility(View.VISIBLE);
                add_image.setVisibility(View.VISIBLE);
            }
        });


    }
}