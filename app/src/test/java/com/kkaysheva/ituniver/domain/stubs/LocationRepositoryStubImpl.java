package com.kkaysheva.ituniver.domain.stubs;

import android.location.Location;
import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.LocationRepository;

import io.reactivex.Maybe;

/**
 * LocationRepositoryStubImpl
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class LocationRepositoryStubImpl implements LocationRepository {

    @NonNull
    @Override
    public Maybe<Location> getDeviceLocation() {
        return Maybe.fromCallable(() -> {
            String fakeProvider = "fake_provider";
            return new Location(fakeProvider);
        });
    }
}
