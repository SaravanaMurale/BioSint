package com.nutro.biosint.fragmentmanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.nutro.biosint.R;
import com.nutro.biosint.utils.AppConstants;
import com.nutro.biosint.utils.GpsUtils;
import com.nutro.biosint.utils.LocationJobIntentService;
import com.nutro.biosint.utils.PermissionUtils;

import static com.nutro.biosint.utils.AppConstants.GPS_PROVIDER_CODE;
import static com.nutro.biosint.utils.AppConstants.LOCATION_PERMISSION_REQUEST_CODE;

public class HomeFragmentManager extends Fragment {

    private boolean gpsEnabledStatus;
    public boolean isGPS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.manager_home_fragment, container, false);

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
                    }, 10000);
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
                }, 10000);


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

                System.out.println("MYLATLONGI" + lat + " " + lon);

                Toast.makeText(getActivity(),"MyLat "+lat+" "+lon,Toast.LENGTH_LONG).show();


              /*  handler.post(new Runnable() {
                    @Override
                    public void run() {

                        showData.setText(displayData);
                    }
                });*/


            }

        }
    }


}


/* mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {


                if (task.isSuccessful()) {
                    mLastKnownLocation = task.getResult();
                    if (mLastKnownLocation != null) {

                        myLocationLat = mLastKnownLocation.getLatitude();
                        myLocationLon = mLastKnownLocation.getLongitude();

                        System.out.println("LATITIDEANDLONGITITE " + myLocationLat + " " + myLocationLon);


                    } else if (mLastKnownLocation == null) {

                        final LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(10000);
                        locationRequest.setFastestInterval(5000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                        locationCallback = new LocationCallback() {

                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);

                                if (locationResult == null) {
                                    return;
                                } else if (locationResult != null) {

                                    mLastKnownLocation = locationResult.getLastLocation();

                                    myLocationLat = mLastKnownLocation.getLatitude();
                                    myLocationLon = mLastKnownLocation.getLongitude();

                                    System.out.println("MyLocationLatLong" + myLocationLat + " " + myLocationLon);


                                    mFusedLocationProviderClient.removeLocationUpdates(locationCallback);

                                }


                            }
                        };
                    }

                }


            }
        });*/

