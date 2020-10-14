package com.nutro.biosint.fragmentmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutro.biosint.R;
import com.nutro.biosint.modelrequest.EmployeeNameDTO;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;
import com.nutro.biosint.utils.GetMyEmpDetails;

import java.util.ArrayList;
import java.util.List;

public class ViewEmployeeCheckInReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    GetMyEmpDetails getMyEmpDetails;
    Spinner spinner;
    List<String> employeeNameDTOList;

    public ViewEmployeeCheckInReportFragment() {

    }

    public ViewEmployeeCheckInReportFragment(List<String> employeeNameDTOList) {

        this.employeeNameDTOList = employeeNameDTOList;


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_view_checkin_report, container, false);

        //getMyEmpDetails = new GetMyEmpDetails(getActivity(), this);

        spinner = (Spinner) view.findViewById(R.id.employeeNameSpinner);

        //employeeNameDTOList = new ArrayList<>();

        //getMyEmpDetails.getEmployeeDetails();
        /*employeeNameDTOList.add("first");
        employeeNameDTOList.add("second");
        employeeNameDTOList.add("third");
        employeeNameDTOList.add("fourth");
        employeeNameDTOList.add("fifth");*/


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, employeeNameDTOList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        return view;
    }

   /* @Override
    public void getMyEmployeeDetails(List<ManageEmployeeResponse> manageEmployeeResponse) {

        for (int i = 0; i < manageEmployeeResponse.size(); i++) {
            employeeNameDTOList.add(manageEmployeeResponse.get(i).getName());

        }


    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("SelectedSpinnerIs " + employeeNameDTOList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
