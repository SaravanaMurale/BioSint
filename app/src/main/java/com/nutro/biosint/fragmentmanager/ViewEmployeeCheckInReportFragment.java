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

public class ViewEmployeeCheckInReportFragment extends Fragment implements GetMyEmpDetails.GetMyAllEmployeeDetailsListener, AdapterView.OnItemSelectedListener {

    GetMyEmpDetails getMyEmpDetails;
    Spinner spinner;
    List<String> employeeNameDTOList;

    String[] country = { "India", "USA", "China", "Japan", "Other"};



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.layout_view_checkin_report,container,false);

        spinner = (Spinner)view.findViewById(R.id.employeeNameSpinner);
        //spinner.setOnItemSelectedListener(this);

        employeeNameDTOList=new ArrayList<>();
        getMyEmpDetails=new GetMyEmpDetails(getActivity(),this);

        getMyEmpDetails.getEmployeeDetails();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_dropdown_item, employeeNameDTOList);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        return view;
    }

    @Override
    public void getMyEmployeeDetails(List<ManageEmployeeResponse> manageEmployeeResponse) {

        /*System.out.println("IamInterfaceCalled");
        System.out.println("EMMMPPPs " + manageEmployeeResponse.get(0).getName());
        System.out.println("EMMMPPPs " + manageEmployeeResponse.get(0).getEmpDesi());

        System.out.println("EMMMPPPs " + manageEmployeeResponse.get(1).getName());
        System.out.println("EMMMPPPs " + manageEmployeeResponse.get(1).getEmpDesi());
*/
        for (int i = 0; i <manageEmployeeResponse.size() ; i++) {
            employeeNameDTOList.add(manageEmployeeResponse.get(i).getName());

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("SelectedSpinnerIs "+employeeNameDTOList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}