package com.nutro.biosint.fragmentmanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutro.biosint.R;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;
import com.nutro.biosint.utils.GetMyEmpDetails;

import java.util.Calendar;
import java.util.List;

public class ViewMyEmpExpenseReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    List<ManageEmployeeResponse> employeeNameDTOList;
    Button btn_viewExpenseReport;
    TextView selectViewExpenseFromDate, selectViewExpenseToDate;
    String fromDate=null,toDate=null,selectedUserId=null;


    Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);


    public ViewMyEmpExpenseReportFragment(List<ManageEmployeeResponse> employeeNameDTOList) {
        this.employeeNameDTOList = employeeNameDTOList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_view_my_emp_expense_report, container, false);

        spinner = (Spinner) view.findViewById(R.id.myEmpExpenseSpinner);
        btn_viewExpenseReport = (Button) view.findViewById(R.id.btn_viewExpenseReport);

        List<String> empName = GetMyEmpDetails.getEmployeeName(employeeNameDTOList);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        selectViewExpenseFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int from = 1;
                selectDate(from);

            }
        });

        selectViewExpenseToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int to = 2;
                selectDate(to);
            }
        });


        btn_viewExpenseReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fromDate!=null && toDate!=null && selectedUserId!=null){

                }

            }
        });


        return view;
    }

    private void selectDate(final int userInput) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int monthOfYear = month + 1;
                String date = dayOfMonth + "/" + monthOfYear + "/" + year;

                if (userInput == 1) {
                    selectViewExpenseFromDate.setText(date);
                } else if (userInput == 2) {
                    selectViewExpenseToDate.setText(date);
                }

            }
        }, year, month, day);

        datePickerDialog.show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("SelectedSpinnerIs " + employeeNameDTOList.get(position).getName());
        System.out.println("SelectedSpinnerId " + employeeNameDTOList.get(position).getUserId());

        Toast.makeText(getActivity(), "selected " + employeeNameDTOList.get(position).getName(), Toast.LENGTH_LONG).show();
        Toast.makeText(getActivity(), "selected Id " + employeeNameDTOList.get(position).getUserId(), Toast.LENGTH_LONG).show();


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
