package com.kkaysheva.ituniver.data.network.geocode;

import android.support.annotation.NonNull;

import com.kkaysheva.ituniver.domain.mapper.Mapper;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * GeoCodeServiceRetrofit
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public final class GeoCodeServiceRetrofit implements GeoCodeService {

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
    @Override
    public Single<String> loadGeoCode(@NonNull String latLng) {
        return geoCodeApi.loadAddress(latLng, FORMAT)
                .map(mapper::map);
    }
}
