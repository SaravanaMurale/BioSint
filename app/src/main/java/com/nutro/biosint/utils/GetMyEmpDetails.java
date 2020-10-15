package com.nutro.biosint.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;

import java.util.ArrayList;
import java.util.List;

public class GetMyEmpDetails {

    private Context context;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference employeeCollectionRef = firebaseFirestore.collection("User");
    GetMyAllEmployeeDetailsListener getMyAllEmployeeDetailsListener;

    public GetMyEmpDetails(Context context, GetMyAllEmployeeDetailsListener getMyAllEmployeeDetailsListener) {
        this.context = context;
        this.getMyAllEmployeeDetailsListener = getMyAllEmployeeDetailsListener;
    }

    List<ManageEmployeeResponse> manageEmployeeResponseList = new ArrayList<>();

    public interface GetMyAllEmployeeDetailsListener {
        void getMyEmployeeDetails(List<ManageEmployeeResponse> manageEmployeeResponse);
    }

    public void getEmployeeDetails() {

        //String managerUserId = PreferenceUtil.getValueString(context, PreferenceUtil.USERID);

        employeeCollectionRef.whereEqualTo("managerUserId", PreferenceUtil.getManagerId(context)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    ManageEmployeeResponse manageEmployeeResponse = documentSnapshot.toObject(ManageEmployeeResponse.class);

                    manageEmployeeResponseList.add(manageEmployeeResponse);
                }

                getMyAllEmployeeDetailsListener.getMyEmployeeDetails(manageEmployeeResponseList);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    public static List<String> getEmployeeName(List<ManageEmployeeResponse> employeeNameDTOList) {
        List<String> empName = new ArrayList<>();
        for (int i = 0; i < employeeNameDTOList.size(); i++) {

            empName.add(employeeNameDTOList.get(i).getName());

        }

        return empName;

    }


}
