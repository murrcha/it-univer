package com.kkaysheva.ituniver.stubs.service;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.data.network.directions.GoogleDirectionsService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * DirectionsServiceStub
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class DirectionsServiceStub implements GoogleDirectionsService {

    @NonNull
    @Override
    public Single<List<LatLng>> loadDirections(@NonNull LatLng origin, @NonNull LatLng destination) {
        return Single.fromCallable(() -> {
            List<LatLng> route = new ArrayList<>();
            route.add(origin);
            route.add(destination);
            return route;
        });
    }
}
