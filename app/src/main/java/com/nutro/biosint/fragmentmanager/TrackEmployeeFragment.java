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

import com.nutro.biosint.R;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;
import com.nutro.biosint.utils.GetMyEmpDetails;

import java.util.List;

public class TrackEmployeeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner trackEmpSpinner;

    List<ManageEmployeeResponse> employeeNameDTOList;


    public TrackEmployeeFragment(List<ManageEmployeeResponse> employeeNameDTOList) {

        this.employeeNameDTOList = employeeNameDTOList;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.track_employee_fragment, container, false);

        trackEmpSpinner = (Spinner) view.findViewById(R.id.trackEmpSpinner);

        List<String> empName = GetMyEmpDetails.getEmployeeName(employeeNameDTOList);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        trackEmpSpinner.setAdapter(adapter);
        trackEmpSpinner.setOnItemSelectedListener(this);


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("SelectedSpinnerIs " + employeeNameDTOList.get(position).getName());
        System.out.println("SelectedSpinnerId " + employeeNameDTOList.get(position).getUserId());

        Toast.makeText(getActivity(), "selected " + employeeNameDTOList.get(position).getName() +" "+employeeNameDTOList.get(position).getUserId(), Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(), "selected Id " + employeeNameDTOList.get(position).getUserId(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
