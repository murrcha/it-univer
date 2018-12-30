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
public class GeoCodeServiceRetrofit {

    @NonNull
    private GeoCodeApi geoCodeApi;

    @Inject
    public GeoCodeServiceRetrofit(@NonNull GeoCodeApi geoCodeApi) {
        this.geoCodeApi = geoCodeApi;
    }

    @NonNull
    public Single<String> loadGeoCode(String latLng) {
        return geoCodeApi.loadAddress(latLng)
                .flatMap(geoCodeResponse ->
                        Single.just(geoCodeResponse
                                .getGeoObjectCollection()
                                .getGeoObjects()
                                .get(0)
                                .getMetaDataProperty()
                                .getGeocoderMetaData()
                                .getAddressDetails()
                                .getCountry()
                                .getAddressLine()));
    }
}
