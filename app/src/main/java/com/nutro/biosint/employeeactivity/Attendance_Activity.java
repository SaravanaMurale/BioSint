package com.nutro.biosint.employeeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nutro.biosint.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Attendance_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_attendance_fragment);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
    }
}