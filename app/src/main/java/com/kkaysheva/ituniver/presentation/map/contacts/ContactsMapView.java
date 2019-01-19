package com.kkaysheva.ituniver.presentation.map.contacts;

import android.location.Location;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface ContactsMapView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void configureMap(GoogleMap map);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void addAllMarkers(@NonNull List<LatLng> locations, GoogleMap map);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showAllMarkers(@NonNull List<LatLng> locations, GoogleMap map);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showRoute(@NonNull List<LatLng> route, GoogleMap map);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(@NonNull String message);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void saveDeviceLocation(@NonNull Location location);
}
