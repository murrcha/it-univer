package com.kkaysheva.ituniver.presentation.map;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

/**
 * ContactMapView
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public interface ContactMapView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void configureMap();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showAddress(@NonNull String address);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void addMarker(@NonNull LatLng latLng);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showMarker(@NonNull LatLng latLng);
}
