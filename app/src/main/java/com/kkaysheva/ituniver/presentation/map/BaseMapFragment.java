package com.kkaysheva.ituniver.presentation.map;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpDelegate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.kkaysheva.ituniver.R;

public abstract class BaseMapFragment extends Fragment implements OnMapReadyCallback {

    protected static final int REQUEST_LOCATION_PERMISSIONS = 1;
    protected static final double DEFAULT_LATITUDE = 55.753215;
    protected static final double DEFAULT_LONGITUDE = 37.622504;
    protected static final int DEFAULT_ZOOM = 17;
    protected static final int MIN_ZOOM = 11;

    private static final String TAG = BaseMapFragment.class.getSimpleName();

    private static final String KEY_MAP_VIEW_OUT_STATE = "map_view_state";
    private static final int REQUEST_ERROR = 0;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    private MapView mapView;

    private boolean isGoogleMapReady = false;
    private boolean isStateSaved = false;

    private MvpDelegate<? extends BaseMapFragment> mvpDelegate;

    protected abstract int fragmentLayout();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getMvpDelegate().onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        return inflater.inflate(fragmentLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = view.findViewById(R.id.map_view);

        if (savedInstanceState == null) {
            mapView.onCreate(null);
        } else {
            mapView.onCreate(savedInstanceState.getBundle(KEY_MAP_VIEW_OUT_STATE));
        }

        isGoogleMapReady = false;
        mapView.getMapAsync(this);
        Log.d(TAG, "onViewCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();

        isStateSaved = false;
        if (isGoogleMapReady) {
            getMvpDelegate().onAttach();
        }
        mapView.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();

        checkApiAvailability();

        isStateSaved = false;
        if (isGoogleMapReady) {
            getMvpDelegate().onAttach();
        }
        mapView.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        mapView.onPause();
        Log.d(TAG, "onPause: ");

        super.onPause();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        getMvpDelegate().onDetach();
        Log.d(TAG, "onStop: ");

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        getMvpDelegate().onDetach();
        getMvpDelegate().onDestroyView();
        Log.d(TAG, "onDestroyView: ");

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (requireActivity().isFinishing()) {
            getMvpDelegate().onDestroy();
            return;
        }

        if (isStateSaved) {
            isStateSaved = false;
            return;
        }

        boolean anyParentIsRemoving = false;

        Fragment parent = getParentFragment();
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving();
            parent = parent.getParentFragment();
        }

        if (isRemoving() || anyParentIsRemoving) {
            getMvpDelegate().onDestroy();
        }

        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        isStateSaved = true;

        getMvpDelegate().onSaveInstanceState(outState);
        getMvpDelegate().onDetach();

        Bundle bundle = new Bundle();
        mapView.onSaveInstanceState(bundle);
        outState.putBundle(KEY_MAP_VIEW_OUT_STATE, bundle);

        Log.d(TAG, "onSaveInstanceState: ");

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        Log.d(TAG, "onLowMemory: ");

        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        isGoogleMapReady = true;
        getMvpDelegate().onAttach();

        Log.d(TAG, "onMapReady: ");
    }

    public MvpDelegate getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new MvpDelegate<>(this);
        }
        return mvpDelegate;
    }

    protected void checkApiAvailability() {
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
        }
    }

    protected void requestPermissions() {
        requestPermissions(LOCATION_PERMISSIONS,
                REQUEST_LOCATION_PERMISSIONS);
    }

    protected boolean hasLocationPermission() {
        int result = ContextCompat.checkSelfPermission(requireActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}


