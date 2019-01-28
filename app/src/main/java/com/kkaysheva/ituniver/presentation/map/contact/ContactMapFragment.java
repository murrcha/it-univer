package com.kkaysheva.ituniver.presentation.map.contact;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.di.map.MapComponent;
import com.kkaysheva.ituniver.presentation.app.ITUniverApplication;
import com.kkaysheva.ituniver.presentation.map.BaseMapFragment;

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
    private static final String CONTACT_ID = "contactId";

    @Inject
    Provider<ContactMapPresenter> presenterProvider;

    @InjectPresenter
    ContactMapPresenter presenter;

    private GoogleMap map;

    private int contactId;

    private Location lastKnownLocation;

    public static ContactMapFragment newInstance(int contactId) {
        Bundle args = new Bundle();
        args.putInt(CONTACT_ID, contactId);
        ContactMapFragment fragment = new ContactMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        ITUniverApplication ITUniverApplication = (ITUniverApplication) requireActivity().getApplication();
        MapComponent mapComponent = ITUniverApplication.getApplicationComponent()
                .plusMapComponent();
        mapComponent.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contactId = getArguments().getInt(CONTACT_ID);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar(view, R.id.map_toolbar, R.string.map_title);
    }

    @Override
    public void onDestroyView() {
        map = null;
        super.onDestroyView();
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
        presenter.configureMap(map);
        map.setOnMapClickListener(latLng -> {
            presenter.addMarker(latLng, map);
            presenter.getAddress(contactId, latLng);
        });
        Log.d(TAG, "onMapReady: ");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                presenter.configureMap(map);
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void configureMap(GoogleMap map) {
        try {
            if (hasLocationPermission()) {
                Log.d(TAG, "configureMap: has permissions");
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                map.getUiSettings().setZoomControlsEnabled(true);
                presenter.getDeviceLocation();
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
            presenter.getLocationById(contactId, map);
        } catch (SecurityException e) {
            Log.e(TAG, "configureMap: ", e);
        }
    }

    @Override
    public void showAddress(@NonNull String address) {
        Toast.makeText(requireContext(), address, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "showAddress: ");
    }

    @Override
    public void addMarker(@NonNull LatLng latLng, GoogleMap map) {
        map.clear();
        map.addMarker(new MarkerOptions().position(latLng));
        Log.d(TAG, "addMarker: ");
    }

    @Override
    public void showMarker(@NonNull LatLng latLng, GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
        Log.d(TAG, "showMarker: ");
    }

    @Override
    public void showError(@NonNull String error) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "showError: ");
    }

    @Override
    public void saveDeviceLocation(Location location) {
        lastKnownLocation = location;
        Log.d(TAG, "saveDeviceLocation: ");
    }

    @ProvidePresenter
    ContactMapPresenter providePresenter() {
        return presenterProvider.get();
    }
}
