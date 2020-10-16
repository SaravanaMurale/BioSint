package com.nutro.biosint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

import java.util.ArrayList;

public class SpinnerCheckBoxActivity extends AppCompatActivity implements MyAdapter.SpinnerCheckBoxSelectedListener {

    Spinner spinnerTest;

    public static ArrayList arrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_check_box);

        spinnerTest = findViewById(R.id.spinnerTest);

        final String[] select_qualification = {
                "Select Qualification", "10th / Below", "12th", "Diploma", "UG",
                "PG", "Phd"};

        ArrayList<SpinAdapter> listVOs = new ArrayList<>();

        for (int i = 0; i < select_qualification.length; i++) {
            SpinAdapter stateVO = new SpinAdapter();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }

        MyAdapter myAdapter = new MyAdapter(SpinnerCheckBoxActivity.this, 0,
                listVOs, SpinnerCheckBoxActivity.this);

        Log.d("TAG", listVOs.toString());

        spinnerTest.setAdapter(myAdapter);


    }

    @Override
    public void selectSpinnerCheckBox(String item) {

        System.out.println("SelectedSpinnerItem " + item);

    }
}