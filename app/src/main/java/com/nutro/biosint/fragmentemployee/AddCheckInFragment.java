package com.nutro.biosint.fragmentemployee;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.nutro.biosint.R;
import com.nutro.biosint.modelrequest.AddEmployeeCheckInDTO;
import com.nutro.biosint.utils.GpsUtils;
import com.nutro.biosint.utils.MathUtil;
import com.nutro.biosint.utils.PermissionUtils;
import com.nutro.biosint.utils.PreferenceUtil;

import java.util.List;

import static com.nutro.biosint.utils.AppConstants.GPS_PROVIDER_CODE;
import static com.nutro.biosint.utils.AppConstants.LOCATION_PERMISSION_REQUEST_CODE;
import static com.nutro.biosint.utils.MathUtil.validateName;

public class AddCheckInFragment extends Fragment implements OnMapReadyCallback {

    public boolean isGPS;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private View mapView;

    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private final float DEFAULT_ZOOM = 13;

    public LocationManager locationManager;
    Double myLocationLat, myLocationLon;

    private Button btnAddCheckIn;
    private EditText login_checkin_name;
    private EditText login_checkin_details;
    private EditText login_checkin_location;

    private SupportMapFragment mapFragment;
    private boolean gpsEnabledStatus;

    FirebaseFirestore db;
    CollectionReference addEmployeeCheckInCollection;
    DocumentReference addEmployeeCheckInDocument;


    //AddCheckInListener addCheckInListener;

    interface AddCheckInListener {
        public void onAddCheckInClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.employee_add_checkin_fragment, container, false);

        initFireBase();

        addEmployeeCheckInCollection = db.collection("CheckIn");

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            /*mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    //LatLng latLng = new LatLng(1.289545, 103.849972);
                    *//*googleMap.addMarker(new MarkerOptions().position(latLng)
                            .title("Singapore"));*//*
                    //googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            });*/
        }

        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        checkLocationPermission();

        btnAddCheckIn = (Button) rootView.findViewById(R.id.btnAddCheckIn);
        login_checkin_name = (EditText) rootView.findViewById(R.id.login_checkin_name);
        login_checkin_details = (EditText) rootView.findViewById(R.id.login_checkin_details);
        login_checkin_location = (EditText) rootView.findViewById(R.id.login_checkin_location);


        login_checkin_name.addTextChangedListener(new MyTextWatcher(login_checkin_name));
        login_checkin_details.addTextChangedListener(new MyTextWatcher(login_checkin_details));
        login_checkin_location.addTextChangedListener(new MyTextWatcher(login_checkin_location));


        return rootView;
    }

    private void initFireBase() {

        db = FirebaseFirestore.getInstance();
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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getDeviceLocation();
                        }
                    },10000);

                } else if (!gpsEnabledStatus) {
                    System.out.println("IamcalledEnalbleGPS");
                    enableGPS();
                }


            }

        }

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

                        System.out.println("LATITIDEANDLONGITITE " + myLocationLat + " " + myLocationLon);

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

            login_checkin_location.setText(address + " " + area + " " + city + " " + postalCode);


        } else {

            Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            final String name = login_checkin_name.getText().toString().trim();
            final String details = login_checkin_details.getText().toString().trim();
            String location = login_checkin_location.getText().toString().trim();

            btnAddCheckIn.setEnabled(validateName(name) && validateName(details) && validateName(location));

            if (btnAddCheckIn.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        btnAddCheckIn.setBackground(getActivity().getDrawable(R.drawable.background_rectangle_shape));
                        btnAddCheckIn.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));

                        btnAddCheckIn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addCheckInFireStore(name, details);
                            }
                        });


                    }

                }
            } else if (!btnAddCheckIn.isEnabled()) {
                btnAddCheckIn.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnAddCheckIn.setBackground(getActivity().getDrawable(R.color.gray));

                }

            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void addCheckInFireStore(String name, String details) {

        addEmployeeCheckInDocument = addEmployeeCheckInCollection.document();
        GeoPoint geoPoint = new GeoPoint(myLocationLat, myLocationLon);
        AddEmployeeCheckInDTO addEmployeeCheckInDTO = new AddEmployeeCheckInDTO(PreferenceUtil.getManagerId(getContext()), PreferenceUtil.getEmpUserId(getContext()), name, details, geoPoint, MathUtil.date(), MathUtil.time());

        addCheckInDetails(addEmployeeCheckInDTO, new AddCheckInListener() {
            @Override
            public void onAddCheckInClick() {

                Toast.makeText(getActivity(), "CheckInAdded", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void addCheckInDetails(AddEmployeeCheckInDTO addEmployeeCheckInDTO, final AddCheckInListener addCheckInListener) {
        addEmployeeCheckInDocument.set(addEmployeeCheckInDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    addCheckInListener.onAddCheckInClick();
                }

            }
        });
    }


}
   