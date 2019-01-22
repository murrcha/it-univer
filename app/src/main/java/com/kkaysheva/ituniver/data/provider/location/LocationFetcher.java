package com.kkaysheva.ituniver.data.provider.location;

import android.annotation.SuppressLint;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;

import javax.inject.Inject;

import io.reactivex.Maybe;

/**
 * LocationFetcher
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class LocationFetcher {

    @NonNull
    private final FusedLocationProviderClient client;

    @Inject
    public LocationFetcher(@NonNull FusedLocationProviderClient client) {
        this.client = client;
    }

    @SuppressLint({"CheckResult", "MissingPermission"})
    public Maybe<Location> getDeviceLocation() {
        return Maybe.create(
                emitter -> {
                    try {
                        client.getLastLocation()
                                .addOnSuccessListener(location -> {
                                    if (location != null) {
                                        emitter.onSuccess(location);
                                    } else {
                                        emitter.onComplete();
                                    }
                                })
                                .addOnFailureListener(emitter::onError);
                    } catch (Throwable throwable) {
                        emitter.onError(throwable);
                    }
                });
    }
}
