package com.kkaysheva.ituniver.domain.stubs;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.data.network.geocode.GeoCodeService;

import io.reactivex.Single;

/**
 * GeoCodeServiceStubImpl
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class GeoCodeServiceStubImpl implements GeoCodeService {

    @NonNull
    @Override
    public Single<String> loadGeoCode(@NonNull LatLng latLng) {
        return Single.fromCallable(() -> "Izhevsk, Lenina, 1");
    }
}
