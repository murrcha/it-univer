package com.kkaysheva.ituniver.stubs.repository;

import android.location.Location;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.LocationRepository;

import io.reactivex.Maybe;

/**
 * LocationRepositoryStub
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class LocationRepositoryStub implements LocationRepository {

    @NonNull
    @Override
    public Maybe<Location> getDeviceLocation() {
        return Maybe.just(new Location("fake_provider"));
    }
}
