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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nutro.biosint.R;
import com.nutro.biosint.adapter.ViewMyEmployeeCheckInAdapter;
import com.nutro.biosint.modelrequest.EmployeeNameDTO;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;
import com.nutro.biosint.modelresponse.ViewCheckInResponse;
import com.nutro.biosint.utils.GetMyEmpDetails;
import com.nutro.biosint.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class ViewEmployeeCheckInReportFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    FirebaseFirestore db;
    CollectionReference viewMyEmployeeCheckInCollection;
    DocumentReference viewMyEmployeeCheckInDocument;

    GetMyEmpDetails getMyEmpDetails;
    Spinner spinner;
    List<ManageEmployeeResponse> employeeNameDTOList;

    private RecyclerView viewMyEmployeeCheckInRecyclerView;
    private ViewMyEmployeeCheckInAdapter viewMyEmployeeCheckInAdapter;
    private List<ViewCheckInResponse> viewCheckInResponseList;


    public ViewEmployeeCheckInReportFragment(List<ManageEmployeeResponse> employeeNameDTOList) {
        this.employeeNameDTOList = employeeNameDTOList;
    }

    private interface MyEmpCheckInListener {
        public void myEmpCheckIn(List<ViewCheckInResponse> viewCheckInResponseList);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_view_checkin_report, container, false);


        db = FirebaseFirestore.getInstance();
        viewMyEmployeeCheckInCollection = db.collection("CheckIn");


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

        viewCheckInResponseList.clear();
        viewMyEmployeeCheckInAdapter.notifyDataSetChanged();
        getMyEmpCheckIndetails(employeeNameDTOList.get(position).getUserId());


    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getMyEmpCheckIndetails(String userId) {

        getMyEmpCheckInDetailsFromFireStore(userId, new MyEmpCheckInListener() {
            @Override
            public void myEmpCheckIn(List<ViewCheckInResponse> viewCheckInResponseList) {

                viewMyEmployeeCheckInAdapter.setData(viewCheckInResponseList);

            }
        });


    }

    private void getMyEmpCheckInDetailsFromFireStore(String empUserId, final MyEmpCheckInListener myEmpCheckInListener) {

        viewMyEmployeeCheckInCollection
                .whereEqualTo("managerUserId", PreferenceUtil.getManagerId(getContext()))
                .whereEqualTo("empUserId", empUserId)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ViewCheckInResponse viewCheckInResponse = documentSnapshot.toObject(ViewCheckInResponse.class);
                    viewCheckInResponseList.add(viewCheckInResponse);
                }

                myEmpCheckInListener.myEmpCheckIn(viewCheckInResponseList);

            }
        });
    }

}
