package com.kkaysheva.ituniver.presentation.map.contact;

import android.location.Location;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * ContactMapView
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public interface ContactMapView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void configureMap(GoogleMap map);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showAddress(@NonNull String address);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void addMarker(@NonNull LatLng latLng, GoogleMap map);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMarker(@NonNull LatLng latLng, GoogleMap map);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(@NonNull String error);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void saveDeviceLocation(Location location);
}
