package com.kkaysheva.ituniver.data.network.directions;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.kkaysheva.ituniver.R;
import com.kkaysheva.ituniver.domain.mapper.Mapper;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * GoogleDirectionsServiceRetrofit
 *
 * @author Ksenya Kaysheva (murrcha@me.com)
 * @since 01.2019
 */
public final class GoogleDirectionsServiceRetrofit implements GoogleDirectionsService {

    private static final String TRAVEL_MODE = "walking";

    @NonNull
    private final GoogleDirectionsApi googleDirectionsApi;

    @NonNull
    private final Mapper<DirectionsResponse, List<LatLng>> mapper;

    @NonNull
    private final Context context;

    @Inject
    public GoogleDirectionsServiceRetrofit(@NonNull GoogleDirectionsApi googleDirectionsApi,
                                           @NonNull Mapper<DirectionsResponse, List<LatLng>> mapper,
                                           @NonNull Context context) {
        this.googleDirectionsApi = googleDirectionsApi;
        this.mapper = mapper;
        this.context = context;
    }

    @NonNull
    @Override
    public Single<List<LatLng>> loadDirections(@NonNull String origin, @NonNull String destination) {
        return googleDirectionsApi
                .getDirections(origin, destination, TRAVEL_MODE, context.getString(R.string.directions_api_key))
                .flatMap(directionsResponse -> Single.just(mapper.map(directionsResponse)));
    }
}
