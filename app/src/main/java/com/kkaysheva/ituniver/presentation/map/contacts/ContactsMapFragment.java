package com.kkaysheva.ituniver.presentation.map.contacts;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.app.AppDelegate;
import com.kkaysheva.ituniver.di.map.MapComponent;
import com.kkaysheva.ituniver.presentation.map.BaseMapFragment;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * ContactsMapFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class ContactsMapFragment extends BaseMapFragment implements ContactsMapView {

    private static final String TAG = ContactsMapFragment.class.getSimpleName();
    private static final int PADDING = 100;

    @Inject
    Provider<ContactsMapPresenter> presenterProvider;

    @InjectPresenter
    ContactsMapPresenter presenter;

    private GoogleMap map;

    private Location lastKnownLocation;
    private LatLng defaultLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public static ContactsMapFragment newInstance() {
        return new ContactsMapFragment();
    }

    @Override
    public void onAttach(Context context) {
        AppDelegate appDelegate = (AppDelegate) requireActivity().getApplication();
        MapComponent mapComponent = appDelegate.getAppComponent()
                .plusMapComponent();
        mapComponent.inject(this);

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        defaultLocation = new LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.map_toolbar);
        toolbar.setTitle(R.string.map_title);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_map;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        map = googleMap;
        presenter.configureMap();
        Log.d(TAG, "onMapReady: ready");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                presenter.configureMap();
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void configureMap() {
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
                getDeviceLocation();
                map.setOnMyLocationButtonClickListener(() -> {
                    if (lastKnownLocation != null) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                                lastKnownLocation.getLatitude(),
                                lastKnownLocation.getLongitude()
                        ), DEFAULT_ZOOM));
                    } else {
                        Toast.makeText(requireContext(), "No current location", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                });
            } else {
                Log.d(TAG, "configureMap: request permissions");
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                requestPermissions();
            }
            presenter.getLocationForAll();
        } catch (SecurityException e) {
            Log.e(TAG, "configureMap: ", e);
        }
    }

    @Override
    public void addAllMarkers(@NonNull List<LatLng> locations) {
        if (map != null) {
            map.clear();
            for (LatLng location : locations) {
                map.addMarker(new MarkerOptions().position(location));
                Log.d(TAG, "addAllMarkers: " + location.toString());
            }
        } else {
            Log.d(TAG, "addAllMarkers: map is null");
        }
    }

    @Override
    public void showAllMarkers(@NonNull List<LatLng> locations) {
        if (map != null && !locations.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng location : locations) {
                builder.include(new LatLng(location.latitude, location.longitude));
            }
            LatLngBounds bounds = builder.build();
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, PADDING));
        }
    }

    private void getDeviceLocation() {
        try {
            if (hasLocationPermission()) {
                Task locationResult = fusedLocationProviderClient.getLastLocation();
                //noinspection unchecked
                locationResult.addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        lastKnownLocation = (Location) task.getResult();
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

    @ProvidePresenter
    ContactsMapPresenter providePresenter() {
        return presenterProvider.get();
    }
}
