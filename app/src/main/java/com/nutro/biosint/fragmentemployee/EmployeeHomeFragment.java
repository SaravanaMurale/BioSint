package com.nutro.biosint.fragmentemployee;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.nutro.biosint.R;
import com.nutro.biosint.modelrequest.AddEmployeeCheckInDTO;
import com.nutro.biosint.utils.AppConstants;
import com.nutro.biosint.utils.GpsUtils;
import com.nutro.biosint.utils.LocationJobIntentService;
import com.nutro.biosint.utils.MathUtil;
import com.nutro.biosint.utils.PermissionUtils;
import com.nutro.biosint.utils.PreferenceUtil;

import static com.nutro.biosint.utils.AppConstants.GPS_PROVIDER_CODE;
import static com.nutro.biosint.utils.AppConstants.LOCATION_PERMISSION_REQUEST_CODE;

public class EmployeeHomeFragment extends Fragment {

    private boolean gpsEnabledStatus;
    public boolean isGPS;

    private FirebaseFirestore db;
    private CollectionReference addEmployeeCurrentLocationCollection;
    private DocumentReference addEmployeeCurrentLocationDocument;

    public interface addMyLocationListener {

        public void addMyLocationClick();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_employee__home_fragment, container, false);

        db = FirebaseFirestore.getInstance();
        addEmployeeCurrentLocationCollection = db.collection("EmpCurrentLocation");

        checkLocationPermission();

        return view;
    }

    private void checkLocationPermission() {

        if (!PermissionUtils.hasPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                && !PermissionUtils.hasPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

            PermissionUtils.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);


        } else {

            if (PermissionUtils.hasPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    && PermissionUtils.hasPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

                //If permission is granted turn on gps


                gpsEnabledStatus = new GpsUtils(getActivity()).gpsStatus();

                if (gpsEnabledStatus) {

                    //             getDeviceLocation();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getDeviceLocation();
                        }
                    }, 5000);
                } else if (!gpsEnabledStatus) {
                    System.out.println("IamcalledEnalbleGPS");
                    enableGPS();
                }


            }

        }

    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {

        System.out.println("GetDeviceLocationIsCalled");

        MyResultReceiver myResultReceiver = new MyResultReceiver(null);

        Intent serviceIntent = new Intent(getActivity(), LocationJobIntentService.class);

        serviceIntent.putExtra("MYRESULTRECEIVER", myResultReceiver);

        LocationJobIntentService.enqueueWork(getContext(), serviceIntent);

    }

    private void enableGPS() {

        new GpsUtils(getActivity()).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS*

                isGPS = isGPSEnable;

                System.out.println("ISGPS" + isGPS);

            }
        });

    }

    @Override
    public void
    onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_PROVIDER_CODE) {

                isGPS = true;


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDeviceLocation();
                    }
                }, 5000);


            }

        } else {

        }
    }

    private class MyResultReceiver extends ResultReceiver {


        public MyResultReceiver(Handler handler) {
            super(handler);

            System.out.println("MyResultReceiverIsCalled");
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            System.out.println("outside");

            if (resultCode == AppConstants.RESULT_CODE && resultData != null) {

                System.out.println("Inseide");

                Double lat = resultData.getDouble("MYLATITIDE");
                Double lon = resultData.getDouble("MYLONGITUTE");

                addMyCurrentLocationInFireStore(lat, lon);

                System.out.println("MYLATLONGI" + lat + " " + lon);

                Toast.makeText(getActivity(), "MyLat " + lat + " " + lon, Toast.LENGTH_LONG).show();


              /*  handler.post(new Runnable() {
                    @Override
                    public void run() {

                        showData.setText(displayData);
                    }
                });*/


            }

        }
    }

    private void addMyCurrentLocationInFireStore(Double lat, Double lon) {

        addMyLocationInFS(lat, lon, new addMyLocationListener() {
            @Override
            public void addMyLocationClick() {

                Toast.makeText(getContext(), "EmployeeCurrentLocationAddedSuccessfully", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void addMyLocationInFS(Double lat, Double lon, final addMyLocationListener addMyLocationListener) {

        addEmployeeCurrentLocationDocument = addEmployeeCurrentLocationCollection.document();
        GeoPoint geoPoint = new GeoPoint(lat, lon);
        AddEmployeeCheckInDTO addEmployeeCheckInDTO = new AddEmployeeCheckInDTO(PreferenceUtil.getValueString(getContext(), PreferenceUtil.MY_MANAGER_USER_ID), PreferenceUtil.getValueString(getContext(), PreferenceUtil.MY_USER_ID), geoPoint, MathUtil.date(), MathUtil.time());

        addEmployeeCurrentLocationDocument.set(addEmployeeCheckInDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    addMyLocationListener.addMyLocationClick();
                }

            }
        });


    }

}
