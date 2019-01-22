package com.kkaysheva.ituniver.data.network.geocode;

import android.support.annotation.NonNull;

import io.reactivex.Single;

/**
 * GeoCodeService
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public interface GeoCodeService {

    @NonNull
    Single<String> loadGeoCode(@NonNull String latLng);
}
