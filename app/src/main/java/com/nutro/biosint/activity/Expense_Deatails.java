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
        setContentView(R.layout.layout_expense__deatails_fragment);



    }
}