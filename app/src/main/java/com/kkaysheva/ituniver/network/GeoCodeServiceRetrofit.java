package com.kkaysheva.ituniver.network;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.mapper.Mapper;
import com.kkaysheva.ituniver.network.model.GeoCodeResponse;

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

    @NonNull
    private final GeoCodeApi geoCodeApi;

    @NonNull
    private final Mapper<GeoCodeResponse, String> mapper;

    @Inject
    public GeoCodeServiceRetrofit(@NonNull GeoCodeApi geoCodeApi,
                                  @NonNull Mapper<GeoCodeResponse, String> responseMapper) {
        this.geoCodeApi = geoCodeApi;
        this.mapper = responseMapper;
    }

    @NonNull
    public Single<String> loadGeoCode(@NonNull String latLng) {
        return geoCodeApi.loadAddress(latLng, FORMAT)
                .flatMap(geoCodeResponse -> Single.just(mapper.map(geoCodeResponse)));
    }
}
