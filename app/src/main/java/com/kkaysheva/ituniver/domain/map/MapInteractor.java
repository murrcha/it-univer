package com.kkaysheva.ituniver.domain.map;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * MapInteractor
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public interface MapInteractor {

    @NonNull
    Single<String> getAddress(@NonNull LatLng latLng);

    @NonNull
    Completable saveAddress(int contactId, @NonNull LatLng latLng, @NonNull String address);

    @NonNull
    Maybe<LatLng> getLocationById(int contactId);

    @NonNull
    Single<List<LatLng>> getLocations();

    @NonNull
    Single<List<LatLng>> getDirections(@NonNull LatLng origin, @NonNull LatLng destination);

    @NonNull
    Maybe<Location> getDeviceLocation();
}
