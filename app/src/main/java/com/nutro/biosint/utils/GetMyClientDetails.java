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
import com.nutro.biosint.modelresponse.MyClientResponse;

import java.util.ArrayList;
import java.util.List;

public class GetMyClientDetails {

    private Context context;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference getMyClientCollectionRef = firebaseFirestore.collection("AddClients");
    GetMyClientDetailsListener getMyClientDetailsListener;

    public GetMyClientDetails(Context context, GetMyClientDetailsListener getMyClientDetailsListener) {
        this.context = context;
        this.getMyClientDetailsListener = getMyClientDetailsListener;
    }

    public interface GetMyClientDetailsListener {
        public void getMyClient(List<MyClientResponse> myClientResponseArrayList);
    }

    List<MyClientResponse> myClientResponseArrayList = new ArrayList<>();

    public void getMyClientList() {

        getMyClientCollectionRef
                .whereEqualTo("managerUserId", PreferenceUtil.getEmpUserId(context))
                .whereEqualTo("isClientAssigned", false).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    MyClientResponse myClientResponse = documentSnapshot.toObject(MyClientResponse.class);

                    myClientResponseArrayList.add(myClientResponse);
                }

                getMyClientDetailsListener.getMyClient(myClientResponseArrayList);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    public static List<String> getClientNameAndOrganization(List<MyClientResponse> myClientDetailsList) {
        List<String> clientNameOrg = new ArrayList<>();

        for (int i = 0; i < myClientDetailsList.size(); i++) {
            clientNameOrg.add(myClientDetailsList.get(i).getClientName() + "(" + myClientDetailsList.get(i).getClientOrg() + ")");
        }

        return clientNameOrg;
    }

}
