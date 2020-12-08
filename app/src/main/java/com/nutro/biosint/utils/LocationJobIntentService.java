package com.nutro.biosint.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.ResultReceiver;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationJobIntentService extends JobIntentService {


    private static FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean gpsEnabledStatus;
    public boolean isGPS;

    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private final float DEFAULT_ZOOM = 13;

    public static LocationManager locationManager;
    Double myLocationLat, myLocationLon;

    static Context mCtx;


    public static void enqueueWork(Context context, Intent serviceIntent) {

        System.out.println("InSideEnqueue");

        enqueueWork(context, LocationJobIntentService.class, 1, serviceIntent);
        mCtx=context;


    }


    @SuppressLint("MissingPermission")
    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        System.out.println("InsideOnHandleWork");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mCtx);
        locationManager = (LocationManager) mCtx.getSystemService(Context.LOCATION_SERVICE);


        final ResultReceiver resultReceiver = intent.getParcelableExtra("MYRESULTRECEIVER");


        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {


                if (task.isSuccessful()) {
                    mLastKnownLocation = task.getResult();
                    if (mLastKnownLocation != null) {

                        myLocationLat = mLastKnownLocation.getLatitude();
                        myLocationLon = mLastKnownLocation.getLongitude();

                        System.out.println("LATITIDEANDLONGITITE " + myLocationLat + " " + myLocationLon);

                        Bundle latLong = new Bundle();
                        latLong.putDouble("MYLATITIDE", myLocationLat);
                        latLong.putDouble("MYLONGITUTE", myLocationLon);

                        resultReceiver.send(AppConstants.RESULT_CODE, latLong);


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

                                    Bundle latLong = new Bundle();
                                    latLong.putDouble("MYLATITIDE", myLocationLat);
                                    latLong.putDouble("MYLONGITUTE", myLocationLon);

                                    resultReceiver.send(AppConstants.RESULT_CODE, latLong);


                                    mFusedLocationProviderClient.removeLocationUpdates(locationCallback);

                                }


                            }
                        };
                    }

                }


            }
        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onStopCurrentWork() {
        //Log.d(TAG, "onStopCurrentWork");
        return super.onStopCurrentWork();
    }

}
