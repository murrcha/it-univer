package com.kkaysheva.ituniver.network;

import com.kkaysheva.ituniver.network.model.GeoCodeResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * GeoCodeApi
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 12.2018
 */
public interface GeoCodeApi {

    String BASE_URL = "https://geocode-maps.yandex.ru/";

    @GET("1.x/?geocode={latLng}&format=json")
    Single<GeoCodeResponse> loadAddress(@Path("latLng") String latLng);
}
