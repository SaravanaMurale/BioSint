package com.nutro.biosint.fragmentmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutro.biosint.R;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;
import com.nutro.biosint.utils.GetMyEmpDetails;

import java.util.List;

public class ViewEmployeeCheckInReportFragment extends Fragment implements GetMyEmpDetails.GetMyAllEmployeeDetailsListener{

    GetMyEmpDetails getMyEmpDetails;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.layout_view_checkin_report,container,false);

        //getMyEmployeeDetails()

       // GetMyEmpDetails.getMyEmpDetails();

        getMyEmpDetails=new GetMyEmpDetails(getActivity());

        getMyEmpDetails.getEmployeeDetails();

        return view;
    }

    @Override
    public void getMyEmployeeDetails(List<ManageEmployeeResponse> manageEmployeeResponse) {



    }
}
