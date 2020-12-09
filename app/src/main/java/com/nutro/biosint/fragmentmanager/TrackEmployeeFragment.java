package com.nutro.biosint.fragmentmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nutro.biosint.R;
import com.nutro.biosint.modelresponse.ManageEmployeeResponse;
import com.nutro.biosint.modelresponse.ViewCheckInResponse;
import com.nutro.biosint.utils.AppConstants;
import com.nutro.biosint.utils.GetMyEmpDetails;
import com.nutro.biosint.utils.PreferenceUtil;

import java.util.List;

public class TrackEmployeeFragment extends Fragment implements AdapterView.OnItemSelectedListener, OnMapReadyCallback {

    FirebaseFirestore db;
    CollectionReference viewMyEmployeeCurrentLocationCollection;

    private Spinner trackEmpSpinner;
    List<ManageEmployeeResponse> employeeNameDTOList;

    private GoogleMap mGoogleMap;
    private View mapView;

    Double myEmpCurLat;
    Double myEmpCurLong;
    MarkerOptions myEmpCurrentLocation;
    Marker myEmpCurrentLocationMarker;


    interface MyEmpLocationListener {
        public void myEmpLocation(ViewCheckInResponse viewCheckInResponse);
    }


    public TrackEmployeeFragment(List<ManageEmployeeResponse> employeeNameDTOList) {

        this.employeeNameDTOList = employeeNameDTOList;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.track_employee_fragment, container, false);

        db = FirebaseFirestore.getInstance();
        viewMyEmployeeCurrentLocationCollection = db.collection("EmpCurrentLocation");
        trackEmpSpinner = (Spinner) view.findViewById(R.id.trackEmpSpinner);
        List<String> empName = GetMyEmpDetails.getEmployeeName(employeeNameDTOList);


        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, empName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        trackEmpSpinner.setAdapter(adapter);
        trackEmpSpinner.setOnItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.trackEmpMap);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("SelectedSpinnerIs " + employeeNameDTOList.get(position).getName());
        System.out.println("SelectedSpinnerId " + employeeNameDTOList.get(position).getUserId());

        Toast.makeText(getActivity(), "selected " + employeeNameDTOList.get(position).getName() + " " + employeeNameDTOList.get(position).getUserId(), Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(), "selected Id " + employeeNameDTOList.get(position).getUserId(), Toast.LENGTH_LONG).show();

        getMyEmpLocationdetails(employeeNameDTOList.get(position).getUserId());

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(false);

    }


    private void getMyEmpLocationdetails(String userId) {

        getMyEmpLocationFromFireStore(userId, new MyEmpLocationListener() {
            @Override
            public void myEmpLocation(ViewCheckInResponse viewCheckInResponse) {

                myEmpCurLat = viewCheckInResponse.getGeoPoint().getLatitude();
                myEmpCurLong = viewCheckInResponse.getGeoPoint().getLongitude();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addMarker(myEmpCurLat, myEmpCurLong);
                    }
                }, 2000);

                moveCamera(myEmpCurLat, myEmpCurLong);

            }
        });
    }

    private void moveCamera(Double myEmpCurLat, Double myEmpCurLong) {

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myEmpCurLat, myEmpCurLong), AppConstants.DEFAULT_ZOOM));

    }

    private void addMarker(Double myEmpCurLat, Double myEmpCurLong) {

        myEmpCurrentLocation = new MarkerOptions().position(new LatLng(myEmpCurLat, myEmpCurLong)).title("Murali");
        myEmpCurrentLocationMarker = mGoogleMap.addMarker(myEmpCurrentLocation);
        myEmpCurrentLocationMarker.showInfoWindow();


    }

    private void getMyEmpLocationFromFireStore(String userId, final MyEmpLocationListener myEmpLocationListener) {

        viewMyEmployeeCurrentLocationCollection
                .whereEqualTo("managerUserId", PreferenceUtil.getValueString(getContext(), PreferenceUtil.MY_MANAGER_USER_ID))
                .whereEqualTo("empUserId", userId)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ViewCheckInResponse viewCheckInResponse = documentSnapshot.toObject(ViewCheckInResponse.class);
                    myEmpLocationListener.myEmpLocation(viewCheckInResponse);
                }

            }
        });

    }
}
