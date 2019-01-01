package com.kkaysheva.ituniver.presentation.map;

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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.app.AppDelegate;
import com.kkaysheva.ituniver.di.map.MapComponent;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * ContactMapFragment
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class ContactMapFragment extends BaseMapFragment implements ContactMapView {

    private static final String TAG = ContactMapFragment.class.getSimpleName();

    @Inject
    Provider<ContactMapPresenter> presenterProvider;

    @InjectPresenter
    ContactMapPresenter presenter;

    private GoogleMap map;

    private Location lastKnownLocation;
    private LatLng defaultLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public static ContactMapFragment newInstance() {
        return new ContactMapFragment();
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
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
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
        map.setOnMapClickListener(latLng -> {
            presenter.addMarker(latLng);
            presenter.getAddress(latLng);
        });
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
                requestPermissions();
            }
        } catch (SecurityException e) {
            Log.e(TAG, "configureMap: ", e);
        }
    }

    @Override
    public void addMarker(@NonNull LatLng latLng) {
        if (map != null) {
            map.clear();
            map.addMarker(new MarkerOptions().position(latLng));
        }
    }

    @Override
    public void showAddress(@NonNull String address) {
        Toast.makeText(requireContext(), address, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorLog(@NonNull Throwable throwable) {
        Log.e(TAG, "showErrorLog: ", throwable);
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

    @ProvidePresenter
    ContactMapPresenter providePresenter() {
        return presenterProvider.get();
    }
}
