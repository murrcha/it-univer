package com.kkaysheva.ituniver.presentation.map;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.kkaysheva.ituniver.R;

/**
 * TestMapFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class TestMapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = TestMapFragment.class.getSimpleName();
    private static final String MAP_VIEW_BUNDLE_KEY = "map_view_bundle_key";

    private static final int REQUEST_ERROR = 0;
    private static final int REQUEST_LOCATION_PERMISSIONS = 1;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int DEFAULT_ZOOM = 17;
    private static final int MIN_ZOOM = 11;
    private static final double DEFAULT_LATITUDE = 55.753215;
    private static final double DEFAULT_LONGITUDE = 37.622504;

    private Location lastKnownLocation;
    private LatLng defaultLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private MapView mapView;
    private GoogleMap map;

    public static TestMapFragment newInstance() {
        return new TestMapFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        defaultLocation = new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.map_toolbar);
        toolbar.setTitle(R.string.map_title);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(requireContext());
        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability
                    .getErrorDialog(requireActivity(), errorCode, REQUEST_ERROR,
                            dialog -> {
                                Toast.makeText(requireContext(), "No Google Play Services", Toast.LENGTH_SHORT).show();
                                requireActivity().onBackPressed();
                            });
            errorDialog.show();
        } else {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }
        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                configureMap();
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(latLng -> {
            map.clear();
            map.addMarker(new MarkerOptions().position(latLng));
            Toast.makeText(requireContext(), latLng.toString(), Toast.LENGTH_SHORT).show();
        });
        Log.d(TAG, "onMapReady: ready");
        configureMap();
    }

    private boolean hasLocationPermission() {
        int result = ContextCompat.checkSelfPermission(requireActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void getDeviceLocation() {
        try {
            if (hasLocationPermission()) {
                Task locationResult = fusedLocationProviderClient.getLastLocation();
                //noinspection unchecked
                locationResult.addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        lastKnownLocation = (Location) task.getResult();
                        if (lastKnownLocation != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "getDeviceLocation: ", task.getException());
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: ", e);
        }
    }

    private void configureMap() {
        if (map == null) {
            Log.d(TAG, "configureMap: map = null");
            return;
        }
        try {
            if (hasLocationPermission()) {
                Log.d(TAG, "configureMap: has permissions");
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                map.getUiSettings().setZoomControlsEnabled(true);
                map.setMinZoomPreference(MIN_ZOOM);
                getDeviceLocation();
                map.setOnMyLocationButtonClickListener(() -> {
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                            lastKnownLocation.getLatitude(),
                            lastKnownLocation.getLongitude()
                    ), DEFAULT_ZOOM));
                    return false;
                });
            } else {
                Log.d(TAG, "configureMap: request permissions");
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                requestPermissions(LOCATION_PERMISSIONS,
                        REQUEST_LOCATION_PERMISSIONS);
            }
        } catch (SecurityException e) {
            Log.e(TAG, "configureMap: ", e);
        }
    }
}
