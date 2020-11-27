package com.nutro.biosint.fragmentmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nutro.biosint.R;
import com.nutro.biosint.adapter.ViewUserAdapter;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;
import com.nutro.biosint.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class AddUserFragment extends Fragment implements ViewUserAdapter.EmployeeClickListener {

    Button addEmployeeBtn;

    RecyclerView viewUserRecyclerView;
    ViewUserAdapter viewUserAdapter;

    List<ManageEmployeeResponse> manageEmployeeResponseList;

    private FirebaseFirestore firebaseFirestore;
    private CollectionReference employeeCollectionRef;

    List<ManageEmployeeResponse> employeeNameDTOList;

    public AddUserFragment(List<ManageEmployeeResponse> employeeNameDTOList) {
        this.employeeNameDTOList = employeeNameDTOList;
    }

    public interface EmployeeDetailsGetListener {
        void getEmployeeDetails(List<ManageEmployeeResponse> manageEmployeeResponse);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.manage_user_fragment, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        employeeCollectionRef = firebaseFirestore.collection("User");

        addEmployeeBtn = (Button) view.findViewById(R.id.addEmployeeBtn);

        viewUserRecyclerView = (RecyclerView) view.findViewById(R.id.viewUserRecyclerView);
        viewUserRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewUserRecyclerView.setHasFixedSize(true);

        manageEmployeeResponseList = new ArrayList<>();

        viewUserAdapter = new ViewUserAdapter(getActivity(), manageEmployeeResponseList, this);

        viewUserRecyclerView.setAdapter(viewUserAdapter);

        getEmployeeDetails(new EmployeeDetailsGetListener() {
            @Override
            public void getEmployeeDetails(List<ManageEmployeeResponse> manageEmployeeResponse) {

                viewUserAdapter.setData(manageEmployeeResponse);

            }
        });


        addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new AddEmployeeFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.screenArea, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view;
    }

    private void getEmployeeDetails(final EmployeeDetailsGetListener employeeDetailsGetListener) {

        String managerUserId = PreferenceUtil.getValueString(getActivity(), PreferenceUtil.MY_USER_ID);

        employeeCollectionRef.whereEqualTo("managerUserId", managerUserId).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    ManageEmployeeResponse manageEmployeeResponse = documentSnapshot.toObject(ManageEmployeeResponse.class);

                    manageEmployeeResponseList.add(manageEmployeeResponse);
                }

                employeeDetailsGetListener.getEmployeeDetails(manageEmployeeResponseList);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    @Override
    public void onWorkAssignClick(ManageEmployeeResponse manageEmployeeResponse) {

    }

    @Override
    public void onViewCheckInClick(ManageEmployeeResponse manageEmployeeResponse) {

        Fragment fragment = new ViewEmployeeCheckInReportFragment(employeeNameDTOList);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.screenArea, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
