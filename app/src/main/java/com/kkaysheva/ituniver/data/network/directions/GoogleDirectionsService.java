package com.kkaysheva.ituniver.data.network.directions;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Single;

/**
 * GoogleDirectionsService
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public interface GoogleDirectionsService {

    @NonNull
    Single<List<LatLng>> loadDirections(@NonNull LatLng origin, @NonNull LatLng destination);
}
