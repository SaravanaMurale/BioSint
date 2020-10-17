package com.nutro.biosint.fragmentmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutro.biosint.MyAdapter;
import com.nutro.biosint.R;
import com.nutro.biosint.SpinAdapter;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;
import com.nutro.biosint.modelresponse.MyClientResponse;
import com.nutro.biosint.utils.GetMyClientDetails;
import com.nutro.biosint.utils.GetMyEmpDetails;

import java.util.ArrayList;
import java.util.List;

public class AssignClientsFragment extends Fragment implements AdapterView.OnItemSelectedListener, MyAdapter.SpinnerCheckBoxSelectedListener {

    Spinner assignClientSpinner;
    Spinner addEmpInSpinnerCheckbox;

    List<MyClientResponse> myClientResponseArrayList;
    List<ManageEmployeeResponse> employeeNameDTOList;

    Button btnAssignClient;
    ArrayList<SpinAdapter> listVOs;

    public AssignClientsFragment(List<MyClientResponse> myClientResponseArrayList, List<ManageEmployeeResponse> employeeNameDTOList) {
        this.myClientResponseArrayList = myClientResponseArrayList;
        this.employeeNameDTOList = employeeNameDTOList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manager_assign_clients_fragment, container, false);

        assignClientSpinner = (Spinner) view.findViewById(R.id.assignClientSpinner);
        addEmpInSpinnerCheckbox = (Spinner) view.findViewById(R.id.addEmpInSpinnerCheckbox);

        btnAssignClient = (Button) view.findViewById(R.id.btnAssignClient);

        List<String> empName = GetMyEmpDetails.getEmployeeName(employeeNameDTOList);

        empName.add(0,"Select Employee");

        listVOs = new ArrayList<>();

        for (int i = 0; i < empName.size(); i++) {
            SpinAdapter stateVO = new SpinAdapter();
            stateVO.setTitle(empName.get(i));
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }

        List<String> getClientNameAndOrg = GetMyClientDetails.getClientNameAndOrganization(myClientResponseArrayList);
        System.out.println("ClientSize " + getClientNameAndOrg.size());
        getClientNameAndOrg.add(0, "Select Client");

        MyAdapter myAdapter = new MyAdapter(getActivity(), 0, listVOs, AssignClientsFragment.this);

        Log.d("TAG", listVOs.toString());

        addEmpInSpinnerCheckbox.setAdapter(myAdapter);


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getClientNameAndOrg);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        assignClientSpinner.setAdapter(adapter);
        assignClientSpinner.setOnItemSelectedListener(this);


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void selectSpinnerCheckBox(String item) {

    }
}
