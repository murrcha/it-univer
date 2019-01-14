package com.kkaysheva.ituniver.data.network.directions;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * GoogleDirectionsApi
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public interface GoogleDirectionsApi {

    String GOOGLE_BASE_URL = "https://maps.googleapis.com/";

    @GET("maps/api/directions/json")
    Single<DirectionsResponse> getDirections(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("mode") String travelMode,
            @Query("key") String key);
}
