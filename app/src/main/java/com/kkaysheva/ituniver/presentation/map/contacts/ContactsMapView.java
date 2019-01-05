package com.kkaysheva.ituniver.presentation.map.contacts;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface ContactsMapView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void configureMap();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void addAllMarkers(@NonNull List<LatLng> locations);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showAllMarkers(@NonNull List<LatLng> locations);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showRoute(@NonNull List<LatLng> route);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(@NonNull String message);
}
