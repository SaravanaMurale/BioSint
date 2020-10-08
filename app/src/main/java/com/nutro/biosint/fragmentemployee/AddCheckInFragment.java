package com.nutro.biosint.fragmentemployee;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nutro.biosint.R;
import com.nutro.biosint.utils.GpsUtils;
import com.nutro.biosint.utils.PermissionUtils;

import java.util.List;

import static com.nutro.biosint.utils.AppConstants.GPS_PROVIDER_CODE;
import static com.nutro.biosint.utils.AppConstants.LOCATION_PERMISSION_REQUEST_CODE;

public class AddCheckInFragment extends Fragment implements OnMapReadyCallback {

    public boolean isGPS;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private View mapView;

    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private final float DEFAULT_ZOOM = 13;

    private LocationManager locationManager;
    Double myLocationLat, myLocationLon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_checkin_fragment, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView = mapFragment.getView();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


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

                enableGPS();


            }

        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

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

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {

        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                if (task.isSuccessful()) {
                    mLastKnownLocation = task.getResult();
                    if (mLastKnownLocation != null) {

                        myLocationLat = mLastKnownLocation.getLatitude();
                        myLocationLon = mLastKnownLocation.getLongitude();

                        getAddressFromLatiandLongi(myLocationLat, myLocationLon);

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

                                    getAddressFromLatiandLongi(myLocationLat, myLocationLon);

                                    mFusedLocationProviderClient.removeLocationUpdates(locationCallback);

                                }


                            }
                        };
                    }

                }


            }
        });

    }

    private void getAddressFromLatiandLongi(Double myLocationLat, Double myLocationLon) {

        List<Address> geoAddresses = GpsUtils.getAddressFromMap(getActivity(), myLocationLat, myLocationLon);

        if (geoAddresses.size() != 0) {

            String address = geoAddresses.get(0).getAddressLine(0);
            String area = geoAddresses.get(0).getLocality();
            String city = geoAddresses.get(0).getAdminArea();
            String country = geoAddresses.get(0).getCountryName();
            String postalCode = geoAddresses.get(0).getPostalCode();
            String subAdminArea = geoAddresses.get(0).getSubAdminArea();
            String subLocality = geoAddresses.get(0).getSubLocality();
            String premises = geoAddresses.get(0).getPremises();
            String addressLine = geoAddresses.get(0).getAddressLine(0);

            System.out.println("MyAddress" + address + " " + area + " " + city + " " + postalCode);


        } else {

            Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG).show();
        }

    }
}
   