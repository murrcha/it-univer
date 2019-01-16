package com.kkaysheva.ituniver.presentation.map.contacts;

import android.content.Context;
import android.graphics.Color;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.di.map.MapComponent;
import com.kkaysheva.ituniver.presentation.app.AppDelegate;
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
    private static final int PADDING = 200;

    @Inject
    Provider<ContactsMapPresenter> presenterProvider;

    @InjectPresenter
    ContactsMapPresenter presenter;

    private GoogleMap map;

    private Polyline routeLine;

    private boolean firstMarkerSelected = false;
    private Marker origin;
    private Marker destination;

    private Location lastKnownLocation;

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
        map.setOnMarkerClickListener(marker -> {
            Log.d(TAG, "onMarkerClick: " + marker.getPosition().toString());
            if (firstMarkerSelected) {
                destination = marker;
                marker.setTitle("To");
                marker.showInfoWindow();
                if (routeLine != null) {
                    routeLine.remove();
                }
                presenter.getRoute(origin.getPosition(), destination.getPosition());
                firstMarkerSelected = false;
            } else {
                origin = marker;
                marker.setTitle("From");
                marker.showInfoWindow();
                firstMarkerSelected = true;
            }
            return true;
        });
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
                Log.d(TAG, "configureMap: has permissions");
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                requestPermissions();
                Log.d(TAG, "configureMap: request permissions");
            }
            presenter.getLocationForAll();
            Log.d(TAG, "configureMap: get all locations");
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
            }
            Log.d(TAG, "addAllMarkers: add markers ");
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
            Log.d(TAG, "showAllMarkers: show markers");
        } else {
            Log.d(TAG, "showAllMarkers: map is null");
        }
    }

    @Override
    public void showRoute(@NonNull List<LatLng> polyline) {
        if (map != null && !polyline.isEmpty()) {
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.addAll(polyline);
            polylineOptions.color(Color.BLUE);
            routeLine = map.addPolyline(polylineOptions);
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng location : polyline) {
                builder.include(new LatLng(location.latitude, location.longitude));
            }
            LatLngBounds bounds = builder.build();
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, PADDING));
            Log.d(TAG, "showRoute: show route");
        } else {
            Log.d(TAG, "showRoute: map is null");
        }
    }

    @Override
    public void showError(@NonNull String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveDeviceLocation(@NonNull Location location) {
        lastKnownLocation = location;
    }

    @ProvidePresenter
    ContactsMapPresenter providePresenter() {
        return presenterProvider.get();
    }
}
