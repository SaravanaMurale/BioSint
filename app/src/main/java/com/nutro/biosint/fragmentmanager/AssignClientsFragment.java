package com.nutro.biosint.fragmentmanager;

import android.os.Bundle;
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

import com.nutro.biosint.R;
import com.nutro.biosint.modelresponse.MyClientResponse;
import com.nutro.biosint.utils.GetMyClientDetails;

import java.util.List;

public class AssignClientsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner assignClientSpinner;
    List<MyClientResponse> myClientResponseArrayList;

    Button btnAssignClient;

    public AssignClientsFragment(List<MyClientResponse> myClientResponseArrayList) {
        this.myClientResponseArrayList = myClientResponseArrayList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manager_assign_clients_fragment, container, false);

        assignClientSpinner = (Spinner) view.findViewById(R.id.assignClientSpinner);
        btnAssignClient = (Button) view.findViewById(R.id.btnAssignClient);

        List<String> getClientNameAndOrg = GetMyClientDetails.getClientNameAndOrganization(myClientResponseArrayList);
        System.out.println("ClientSize " + getClientNameAndOrg.size());
        getClientNameAndOrg.add(0, "Select Client");

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getClientNameAndOrg);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        assignClientSpinner.setAdapter(adapter);
        assignClientSpinner.setOnItemSelectedListener(this);

        btnAssignClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
