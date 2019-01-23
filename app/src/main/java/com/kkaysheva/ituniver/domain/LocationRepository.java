package com.kkaysheva.ituniver.domain;

import android.location.Location;
import android.support.annotation.NonNull;

import io.reactivex.Maybe;

/**
 * LocationRepository
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public interface LocationRepository {

    @NonNull
    Maybe<Location> getDeviceLocation();
}
