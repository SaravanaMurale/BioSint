package com.nutro.biosint.fragmentmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nutro.biosint.R;
import com.nutro.biosint.adapter.ViewMyEmployeeCheckInAdapter;
import com.nutro.biosint.modelrequest.EmployeeNameDTO;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;
import com.nutro.biosint.modelresponse.ViewCheckInResponse;
import com.nutro.biosint.utils.GetMyEmpDetails;

import java.util.ArrayList;
import java.util.List;

public class ViewEmployeeCheckInReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    GetMyEmpDetails getMyEmpDetails;
    Spinner spinner;
    List<ManageEmployeeResponse> employeeNameDTOList;

    private RecyclerView viewMyEmployeeCheckInRecyclerView;
    private ViewMyEmployeeCheckInAdapter viewMyEmployeeCheckInAdapter;
    private List<ViewCheckInResponse> viewCheckInResponseList;


    public ViewEmployeeCheckInReportFragment(List<ManageEmployeeResponse> employeeNameDTOList) {
        this.employeeNameDTOList = employeeNameDTOList;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_view_checkin_report, container, false);


        spinner = (Spinner) view.findViewById(R.id.employeeNameSpinner);
        viewMyEmployeeCheckInRecyclerView = (RecyclerView) view.findViewById(R.id.viewEmployeeCheckInRecyclerView);
        viewMyEmployeeCheckInRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewMyEmployeeCheckInRecyclerView.setHasFixedSize(true);

        viewCheckInResponseList = new ArrayList<>();

        viewMyEmployeeCheckInAdapter = new ViewMyEmployeeCheckInAdapter(getActivity(), viewCheckInResponseList);
        viewMyEmployeeCheckInRecyclerView.setAdapter(viewMyEmployeeCheckInAdapter);


        List<String> empName = GetMyEmpDetails.getEmployeeName(employeeNameDTOList);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        return view;
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
