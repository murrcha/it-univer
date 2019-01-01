package com.kkaysheva.ituniver.network;

import com.kkaysheva.ituniver.network.model.GeoCodeResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * GeoCodeApi
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public interface GeoCodeApi {

    String BASE_URL = "https://geocode-maps.yandex.ru/1.x/";

    @GET(".")
    Single<GeoCodeResponse> loadAddress(
            @Query("geocode") String latLng,
            @Query("format") String format);
}
