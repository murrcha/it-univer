package com.kkaysheva.ituniver.stubs.interactor;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.domain.map.MapInteractor;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * MapInteractorStub
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class MapInteractorStub implements MapInteractor {

    @NonNull
    @Override
    public Single<String> getAddress(@NonNull LatLng latLng) {
        return Single.fromCallable(() -> "address");
    }

    @NonNull
    @Override
    public Completable saveAddress(int contactId, @NonNull LatLng latLng, @NonNull String address) {
        return Completable.fromAction(Completable::complete);
    }

    @NonNull
    @Override
    public Maybe<LatLng> getLocationById(int contactId) {
        return Maybe.fromCallable(() -> new LatLng(53.198438, 56.843609));
    }

    @NonNull
    @Override
    public Single<List<LatLng>> getLocations() {
        return Single.fromCallable(() -> Arrays.asList(
                new LatLng(53.198438, 56.843609),
                new LatLng(53.198438, 56.843609)
        ));
    }

    @NonNull
    @Override
    public Single<List<LatLng>> getDirections(@NonNull LatLng origin, @NonNull LatLng destination) {
        return Single.fromCallable(() -> Arrays.asList(
                new LatLng(53.198438, 56.843609),
                new LatLng(53.198438, 56.843609)
        ));
    }

    @NonNull
    @Override
    public Maybe<Location> getDeviceLocation() {
        return Maybe.fromCallable(() -> new Location("fake"));
    }
}
