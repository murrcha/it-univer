package com.kkaysheva.ituniver.network;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * GeoCodeServiceRetrofit
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class GeoCodeServiceRetrofit {

    private static final String FORMAT = "json";
    private static final int FIRST_ELEMENT = 0;

    @NonNull
    private GeoCodeApi geoCodeApi;

    @Inject
    public GeoCodeServiceRetrofit(@NonNull GeoCodeApi geoCodeApi) {
        this.geoCodeApi = geoCodeApi;
    }

    @NonNull
    public Single<String> loadGeoCode(String latLng) {
        return geoCodeApi.loadAddress(latLng, FORMAT)
                .flatMap(geoCodeResponse ->
                        Single.just(geoCodeResponse
                                .getResponse()
                                .getGeoObjectCollection()
                                .getFeatureMember()
                                .get(FIRST_ELEMENT)
                                .getGeoObject()
                                .getMetaDataProperty()
                                .getGeocoderMetaData()
                                .getAddressDetails()
                                .getCountry()
                                .getAddressLine()
                        ));
    }
}
